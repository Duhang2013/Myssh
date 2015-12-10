package com.ssh.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.ssh.model.Students;

/**
 * 
 * @author 杜航
 * @function:学生接口实现类
 * 
 */

public class StudentsDAO extends HibernateDaoSupport implements IStudentsDAO {
	private static final Logger log = LoggerFactory.getLogger(StudentsDAO.class);

	protected void initDao() {
	}

	/*
	 * 杜航 
	 * 功能：保存Students对象
	 */
	@Override
	public void save(Students transientInstance) {
		log.debug("saving Students instance");
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
	 * 功能：删除Students对象
	 */
	@Override
	public void delete(Students persistentInstance) {
		log.debug("deleting Students instance");
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
	 * 功能：根据Id属性，查找Students对象
	 */
	@Override
	public Students findById(java.lang.Integer id) {
		log.debug("getting Students instance with id: " + id);
		try {
			Students instance = (Students) getHibernateTemplate().get(
					"com.ssh.model.Students", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/*
	 * 杜航 
	 * 功能：根据属性，查找Students对象
	 */
	@Override
	public List findByExample(Students instance) {
		log.debug("finding Students instance by example");
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
	 * 功能：根据Example，查找Students对象
	 */
	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Students instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Students as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/*
	 * 杜航 
	 * 功能：根据username属性，查找Students对象
	 */
	@Override
	public List findByUsername(Object username) {
		return findByProperty(USERNAME, username);
	}

	/*
	 * 杜航 
	 * 功能：根据gender属性，查找Students对象
	 */
	@Override
	public List findByGender(Object gender) {
		return findByProperty(GENDER, gender);
	}

	/*
	 * 杜航 
	 * 功能：根据age属性，查找Students对象
	 */
	@Override
	public List findByAge(Object age) {
		return findByProperty(AGE, age);
	}

	/*
	 * 杜航 
	 * 功能：根据degree属性，查找Students对象
	 */
	@Override
	public List findByDegree(Object degree) {
		return findByProperty(DEGREE, degree);
	}

	/*
	 * 杜航 
	 * 功能：根据remark属性，查找Students对象
	 */
	@Override
	public List findByRemark(Object remark) {
		return findByProperty(REMARK, remark);
	}

	/*
	 * 杜航 
	 * 功能：查找所有的Students对象
	 */
	@Override
	public List findAll() {
		log.debug("finding all Students instances");
		try {
			String queryString = "from Students";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/*
	 * 杜航 
	 * 功能：将传入的detached(分离的)状态的Students对象的属性复制到持久化对象中，并返回该持久化对象
	 * 如果该session中没有关联的持久化对象，加载一个，如果传入对象未保存，保存一个副本并作为持久对象返回，传入对象依然保持detached状态。
	 */
	@Override
	public Students merge(Students detachedInstance) {
		log.debug("merging Students instance");
		try {
			Students result = (Students) getHibernateTemplate().merge(
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
	 * 功能：将Students对象持久化并保存
	 * 如果对象未保存(Transient状态)，调用save方法保存。如果对象已保存(Detached状态
	 * )，调用update方法将对象与Session重新关联
	 */
	@Override
	public void attachDirty(Students instance) {
		log.debug("attaching dirty Students instance");
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
	 * 功能：将传入的Students对象设置为transient(暂时的)状态
	 */
	@Override
	public void attachClean(Students instance) {
		log.debug("attaching clean Students instance");
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
	 * 功能：得到StudentsDAO上下文对象
	 */
	public static IStudentsDAO getFromApplicationContext(ApplicationContext ctx) {
		return (IStudentsDAO) ctx.getBean("StudentsDAO");
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