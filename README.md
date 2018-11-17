## paho-mqtt

This demo is a simple implementation of a small program-side IoT case.

## 中文文档说明

* [中文文档说明](doc/doc.md)

### Server-side configuration

The first is configuration modifications, which you can configure for MQTT in the YML configuration file in this branch, and the core parameters are:

> ssl: false # 使用ssl加密
>
> protocol: MQTT_WS_PAHO  # MQTT  MQTT_WS_MQTT(mqtts.js)   MQTT_WS_PAHO(paho.js)

This project uses the Paho.js mqtt connection form, so protocol to select Mqtt_ws_paho.
The project is currently unencrypted, starting SSL this case is temporarily unable to communicate. It is good to start the project directly by default.

> Address after the project starts ：ws://192.168.1.121:8094/mqtt

WS, and Suffix MQTT is the configuration in Com.myself.nettychat.bootstrap.AbstractBootstrapServer.java

### Small program Configuration

You need a small program developer tool, and by default that you are a developer with basic small program development experience, omit the basic configuration of the section here, you just need to completely copy the files in the **wechat-client** folder in this branch to your new small program project, debugging conditions do not need to AppID What you need to be aware of is the 78th line in Pages/connect/connect.js.

```javascript
var client = new MQTT.Client("ws://" + this.data.server_addr+"/mqtt", "clientId_" + Math.random().toString(36).substr(2));
```

Here is the small program connection address configuration, the default and project startup consistent, you need to fill in the small program on the connection page of your
**IP：Port**

Then the connection succeeds, and then you can subscribe to a topic on the Subscribe page, and this demo is subscribed to test.

![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/inchat-mqtt/TIM%E5%9B%BE%E7%89%8720181101151707.png)

### Java Analog MQTT Client

To run the com.myself.nettychat.MqttPublishSample in test, you need to modify the configuration of the cost machine, similar to the connection address, etc.

> String broker       = "ws://192.168.1.121:8094/mqtt";//地址

It is important to note that your topic should also be consistent with the theme of the small program subscription Oh!

Run test cases to simulate hardware sending information

![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/inchat-mqtt/TIM%E5%9B%BE%E7%89%8720181101151715.png)
![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/inchat-mqtt/TIM%E5%9B%BE%E7%89%8720181101151719.png)


### Test

Back to the Message page of the small program, you can see that the message was received

![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/inchat-mqtt/TIM%E5%9B%BE%E7%89%8720181101151723.png)
