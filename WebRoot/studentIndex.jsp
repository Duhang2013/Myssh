<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<link rel=stylesheet type=text/css href="css/studentIndex.css">
<title>管理学生信息（增、删、查、改、分页）</title>

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

<!--  
<style type="text/css">
#myModal {
	margin: auto auto;
}
</style>
-->
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
					<th>姓名</th>
					<th>性别</th>
					<th>年龄</th>
					<th>学历</th>
					<th>备注</th>
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
						<input type="text" class="form-control" id="gender"
							name="u.gender" placeholder="性别">
					</div>
					<div class="form-group">
						<input type="text" class="form-control" id="age" name="u.age"
							placeholder="年龄">
					</div>
					<div class="form-group">
						<input type="text" class="form-control" id="degree"
							name="u.degree" placeholder="学历">
					</div>
					<div class="form-group">
						<input type="text" class="form-control" id="remark"
							name="u.remark" placeholder="备注">
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
	var selectedId,selectedUsername,selectedGender,selectedAge,selectedDegree,selectedRemark;
	$(document).ready(function() {
		table = $('#example').DataTable({
			"processing": true,
	        "serverSide": true,
	        "ajax": {
            		"url": "getAllStudent.action",
            		"type": "get"
        	},
	        "columns": [
	            { "data": "id" },
	            { "data": "username" },
	            { "data": "gender" },
	            { "data": "age" },
	            { "data": "degree" },
	            { "data": "remark" }
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
			selectedGender = table.row('.selected').data().gender;
			selectedAge = table.row('.selected').data().age;
			selectedDegree = table.row('.selected').data().degree;
			selectedRemark = table.row('.selected').data().remark;
			console.log("选中行的id:"+ selectedId);
			$("#username").val(selectedUsername);
			//setRadioBoxByValue("u.gender",selectedGender);
			$("#gender").val(selectedGender);
			$("#age").val(selectedAge);
			$("#degree").val(selectedDegree);
			$("#remark").val(selectedRemark);
    	} );
	
	});
	
	/**
     * 添加模拟数据
     */
    function addInitData() {
        $("#username").val("Inputting");
		$("#gender").val("男");
		$("#age").val("18");
		$("#degree").val("高中");
		$("#remark").val("( ^_^ )/~~拜拜");
		$("#myModal").modal("show");
    }
	
	/**
     * 清除
     */
    function clearData() {
        $("#username").val("");
		$("#gender").val("");
		$("#age").val("");
		$("#degree").val("");
		$("#remark").val("");
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
					//"gender" : getRadioBoxValue("u.gender"),
					"gender"   : $("#gender").val(),
					"age" 	   : $("#age").val(),
					"degree"   : $("#degree").val(),
					"remark"   : $("#remark").val()
				},
				success : function(data) {
					console.log("结果" + data);
					clearData();
					$("#myModal").modal("hide");
					$("#myModalLabel").text("新增");
					table.ajax.reload();		
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
		url = "addStudentJson.action";
	}
	
	/**
	 * 删除数据
	 **/
	function delData() {
		var flag = confirm("确定删除么？");
		if(flag){
			$.ajax({
				url : "delStudentJson.action",
				data : {
					"id" : selectedId
				},
				success : function(data) {
					//console.log("结果" + data);
					table.ajax.reload();		
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
		url = "editStudentJson.action";
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
    	if($("#gender").val()==""){
    		alert("亲~性别不能为空哦( ^_^ )");
    		checkFlag = false;
    	}
    	if($("#age").val()==""){
    		alert("亲~年龄不能为空哦( ^_^ )");
    		checkFlag = false;
    	}
    	if($("#degree").val()==""){
    		alert("亲~学历不能为空哦( ^_^ )");
    		checkFlag = false;
    	}
		$("#myModal").modal("show");
		return checkFlag;
    }
	
	/**
	 * 取得Radio被选中的值
	 **/
	function getRadioBoxValue(radioName) {
		var value;
		console.log("1");
		var obj = document.getElementsByName(radioName);  //这个是以标签的name来取控件
		console.log("2");
		for(i=0; i<obj.length;i++)    {
			if(obj[i].checked)    { 
				value = obj[i].value;
				//console.log("函数内部radioValue"+value);
				break;
			} 
		} 
		return   value;           
	}
	
	/**
	 * 通过值修改所选中的单选按钮Radio的状态
	 **/
	function setRadioBoxByValue(radioName,radioValue){        //传入radio的name和选中项的值
		var obj = document.getElementsByName(radioName);
		for(var i=0;i<obj.length;i++){
        	if(obj[i].value==radioValue){
         		obj[i].checked=true; //修改选中状态
         		break; //停止循环
        	}
		}
}

</script>








