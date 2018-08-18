# SBToNettyChat（当前版本1.2.0）

## 简介
基于SpringBoot-WebSocket通用框架,结合Netty进行聊天社交，并记录聊天日志，异步存储
 
## 特点
异步处理，netty实时消息通信，JPA，WebSocket，数据库操作记录，聊天历史等

## 功能

    实时聊天
    异步CRUD处理消息日志
    获取聊天历史
    下版（1.3.0）：用户登录

## 相关项目

你可能还想了解的项目：

* ssmnetty： https://github.com/UncleCatMySelf/ssmnetty

基于SSM与Netty结合实现软硬件通信（TCP）、API向指定链接发送消息

* SBToNettyChat： https://github.com/UncleCatMySelf/SBToNettyChat

基于SpringBoot-WebSocket通用框架,结合Netty进行聊天社交，并记录聊天日志，异步数据库存储，记录聊天历史等

* NettyPremission： https://github.com/UncleCatMySelf/NettyPremission

运用Netty为主进行权限消息分发的组件，两种权限机制（Circle圈子模式，同等级的一个社交圈、Grade等级模式，每一个等级可以消费到比自己低等级的消息）

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

## 配置

    application.yml 数据库配置
    http://localhost:8080/chat/netty 启动访问路径

## 效果图 

![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/nettychat/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20180818143835.png)
![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/nettychat/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20180818143838.png)

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
