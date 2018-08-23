<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<title>Su Su</title>
	</head>

	<link rel="stylesheet" type="text/css" href="/susu/css/registered.css"/>

	<body>

		<!--遮罩-->
		<div class="mask"></div>
		<div class="mask2"></div>
		<!--遮罩-->

		<!-- 主体开始 -->
		<div class="bodyer">
			<div class="panel">
				<div class='logo'></div>
				<h3 class="panel_tit">“酥酥”聊天室</h3>
				<form action="/susu/admin/toLogin" method="post" class="register">
					<div class="register_top">
						<a href="#">登录</a>
						<span>|</span>
						<a href="#">注册</a>
					</div>
					<div class="user_icon"><i></i>
						<input type="text" placeholder="用户名"  name="userName" id="userName" value="${userName!''}" /></div>
					<div class="pass_icon">
						<i></i>
						<input type="password" placeholder="密码" name="passWord" id="passWord" value="${passWord!''}" />
						<div class="Span">
							<span>请填写用户名</span>
							<span>2-5位中文字符和英文字符</span>
							<span>请填写6-16位数字和字母的组合密码</span>
							<span>密码格式有误</span>
							<#--<span>${msg!''}</span>-->
						</div>
					</div>
					<input type="submit" class="btn1" value='登陆' />
                    <!--提示密码错误或用户名错误-->
                    <div class="tips">${msg!''}</div>
				<form>
			</div>
		</div>
		<!-- 主体结束 -->
	</body>
	<script src='/susu/js/jQuery-1.12.4.js'></script>
	<script src='/susu/js/registered.js'></script>
	<script>
        if("${msg!''}" != ''){
        MsgTo();
        }
    </script>
</html>