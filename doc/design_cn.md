# 项目设计思路

## 关于登录

关于InChat统一登录的接口设计，设计针对小程序、APP、Web端的登录作用，所以将作为token的形式登录InChat的WebSocket长连接，用户服务器做sso的认证登录后得到token后直接发送login信息到InChat，用户服务器需要重写InChat中的verifyToken方法校验自己的的Token信息是否有效，正常则启动长连接。考虑到token失效问题，WebSocket长连接的登录仅做初次登录，接下来考虑以心跳形式保持链接状态（pingpong），使用token认证是为保护InChat链接的常规化（目前暂时这样设计后面根据使用情况更改设计）

![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/design/%E6%9C%AA%E5%91%BD%E5%90%8D%E6%96%87%E4%BB%B6(12).png)

* 【2018-11-22】 编码实现和[详细实现文档](detail/Login-cn.md)