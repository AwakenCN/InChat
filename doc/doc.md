# InChat-IM-API（当前版本0.0.2）

## 简介

本项目为InChat核心项目，服务端项目，以API形式作为对外功能，类似腾讯IM的服务端作用，本文也将着重讲解本项目的各个API，目前还没有嵌入Iot通信模块，仅以WebSocket的聊天室作为初期发展，需要使用到Iot的朋友可以先去[Master](https://github.com/UncleCatMySelf/InChat/tree/master)项目了解。

## swagger-ui

前端对接公告，目前推出API，请均已此文档说明的为主，其余API非正式版或测试版，误用
查看API列表
> http://localhost:8080/susu/swagger-ui.html

## API列表详情

* 1、账号注册
> POST  http://loclhost:8080/susu/user/to_register
- 参数：username（用户名）
- 参数：password（密码）
- 前端Tip:传值判断，参数均不能为空，密码限制在前端做判断


## 返回码与信息值

| 返回码 | 信息内容 | 备注 |
|------|---------|------|
| 200  |  成功   |      |
| 555  | 参数错误|      |
| 556  | 用户名存在|    |

## 提示

仅API列表详情中的API处于可用状态，其余API请勿使用，暂未基本完成，使用请详看文档