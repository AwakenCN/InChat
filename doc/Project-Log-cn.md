# 项目研发日志更新

* 【2018-11-21】 目前Maven包开发目标，设计WebSocket登录接口[详情请看设计文档](design_cn.md)
* 【2018-11-22】 编码实现WebSocket聊天通讯的登录流程，[撰写说明文档](detail/Login-cn.md)
* 【2018-11-23】 添加贡献建议信息，重构修改登录检测常量代码，提取成接口形式
* 【2018-11-26】 登录存储用户token与链接实例，发送给自己功能API，部分功能代码重构
* 【2018-11-27】 发送消息给在线其他用户，代码部分模块加注释，[撰写设计文档](detail/login_rect.md)，但是中途思路有所转变
* 【2018-11-29】 修复登录下线关闭channel异常BUG，移除原始未重构代码，更新下线模块，与本地在线存储功能。
* 【2018-11-30】 启动赞助功能，新增【服务端向API发送消息】接口功能，暂未测试
* 【2018-12-02】 转变部分项目思路，减少用户对框架的依赖与开发成本。