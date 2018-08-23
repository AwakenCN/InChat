<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Su Su</title>
</head>
<link rel="stylesheet" type="text/css" href="/susu/css/newChat.css" />

<body>
<#--<form onsubmit="return false;">-->
    <#--<input hidden id="userName" value="${userName!''}">-->
    <#--<h3>“酥酥”聊天室</h3>-->
    <#--<textarea id="responseText"></textarea>-->
    <#--<br>-->
    <#--<input class='msg' type="text" name="message" placeholder='发送消息' value="">-->
    <#--<input class='btn' type="button" value="回车发送">-->
<#--</form>-->
<div class="container">
    <!--左侧列表-->
    <div class="left_content">
        <div class="nickName">${userName!''}</div>
    </div>
    <input hidden id="userName" value="${userName!''}">
    <!--主体内容-->
    <div class="content">
        <div class="content_top">
            <div class="tips"></div>
            <span class="openHistory">历史记录</span>
        </div>
        <div class="content_bodyer">
            <div class="history" id="TTHistory">

            </div>
            <div class="scrollbar">
                <div class="thumb" id="thumb"></div>
            </div>
            <div class="chat">

            </div>
        </div>
        <form>
            <textarea class='msg' type="text" name="message" placeholder='发送消息' value=""></textarea>
            <input class="btn" type="button" value="回车发送"/>
        </form>
    </div>
</div>
</body>

<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script src='/susu/js/newChat.js'></script>
<script>
    var msg = '\n';
    <#list userMsgList as userMsg>
    msg = msg +'<div>'+ '${userMsg.name!''}'+':'+'${userMsg.msg!''}' +'</div>';
    </#list>
    msg = msg+'<div>--- 以上为历史记录 ---</div>';
</script>
</html>