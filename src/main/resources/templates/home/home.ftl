<!DOCTYPE html>
<html>
<#include "../common/header.ftl">
<style type="text/css">
    .infinite-scroll-preloader {
        margin-top:-20px;
    }
</style>
<body>
<div class="page">
    <header class="bar bar-nav">
        <h1 class="title">酥酥</h1>
    </header>
    <nav class="bar bar-tab">
        <a class="tab-item active" href="#">
            <span class="icon icon-message"></span>
            <span class="tab-label">信息</span>
        </a>
        <a class="tab-item" href="/susu/su/chat">
            <span class="icon icon-friends"></span>
            <span class="tab-label">通讯录</span>
        </a>
        <a class="tab-item" href="/susu/su/find">
            <span class="icon icon-browser"></span>
            <span class="tab-label">发现</span>
        </a>
        <a class="tab-item" href="/susu/su/me">
            <span class="icon icon-me"></span>
            <span class="tab-label">我</span>
        </a>
    </nav>
    <div class="content">
        <form role="form" id="rec" hidden>
        </form>
        <!-- 添加 class infinite-scroll 和 data-distance  向下无限滚动可不加infinite-scroll-bottom类，这里加上是为了和下面的向上无限滚动区分-->
        <#--<div class="content infinite-scroll infinite-scroll-bottom" data-distance="100">-->
            <div class="list-block">
                <ul class="list-container">
                    <li class="item-content" onclick="allchat()">
                        <div class="item-inner">
                            <img style="height: 70px;width: 70px;" src="/susu/image/logoSmall.png"><div class="item-title">全体用户</div>
                        </div>
                    </li>
                </ul>
            </div>
            <#--<!-- 加载提示符 &ndash;&gt;-->
            <#--<div class="infinite-scroll-preloader">-->
                <#--<div class="preloader"></div>-->
            <#--</div>-->
        <#--</div>-->
    </div>
</div>
<#include "../common/floor.ftl">
<script type="text/javascript">
    function allchat() {
        document.getElementById('rec').action = "http://localhost:8080/susu/chat/netty";
        document.getElementById('rec').method = "GET";
        document.getElementById('rec').submit();
    }
</script>
</body>
</html>