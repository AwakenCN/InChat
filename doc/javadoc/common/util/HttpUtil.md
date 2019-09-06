## HttpUtil

包路径：com.github.unclecatmyself.common.utils

类说明：HTTP接口基础工具处理

### 方法

- checkType(FullHttpRequest msg)

> 返回 String

说明：校验API接口获取通讯数据的类型，类似Rest API 的后缀判断

- getToken(FullHttpRequest msg) 

> 返回 [SendServerVO](../bean/vo/SendServerVO.md)

说明：从uri中获取对应的Token值，构造对应的[SendServerVO](../bean/vo/SendServerVO.md)
