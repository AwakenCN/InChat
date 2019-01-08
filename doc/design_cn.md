# 项目设计思路

## 设计原则

AOP、DI为主，基于Spring Boot快速搭建，尽量减少用户的二次搭配开发

## 关于登录

关于InChat统一登录的接口设计，设计针对小程序、APP、Web端的登录作用，所以将作为token的形式登录InChat的WebSocket长连接，用户服务器做sso的认证登录后得到token后直接发送login信息到InChat，用户服务器需要重写InChat中的verifyToken方法校验自己的的Token信息是否有效，正常则启动长连接。考虑到token失效问题，WebSocket长连接的登录仅做初次登录，接下来考虑以心跳形式保持链接状态（pingpong），使用token认证是为保护InChat链接的常规化（目前暂时这样设计后面根据使用情况更改设计）

![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/design/%E6%9C%AA%E5%91%BD%E5%90%8D%E6%96%87%E4%BB%B6(12).png)

* 【2018-11-22】 编码实现和[详细实现文档](detail/Login-cn.md)

## 离线消息模板

一版采用RabbitMQ----移除

## 整体设计

![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/inchat/%E6%9C%AA%E5%91%BD%E5%90%8D%E6%96%87%E4%BB%B6(19).png)

1、移除原本Token概念，用户均以唯一标识登录并存储在线键值对于系统本地Cache。（登录存储、退出移除）
2、DefaultWebSocketHandler，是框架默认的处理，数据均已json格式，拆分业务点
3、由InChat内部的服务（WebSocketHandlerService  ）进行具体的业务实现并回写
4、对于数据的存储与写入，默认是异步写入数据，用户可以重写InChatToDataBaseService，获取实时数据
5、在高并发情况下，我们不推荐异步处理数据，可以通过配置化启动mq（目前支持RabbitMq），框架自动注入对应Bean，并由MQ来执行数据写入
6、关于数据库连接池，我们不会绑定用户使用，而是支持用户自定义。

