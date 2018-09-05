<!DOCTYPE html>
<html>
<#include "../common/header.ftl">
<body>
<div class="page-group">
    <div class="page page-current" id="router">
        <!-- 你的html代码 -->
        <header class="bar bar-nav">
            <h1 class="title">登录</h1>
        </header>
        <form role="form" id="rec" hidden>
            <input type="text" name="fUserName" id="fUserName"/>
            <input type="text" name="fPassWord" id="fPassWord"/>
        </form>
        <div class="content">
                <div class="list-block">
                    <ul>
                        <li>
                            <div class="item-content">
                                <div class="item-media"><i class="icon icon-form-name"></i></div>
                                <div class="item-inner">
                                    <div class="item-title label">账号</div>
                                    <div class="item-input">
                                        <input type="text" placeholder="Your name" name="userName" id="userName" value="${userName!''}">
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-media"><i class="icon icon-form-password"></i></div>
                                <div class="item-inner">
                                    <div class="item-title label">密码</div>
                                    <div class="item-input">
                                        <input type="password" placeholder="Password"  name="passWord" id="passWord" value="${passWord!''}">
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="content-block">
                    <div class="row">
                        <div class="col-50"><a href="#" id="login" class="button button-big button-fill button-danger" onclick="loginBtn()">登录</a></div>
                        <div class="col-50"><a href="#router3" class="button button-big button-fill button-success">注册</a></div>
                    </div>
                </div>
        </div>
        <!-- 你的html代码 -->
    </div>

    <div class="page" id='router3'>
        <header class="bar bar-nav">
            <h1 class='title'>注册</h1>
        </header>
        <div class="content">
            <div class="content-block">

                <div class="list-block">
                    <ul>
                        <!-- Text inputs -->
                        <li>
                            <div class="item-content">
                                <div class="item-media"><i class="icon icon-form-name"></i></div>
                                <div class="item-inner">
                                    <div class="item-title label">账号</div>
                                    <div class="item-input">
                                        <input type="text" placeholder="Your name" name="userName" id="rUserName" value="${userName!''}">
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-media"><i class="icon icon-form-password"></i></div>
                                <div class="item-inner">
                                    <div class="item-title label">密码</div>
                                    <div class="item-input">
                                        <input type="password" placeholder="Password"  name="passWord" id="rPassWord" value="${passWord!''}">
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="content-block">
                    <div class="row">
                        <div class="col-50"><a href="#" class="button button-big button-fill button-danger" onclick="regist()">注册</a></div>
                        <div class="col-50"><a href="#" class="button button-big button-fill button-success back">返回</a></div>
                    </div>
                </div>

            </div>
        </div>
    </div>

</div>
<#include "../common/floor.ftl">
<script type="text/javascript">

    if("${msg!''}" != ''){
        $.toast("${msg!''}");
    }

    function loginBtn() {
        document.getElementById('rec').action = "http://localhost:8080/susu/admin/toLogin";
        document.getElementById('rec').method = "POST";
        document.getElementById('fUserName').value = document.getElementById('userName').value;
//        console.log(document.getElementById('fUserName').value);
        document.getElementById('fPassWord').value = document.getElementById('passWord').value;
//        console.log(document.getElementById('fPassWord').value);
        document.getElementById('rec').submit();
    }

    function regist() {
        document.getElementById('rec').action = "http://localhost:8080/susu/admin/toRegister";
        document.getElementById('rec').method = "POST";
        document.getElementById('fUserName').value = document.getElementById('rUserName').value;
        document.getElementById('fPassWord').value = document.getElementById('rPassWord').value;
        document.getElementById('rec').submit();
    }
</script>
</body>
</html>