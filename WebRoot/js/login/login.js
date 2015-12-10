
function checkGotoPageData(currentPage, totalPages)
{
	var VcurrentPage = currentPage.value;
	var VtotalPages = totalPages.value;
	var patrn = /^[1-9][0-9]{0,9}$/;
	if (!patrn.exec(VcurrentPage))
	{
		alert("请输入合法数字！");
		currentPage.focus();
		currentPage.select();
		event.returnValue = false;
		return false;
	}
	if (Number(VcurrentPage) > Number(VtotalPages))
	{
		alert("该页不存在！");
		currentPage.focus();
		currentPage.select();
		event.returnValue = false;
		return false;
	}
}

//登录时检测用户名、密码是否为空
function checkLoginData(adminLoginForm)
{
	if (adminLoginForm.admin.value == "")
	{
		alert("用户名不能为空！");
		admin.focus();
		event.returnValue = false;
		return false;
	}
	if (adminLoginForm.password.value == "")
	{
		alert("密码不能为空！");
		password.focus();
		event.returnValue = false;
		return false;
	}
	if (adminLoginForm.identity.value == "")
	{
		alert("请选择身份登录！");
		identity.focus();
		event.returnValue = false;
		return false;
	}
	
}

function checkLoginData(adminLoginForm)
{
	for(i=0;i<adminLoginForm.length;i++){
		if(adminLoginForm.elements[i].value==""){
			alert("很抱歉，"+adminLoginForm.elements[i].title+"不能为空！");
			adminLoginForm.elements[i].focus();
			return false;
		}
	}	
}

//跳转至某页
function checkGotoPageData(currentPage, totalPages)
{
	var VcurrentPage = currentPage.value;
	var VtotalPages = totalPages.value;
	var patrn = /^[1-9][0-9]{0,9}$/;
	if (!patrn.exec(VcurrentPage))
	{
		alert("请输入合法数字！");
		currentPage.focus();
		currentPage.select();
		event.returnValue = false;
		return false;
	}
	if (Number(VcurrentPage) > Number(VtotalPages))
	{
		alert("该页不存在！");
		currentPage.focus();
		currentPage.select();
		event.returnValue = false;
		return false;
	}
}