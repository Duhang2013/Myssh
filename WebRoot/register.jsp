<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Duang学堂教育信息管理系统注册页</title>
		<link rel="stylesheet" href="css/register/reset.css" />
		<link rel="stylesheet" href="css/register/register_base.css" />
		<link rel="stylesheet" href="css/register/register.css"/>
		<script type="text/javascript" src="js/register/register.js"></script>
	</head>
	
	<body>
	<div id="wrapper">
		<div class="container w960 mt20">
			<div id="processor" >
				<ol class="processorBox oh">
					<li id="li_1" class="current">
						<div class="step_inner fl">
							<span class="icon_step">1</span>
							<h4>填写基本信息</h4>
						</div>
					</li>
					<li id="li_2" >
						<div class="step_inner">
							<span class="icon_step">2</span>
							<h4>邮箱激活</h4>
						</div>
					</li>
					<li id="li_3" >
						<div class="step_inner fr">
							<span class="icon_step">3</span>
							<h4>完善开发者资料</h4>
						</div>
					</li>
				</ol>
				<div class="step_line"></div>
			</div>
			
			<div class="content">
				<div id="step1" class="step hide">
					<form action="" method="post" id="step1_frm">
						<div class="frm_control_group">
							<label class="frm_label">用户名</label>
							<div class="frm_controls">
								<input type="text" id="username" name="u.username" class="frm_input name" maxlength="32"/>
								<img alt="提示图标" src="" id="tishi_icon">
								<span id="tishi_info">用户名非法</span>
							</div>
						</div>
						<div class="frm_control_group">
							<label class="frm_label">邮箱</label>
							<div class="frm_controls">
								<input type="text" name="" class="frm_input email" maxlength="32"/>
								<img alt="提示图标" src="" id="tishi_icon">
								<span id="tishi_info">请填写正确的邮箱</span>
							</div>
						</div>
						<div class="frm_control_group">
							<label class="frm_label">密码</label>
							<div class="frm_controls">
								<input type="password" id="password" name="u.password" class="frm_input passwd"/>
								<img alt="提示图标" src="" id="tishi_icon">
								<span id="tishi_info">密码强度弱</span>
							    <div id="psw_count">
							        <span>弱</span>
							        <span>中</span>
							        <span>强</span>
							    </div>
							</div>
						</div>
						<div class="frm_control_group">
							<label class="frm_label">再次输入密码</label>
							<div class="frm_controls">
								<input type="password" name="" class="frm_input passwd2" />
								<img alt="提示图标" src="" id="tishi_icon">
								<span id="tishi_info">两次密码不一样</span>
							</div>
						</div>
						<div class="frm_control_group">
							<label class="frm_label">验证码</label>
							<div class="frm_controls verifycode">
								<input type="text" name="" class="frm_input verifyCode" maxlength="4"/>
								<img src="img/register/verifycode-3wa62z.png" alt="verifycode" id="verifycode_img"/>
								<a class="changeVerifyCode" href="javascript:;">换一张</a>
							</div>
						</div>
						<div class="toolBar">
							<a id="nextBtn" class="btn btn_primary" href="javascript:;">下一步</a>
						</div>
					</form>
				</div><!-- // step1 end -->
				
				<div id="step2" class="step hide">
					<div class="w330">
						<strong class="f16">感谢注册，确认邮件已发送至你的注册邮箱 : <br />haibao1024@gmail.com</strong>
						<p class="c7b">请进入邮箱查看邮件，并激活微信开放平台帐号。</p>
						<p><a class="btn btn_primary mt20" href="javascript:;">登录邮箱</a></p>
						<p class="c7b mt20">没有收到邮件？</p>
						<p>1. 请检查邮箱地址是否正确，你可以返回 <a href="#" class="c46">重新填写</a></p>
						<p>2. 检查你的邮件垃圾箱</p>
						<p>3. 若仍未收到确认，请尝试 <a href="#" class="c46">重新发送</a></p>
					</div>
				</div><!-- // step2 end -->
				<div id="step3" class="step hide">
					<form action="" method="post" id="step3_frm">
						<div class="frm_control_group">
							<label class="frm_label">真实姓名</label>
							<div class="frm_controls">
								<input type="text" name="" class="frm_input name" maxlength="32"/>
								<p class="frm_tips">请填写真实姓名</p>
							</div>
						</div>
						<div class="frm_control_group">
							<label class="frm_label">手机号</label>
							<div class="frm_controls">
								<input type="text" name="" class="frm_input phone"/>
								<p class="frm_tips">请填写常用手机号</p>
							</div>
						</div>
						<div class="frm_control_group">
							<label class="frm_label">手机验证码</label>
							<div class="frm_controls">
								<input type="text" name="" class="frm_input phoneYzm"/>
								<input type="button" value="获取验证码" class="btn btn_default" />
							</div>
						</div>
						<div class="toolBar">
							<a id="finishedBtn" class="btn btn_primary" href="login.jsp">完成</a>
						</div>
					</form>
				</div><!-- // step3 end -->
			</div>
		</div><!-- // container end -->
	</div><!-- // wrapper end -->
	
	<script src="js/jquery.min.js"></script>
	<script> 
		//显示提示框，目前三个参数(txt：要显示的文本；time：自动关闭的时间（不设置的话默认1500毫秒）；status：默认0为错误提示，1为正确提示；)
		function showTips(txt,time,status)
		{
			var htmlCon = '';
			if(txt != ''){
				if(status != 0 && status != undefined){
					htmlCon = '<div class="tipsBox" style="width:220px;padding:10px;background-color:#4AAF33;border-radius:4px;-webkit-border-radius: 4px;-moz-border-radius: 4px;color:#fff;box-shadow:0 0 3px #ddd inset;-webkit-box-shadow: 0 0 3px #ddd inset;text-align:center;position:fixed;top:40%;left:50%;z-index:999999;margin-left:-120px;"><img src="img/register/ok.png" style="width: 30px;heigth:30px;vertical-align: middle;margin-right:8px;" alt="OK，"/>'+txt+'</div>';
				}else{
					htmlCon = '<div class="tipsBox" style="width:220px;padding:10px;background-color:#D84C31;border-radius:4px;-webkit-border-radius: 4px;-moz-border-radius: 4px;color:#fff;box-shadow:0 0 3px #ddd inset;-webkit-box-shadow: 0 0 3px #ddd inset;text-align:center;position:fixed;top:40%;left:50%;z-index:999999;margin-left:-120px;"><img src="img/register/err.png" style="width: 30px;heigth:30px;vertical-align: middle;margin-right:8px;" alt="Error，"/>'+txt+'</div>';
				}
				$('body').prepend(htmlCon);
				if(time == '' || time == undefined){
					time = 1500; 
				}
				setTimeout(function(){ $('.tipsBox').remove(); },time);
			}
		}
		
		$(function(){
			//AJAX提交以及验证表单
			$('#nextBtn').click(function(){
				var email = $('.email').val();
				var passwd = $('.passwd').val();
				var passwd2 = $('.passwd2').val();
				var verifyCode = $('.verifyCode').val();
				var EmailReg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/; //邮件正则
				if(email == ''){
					showTips('请填写您的邮箱~');
				}else if(!EmailReg.test(email)){
					showTips('您的邮箱格式错咯~');
				}else if(passwd == ''){
					showTips('请填写您的密码~');
				}else if(passwd2 == ''){
					showTips('请再次输入您的密码~');
				}else if(passwd != passwd2 || passwd2 != passwd){
					showTips('两次密码输入不一致呢~');
				}else if(verifyCode == ''){
					showTips('请输入验证码~');
				}else{
					showTips('提交成功~ 即将进入下一步',2000,1);
					//此处省略 ajax 提交表单 代码...
					
					$.ajax({
						url : "register.action",
						data : {
							"username" : $("#username").val(),
							"password" : $("#password").val()
						},
						success : function(data) {
							//console.log("结果" + data);	
							console.log("数据正确提交到后台");
						},
						error : function(){
                    		alert("系统异常，请稍后重试！");
               			}
					});

				}
			});
			//切换步骤（目前只用来演示）
			$('.processorBox li').click(function(){
				var i = $(this).index();
				$('.processorBox li').removeClass('current').eq(i).addClass('current');
				$('.step').fadeOut(300).eq(i).fadeIn(500);
			});

		});
	</script>		
	</body>
</html>