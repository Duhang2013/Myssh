package com.ssh.service;//ҵ���߼���

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ssh.dao.IUsersDAO;
import com.ssh.model.Users;

public class UsersService implements IUsersService {
	private static final Logger log = LoggerFactory
			.getLogger(UsersService.class);
	private IUsersDAO usersDAO = null;

	/*
	 * 杜航 
	 * 功能：保存Users对象
	 */
	@Override
	public void addUsers(Users u) {
		usersDAO.save(u);
	}

	/*
	 * 杜航 
	 * 功能：删除Users对象
	 */
	@Override
	public void delUsers(Users u) {
		usersDAO.delete(u);
	}

	/*
	 * 杜航 
	 * 功能：查找所有的Users对象
	 */
	@Override
	public List<Users> findAll() {
		return usersDAO.findAll();
	}

	/*
	 * 杜航 
	 * 功能：根据uname属性，查找Users对象
	 */
	@Override
	public Users findByName(String uname) {
		List<Users> list = usersDAO.findByUsername(uname);
		if (list == null) {
			System.out.println("listΪ��");
			return null;
		}
		return list.get(0);

	}

	/*
	 * 杜航 
	 * 功能：用户登录
	 */
	@Override
	public Users login(String uname, String upass) {
		Users u = findByName(uname);
		if (null == u) {
			return null;
		}
		if (uname.equals(u.getUsername()) && upass.equals(u.getPassword())) {
			return u;
		}
		return null;
	}

	/*
	 * 杜航 
	 * 功能：更新Users对象
	 */
	@Override
	public void updateUsers(Users u) {
		usersDAO.attachDirty(u);

	}

	/*
	 * 杜航 
	 * 功能：Hibernate执行sql语句
	 */
	@Override
	public List excuteSQL(String sql) {
		return usersDAO.excuteSQL(sql);
	}

	public void setUsersDAO(IUsersDAO usersDAO) {
		this.usersDAO = usersDAO;
	}

	public IUsersDAO getUsersDAO() {
		return usersDAO;
	}

}