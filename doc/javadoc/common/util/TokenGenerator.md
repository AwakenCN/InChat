## TokenGenerator

包路径：com.github.unclecatmyself.common.utils

类说明：Token生成算法，可用于Session、秘钥等

### 属性

- Integer shiftAmount
    
    定义ID位移量
    
- Long crossRangeAmount

    定义横向参数
    
- Long portraitAmount

    定义纵向参数
    
- Integer Y_Random

    定义前随机数位数
    
- Integer Q_Random

    定义后随机数位数


### 方法

```text
- getToken(String): String                  通过Id生成Token串
- changeForId(Long): Long                   Id移位
- restoreForId(Long): Long                  Id归位
- confusionOperationId(Long): Long          id混淆计算
- reductionOperationId(Integer): String     id还原计算
- getYRandomString(Integer): String         Y随机数，末尾为数字
- getQRandomString(Long): String            Q随机数，第一位为数字
- getUseridOnBase(String): String           userId的Base64位算法
- getBaseOnUserid(String): String           ID解密
- JudgementToken                            token校验及userID解析(防止token篡改)
```

### 扩展

* [服务端 Token生成算法](https://github.com/AwakenCN/InChat/blob/master/src/main/java/com/github/unclecatmyself/common/utils/TokenGenerator.java)
    根据配置不同的参数，可以自定义Token的生成规则
    

