# 第二章 Netty基本组件

## 一个简单的socket例子

![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/stduy/100.png)

### ServerBoot启动类

```java
package com.example.demo;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 23:00 2018\12\4 0004
 */
public class ServerBoot {

    private static final int PORT = 8000;

    public static void main(String[] args){
        Server server = new Server(PORT);
        server.start();
    }

}
```

### Server监听端口服务类

```java
package com.example.demo;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 22:46 2018\12\4 0004
 */
@Slf4j
public class Server {

    private ServerSocket serverSocket;

    public Server(int port){
        try {
            this.serverSocket = new ServerSocket(port);
            log.info("服务端启动成功，端口：" + port);
        }catch (IOException e){
            log.error("服务端启动失败");
        }
    }

    public void start(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                doStart();
            }
        }).start();
    }

    private void doStart(){
        while (true){
            try {
                //阻塞方法
                Socket client = serverSocket.accept();
                new ClientHandler(client).start();
            }catch (IOException e){
                log.error("服务端异常");
            }
        }
    }
}
```

### ClientHandler客户端接入监听类

```java
package com.example.demo;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 22:50 2018\12\4 0004
 */
@Slf4j
public class ClientHandler {

    public static final int MAX_DATA_LEN = 1024;
    private final Socket socket;

    public ClientHandler(Socket socket){
        this.socket = socket;
    }

    public void start(){
      log.info("新客户端接入");
      new Thread(new Runnable() {
          @Override
          public void run() {
            doStart();
          }
      }).start();
    }

    private void doStart(){
        try {
            InputStream inputStream = socket.getInputStream();
            while (true){
                byte[] data = new byte[MAX_DATA_LEN];
                int len;
                while((len = inputStream.read(data)) != -1){
                    String message = new String(data,0,len);
                    log.info("客户端传来消息：" + message);
                    socket.getOutputStream().write(data);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
```


### Client客户端启动类

```java
package com.example.demo;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 23:01 2018\12\4 0004
 */
@Slf4j
public class Client {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8000;
    private static final int SLEEP_TIME = 5000;

    public static void main(String[] args) throws IOException{
        final Socket socket = new Socket(HOST,PORT);

        new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("客户端启动成功！");
                while (true){
                    try {
                        String message = "hello myself";
                        log.info("客户端发送数据：" + message);
                        socket.getOutputStream().write(message.getBytes());
                    }catch (Exception e){
                        log.info("写数据出错！");
                    }
                    sleep();
                }
            }
        }).start();
    }

    private static void sleep(){
        try {
            Thread.sleep(SLEEP_TIME);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}
```