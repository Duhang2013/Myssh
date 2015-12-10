package com.ssh.dao;

import java.util.List;

import com.ssh.model.Students;

public interface IStudentsDAO {

	// property constants
	public static final String USERNAME = "username";
	public static final String GENDER = "gender";
	public static final String AGE = "age";
	public static final String DEGREE = "degree";
	public static final String REMARK = "remark";

	public abstract void save(Students transientInstance);

	public abstract void delete(Students persistentInstance);

	public abstract Students findById(java.lang.Integer id);

	public abstract List findByExample(Students instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByUsername(Object username);

	public abstract List findByGender(Object gender);

	public abstract List findByAge(Object age);

	public abstract List findByDegree(Object degree);

	public abstract List findByRemark(Object remark);

	public abstract List findAll();

	public abstract Students merge(Students detachedInstance);

	public abstract void attachDirty(Students instance);

	public abstract void attachClean(Students instance);

	/**
	 * Hibernate执行sql语句
	 */
	public List excuteSQL(String sql);

}