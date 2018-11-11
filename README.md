# tcp-wechat

## 项目介绍

针对小程序与单片机硬件执行Iot物联网通讯（TCP/IP）的一套完整Demo。

## 项目配置

> application.xml 

配置tcp端口：8092

> com.myself.nettychat.tcptest.TCPTestClient

配置连接IP与端口，修改代码中的参数

## 启动流程

1、启动项目，tcp监听成功

2、运行com.myself.nettychat.tcptest.TCPTestClient （记得先改ip或端口，如果你有修改的话）

3、运行PostMan，请求下方的API 进行通信测试

## Demo场景

小程序端选购售货机中的商品，点击购买（小程序API向Iot中心发送对应商品的开锁信息），Iot中心中转开锁信息给单片机，单片机接收信息打开对应的锁。

通信机制：帧头+ID+数据类型+24把锁状态+crc校验+帧尾（可以按照需求进行定制）

> com.myself.nettychat.config.TCPServerHandler (通信接收的处理类)


## API（小程序调用接口）

> http://localhost:8080/susu/back/get_channel_size  GET

请求Iot中心，获取当前连接存活状态下的链接实例

```
{
    "code": 200,
    "msg": "成功",
    "data": 1
}
```

> http://localhost:8080/susu/back/get_channel_id_list  GET

请求Iot中心，当前存活状态下的链接Id列表

```
{
    "code": 200,
    "msg": "成功",
    "data": [
        "F5690137563CC8"
    ]
}
```

> http://localhost:8080/susu/back/send_to_channel  POST

参数
* channelId //第二个API获取到的链接Id 
* lock //将要打开的第几把锁   1-24（看单片机接入的锁的数量）

```
{
    "code": 200,
    "msg": "成功",
    "data": "【发送成功】"
}
```

