<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<title>Duang学堂教育信息管理系统首页</title>

<link rel=stylesheet type=text/css href="css/login/login.css">
<script type="text/javascript" src="js/login/login.js"></script>
 
 <style type="text/css">

</style>
</head>
<script>
	var winWidth = document.body.scrollWidth - 12;      // 取窗口宽度
	var winHeight = document.body.scrollHeight - 24;    // 取窗口高度
	if (winHeight <= 0) winHeight = 640;   // 初始高度并不总能取出，设默认值
	box.style.width = winWidth + 'px';        // 设置宽度
	box.style.height = winHeight + 'px';  
</script>
<body>
	<div id="container">
		<div class="logo" align="center">

		</div>
		<div class="myTitle" align="center">
			<h1>Duang学堂教育信息管理系统</h1>
		</div>
		<div id="box">
			<div class=login id=container>
				<div align="center"></div>
				<s:form action="adminLogin.action" method="post">
					<TABLE class=login-body border=0 cellSpacing=0 cellPadding=0
						style="top: 97px; left: 539px;  height: 174px;">
						<TBODY>
							<TR>
								<TD class=listt align="left" width=100px height=39px></TD>
								<TD align="right" width=80px height=39px><img
									src="./img/login/user.png" />
								</TD>
								<TD class=listt align="right" width=70px height=39px><strong>
										<font face="微软雅黑 Light" color="#000000">用户名：</font> </strong>
								</TD>
								<TD class=listt align="left" width=189px height=39px><input
									style="border:2px;background-color:#b8b8b8" class="inputbg"
									tabindex="1" maxlength="25" title="用户名" name="u.username"></input>
								</TD>
								<TD class=listt align="left" width=100px height=39px></TD>
							</TR>
							<TR>
								<TD class=listt align="left" width=100px height=39px></TD>
								<TD align="right" width=80px height=39px><img
									src="./img/login/key.png" />
								</TD>
								<TD class=listt align="right" width=70px height=39px><strong><font
										face="微软雅黑 Light" color="#000000">密 &nbsp;&nbsp;码：</font> </strong>
								<TD class=listt align="left" width=189px height=39px><input
									style="border:2px;background-color:#b8b8b8" type="password"
									class="inputbg" tabindex="1" maxlength="25" title="密  码"
									name="u.password"> </input>
								</TD>
								<TD class=listt align="left" width=100px height=39px><font
									color="#bcf1cc" face="微软雅黑 Light" size="2px"> <a
										href="findPassword.jsp" style="text-decoration:none">忘记了密码？</a>
								</font>
								</TD>
							</TR>
							<TR>
								<TD class=listt align="left" width=100px height=39px></TD>
								<TD align="right" width=80px height=39px><img
									src="./img/login/multi_users.png" />
								</TD>
								<TD class=listt align="right" width=70px height=39px><strong><font
										face="微软雅黑 Light" color="#000000">身 &nbsp;&nbsp;份：</font> </strong>
								</TD>
								<TD class=listt align="left" width=189px height=39px><input
									type="radio" name="user" value="普通用户"></input> <font
									color="#000000" face="微软雅黑 Light">普通用户</font> &nbsp; <input
									type="radio" name="user" value="管理员"></input> <font
									color="#000000" face="微软雅黑 Light">管理员</font>
								</TD>
								<TD class=listt align="left" width=100px height=39px></TD>
							</TR>
							<TR>
								<TD class=listt align="left" width=100px height=39px></TD>
								<TD class=listt height=39 width=339px align="center" colspan="3">
									<input src="img/login/bottonlogin.jpg" type="image"></input>
								</TD>
								<TD class=listt align="left" width=100px height=39px></TD>
							</TR>
							<TR>
								<TD class=listt align="left" width=100px height=39px></TD>
								<TD class=listt height=39 width=339px align="center" colspan="3">
									<font color="#000000"> <font face="微软雅黑 Light"
										size="2px"> 还没有账号？ </font> <font color="#bcf1cc"
										face="微软雅黑 Light" size="2px"> <a href="register.jsp"
											style="text-decoration:none">立即注册</a> </font>
								</TD>
								<TD class=listt align="left" width=100px height=39px></TD>
							</TR>
						</TBODY>
					</TABLE>
				</s:form>
			</DIV>
		</DIV>
	</div>
</body>
</html>