<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>

<%
   String path = request.getContextPath();
   String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<link rel=stylesheet type=text/css href="css/studentIndex.css">
<title>管理用户信息（增、删、查、改、分页）</title>

<!-- CDN -->
<!-- DataTables CSS -->
<!-- 
<link rel="stylesheet" type="text/css"
	href="http://cdn.datatables.net/1.10.7/css/jquery.dataTables.css">
 -->
<!-- jQuery -->
<!-- 
<script type="text/javascript" charset="utf8"
	src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
 -->
<!-- DataTables -->
<!-- 
<script type="text/javascript" charset="utf8"
	src="http://cdn.datatables.net/1.10.7/js/jquery.dataTables.js"></script>
 -->

<!-- 本地 -->
<!-- DataTables CSS -->
<link rel="stylesheet" type="text/css" href="css/jquery.dataTables.css">

<!-- jQuery -->
<script type="text/javascript" charset="utf8" src="js/jquery.js"></script>

<!-- DataTables JS -->
<script type="text/javascript" charset="utf8"
	src="js/jquery.dataTables.js"></script>


<!-- Bootstrap CSS -->

<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">

<!-- jQuery -->
<!--添加该js会出问题：Uncaught TypeError: Object [object Object] has no method 'DataTable'
<script type="text/javascript"charset="utf8"src="js/jquery-1.11.3.min.js"></script>
 -->
<!-- Bootstrap JS -->

<script type="text/javascript" charset="utf8" src="js/bootstrap.min.js"></script>



<style type="text/css">
#myModal {
	margin: auto auto;
}
</style>

</head>
<body>
	<div class="myTitle" align="center">
		<h1>Duang学堂教育信息管理系统</h1>
	</div>
	<div class="container table-responsive">
		<!-- class="table-responsive"：设置响应式设计 -->
		<table id="example" class="table table-striped table-bordered">
			<thead>
				<tr>
					<th>ID</th>
					<th>用户名</th>
					<th>密码</th>
				</tr>
			</thead>
			<tbody>

			</tbody>
			<!-- tbody是必须的 -->
		</table>
	</div>
	<div id="myModal" class="modal fade" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">新增</h4>
				</div>
				<div id="modal-body" align="center">
					<div class="form-group">
						<input type="text" class="form-control" id="username"
							name="u.username" placeholder="姓名">
					</div>
					<div class="form-group">
						<input type="text" class="form-control" id="password"
							name="u.password" placeholder="密码">
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" id="addInitData"
							onclick="addInitData()">添加模拟数据</button>
						<button type="button" class="btn btn-default" id="clearData"
							onclick="clearData()">清空</button>
						<button type="button" class="btn btn-primary" id="save"
							onclick="save()">保存</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

<script type="text/javascript">
	var table;
	var url;
	var selectedId,selectedUsername,selectedPassword;
	$(document).ready(function() {
		table = $('#example').DataTable({
			"processing": true,
	        "serverSide": true,
	        "ajax": {
            		"url": "getAllUser.action",
            		"type": "get"
        	},
	        "columns": [
	            { "data": "id" },
	            { "data": "username" },
	            { "data": "password" }
	        ],
	        "language": {									//语言国际化
                "lengthMenu": "_MENU_ 条记录每页",				//'每页显示记录'的下拉框的提示信息
                "zeroRecords": "没有找到记录",					//当搜索结果为空时，显示的信息
                "info": "第 _PAGE_ 页 ( 总共 _PAGES_ 页 )",		//表格的page分页所需显示的所有字符串
                "infoEmpty": "无记录",						//当表格没有数据时，汇总地方显示的字符串
                "infoFiltered": "(从 _MAX_ 条记录过滤)",			//当表格搜索后，在汇总字符串上需要增加的内容
                "paginate": {								//分页信息显示所需的字符串的所有信息
                    "previous": "上一页",						//分页信息的 'previous' 按钮显示的信息
                    "next": "下一页"							//分页信息的 'next' 按钮显示的信息
                }
            },
            "dom": "<'row'<'col-xs-2'l><'#mytool.col-xs-4'><'col-xs-6'f>r>" +		//通过一个自定义的字符串来定义DataTables里面所有组件的显示,位置和显隐.
                    "t" +
                    "<'row'<'col-xs-6'i><'col-xs-6'p>>",
            initComplete: function () {//初始化结束后的回调函数
                $("#mytool").append('<button type="button" class="btn btn-primary btn-sm" data-toggle="modal" onclick="addData()" >添加</button>&nbsp');
                $("#mytool").append('<button type="button" class="btn btn-success btn-sm" data-toggle="modal" onclick="delData()" >删除</button>&nbsp');
                $("#mytool").append('<button type="button" class="btn btn-info btn-sm" data-toggle="modal" onclick="editData()">修改</button>&nbsp');
            }
		});
		
		//选中行，然后返回其id
		$('#example tbody').on( 'click', 'tr', function () {
	        if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	            table.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
			selectedId = table.row('.selected').data().id;
			selectedUsername = table.row('.selected').data().username;
			selectedPassword = table.row('.selected').data().password;
			console.log("选中行的id:"+ selectedId);
			$("#username").val(selectedUsername);
			$("#password").val(selectedPassword);
    	} );
	
	});
	
	/**
     * 添加模拟数据
     */
    function addInitData() {
        $("#username").val("admin");
		$("#password").val("123456");
		$("#myModal").modal("show");
    }
	
	/**
     * 清除
     */
    function clearData() {
        $("#username").val("");
		$("#password").val("");
		$("#myModal").modal("show");
    }
    
    /**
     * 保存
     */
    function save() {
    	if(checkData()){
    		$.ajax({
				url : url,
				data : {
					"id"       : selectedId,//修改数据时需要返回其id
					"username" : $("#username").val(),
					"password"   : $("#password").val()
				},
				success : function(data) {
					console.log("结果" + data.status);
					clearData();
					$("#myModal").modal("hide");
					$("#myModalLabel").text("新增");
					table.ajax.reload();		
				},
				error : function(){
                    alert("系统异常，请稍后重试！");
               }
			});
    	}
    }

	/**
	 * 添加数据
	 **/
	function addData() {
		$("#myModal").modal("show");
		$("#myModalLabel").text("新增");
		url = "addUserJson.action";
	}
	
	/**
	 * 删除数据
	 **/
	function delData() {
		var flag = confirm("确定删除么？");
		if(flag){
			$.ajax({
				url : "delUserJson.action",
				data : {
					"id" : selectedId
				},
				success : function(data) {
					//console.log("结果" + data);
					table.ajax.reload();		
				},
				error : function(){
                    alert("系统异常，请稍后重试！");
               }
			});
		}
		
	}


	/**
	 * 编辑数据
	 **/
	function editData() {
		$("#myModal").modal("show");
		$("#myModalLabel").text("修改");
		url = "editUserJson.action";
	}
	
	/**
     * 检查数据格式是否符合要求
     * 此处只检验是否为空，为空则提示数据为空，请输入数据。
     */
    function checkData() {
    	var checkFlag = true;
    	if($("#username").val()==""){
    		alert("亲~姓名不能为空哦( ^_^ )");
			checkFlag = false;
    	}
    	if($("#password").val()==""){
    		alert("亲~密码不能为空哦( ^_^ )");
    		checkFlag = false;
    	}
		$("#myModal").modal("show");
		return checkFlag;
    }

</script>