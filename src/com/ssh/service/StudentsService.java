package com.ssh.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ssh.dao.IStudentsDAO;
import com.ssh.model.Students;

public class StudentsService implements IStudentsService {
	private static final Logger log = LoggerFactory.getLogger(StudentsService.class);
	private IStudentsDAO studentsDAO = null;

	/*
	 * 杜航 
	 * 功能：保存Students对象
	 */
	@Override
	public void addStudents(Students u) {
		studentsDAO.save(u);
	}

	/*
	 * 杜航 
	 * 功能：删除Students对象
	 */
	@Override
	public void delStudents(Students u) {
		studentsDAO.delete(u);
	}

	/*
	 * 杜航 
	 * 功能：查找所有的Students对象
	 */
	@Override
	public List<Students> findAll() {
		return studentsDAO.findAll();
	}

	/*
	 * 杜航 
	 * 功能：根据uname属性，查找Students对象
	 */
	@Override
	public Students findByName(String uname) {
		List<Students> list = studentsDAO.findByUsername(uname);
		if (list == null) {
			return null;
		}
		return list.get(0);

	}

	/*
	 * 杜航 
	 * 功能：更新Students对象
	 */
	@Override
	public void updateStudents(Students u) {
		studentsDAO.attachDirty(u);

	}

	/*
	 * 杜航 
	 * 功能：Hibernate执行sql语句
	 */
	@Override
	public List excuteSQL(String sql) {
		return studentsDAO.excuteSQL(sql);
	}

	public void setStudentsDAO(IStudentsDAO studentsDAO) {
		this.studentsDAO = studentsDAO;
	}

	public IStudentsDAO getStudentsDAO() {
		return studentsDAO;
	}


}