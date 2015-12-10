package com.ssh.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.ssh.model.Users;

/**
 * A data access object (DAO) providing persistence and search support for Users
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.ssh.model.Users
 * @author MyEclipse Persistence Tools
 */

public class UsersDAO extends HibernateDaoSupport implements IUsersDAO {
	private static final Logger log = LoggerFactory.getLogger(UsersDAO.class);

	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost/?characterEncoding=UTF8";
	private static String user = "root";
	private static String password = "admin";
	private Connection conn;

	protected void initDao() {
	}

	/*
	 * 杜航 
	 * 功能：保存Users对象
	 */
	@Override
	public void save(Users transientInstance) {
		log.debug("saving Users instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/*
	 * 杜航 
	 * 功能：删除Users对象
	 */
	@Override
	public void delete(Users persistentInstance) {
		log.debug("deleting Users instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/*
	 * 杜航 
	 * 功能：根据Id属性，查找Users对象
	 */
	@Override
	public Users findById(java.lang.Integer id) {
		log.debug("getting Users instance with id: " + id);
		try {
			Users instance = (Users) getHibernateTemplate().get(
					"com.ssh.model.Users", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/*
	 * 杜航 
	 * 功能：根据example，查找Users对象
	 */
	@Override
	public List findByExample(Users instance) {
		log.debug("finding Users instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	/*
	 * 杜航 
	 * 功能：根据属性，查找Users对象
	 */
	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Users instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Users as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/*
	 * 杜航 
	 * 功能：根据username属性，查找Users对象
	 */
	@Override
	public List findByUsername(Object username) {
		return findByProperty(USERNAME, username);
	}

	/*
	 * 杜航 
	 * 功能：根据password属性，查找Users对象
	 */
	@Override
	public List findByPassword(Object password) {
		return findByProperty(PASSWORD, password);
	}

	/*
	 * 杜航 
	 * 功能：查找所有的Users对象
	 */
	@Override
	public List findAll() {
		log.debug("finding all Users instances");
		try {
			String queryString = "from Users";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/*
	 * 杜航 
	 * 功能：将传入的detached(分离的)状态的Users对象的属性复制到持久化对象中，并返回该持久化对象
	 * 如果该session中没有关联的持久化对象，加载一个，如果传入对象未保存，保存一个副本并作为持久对象返回，传入对象依然保持detached状态。
	 */
	@Override
	public Users merge(Users detachedInstance) {
		log.debug("merging Users instance");
		try {
			Users result = (Users) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/*
	 * 杜航 
	 * 功能：将Users对象持久化并保存
	 * 如果对象未保存(Transient状态)，调用save方法保存。如果对象已保存(Detached状态
	 * )，调用update方法将对象与Session重新关联
	 */
	@Override
	public void attachDirty(Users instance) {
		log.debug("attaching dirty Users instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/*
	 * 杜航 
	 * 功能：将传入的Users对象设置为transient(暂时的)状态
	 */
	@Override
	public void attachClean(Users instance) {
		log.debug("attaching clean Users instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/*
	 * 杜航 
	 * 功能：得到UsersDAO上下文对象
	 */
	public static IUsersDAO getFromApplicationContext(ApplicationContext ctx) {
		return (IUsersDAO) ctx.getBean("UsersDAO");
	}

	/*
	 * 杜航 
	 * 功能：Hibernate执行sql语句
	 */
	@Override
	public List excuteSQL(String sql) {

		List list = new ArrayList();
		Query query = getSession().createSQLQuery(sql);
		list = (List) query.list();
		
		return list;
	}

}