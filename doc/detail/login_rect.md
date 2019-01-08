## 前言

本文旨在更新上一节设计中的不合理进行整改，关于登录的不足还有扩展功能的添加。

前文：[登录模块详细设计](Login-cn.md)

对于前文中的登录原核心思路是正确的，不过对于后期的部分业务功能的扩展是比较麻烦的，比如发送给其他人，发送离线消息，判断系统用户是否离线等。
的确，上文只是设计登录而言，但是我们还要考虑到后续功能接口的封装还有业务流程，所以一开始多磨是有好处的。

## 设计整改

这次的架构流程我们大致是这样的。

![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/inchat/login.png)

我们可能需要中心整理一下：

* 客户端是小程序、APP、Web端等的统称
* 客户端向服务器登录请求获取服务器分配的token
* 同时服务器将用户信息与token存入redis
* 客户端登录的同时还要登录InChat，发送json格式向websocket模拟登录

```
{
    type:login
    token:****
}
```

* InChat接收根据Type进行登录流程的执行，用户重写verifyToken方法去用户自定义的Redis中判断，返回真假
* 校验登录成功，InChat本地存储链接与用户信息，这里的用户信息应该是User唯一标识，而不能是Token

> 在我写到这里的时候，脑子中突然觉得说不定可以使用token，甚至想到更多，导致我没有继续写完本章
> 2018-11-27  end.