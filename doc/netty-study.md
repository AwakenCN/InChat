## netty4通信步骤，原理

> 感谢 贡献者：[Noseparte](https://github.com/noseparte)

### 服务端依次发生的步骤
    
* 1.建立服务端监听套接字ServerSocketChannel，以及对应的管道pipeline；
* 2.启动boss线程，将ServerSocketChannel注册到boss线程持有的selector中，并将注册返回的selectionKey赋值给ServerSocketChannel关联的selectionKey变量；
* 3.在ServerSocketChannel对应的管道中触发channelRegistered事件；
* 4.绑定IP和端口
* 5.触发channelActive事件，并将ServerSocketChannel关联的selectionKey的OP_ACCEPT位置为1。
* 6.客户端发起connect请求后，boss线程正在运行的select循环检测到了该ServerSocketChannel的ACCEPT事件就绪，则通过accept系统调用建立一个已连接套接字SocketChannel，并为其创建对应的管道；
* 7.在服务端监听套接字对应的管道中触发channelRead事件；
* 8.channelRead事件由ServerBootstrapAcceptor的channelRead方法响应：为已连接套接字对应的管道加入ChannelInitializer处理器；
启动一个worker线程，并将已连接套接字的注册任务加入到worker线程的任务队列中；
* 9.worker线程执行已连接套接字的注册任务：将已连接套接字注册到worker线程持有的selector中，并将注册返回的selectionKey赋值给已连接套接字关联的selectionKey变量；
在已连接套接字对应的管道中触发channelRegistered事件；channelRegistered事件由ChannelInitializer的channelRegistered方法响应：
将自定义的处理器（譬如EchoServerHandler）加入到已连接套接字对应的管道中；在已连接套接字对应的管道中触发channelActive事件；c
hannelActive事件由已连接套接字对应的管道中的inbound处理器的channelActive方法响应；将已连接套接字关联的selectionKey的OP_READ位置为1；至此，worker线程关联的selector就开始监听已连接套接字的READ事件了。
* 10.在worker线程运行的同时，Boss线程接着在服务端监听套接字对应的管道中触发channelReadComplete事件。
* 11.客户端向服务端发送消息后，worker线程正在运行的selector循环会检测到已连接套接字的READ事件就绪。则通过read系统调用将消息从套接字的接受缓冲区中读到AdaptiveRecvByteBufAllocator（可以自适应调整分配的缓存的大小）分配的缓存中；
* 12.在已连接套接字对应的管道中触发channelRead事件；
* 13.channelRead事件由EchoServerHandler处理器的channelRead方法响应：执行write操作将消息存储到ChannelOutboundBuffer中；
* 14.在已连接套接字对应的管道中触发ChannelReadComplete事件；
* 15.ChannelReadComplete事件由EchoServerHandler处理器的channelReadComplete方法响应：执行flush操作将消息从ChannelOutboundBuffer中flush到套接字的发送缓冲区中；
    
    
    
### 客户端依次发生的步骤

* 1.建立套接字SocketChannel，以及对应的管道pipeline；
* 2.启动客户端线程，将SocketChannel注册到客户端线程持有的selector中，并将注册返回的selectionKey赋值给SocketChannel关联的selectionKey变量；
* 3.触发channelRegistered事件；
* 4.channelRegistered事件由ChannelInitializer的channelRegistered方法响应：将客户端自定义的处理器（譬如EchoClientHandler）按顺序加入到管道中；
* 5.向服务端发起connect请求，并将SocketChannel关联的selectionKey的OP_CONNECT位置为1；
* 6.开始三次握手，客户端线程正在运行的select循环检测到了该SocketChannel的CONNECT事件就绪，则将关联的selectionKey的OP_CONNECT位置为0，再通过调用finishConnect完成连接的建立；
* 7.触发channelActive事件；
* 8.channelActive事件由EchoClientHandler的channelActive方法响应，通过调用ctx.writeAndFlush方法将消息发往服务端；
* 9.首先将消息存储到ChannelOutboundBuffer中；（如果ChannelOutboundBuffer存储的所有未flush的消息的大小超过高水位线writeBufferHighWaterMark（默认值为64 * 1024），则会触发ChannelWritabilityChanged事件）
* 10.然后将消息从ChannelOutboundBuffer中flush到套接字的发送缓冲区中；（如果ChannelOutboundBuffer存储的所有未flush的消息的大小小于低水位线，则会触发ChannelWritabilityChanged事件） 