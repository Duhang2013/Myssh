/**
 * 注册是填写信息的验证
 * 2015-10-9
 */
window.onload = function() {

	// 获取所有的input标签
	var inputs = document.getElementsByTagName("input");
	
	var name = inputs[0];
	var email = inputs[1];
	var password = inputs[2];
	var rePassword = inputs[3];
	rePassword.onfocus = 'this.blur()';
	
	// 获取所有input后面的提示图片和信息
	var name_tishi_icon = name.nextSibling.nextSibling;
	var name_tishi_info = name_tishi_icon.nextSibling.nextSibling;
	
	var email_tishi_icon = email.nextSibling.nextSibling;
	var email_tishi_info = email_tishi_icon.nextSibling.nextSibling;
	
	var psw_tishi_icon = password.nextSibling.nextSibling;
	var psw_tishi_info = psw_tishi_icon.nextSibling.nextSibling;
	
	var repsw_tishi_icon = rePassword.nextSibling.nextSibling;
	var repsw_tishi_info = repsw_tishi_icon.nextSibling.nextSibling;
	rePassword.setAttribute('disabled', 'disabled');
	
	// 正则表达式验证
	/*
	 * 1、name
	 * 	字母（不区分大小写）、数字、下划线、中文(中文unicode范围：u4e00 - u9fa5)
	 * 	reg = /\w\u4e00-\u9fa5/g ->符合要求
	 * 超出要求：/[^\w\u4e00-\u9fa5]/g
	 * 2、email
	 */
	
	function setIconInfo(iconT, infoT, imgsrc, info) {
		iconT.src = imgsrc;
		iconT.style.visibility = 'visible';
		infoT.innerHTML = info;
		infoT.style.visibility = 'visible';
	}
	
	// input获得焦点事件
	name.onfocus = function() { 
		setIconInfo(name_tishi_icon, name_tishi_info, 'img/warning.png', '字母（不区分大小写）、数字、下划线、中文');
	}
	
	email.onfocus = function() { 
		setIconInfo(email_tishi_icon, email_tishi_info, 'img/warning.png', '作为登录帐号，请填写正确的邮箱');
	}
	
	password.onfocus = function() { 
		setIconInfo(psw_tishi_icon, psw_tishi_info, 'img/warning.png', '字母（区分大小写）、数字、下划线等,不少于6位');
	}
	
	rePassword.onfocus = function() { 
		setIconInfo(repsw_tishi_icon, repsw_tishi_info, 'img/warning.png', '重复输入密码');
	}
	
	// input失去焦点事件
	name.onblur = function() { 
		// 正则表达式验证
		// 含有非法字符
		var reg = /[^\w\u4e00-\u9fa5]/g;
		if(reg.test(this.value)) {
			setIconInfo(name_tishi_icon, name_tishi_info, 'img/register/error.png', '含有非法字符！');
		} else if (this.value == "") { // 含有空字符
			setIconInfo(name_tishi_icon, name_tishi_info, 'img/register/error.png', '不能为空！');
		} else if(this.value.length > 25) { // 长度超过25
			setIconInfo(name_tishi_icon, name_tishi_info, 'img/register/error.png', '长度超过25！');
		} else if(this.value.length < 6) { // 少于6
			setIconInfo(name_tishi_icon, name_tishi_info, 'img/register/error.png', '长度少于6！');
		} else{ // ok
			setIconInfo(name_tishi_icon, name_tishi_info, 'img/register/ok.png', '');
		}
	}
	
	email.onblur = function() { 
		var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/g; // /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/; //邮件正则
		if(reg.test(this.value)) { // 正确
			setIconInfo(email_tishi_icon, email_tishi_info, 'img/register/ok.png', '');
		} else if(this.value == ""){
			setIconInfo(email_tishi_icon, email_tishi_info, 'img/register/warning.png', '邮箱不能为空!');
		} else {
			setIconInfo(email_tishi_icon, email_tishi_info, 'img/register/warning.png', '请填写正确的邮箱!');
		}
	}
	
	// 密码强度信息
	var psw_count = document.getElementById("psw_count");
	var spans =	psw_count.getElementsByTagName("span");
	password.onkeyup = function() { // 密码长度小于6
		if(this.value.length < 6) { // 不合法
			rePassword.setAttribute('disabled', 'disabled');
			rePassword.value = ""; // 不合法将原来的rePassword置为空
		} else {
			rePassword.removeAttribute('disabled');
		}
		if(this.value.length < 8) { // 密码弱
			spans[0].className = "low_active";
			spans[1].className = "";
			spans[2].className = "";
		} else if(this.value.length < 12) { // 密码中
			spans[0].className = "mid_active";
			spans[1].className = "mid_active";
			spans[2].className = "";
		} else { // 密码强
			spans[0].className = "high_active";
			spans[1].className = "high_active";
			spans[2].className = "high_active";
		}
	}
	password.onblur = function() { 
		if(this.value.length < 6) {
			setIconInfo(psw_tishi_icon, psw_tishi_info, 'img/register/error.png', '长度少于6！');
			spans[0].className = "";
		} else {
			setIconInfo(psw_tishi_icon, psw_tishi_info, 'img/register/ok.png', '');
		}
	}
	
	rePassword.onblur = function() {
		if(this.value.length == 0 || this.value != password.value) {
			setIconInfo(repsw_tishi_icon, repsw_tishi_info, 'img/register/error.png', '密码不一致!');
		} else {
			setIconInfo(repsw_tishi_icon, repsw_tishi_info, 'img/register/ok.png', '');
		}
	}
	
//	// 下一步按钮事件处理
//	var nextBtn = document.getElementById("nextBtn");
//	nextBtn.onclick = function() {
//		alert("click");
//	}
}


