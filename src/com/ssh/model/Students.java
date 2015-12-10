package com.ssh.model;

/**
 * 
 * @author 杜航
 * @function:Students entity.
 * 
 */

public class Students implements java.io.Serializable {

	/** Fields */

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String username;
	private String gender;
	private String age;
	private String degree;
	private String remark;

	/** Constructors */
	/** default constructor */
	public Students() {
	}

	/** full constructor */
	public Students(Integer id, String username, String gender, String age,
			String degree, String remark) {
		this.id = id;
		this.username = username;
		this.gender = gender;
		this.age = age;
		this.degree = degree;
		this.remark = remark;
	}

	/** Property accessors */
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

}