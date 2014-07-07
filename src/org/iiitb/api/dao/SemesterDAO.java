package org.iiitb.api.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author kempa
 * 
 */
public interface SemesterDAO
{
	public List<String> getTerms(int studentID);
	
	public List<String> getSemester(Connection connection, int studentID, String year);
	
	public List<String> getAllRelevantTerms(Connection cn, int studentID) throws SQLException;
	
}
