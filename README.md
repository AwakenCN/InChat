# tcp-wechat

## Summery

A complete demo of IoT IoT Communications (TCP/IP) for small programs and single-chip hardware.

## 中文说明

* [中文说明](doc/doc.md)

## Project configuration

> The following instructions are for tag V0.9.1

> application.xml 

Configuring TCP Ports：8092

> com.myself.nettychat.tcptest.TCPTestClient

Configure connection IP and ports to modify parameters in code

## Startup Process

1、Startup Project, TCP listening succeeded

2、Run Com.myself.nettychat.tcptest.TCPTestClient (Remember to change the IP or port first, if you have any changes)

3、Run postman to request communication testing at the API below

## Demo Scene

Small terminal purchase of goods in the vending machine, click to Buy (small program API to the IoT center to send the unlock information of the corresponding product), IoT Center Transit unlock information to the MCU, single-chip computer receiving information to open the corresponding lock.

Communication mechanism: Frame head +id+ data type +24 lock state +CRC Check + frame tail (can be customized according to demand)

> com.myself.nettychat.config.TCPServerHandler (Processing classes for communication reception)


## API（Small program Call interface）

> http://localhost:8080/susu/back/get_channel_size  GET

Request IoT hub to get a link instance in the current connection survival state

```
{
    "code": 200,
    "msg": "成功",
    "data": 1
}
```

> http://localhost:8080/susu/back/get_channel_id_list  GET

Request IoT hub, List of link IDs in current surviving state

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

Parameters
* channelId //The link ID obtained by the second API 
* lock //Lock 1-24 to be opened (see the number of locks connected by a single chip computer)

```
{
    "code": 200,
    "msg": "成功",
    "data": "【发送成功】"
}
```

