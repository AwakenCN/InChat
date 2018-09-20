<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Su Su</title>
</head>
<link rel="stylesheet" type="text/css" href="/susu/css/newChat.css" />
<script type='text/javascript'
        src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript'
        src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript'
        src='//g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>
<script type="text/javascript">
<body>
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
            <input class="btn" type="button" value="回车发送" onclick="send(this.form.message.value)"/>
            <input class="btn" type="file" id="file"/>
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