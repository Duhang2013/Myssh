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

import com.ssh.model.Students;

import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ssh.service.IStudentsService;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;

public class StudentsAction extends ActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory
			.getLogger(StudentsAction.class);
	private Students u = new Students();
	private List<Students> studentList = new ArrayList<Students>();
	private List list = null;
	private static IStudentsService studentsService = null;
	private String name;

	private Integer id;
	private String username;
	private String gender;
	private String age;
	private String degree;
	private String remark;

	/**
	 * 服务器模式中不返回的4个参数：draw recordsTotal recordsFiltered data
	 */
	private String draw = "0"; // 总共记录数
	private String recordsTotal; // 过滤后记录数
	private String recordsFiltered; // 表中中需要显示的数据
	private List<Students> data; // 请求次数

	private String start; // 数据起始位置
	private String length; // 数据长度
	private String orderDir = "asc"; // 获取排序方式 默认为asc
	private String[] cols = { "id", "username", "gender", "age", "degree",
			"remark" };// 定义列名
	private String orderColumn = "0"; // 获取客户端需要那一列排序
	private String searchValue; // 获取用户过滤框里的字符

	private String table = "sshexample.students";// 所要查询的表名 注意加上数据库的名字
	
	/*
	 * 杜航 
	 * 功能：根据上下文获得studentsService对象
	 */
	public IStudentsService getStudentsService() {
		if (studentsService == null) {
			ServletContext sc = ServletActionContext.getServletContext();
			ApplicationContext ac = WebApplicationContextUtils
					.getRequiredWebApplicationContext(sc);
			studentsService = (IStudentsService) ac.getBean("StudentsService");
			if (studentsService == null)
				System.out.println("error");
		}
		return studentsService;
	}

	/*
	 * 杜航 
	 * 功能：添加学生信息
	 */
	public String addStudent() throws Exception {
		try {
			getStudentsService().addStudents(u);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("增添学生错误", e);
			return ERROR;
		}
		return SUCCESS;
	}

	/*
	 * 杜航 
	 * 功能：删除学生信息
	 */
	public String delStudent() throws Exception {
		try {
			u.setUsername("");
			getStudentsService().delStudents(u);
		} catch (Exception e) {
			log.error("删除学生错误", e);
			return ERROR;
		}
		return SUCCESS;
	}

	/*
	 * 杜航 
	 * 功能：编辑学生信息
	 */
	public String editStudent() throws Exception {
		if (u == null) {
			System.out.println("null");
		}
		try {

			getStudentsService().updateStudents(u);
		} catch (Exception e) {
			log.error("编辑学生信息错误", e);
			return ERROR;
		}
		return SUCCESS;
	}

	/*
	 * 杜航 
	 * 功能：列表显示学生信息
	 */
	public String listStudent() throws Exception {
		studentList = getStudentsService().findAll();
		//将用户信息打印在控制台上
		for (Students student : studentList) {
			System.out.print(student.getId() + "\t");
			System.out.print(student.getUsername() + "\t");
			System.out.print(student.getGender() + "\t");
			System.out.print(student.getAge() + "\t");
			System.out.print(student.getDegree() + "\t");
			System.out.println(student.getRemark());
		}
		return SUCCESS;

	}

	/** 前后台实现Json数据传递 */
	/*
	 * 杜航 
	 * 功能：获得所有学生的信息
	 */
	public void getAllStudent() throws NumberFormatException, SQLException {

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

		System.out.println("Datatables服务器处理请求的数据：");
		// 打印请求回来的数据
		System.out.print("\t" + "draw:" + draw + "\t" + "start:" + start + "\t"
				+ "length:" + length + "\t" + "orderColumn:" + orderColumn
				+ "\t" + "orderDir:" + orderDir + "\t");
		// 打印查询框的内容
		System.out.println("searchValue:" + searchValue);

		List<String> sArray = new ArrayList<String>();
		if (!searchValue.equals("")) {
			sArray.add(" id like '%" + searchValue + "%'");
			sArray.add(" username like '%" + searchValue + "%'");
			sArray.add(" gender like '%" + searchValue + "%'");
			sArray.add(" age like '%" + searchValue + "%'");
			sArray.add(" degree like '%" + searchValue + "%'");
			sArray.add(" remark like '%" + searchValue + "%'");
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

		List<Students> students = new ArrayList<Students>();

		String recordsFilteredSql = "select count(1) as recordsFiltered from "
				+ table;
		// 获取数据库总记录数
		String recordsTotalSql = "select count(1) as recordsTotal from "
				+ table;
		list = studentsService.excuteSQL(recordsTotalSql);
		recordsTotal = String.valueOf(list.size());
//		System.out.println("总记录数：" + recordsTotal);

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
		
		list = studentsService.excuteSQL(sql);

		// 取出list的每条记录的每个数据项，存放在数组objects中
		for (int i = 0; i < list.size(); i++) {
			Object[] objects = (Object[]) list.get(i);
			// 打印记录
//			for (int j = 0; j < objects.length; j++) {
//				System.out.print(objects[j] + "\t");
//			}
//			System.out.println("");
			students.add(new Students(Integer.parseInt(objects[0].toString()),
					objects[1].toString(), objects[2].toString(), objects[3]
							.toString(), objects[4].toString(), objects[5]
							.toString()));
		}

		if (searchValue != "") {

			list = studentsService.excuteSQL(recordsFilteredSql);
			recordsFilteredSql = String.valueOf(list.size());
		} else {
			recordsFiltered = recordsTotal;
		}
//		System.out.println("过滤后记录数：" + recordsFiltered);
		
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
		info.put("data", students);
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
	 * 功能：增添学生信息
	 */
	public void addStudentJson() throws Exception {
		Students student = new Students();
		// 解决中文乱码问题
		username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
		gender = new String(gender.getBytes("ISO-8859-1"), "UTF-8");
		age = new String(age.getBytes("ISO-8859-1"), "UTF-8");
		degree = new String(degree.getBytes("ISO-8859-1"), "UTF-8");
		remark = new String(remark.getBytes("ISO-8859-1"), "UTF-8");
		student.setUsername(username);
		student.setGender(gender);
		student.setAge(age);
		student.setDegree(degree);
		student.setRemark(remark);
		System.out.println("新用户：" + username + "\t" + "性别：" + gender);
		studentsService.addStudents(student);

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
	 * 功能：删除学生信息
	 */
	public void delStudentJson() throws Exception {
		Students student = new Students();
		student.setId(id);
		System.out.println("选中的ID：" + id);
		student.setUsername("");
		student.setGender("");
		student.setAge("");
		student.setDegree("");
		student.setRemark("");
		studentsService.delStudents(student);

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
	 * 功能：编辑学生信息
	 */
	public void editStudentJson() throws Exception {
		Students student = new Students();
		// 解决中文乱码问题
		username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
		gender = new String(gender.getBytes("ISO-8859-1"), "UTF-8");
		age = new String(age.getBytes("ISO-8859-1"), "UTF-8");
		degree = new String(degree.getBytes("ISO-8859-1"), "UTF-8");
		remark = new String(remark.getBytes("ISO-8859-1"), "UTF-8");
		student.setId(id);
		student.setUsername(username);
		student.setGender(gender);
		student.setAge(age);
		student.setDegree(degree);
		student.setRemark(remark);
		studentsService.updateStudents(student);

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
	 * 功能：获得studentsService对象
	 */
	public void setStudentsService(IStudentsService studentsService) {
		this.studentsService = studentsService;
	}

	public Students getU() {
		return u;
	}

	public void setU(Students u) {
		this.u = u;
	}

	public List<Students> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<Students> studentList) {
		this.studentList = studentList;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public List<Students> getData() {
		return data;
	}

	public void setData(List<Students> data) {
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