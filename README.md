# SBToNettyChat（当前版本1.5.2）

## 简介
仿微信聊天应用，一步一步更新，基于SpringBoot-WebSocket通用框架,结合Netty进行聊天社交，并记录聊天日志，
异步存储，前端暂用SUI Mobile,添加实现TCP/IP后端通信端口（可实时与单片机等TCP硬件通信）、加入图片处理流，
聊天实现文字与图片发送功能
 
## 特点
异步处理，netty实时消息通信，JPA，WebSocket，数据库操作记录，聊天历史等，1.2（修复bug），
1.3（用户登录、防止二次登录）,1.4.1(前端模块确定SUI Mobile，仿微信样式)，
1.5.2（TCP/IP软硬件通信-单片机等应用的TCP通信，Netty处理二进制图片发送聊天功能）

## 基本架构图

![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/nettychat/ggg1.png)

## 功能

    实时聊天
    异步CRUD处理消息日志
    获取聊天历史
    用户登录、记录登录用户聊天历史
    防止二次登录
    SUI Mobile仿微信样式
    TCP/IP软硬件通信（8092）
    图片发送聊天功能
    下版（1.6.0）：好友功能等

## 项目思路

本次我选择SpringBoot2.0.2还有Netty4.1.28这两个版本来结合使用。

SpringBoot集成了WebSocket所以开发通信流比较简单，Netty是一个多线程可高并发的框架，使用netty可以减少很多对于消息流的安全等处理。
那么聊天系统最重要的就是消息的实时推送，所以不要在消息的处理中做太多操作，可是如果要记录历史消息，就一定涉及数据库的CRUD操作，需要怎么处理呢？

我一开始想到了定时器，我先注入一个临时缓存，存储每个用户的消息（在Netty消费信息的时候），然后每天固定的时间去跑定时器（对缓存数据进行数据库存储，清空临时缓存），可是如果我的定时时间是晚上12点，那么用户早上聊天的信息，中午重新登录的时候还是没有的。
于是我又想到了异步任务，但是要什么时候去执行呢？

因为每次要最新的聊天历史，那么我暂时想到的是每个用户离开聊天室的时候，针对于它们的聊天记录进行异步执行。
好在SpringBoot对于异步任务的兼容也很好处理。

## 版本迭代介绍

* 1.2.0版本

修复聊天记录功能，实现重复信息录入，完善前端页面，回车监听等

* 1.3.0版本

用户注册登录功能，系统聊天绑定用户，禁止二次登录等，前端页面大改

* 1.4.1版本

本人主导SUI Mobile构建仿微信样式页面版，使用时开F12手机界面

* 1.5.2版本

TCP/IP软硬件通信-单片机等应用的TCP通信，Netty处理二进制图片发送聊天功能

## 配置

    application.yml 数据库配置、Netty参数配置
    TCP需先去com.myself.nettychat.tcptest包下执行CRC16myself获取发送数据，
    再执行TCPTestClient发送数据，请勿随意更改发送格式（通信协议来的）
    http://localhost:8080/susu/admin/loginsui 启动访问路径

## 效果图 

![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/nettychat/001%20(5).png)
![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/nettychat/001%20(3).png)
![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/nettychat/001%20(4).png)
![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/nettychat/001%20(2).png)
![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/nettychat/001%20(1).png)
![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/nettychat/9.png)
![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/nettychat/10.png)
![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/nettychat/11.png)

## 预留BUG

```
io.netty.handler.codec.CorruptedFrameException: Max frame length of 65536 has been exceeded.
图片过大，需要在前端做图片上传压缩

Uncaught TypeError: msg.substring is not a function at WebSocket.socket.onmessage (newChat.js:38)
前端代码的一点问题，不影响项目正常运行

java.io.IOException: 远程主机强迫关闭了一个现有的连接。
TCP客户端连接主动关闭，不影响，良性报错
```

## 下载地址

下载地址：https://github.com/UncleCatMySelf/SBToNettyChat/releases

## 交流与提问

提问与Bug上报：https://github.com/UncleCatMySelf/SBToNettyChat/issues

QQ群：628793702（仅供交流，不提供问题解答）

## 关于作者

博客园：https://www.cnblogs.com/UncleCatMySelf/

个人公众号：UncleCatMySelf

![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/%E5%85%AC%E4%BC%97%E5%8F%B7.png)

个人开发，感谢支持！

![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/%E4%BB%98%E6%AC%BE.png)
