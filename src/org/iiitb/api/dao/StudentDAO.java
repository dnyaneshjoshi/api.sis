package org.iiitb.api.dao;

import java.util.List;

import org.iiitb.api.model.StudentInfo;

/**
 * @author prashanth
 * 
 */
public interface StudentDAO
{

	public StudentInfo getStudentByRollNo(String rollNo);

	public StudentInfo getStudentByUserId(String rollNo);

	public List<StudentInfo> getFriends(String rollno);

	public String findRelationShip(String rollNo, String friendNo);

	public boolean addFriend(String rollNo, String friendNo);
	
	public List<String> getStudents();
}
