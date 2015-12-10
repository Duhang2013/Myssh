package com.ssh.dao;

import java.sql.Connection;
import java.util.List;

import com.ssh.model.Users;

public interface IUsersDAO {

	// property constants
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";

	public abstract void save(Users transientInstance);

	public abstract void delete(Users persistentInstance);

	public abstract Users findById(java.lang.Integer id);

	public abstract List findByExample(Users instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByUsername(Object username);

	public abstract List findByPassword(Object password);

	public abstract List findAll();

	public abstract Users merge(Users detachedInstance);

	public abstract void attachDirty(Users instance);

	public abstract void attachClean(Users instance);

	/**
	 * Hibernate执行sql语句
	 */
	public List excuteSQL(String sql);

}