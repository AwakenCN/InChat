var socket;
if(!window.WebSocket) {
	window.WebSocket = window.MozWebSocket;
}

if(window.WebSocket) {
	socket = new WebSocket("ws://localhost:8090/ws");
	socket.onmessage = function(event) {
		// var ta = "<div>"+event.data+"</div>";
		// $('.content_bodyer').append(ta);
        var msg = event.data;
        if(msg.substring(0, 1) != '[') {
            var ta = "<div class='msgRight'><span>"+event.data+"</span></div>";
            $('.chat').append(ta);
        } else {
            var ta = "<div class='msgLeft'><span>"+event.data+"</span></div>";
            $('.chat').append(ta);
        }
	};
	socket.onopen = function(event) {
        $('.tips').html('连接开启！');
        $('.tips').css('color', 'green');
        // var his = document.getElementById('TTHistory');
		var his = $('#TTHistory');
        // his.innerHTML = msg;
		console.log(msg);
		his.html(msg);
	};
	socket.onclose = function(event) {
        $('.tips').html('连接被关闭');
        $('.tips').css('color', 'red');
	};
} else {
	var ta = "<div>你的浏览器不支持 WebSocket！</div>";
		$('.content_bodyer').append(ta);
}

function send(message) {
	if(!window.WebSocket) {
		return;
	}
	if(socket.readyState == WebSocket.OPEN) {
		socket.send(message);
	} else {
		alert("连接没有开启.");
	}
}

window.onbeforeunload = function(event) {
	event.returnValue = "刷新提醒";
}

;
document.onkeydown = function(e) {
    // var userName = document.getElementById('userName');
	var userName = $('#userName');
	if(e.keyCode == 13) {
        $('.chat').css('bottom', 0);
		var message = userName.val() + '-' + $('.msg').val().trim();
		send(message);
        $('.msg').val('');
	}
}

$('.openHistory').click(function() {
    $('.history').fadeToggle();
});

$('.content_bodyer').mouseenter(function() {
    $('.scrollbar').fadeIn();
})

$('.content_bodyer').mouseleave(function() {
    $('.scrollbar').fadeOut();
})

var thumb = $('.thumb');
var scrollBar = $('.scrollbar');
var chat = $('.chat');

thumb.on('mousedown', function(e) {
    chat.css('bottom', 'initial');
    thumb.isMouseDown = true;
    return false;
});

thumb.on('selectstart', function() {
    return false;
});

$(window).on('mouseup', function(e) {
    thumb.isMouseDown = false;
});

$(window).on('mousemove', function(e) {
    if (thumb.isMouseDown){
        var pos = e.clientY - scrollBar.parent().offset().top - thumb.height() / 2;
        if (pos < 0 ){
            pos = 0;
        } else if(pos > scrollBar.height() - thumb.height()){
            pos = scrollBar.height() - thumb.height();
        }
        thumb.css("top", pos + "px");

        // 计算thumb所在的位置占父亲的%
        var percentage = thumb.offset().top / (scrollBar.height() - thumb.height());
        var top = (chat.height() - chat.parent().height()) * percentage;
        chat.css("top", -top + "px");
    }
})

