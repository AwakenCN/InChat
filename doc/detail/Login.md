## Just give you the most worthwhile information.

Little brother is working on an open source IM project with the goal of achieving a lightweight, efficient communication framework that supports chat and the internet of things.

The design manuscript just produced yesterday has been realized today. The project is based on Netty two development, about Netty I will not introduce here, understand the people naturally understand.

My budget is to do a MAVEN project that all enterprises or organizations can reference, and is basically out-of-the-box, simply implement the corresponding configuration and override method can build their own IM project (a Q, the effect of a letter). This paper mainly introduces the design and implementation of the login interface.

## Design Info

About Inchat Unified Login interface design, design for small programs, apps, web-side login role, so will be token as the form of login Inchat websocket long connection, User server to do SSO authentication login after getting token to send login information directly to Inchat, the user server needs to override the Verifytoken method in Inchat to verify whether their token information is valid, normal start long connection.
In view of the token failure problem, WebSocket long connected login only for the first time login, then consider maintaining the link state (pingpong) in the form of heartbeat, the use of token authentication is to protect Inchat link routine (currently temporarily such design after changing the design according to usage)
![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/inchat/%E6%9C%AA%E5%91%BD%E5%90%8D%E6%96%87%E4%BB%B6(13).png)

Because most of the current Web projects or IM-based project logins may have multi-terminal single sign-on, so I chose the form of token, why do you need to use token to websocket this side of the verification once? Because you are logged on to the Web application side, and WebSocket, as long as others know your address, then you can link on, we do not want to have too many dead links (invalid links), so we need to send token again to Inchat login,
It is up to inchat to check if it is a legitimate login link and close the link if it is not valid.

## Code implementation

Because we want to do it for others, then we ourselves have to encapsulate a little better, for the configuration I chose enough types.


![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/inchat/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20181122171455.png)

For Netty listening and initialization, I use the way to scan and ASPECTJ, you can see the scan start in the project's auto package, this startup idea I also refer to other open source projects (specifically forgot the address).

```java
@Bean
@ConditionalOnMissingBean(name = "sacnScheduled")
public ScanRunnable initRunable(@Autowired  InitNetty serverBean){
    long time =(serverBean==null || serverBean.getPeriod()<5)?10:serverBean.getPeriod();
    ScanRunnable sacnScheduled = new SacnScheduled(time);
    Thread scanRunnable = new Thread(sacnScheduled);
    scanRunnable.setDaemon(true);
    scanRunnable.start();
    return sacnScheduled;
}


@Bean(initMethod = "open", destroyMethod = "close")
@ConditionalOnMissingBean
public InitServer initServer(InitNetty serverBean){
    if(!ObjectUtils.allNotNull(serverBean.getWebport(),serverBean.getServerName())){
        throw  new NullPointerException("not set port");
    }
    if(serverBean.getBacklog()<1){
        serverBean.setBacklog(_BLACKLOG);
    }
    if(serverBean.getBossThread()<1){
        serverBean.setBossThread(CPU);
    }
    if(serverBean.getInitalDelay()<0){
        serverBean.setInitalDelay(SEDU_DAY);
    }
    if(serverBean.getPeriod()<1){
        serverBean.setPeriod(SEDU_DAY);
    }
    if(serverBean.getHeart()<1){
        serverBean.setHeart(TIMEOUT);
    }
    if(serverBean.getRevbuf()<1){
        serverBean.setRevbuf(BUF_SIZE);
    }
    if(serverBean.getWorkerThread()<1){
        serverBean.setWorkerThread(CPU*2);
    }
    return new InitServer(serverBean);
}
```


There is a defaultwebsockethandler in the figure above, which is the default Netty startup processing.

Of course, before executing it, you also need to perform to an abstract class Websockethandler. It will do some basic functional operations for me.

