/**
 * 
 */
package org.iiitb.api.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.iiitb.api.grades.GradeInfo;

/**
 * @author kempa
 * 
 */
public interface ResultDAO
{
	public List<GradeInfo> getGrades(int studentID, int term, String subjectName);

	public List<GradeInfo> getGrades(int studentID);

	public List<GradeInfo> getGrades(int studentID, int term);
	
	public boolean updateGrades(String studentName,String courseName,String gradeName);
	
	public List<String> getGrades();
	
	public String getCgpa(Connection cn, int id, int term) throws SQLException;
}
