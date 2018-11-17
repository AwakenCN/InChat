# InChat 中文文档说明

> 目前正在撰写中。

## 简介

***(InChat)Iot Netty Chat***

> master项目目前在重新构建，运行可能报错，请转到demo分支运行项目。其为原核心项目

一个轻量级、高效的通信框架, 支持聊天和物联网, 您可以使用它来快速构建具有后台的聊天服务器, 并快速自定义自己的通信 api, 包括具有不同的通讯可以支持的协议。

## 学习资料

* [netty4通信原理](netty-study.md)

## 关于demo分支

原始项目核心演示, 您可以先运行了解, 模仿微聊天聊天应用程序, 逐步更新, 基于 springbot-web 套接字的一般框架, 结合 netty 聊天社交, 并记录聊天日志, 异步存储, 前端临时 sui mobile, 添加实现 tcp/后端通信端口 (mqtt 协议、实时和单片机等 tcp 硬件通信), 添加图片处理流、聊天实现文本和图片发送功能,api 调用 netty 长链接执行发送消息 (联机、用户列表的数量)

## demo分支演示效果图

![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/nettychat/001%20(5).png)
![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/nettychat/001%20(3).png)
![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/nettychat/001%20(4).png)
![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/nettychat/001%20(2).png)
![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/nettychat/001%20(1).png)
![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/nettychat/9.png)
![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/nettychat/10.png)
![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/nettychat/11.png)

## 关于im-api分支

腾讯 im (云通信) 后端仿真项目, 以 api 的形式对接, 如果有前端想要停靠可以运行此分支, 预计该分支最终将为一个单一的服务并发 300, 000个用户的 im 后台项目

## 关于paho-mqtt分支

基于 paho. js 和 java mqtt 客户端消息订阅和通信的小型程序端或移动 web 端, 小程序物联网演示目前支持 ws 格式

## 关于tcp-wechat分支

基于小程序端与单片机等硬件 tcp/ip 的主要通信, 将物联网中心作为中转, 本演示将充分实现具体功能

## 联系

QQ群：628793702