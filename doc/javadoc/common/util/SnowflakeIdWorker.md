## SnowflakeIdWorker

包路径：com.github.unclecatmyself.common.utils

类说明：Twitter 雪花算法，旨在生成全局唯一ID，可用于分布式ID生成策略

### 属性

- long twepoch 

    开始时间截 (2019-02-27)
    
- long workerIdBits 

    机器id所占的位数
> 注：因为可能部署在一台物理机上，所以该参数实际为serverId

- long maxWorkerId 

    支持的最大机器id，结果是1024 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
    
- long sequenceBits 

    序列在id中占的位数
    
- long workerIdShift 

    机器ID向左移位数
    
- long timestampLeftShift
 
    时间截向左移位数
    
- long sequenceMask

    生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)

- long workerId

    工作机器ID(0~1024)
    
- long sequence

    毫秒内序列(0~4095)
    
- long lastTimestamp

    上次生成ID的时间截
 

在netty的加密中也有使用到io.netty.handler.ssl.SslContext，netty有自己的SslContextBuilder构建器，不过本项目使用了JDK原生的


### 方法

- long nextId()

> 说明：获得下一个ID (该方法是线程安全的)

### 扩展

* [雪花算法 SnowflakeIdWorker](https://github.com/AwakenCN/InChat/blob/master/src/main/java/com/github/unclecatmyself/common/utils/SnowflakeIdWorker.java)
* [百度 UIDGenerator](https://github.com/noseparte/Almost-Famous/blob/master/famous-unique/README.md)


