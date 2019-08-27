## SslUtil

包路径：com.github.unclecatmyself.common.utils

类说明：提供创建javax.net.ssl.SSLContext的静态方法，方便用户自己创建加密通道

### 属性

- javax.net.ssl.SSLContext 

在netty的加密中也有使用到io.netty.handler.ssl.SslContext，netty有自己的SslContextBuilder构建器，不过本项目使用了JDK原生的

> 补充：io.netty.handler.ssl.SslContext——公共抽象类，安全套接字协议实现充当工厂SSLEngine和SslHandler。在内部，它通过JDK SSLContext或OpenSSL 实现SSL_CTX，还有关于它的使用方式，如果你需要ssl加密的话

### 方法

- createSSLContext(String type ,String path ,String password) throws Exception

> 返回 javax.net.ssl.SSLContext

说明：基于类型type（通常是jks），路径path（jks文件的路径），密码password（生成jks文件的密码），生成返回一个SslContext

### 扩展

这一类方法可以直接引用到任何常规需要ssl加密的项目中，你也可以尝试使用netty的加密工具尝试

