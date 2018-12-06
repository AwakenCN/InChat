## paho-mqtt

本Demo是小程序端的Iot案例简单实现。

### 服务端配置

首先是配置修改，你可以在本分支的yml配置文件进行mqtt的配置，核心的参数是：

> ssl: false # 使用ssl加密
>
> protocol: MQTT_WS_PAHO  # MQTT  MQTT_WS_MQTT(mqtts.js)   MQTT_WS_PAHO(paho.js)

本项目使用的paho.js的mqtt连接形式，所以protocol要选择MQTT_WS_PAHO。项目目前是未加密的，启动ssl本案例暂时不能通讯。
默认直接启动项目就好。

> 项目启动后的地址 ：ws://192.168.1.121:8094/mqtt

ws、与后缀mqtt是com.myself.nettychat.bootstrap.AbstractBootstrapServer.java中的配置

### 小程序配置

你需要小程序开发者工具，并默认认定你是具备基本的小程序开发经验的开发者，这里省略部分的基本配置，你只需要将本分支中**wechat-client**文件夹中的文件完全复制到你新建的小程序项目即可，调试情况下无需AppID
你需要注意的是pages/connect/connect.js中的第78行

```javascript
var client = new MQTT.Client("ws://" + this.data.server_addr+"/mqtt", "clientId_" + Math.random().toString(36).substr(2));
```

这里就是小程序的连接地址配置，默认和项目启动的一致，你需要在小程序的连接页面填写你的
**IP：端口**

然后就连接成功了，接着你可以在subscribe页面订阅一个主题，本Demo是订阅TEST。

![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/inchat-mqtt/TIM%E5%9B%BE%E7%89%8720181101151707.png)

### Java模拟MQtt客户端

运行test中的com.myself.nettychat.MqttPublishSample，你需要修改成本机的配置，类似连接地址等

> String broker       = "ws://192.168.1.121:8094/mqtt";//地址

需要注意的是，你的topic也要与小程序订阅的主题一致哦！

运行测试用例，模拟硬件发送信息

![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/inchat-mqtt/TIM%E5%9B%BE%E7%89%8720181101151715.png)
![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/inchat-mqtt/TIM%E5%9B%BE%E7%89%8720181101151719.png)


### 测试

回到小程序的message页面，你可以看到接收到了消息

![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/inchat-mqtt/TIM%E5%9B%BE%E7%89%8720181101151723.png)
