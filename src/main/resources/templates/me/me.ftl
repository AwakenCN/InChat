<!DOCTYPE html>
<html>
<#include "../common/header.ftl">
<body>
<div class="page">
    <header class="bar bar-nav">
        <h1 class="title">酥酥</h1>
    </header>
    <nav class="bar bar-tab">
        <a class="tab-item" href="/susu/su/home">
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
        <a class="tab-item active" href="#">
            <span class="icon icon-me"></span>
            <span class="tab-label">我</span>
        </a>
    </nav>
    <div class="content">
        <div class="list-block">
            <ul class="list-container">
                <li class="item-content">
                    <div class="item-inner">
                        <img style="height: 70px;width: 70px;" src="/susu/image/logoSmall.png"><div class="item-title">${userName!''}</div>
                    </div>
                </li>
            </ul>
        </div>
        <div style="height: 10px;"></div>
        <div class="list-block">
            <ul class="list-container">
                <li class="item-content">
                    <div class="item-inner">
                        <span class="icon icon-gift"></span><div class="item-title">收藏</div>
                    </div>
                </li>
            </ul>
        </div>
        <div class="list-block">
            <ul class="list-container">
                <li class="item-content">
                    <div class="item-inner">
                        <span class="icon icon-picture"></span><div class="item-title">相册</div>
                    </div>
                </li>
            </ul>
        </div>
        <div class="list-block">
            <ul class="list-container">
                <li class="item-content">
                    <div class="item-inner">
                        <span class="icon icon-card"></span><div class="item-title">卡包</div>
                    </div>
                </li>
            </ul>
        </div>
        <div class="list-block">
            <ul class="list-container">
                <li class="item-content">
                    <div class="item-inner">
                        <span class="icon icon-emoji"></span><div class="item-title">表情</div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</div>
<#include "../common/floor.ftl">
</body>
</html>