```java
@Slf4j
public abstract class WebSocketHandler extends SimpleChannelInboundHandler<Object> {

    WebSocketHandlerApi webSocketHandlerApi;

    public WebSocketHandler(WebSocketHandlerApi webSocketHandlerApi){
        this.webSocketHandlerApi = webSocketHandlerApi;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof TextWebSocketFrame){
            textdoMessage(ctx,(TextWebSocketFrame)msg);
        }else if (msg instanceof WebSocketFrame){
            webdoMessage(ctx,(WebSocketFrame)msg);
        }
    }

    protected abstract void webdoMessage(ChannelHandlerContext ctx, WebSocketFrame msg);

    protected abstract void textdoMessage(ChannelHandlerContext ctx, TextWebSocketFrame msg);

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("【DefaultWebSocketHandler：channelInactive】"+ctx.channel().localAddress().toString()+"关闭成功");
        webSocketHandlerApi.close(ctx.channel());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            webSocketHandlerApi.doTimeOut(ctx.channel(),(IdleStateEvent)evt);
        }
        super.userEventTriggered(ctx, evt);
    }
}
```

For the login interface, he is based on WebSocket, and is the Textwebsocketframe type, Websocketframe is the late image chat function,

So our Defaultwebsockethandler only need to implement textdomessage for the time being.
For the transmission of WebSocket we recommend the use of JSON form, which is beneficial for both the front and back ends.

```java
    @Override
    protected void textdoMessage(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        Channel channel = ctx.channel();
        ServerWebSocketHandlerService serverWebSocketHandlerService;
        if (webSocketHandlerApi instanceof ServerWebSocketHandlerService){
            serverWebSocketHandlerService = (ServerWebSocketHandlerService)webSocketHandlerApi;
        }else{
            throw new NoFindHandlerException("Server Handler 不匹配");
        }
        Map<String,String> maps = (Map) JSON.parse(msg.text());
        switch (maps.get("type")){
            case "login":
                serverWebSocketHandlerService.login(channel,msg);
                break;
            default:
                break;
        }
    }
```

By the above code, you may have thought of the approximate JSON content of the front-end login, that's right.


```
{
    type: "login",
    token: value
}
```

Serverwebsockethandlerservice is a self-defined interface implementation service for the back-end websocket, and we now use it to his login interface, let's take a look at his login implementation method.


```java
    
    @Autowired
    InChatVerifyService inChatVerifyService;
    
    @Override
    public boolean login(Channel channel, TextWebSocketFrame textWebSocketFrame) {
        //校验规则，自定义校验规则
        Map<String,String> maps = (Map<String, String>) JSON.parse(textWebSocketFrame.text());
        System.out.println("login-"+textWebSocketFrame.text());
        String token = maps.get("token");
        Gson gson = new Gson();
        Map<String,String> backMap = new HashMap<>();
        if (inChatVerifyService.verifyToken(token)){
            backMap.put("type","login");
            backMap.put("success","true");
            channel.writeAndFlush(new TextWebSocketFrame(gson.toJson(backMap)));
            return true;
        }
        backMap.put("type","login");
        backMap.put("success","false");
        channel.writeAndFlush(new TextWebSocketFrame(gson.toJson(backMap)));
        close(channel);
        return false;
    }
```

As can be seen from the above, 
We will token and Inchatverifyservice.verifytoken do checksum, this is an interface, I did not write the implementation, because this is the user's thing, it needs to implement and rewrite my Verifytoken method, and return to me a value, the following is my test when writing the simulation implementation.

```java
/**
 * 不属于项目代码
 * Created by MySelf on 2018/11/22.
 */
@Service
public class InChatVerifyServiceImpl implements InChatVerifyService {

    @Override
    public boolean verifyToken(String token) {
        //与Redis中的Token做比较，请用户自己实现,查找是否存在该Token值
        System.out.println("verify---"+token);
        if ("3333".equals(token)){
            return true;
        }
        return false;
    }
}
```

I just did a normal checksum, for the user can inject redistemplate and then do the checksum work.
To this, our login interface will be achieved!

## Look at the effect.

![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/inchat/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20181122171241.png)

![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/inchat/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20181122171245.png)

![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/inchat/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20181122171247.png)
