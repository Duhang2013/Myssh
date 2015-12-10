package com.ssh.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssh.model.Users;

import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ssh.service.IUsersService;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UsersAction extends ActionSupport {

	private static final Logger log = LoggerFactory
			.getLogger(UsersAction.class);
	private Users u = new Users();
	private List<Users> userList = new ArrayList<Users>();
	private static IUsersService usersService = null;
	private List list = null;
	private String name;
	private String id;
	private String username;
	private String password;

	boolean nameError;
	boolean pswError;
	/**
	 * 服务器模式中不返回的4个参数：draw recordsTotal recordsFiltered data
	 */
	private String draw = "0"; // 总共记录数
	private String recordsTotal; // 过滤后记录数
	private String recordsFiltered; // 表中中需要显示的数据
	private List<Users> data; // 请求次数

	private String start; // 数据起始位置
	private String length; // 数据长度
	private String orderDir = "asc"; // 获取排序方式 默认为asc
	private String[] cols = { "id", "username", "password" };// 定义列名
	private String orderColumn = "0"; // 获取客户端需要那一列排序
	private String searchValue; // 获取用户过滤框里的字符

	private String table = "sshexample.users";// 所要查询的表名 注意加上数据库的名字

	/*
	 * 杜航 
	 * 功能：根据上下文获得UsersService对象
	 */
	public IUsersService getUsersService() {
		if (usersService == null) {
			ServletContext sc = ServletActionContext.getServletContext();
			ApplicationContext ac = WebApplicationContextUtils
					.getRequiredWebApplicationContext(sc);
			usersService = (IUsersService) ac.getBean("UsersService");
			if (usersService == null)
				System.out.println("error");
		}
		return usersService;
	}

	/*
	 * 杜航 
	 * 功能：添加用户
	 */
	public String addUser() throws Exception {
		try {
			getUsersService().addUsers(u);
			System.out.println("用户名：" + u.getUsername());
			System.out.println("密码：" + u.getPassword());
		} catch (Exception e) {
			log.error("保存用户错误", e);
			return ERROR;
		}
		return SUCCESS;
	}

	/*
	 * 杜航 
	 * 功能：删除用户
	 */
	public String delUser() throws Exception {
		try {
			u.setUsername("");
			u.setPassword("");
			getUsersService().delUsers(u);
		} catch (Exception e) {
			log.error("删除用户错误", e);
			return ERROR;
		}
		return SUCCESS;
	}

	/*
	 * 杜航 
	 * 功能：编辑用户信息
	 */
	public String editUser() throws Exception {
		if (u == null) {
			System.out.println("null");
		}
		try {

			getUsersService().updateUsers(u);
		} catch (Exception e) {
			log.error("编辑用户错误", e);
			return ERROR;
		}
		return SUCCESS;
	}

	/*
	 * 杜航 
	 * 功能：列表显示用户
	 */
	public String listUser() throws Exception {
		userList = getUsersService().findAll();
		// 将Action中的Hibernate查询结果LIST输出在控制台：
		// for(Users user :userList){
		// System.out.print(user.getId()+"\t");
		// System.out.print(user.getUsername()+"\t");
		// System.out.println(user.getPassword());
		// }
		return SUCCESS;

	}

	/*
	 * 杜航 
	 * 功能：用户登录
	 */
	public String login() throws Exception {
//		System.out.println(u.getUsername() + " " + u.getPassword());
		try {
			Users loginUser = getUsersService().login(u.getUsername(), u.getPassword());
			if (null != loginUser) {
				ActionContext context = ActionContext.getContext();
				context.getSession().put("LOGINUSER", loginUser);
				return SUCCESS;
			} else {
				return ERROR;
			}
		} catch (Exception e) {
			log.error("登录查询错误", e);
			return ERROR;
		}
	}
	

	/** 前后台实现Json数据传递 */
	/*
	 * 杜航 
	 * 功能：新增用户登录
	 */
	public void newLogin() throws Exception {
		try {
			Users user = new Users();
			System.out.println("输入的用户名" + username + " " + "输入的密码" + password);
			// 解决中文乱码问题
			username = new String(username.getBytes("ISO-8859-1"),"UTF-8");
			password = new String(password.getBytes("ISO-8859-1"),"UTF-8");
			user = usersService.findByName(username); // 根据唯一的用户名找到用户记录
			System.out.println("返回的用户名" + user.getUsername() + " " + "返回的密码" + user.getPassword());

			if (user.getUsername() != null) { // 存在所输入的用户（如果不存在用户则直接进入catch语句）
				if (user.getPassword().equals(password)) {// 此处不能用==验证
					System.out.println("登陆成功");
					nameError = false;
					pswError = false;
				} else {
					System.out.println("密码错误");
					nameError = false;
					pswError = true;
				}
			}
		} catch (Exception e) {
			log.error("登录查询错误", e);
			System.out.println("用户名不存在");
			nameError = true;
			pswError = false;
			e.printStackTrace();
		}finally{
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = null;
			try {
				out = response.getWriter();
			} catch (IOException e) {
				e.printStackTrace();
			}

			Map<Object, Object> info = new HashMap<Object, Object>();
			info.put("nameError", nameError);
			info.put("pswError", pswError);
			String json = new Gson().toJson(info);
			System.out.println(json);
			out.println(json);

			out.flush();
			out.close();

		}
		
		
	}

	/*
	 * 杜航 
	 * 功能：获得所有用户的信息
	 */
	public void getAllUser() throws NumberFormatException, SQLException {

		HttpServletRequest request = ServletActionContext.getRequest();
		draw = request.getParameter("draw");
		start = request.getParameter("start");
		length = request.getParameter("length");
		orderColumn = request.getParameter("order[0][column]");
		orderColumn = cols[Integer.parseInt(orderColumn)];
		orderDir = request.getParameter("order[0][dir]");
		searchValue = request.getParameter("search[value]");

		// 解决request请求返回时中文乱码
		try {
			searchValue = new String(searchValue.getBytes("ISO-8859-1"),
					"utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// 打印请求回来的数据
		System.out.print("draw:" + draw + "\t" + "start:" + start + "\t"
				+ "length:" + length + "\t" + "orderColumn:" + orderColumn
				+ "\t" + "orderDir:" + orderDir + "\t");
		// 打印查询框的内容
		System.out.println("searchValue:" + searchValue);

		List<String> sArray = new ArrayList<String>();
		if (!searchValue.equals("")) {
			sArray.add(" id like '%" + searchValue + "%'");
			sArray.add(" username like '%" + searchValue + "%'");
			sArray.add(" password like '%" + searchValue + "%'");
		}

		String individualSearch = ""; // 个别查询
		if (sArray.size() == 1) {
			individualSearch = sArray.get(0);
		} else if (sArray.size() > 1) {
			for (int i = 0; i < sArray.size() - 1; i++) {
				individualSearch += sArray.get(i) + " or ";
			}
			individualSearch += sArray.get(sArray.size() - 1);
		}

		List<Users> users = new ArrayList<Users>();

		String recordsFilteredSql = "select count(1) as recordsFiltered from "
				+ table;
		// 获取数据库总记录数
		String recordsTotalSql = "select count(1) as recordsTotal from "
				+ table;
		list = usersService.excuteSQL(recordsTotalSql);
		recordsTotal = String.valueOf(list.size());
		System.out.println("总记录数recordsTotal为：" + recordsTotal);

		String searchSQL = "";
		String sql = "SELECT * FROM " + table;
		if (individualSearch != "") {
			searchSQL = " where " + individualSearch;
		}
		sql += searchSQL;
		recordsFilteredSql += searchSQL;
		sql += " order by " + orderColumn + " " + orderDir;
		recordsFilteredSql += " order by " + orderColumn + " " + orderDir;
		sql += " limit " + start + ", " + length; // 用来分页

		list = usersService.excuteSQL(sql);

		// 取出list的每条记录的每个数据项，存放在数组objects中
		for (int i = 0; i < list.size(); i++) {
			Object[] objects = (Object[]) list.get(i);
			// 打印记录
//			for (int j = 0; j < objects.length; j++) {
//				System.out.print(objects[j] + "\t");
//			}
//			System.out.println("");
			users.add(new Users(Integer.parseInt(objects[0].toString()),
					objects[1].toString(), objects[2].toString()));
		}
		

		// 将Action中的Hibernate查询结果LIST输出在控制台：可以观察到，输出的记录是重复的length条.
		// 已解决，问题出现上面的while种，采用构造方法赋值没问题，采用set有问题
//		 for(Users u :users){
//		 System.out.print(u.getId()+"\t");
//		 System.out.print(u.getUsername()+"\t");
//		 System.out.println(u.getPassword());
//		 }

		if (searchValue != "") {

			list = usersService.excuteSQL(recordsFilteredSql);
			recordsFilteredSql = String.valueOf(list.size());
		} else {
			recordsFiltered = recordsTotal;
		}
		// System.out.println("过滤后记录数：" + recordsFiltered);

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Map<Object, Object> info = new HashMap<Object, Object>();
		info.put("data", users);
		info.put("recordsTotal", recordsTotal);
		info.put("recordsFiltered", recordsFiltered);
		info.put("draw", draw);
		String json = new Gson().toJson(info);
		out.println(json);

		out.flush();
		out.close();

	}

	/*
	 * 杜航 
	 * 功能：增添用户
	 */
	public void addUserJson() throws Exception {
		Users user = new Users();
		// 解决中文乱码问题
		username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
		password = new String(password.getBytes("ISO-8859-1"), "UTF-8");
		user.setUsername(username);
		user.setPassword(password);
//		System.out.println("新用户：" + username + "\t" + "用户密码：" + password);
		usersService.addUsers(user);

		JSONObject json = new JSONObject();
		json.put("status", "success");

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}

		out.println(json);
//		System.out.println("json数据：" + json);
		out.flush();
		out.close();

	}

	/*
	 * 杜航 
	 * 功能：删除用户
	 */
	public void delUserJson() throws Exception {
		Users user = new Users();
		user.setId(Integer.parseInt(id));
		user.setUsername("");
		user.setPassword("");
//		System.out.println("选中的ID：" + id);
		usersService.delUsers(user);

		JSONObject json = new JSONObject();
		json.put("status", "success");

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}

		out.println(json);
		System.out.println("json数据：" + json);
		out.flush();
		out.close();

	}

	/*
	 * 杜航 
	 * 功能：编辑用户
	 */
	public void editUserJson() throws Exception {
		Users user = new Users();
		// 解决中文乱码问题
		username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
		password = new String(password.getBytes("ISO-8859-1"), "UTF-8");
		user.setId(Integer.parseInt(id));
		user.setUsername(username);
		user.setPassword(password);
//		System.out.println("新用户：" + username + "\t" + "用户密码：" + password);
		usersService.updateUsers(user);

		JSONObject json = new JSONObject();
		json.put("status", "success");

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}

		out.println(json);
		System.out.println("json数据：" + json);
		out.flush();
		out.close();

	}

	/*
	 * 杜航 
	 * 功能：用户注册
	 */
	public void register() throws Exception {
		Users user = new Users();
		// 解决中文乱码问题
		username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
		password = new String(password.getBytes("ISO-8859-1"), "UTF-8");
		user.setUsername(username);
		user.setPassword(password);
//		System.out.println("新用户：" + username + "\t" + "用户密码：" + password);
		usersService.addUsers(user);

		JSONObject json = new JSONObject();
		json.put("status", "success");

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}

		out.println(json);
		// System.out.println("json数据：" + json);
		out.flush();
		out.close();

	}

	/*
	 * 杜航 
	 * 功能：新增用户修改密码 （根据用户名以及注册邮箱（验证身份）来修改密码）
	 */
	public void modPassword() throws Exception {
		Users user = new Users();
		// 解决中文乱码问题
		username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
		password = new String(password.getBytes("ISO-8859-1"), "UTF-8");
		user = usersService.findByName(username); // 根据唯一的用户名找到用户记录
		user.setPassword(password); // 设置新的密码
//		System.out.println("新用户：" + username + "\t" + "用户密码：" + password);
		usersService.updateUsers(user);

		JSONObject json = new JSONObject();
		json.put("status", "success");

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}

		out.println(json);
		out.flush();
		out.close();

	}

	/*
	 * 杜航 
	 * 功能：获得studentsService对象
	 */
	public void setUsersService(IUsersService usersService) {
		this.usersService = usersService;
	}

	public Users getU() {
		return u;
	}

	public void setU(Users u) {
		this.u = u;
	}

	public List<Users> getUserList() {
		return userList;
	}

	public void setUserList(List<Users> userList) {
		this.userList = userList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isNameError() {
		return nameError;
	}

	public void setNameError(boolean nameError) {
		this.nameError = nameError;
	}

	public boolean isPswError() {
		return pswError;
	}

	public void setPswError(boolean pswError) {
		this.pswError = pswError;
	}

	public String getDraw() {
		return draw;
	}

	public void setDraw(String draw) {
		this.draw = draw;
	}

	public String getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(String recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public String getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(String recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public List<Users> getData() {
		return data;
	}

	public void setData(List<Users> data) {
		this.data = data;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getOrderDir() {
		return orderDir;
	}

	public void setOrderDir(String orderDir) {
		this.orderDir = orderDir;
	}

	public String[] getCols() {
		return cols;
	}

	public void setCols(String[] cols) {
		this.cols = cols;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

}