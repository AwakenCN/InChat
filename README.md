# InChat-IM-API（0.0.2 Version）

## Summery

This project is Inchat core project, server-side project, in the form of APIs as external functions, similar to Tencent IM service-side role, this article will also focus on the various APIs of this project, there is no embedded IoT communication module, only websocket chat room as the initial development,
Friends who need to use IoT can first go to the [Master](Https://github.com/UncleCatMySelf/InChat/tree/master) project to learn.

## 中文文档

* [中文文档](doc/doc.md)

## swagger-ui

Front end docking announcement, currently launched API, please have this document description of the main, the rest of the API informal or beta version, misuse View the list of APIs
> http://localhost:8080/susu/swagger-ui.html

## API List Details

* 1、Account Registration
> POST  http://loclhost:8080/susu/user/to_register
- Params：username
- Params：password
- Front End tip: Pass value judgment, parameters can not be empty, password limit in the front end to do judgment


## Return code and information values

| Return code | Content of information | Note |
|------|---------|------|
| 200  |  Success   |      |
| 555  | Parameter error|      |
| 556  | User name exists|    |

## Tips

Only the APIs in the API list details are available, and the remaining APIs are not used, are not basically complete, please look at the documentation in detail