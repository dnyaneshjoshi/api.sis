package org.iiitb.api.dao;

import java.sql.Connection;
import java.util.List;

import org.iiitb.api.syllabus.SyllabusInfo;

public interface SyllabusDAO {
	
	public List<SyllabusInfo> getSyllabus(Connection connection, String subjectCode);
	
	public boolean setSyllabus(Connection connection, String courseName, String syllabus);
	
	// need to move this to User DAO
	public List<String> getFaculty(Connection connection);
	
}
