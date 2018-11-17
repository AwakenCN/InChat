# InChat-Demo（1.6.0 Version）

### [Developer Chat](https://gitter.im/In-Chat/Lobby)

## 简介

***(InChat)Iot Netty Chat***

Imitation WeChat chat app, one step update, based on Springboot-websocket General framework, combined with Netty for chat social, and record chat logs,
Asynchronous storage, front-end provisional sui Mobile, add TCP/IP back-end communication ports (MQTT protocol, real-time communication with single-chip computer and other TCP hardware), add picture processing flow, Chat implementation text and picture sending function, API call Netty long link execution Send message (number of online, user list)

## 中文文档

* [中文文档](doc/doc.md)

## Basic architecture Diagram(1.5.2 Version)

![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/nettychat/ggg1.png)

## Configuration

>application.yml Database configuration, netty parameter configuration

>TCP must first go to the Com.myself.nettychat.tcptest package to perform crc16myself get send data

>Do tcptestclient send data again, do not change the sending format at will (communication protocol)

>http://localhost:8080/susu/admin/loginsui Start Access Path

>http://localhost:8080/susu/swagger-ui.html View API Documentation

## Function

>Live Chat

>Asynchronous CRUD handles message logs

>Get Chat History

>User login, record login user chat history

>Prevent two logins

>SUI Mobile imitation WeChat style

>TCP/IP hardware and software communication (8092)

>IoT communications under the MQTT Protocol (8094)

>Picture Sending chat function

>API call Netty Long link execution Send message (number of online users, list of users)

>Next edition (1.7.0): Friends features, etc.

## Version Iteration Introduction(Tags)

* 1.0.0 Version

User login, Chat history, random username, asynchronous data write

* 1.2.0 Version

Repair Chat recording function, realize duplicate information entry, perfect front-end page, return monitor, etc.

* 1.3.0 Version

User Registration login function, system chat binding users, prohibition of two logins, etc., front-end page Large change

* 1.4.1 Version

I lead Sui mobile to build imitation WeChat style page version, when using the F12 mobile phone interface

* 1.5.2 Version

TCP/IP hardware and software communication-single-chip computer and other applications of TCP communication, Netty processing binary pictures send chat function

* 1.5.8 Version

MQTT protocol hardware and software communication, IoT Internet of Things

* 1.6.0 Version

API call Netty Long link execution Send message (number of online, user list)

## 效果图 

![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/nettychat/001%20(5).png)
![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/nettychat/001%20(3).png)
![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/nettychat/001%20(4).png)
![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/nettychat/001%20(2).png)
![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/nettychat/001%20(1).png)
![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/nettychat/9.png)
![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/nettychat/10.png)
![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/nettychat/11.png)

## Reserved bugs

```
io.netty.handler.codec.CorruptedFrameException: Max frame length of 65536 has been exceeded.
Picture is too large, need to do picture upload compression at the front end

Uncaught TypeError: msg.substring is not a function at WebSocket.socket.onmessage (newChat.js:38)
A bit of a problem with front-end code that doesn't affect the normal operation of the project

java.io.IOException: 远程主机强迫关闭了一个现有的连接。
TCP Client Connection active shutdown, no impact, benign error
```

## Download Address

https://github.com/UncleCatMySelf/SBToNettyChat/releases

## Communication & questions

https://github.com/UncleCatMySelf/SBToNettyChat/issues

QQ Group ：628793702

## About the author

![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/%E5%85%AC%E4%BC%97%E5%8F%B7.png)
