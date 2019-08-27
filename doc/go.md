## InChat项目里程碑

> 项目研发的开放目标，以Maven包为基本的发布（兼对应版本的文档输出）设定为每个里程碑的产出。

### 项目于2018年8月14号开始成立，前一个月以Demo为发展路线，后期转为框架研发。

### V1.0.0版本 【2018-12-14】

> 版本昵称：赤猫

版本目标：完成基本的消息通讯（仅支持文本消息），离线消息存储，历史消息查询，一对一聊天、自我聊天、群聊等

* [V1.0.0版本使用说明](doc/version/v1.0.0.md)
* [V1.0.0版本使用说明视频教程](https://v.qq.com/x/page/i0813oy0lov.html)

> 目前项目代码已经推进入了下一版本，如果你想下载这个版本的源码，可以到这里下载[V1.0.0-alpha](https://github.com/UncleCatMySelf/InChat/releases/tag/V1.0.0-alpha)

```
<!-- https://mvnrepository.com/artifact/com.github.UncleCatMySelf/InChat -->
<dependency>
    <groupId>com.github.UncleCatMySelf</groupId>
    <artifactId>InChat</artifactId>
    <version>1.0-alpha</version>
</dependency>
```

### V1.1.0-alpha版本 【2018-12-21】

> 版本昵称：赤猫-1

版本目标：移除对SpringBoot的环境依赖，InChat独立生存与使用，结合上一版的功能，并添加服务器发送消息接口

* [V1.1.0-alpha版本使用说明](https://unclecatmyself.github.io/2018/12/21/InChatV1.1.0%E7%89%88%E6%9C%AC%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E/)
* [V1.1.0-alpha版本视频教学](https://v.qq.com/x/page/i08165ym286.html)

```
<dependency>
    <groupId>com.github.UncleCatMySelf</groupId>
    <artifactId>InChat</artifactId>
    <version>1.1.0-alpha</version>
</dependency>
```

### V1.1.2版本 【2019-01-04】

> 版本昵称：赤猫-2

版本更新：添加HTTP接口调用，捕获已知异常

* [V1.1.2版本使用说明](https://unclecatmyself.github.io/2019/01/03/inchatby112/)
* [V1.1.2版本视频教学](https://v.qq.com/x/page/y08228i7znk.html)

```
<dependency>
    <groupId>com.github.UncleCatMySelf</groupId>
    <artifactId>InChat</artifactId>
    <version>1.1.2</version>
</dependency>
```


### V1.1.3版本 【2019-01-15】

> 版本昵称：橙猫

版本目标：InChat集群与wss,SSL加密与分布式测试点对点与群聊

* [InChatV1.1.3版本使用说明](https://unclecatmyself.github.io/2019/01/15/inchatby113/)

```
<dependency>
    <groupId>com.github.UncleCatMySelf</groupId>
    <artifactId>InChat</artifactId>
    <version>1.1.3</version>
</dependency>
```

### V1.1.4版本 【2019-08-23】

版本目标：实现数据存储，单机、单机加密、分布式等默认功能

* [基于InChat的SpringBoot版本通讯聊天数据存储Demo，附带详细流程说明](https://unclecatmyself.github.io/2019/08/23/inchatby4/)

```
<dependency>
    <groupId>com.github.UncleCatMySelf</groupId>
    <artifactId>InChat</artifactId>
    <version>1.1.3</version>
</dependency>
```