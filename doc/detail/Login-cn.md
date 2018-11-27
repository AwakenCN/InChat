## 只给你最值得的信息

小弟正在做的一个开源IM项目，目标是实现一个轻量级、高效率的支持聊天与物联网的通讯框架。昨天刚刚出的设计稿并再今天做了实现。

项目是基于Netty的二次开发，关于Netty我这里就不再介绍了，懂的人自然都懂。我的预算是做一个所有企业或组织可以引用的Maven项目，并且是基本上开箱即用，简单实现对应的配置与重写方法就可以搭建自己的IM项目（某Q、某信的效果）。

本文着重介绍的是登录接口的设计与实现。

## 设计信息

关于InChat统一登录的接口设计，设计针对小程序、APP、Web端的登录作用，所以将作为token的形式登录InChat的WebSocket长连接，用户服务器做sso的认证登录后得到token后直接发送login信息到InChat，用户服务器需要重写InChat中的verifyToken方法校验自己的的Token信息是否有效，正常则启动长连接。考虑到token失效问题，WebSocket长连接的登录仅做初次登录，接下来考虑以心跳形式保持链接状态（pingpong），使用token认证是为保护InChat链接的常规化（目前暂时这样设计后面根据使用情况更改设计）

![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/inchat/%E6%9C%AA%E5%91%BD%E5%90%8D%E6%96%87%E4%BB%B6(13).png)

由于目前大部分的Web项目或基于IM的项目登录可能存在多端的单点登录，所以我选用了Token的形式，为什么登录后还需要用token再来websocket这边校验一次呢？

因为你登录的是web应用程序端的，而websocket而言，只要别人知道你的地址，那么就可以链接上，我们不希望存在过多的死链接（无效链接），所以我们需要将token再次发给InChat登录，由InChat来检验是否是合法登录链接，如果无效则关闭链接。

## 代码实现

由于是想要做给别人用的，那么我们自己本身就要封装的好一点，对于配置我选了足够多的类型。

![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/inchat/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20181122171455.png)

对于netty监听与初始化，我使用的方式是扫描与AspectJ，你可以在项目的auto包中看到扫描启动，这种启动思路我也是参考了其他的开源项目（具体忘记了地址）。

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


在上图中存在一个DefaultWebSocketHandler，这个是默认的netty启动处理。

当然在执行它之前，还需要执行到一个抽象的类WebSocketHandler。

它将会为我做一些基本的功能操作。

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

对于登录接口，他是基于WebSocket的，且是TextWebSocketFrame类型的，WebSocketFrame是后期的图片聊天功能，所以我们的DefaultWebSocketHandler暂时只需要实现textdoMessage。

对于websocket的传输我们推荐使用json形式，这对于前后端都是由好处的。

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

由上面的代码，你也许才想到了前端登录的大致json内容，没错是这样的。

```
{
    type: "login",
    token: value
}
```

ServerWebSocketHandlerService是一个自己定义的后端WebSocket存在的接口实现服务，我们现在使用到他的登录接口，让我们来看看他的登录实现方法。

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

由以上可看出，我们会将token和inChatVerifyService.verifyToken做校验，这是一个接口，我并没有写实现，因为这个是用户的事情了，它需要实现并重写我的verifyToken方法，并返回给我一个值，如下是我测试的时候写的模拟实现。

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

我仅仅做了普通的校验，对于用户可以注入RedisTemplate然后进行校验等工作。到此我们的登录接口就实现好了！

## 看看效果

![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/inchat/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20181122171241.png)

![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/inchat/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20181122171245.png)

![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/inchat/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20181122171247.png)


> 2017-11-25 end.