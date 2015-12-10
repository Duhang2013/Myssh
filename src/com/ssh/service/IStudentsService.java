package com.ssh.service;

import java.util.List;
import com.ssh.model.Students;

public interface IStudentsService {
	public List<Students> findAll();

	public Students findByName(String uname);

	public void addStudents(Students u);

	public void delStudents(Students u);

	public void updateStudents(Students u);

	public List excuteSQL(String sql);

}
