package com.ssh.service;

import java.sql.ResultSet;
import java.util.List;
import com.ssh.model.Users;

public interface IUsersService {
	public List<Users> findAll();

	public Users findByName(String uname);

	public void addUsers(Users u);

	public void delUsers(Users u);

	public void updateUsers(Users u);

	public Users login(String uname, String upass);

	public List excuteSQL(String sql);

}
