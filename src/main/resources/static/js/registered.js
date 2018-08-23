var register = $('.register_top').children().first();
var register2 = $('.register_top').children().last();
register.click(function () {
    $(register).css('color', ' #303030');
    register2.css('color', '#999');
    $('.btn1').val('登陆');
    $('form').attr("action", "/susu/admin/toLogin");
});
register2.click(function () {
    $(register2).css('color', ' #303030');
    register.css('color', '#999');
    $('.btn1').val('注册');
    $('form').attr("action", "/susu/admin/toRegister");
});
$('.btn3').click(function () {
    $('.more').slideToggle(0);
});

/**
 * 用户名验证
 */
var input1 = $('.user_icon').children('input');
var userName = /^[\u4e00-\u9fa5_a-zA-Z]{2,5}$/;
input1.blur(function () {
    var val = input1.val().trim();
    if (val.length == 0) {
        $($('.Span').children()[0]).css('display', 'block');
        $($('.Span').children()[0]).siblings().css('display', 'none');
    } else if (userName.test(val)) {
        $($('.Span').children('span')).css('display', 'none');
    } else {
        $($('.Span').children()[1]).css('display', 'block');
        $($('.Span').children()[1]).siblings().css('display', 'none');
    }
});

/**
 * 密码验证
 */
var input2 = $('.pass_icon').children('input');
// 关于密码的正则表达式（6-16位数字和字母的组合）
var Password = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/;
input2.blur(function () {
    var val = input2.val().trim();
    if (val.length == 0) {
        $($('.Span').children()[2]).css('display', 'block');
        $($('.Span').children()[2]).siblings().css('display', 'none');
    } else if (Password.test(val)) {
        console.log(1);
        $($('.Span').children('span')).css('display', 'none');
    } else {
        $($('.Span').children()[3]).css('display', 'block');
        $($('.Span').children()[3]).siblings().css('display', 'none');
    }
});

//登录注册校验
$('form').submit(function(e) {
	var User = $('.user_icon').children('input');
	var Pass = $('.pass_icon').children('input');
    var md5_pwd= $('#md5_pwd');
	if (userName.test(User) && Password.test(Pass)) {
		return true;
	}
})

function MsgTo() {
    $('.tips').fadeIn(10, function() {
        $('.tips').fadeOut(1500);
    });
}

