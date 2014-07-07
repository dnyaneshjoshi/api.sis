package org.iiitb.api.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.iiitb.api.dao.SyllabusDAO;
import org.iiitb.api.syllabus.SyllabusInfo;
import org.iiitb.util.ConnectionPool;

public class SyllabusDAOImpl implements SyllabusDAO {

	private static final String GET_SYLLABUS_QUERY = "SELECT " +
			"topic.topic_id as topicId, " +
			"topic.name as topicName " +
			"FROM " +
			"(topic INNER JOIN syllabus ON topic.syllabus_id = syllabus.syllabus_id) " +
			"INNER JOIN course ON course.course_id = syllabus.course_id " +
			"AND course.code = ?"; 
	
	private static final String GET_COURSE_ID = "SELECT " +
			"course_id " +
			"FROM course " +
			"WHERE name = ?";
	
	private static final String SET_SYLLABUS = "INSERT INTO syllabus " +
			"(syllabus_id, course_id) " +
			"VALUES " +
			"(syllabus_id, ?)";
	
	private static final String GET_SYLLABUS_ID = "SELECT " +
			"syllabus_id " +
			"FROM syllabus " +
			"WHERE course_id = ?";
	
	private static final String SET_TOPIC = "INSERT INTO topic " +
			"(topic_id, name, syllabus_id) " +
			"VALUES " +
			"(topic_id, ?, ?)";
	
	private static final String GET_FACULTY = "select name from user where user_type = 'F'";
	
	private List<String> facultyList;
	
	  private void createSyllabusInfoListFromResultSet(ResultSet rs, List<SyllabusInfo> syllabusInfoList) throws SQLException {
		    while (rs.next()) {
		      
		    	String topicId = rs.getString("topicId");
		    	String topicName = rs.getString("topicName");
		      
		    	SyllabusInfo syllabusInfo = new SyllabusInfo(topicId, topicName);
		    	syllabusInfoList.add(syllabusInfo);
		    }
		    if (syllabusInfoList.isEmpty()) {
		    	SyllabusInfo syllabusInfo = new SyllabusInfo("0", "Sorry! Syllabus is not updated for this subject.");
		    	syllabusInfoList.add(syllabusInfo);
		    }
		  }
	
	 
	@Override
	public List<SyllabusInfo> getSyllabus(Connection connection, String subjectCode) {
		// TODO Auto-generated method stub
		List<SyllabusInfo> syllabusInfoList = null;
	    PreparedStatement ps = null;
	    try {
	    	syllabusInfoList = new ArrayList<SyllabusInfo>();
	    	ps = connection.prepareStatement(GET_SYLLABUS_QUERY);
	    	int index = 1;
	    	ps.setString(index, subjectCode);
	    	ResultSet rs = ps.executeQuery();
	    	createSyllabusInfoListFromResultSet(rs, syllabusInfoList);
	    	

	    } 	catch (SQLException e) {
	    		e.printStackTrace();
	    } 	finally {
	    		if (null != ps) {
	    			try {
	    				ps.close();
	    			} catch (SQLException e) {
	    				e.printStackTrace();
	    			}
	    		}
	    }
	    return syllabusInfoList;
	}
	
	public boolean setSyllabus(Connection connection, String courseName, String syllabus) {
		
		int index, is;
		String course_id = null, syllabus_id = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Date lastDateForEnrollnment = null;
		
		try {
			
	    	// Get Course ID for the Course
	    	ps = connection.prepareStatement(GET_COURSE_ID);
	    	
	    	index = 1;
	    	ps.setString(index, courseName);
	    	rs = ps.executeQuery();
	    	
	    	while (rs.next()) {
	    		course_id = rs.getString("course_id");
		    	System.out.println("course_id::" + course_id);
	    	}
	    	
	    	// Insert Syllabus details for the Course ID
	    	ps = connection.prepareStatement(SET_SYLLABUS);
	    	
	    	index = 1;
	    	ps.setInt(index, Integer.parseInt(course_id));

	    	is = ps.executeUpdate();
	    	
	    	// Get Syllabus ID for the Course ID
	    	ps = connection.prepareStatement(GET_SYLLABUS_ID);
	    	
	    	index = 1;
	    	ps.setInt(index, Integer.parseInt(course_id));
	    	rs = ps.executeQuery();
	    	
	    	while (rs.next()) {
	    		syllabus_id = rs.getString("syllabus_id");
		    	System.out.println("syllabus_id::" + syllabus_id);
	    	}
	    	
	    	// Insert Topic for the Syllabus ID
	    	ps = connection.prepareStatement(SET_TOPIC);
	    	
	    	index = 1;
	    	ps.setString(index, syllabus);
	    	index = 2;
	    	ps.setInt(index, Integer.parseInt(syllabus_id));

	    	is = ps.executeUpdate();
	    	
	    	if (is != 1) return false; 
	    	
	    } 	catch (SQLException e) {
	    		e.printStackTrace();
	    } 	finally {
	    		if (null != ps) {
	    			try {
	    				ps.close();
	    			} catch (SQLException e) {
	    				e.printStackTrace();
	    			}
	    		}
	    }
		return true;
	}


	@Override
	public List<String> getFaculty(Connection connection) {
		PreparedStatement ps = null;
	    int index;
	    try {
	    	facultyList = new ArrayList<String>();
	    	ps = connection.prepareStatement(GET_FACULTY);
	    	ResultSet rs = ps.executeQuery();
	    	
	    	while( rs.next() ) {
	    		facultyList.add( rs.getString("name") );
	    	}
	    	
	    } 	catch (SQLException e) {
	    		e.printStackTrace();
	    } 	finally {
	    		if (null != ps) {
	    			try {
	    				ps.close();
	    			} catch (SQLException e) {
	    				e.printStackTrace();
	    			}
	    		}
	    }
	    return facultyList;
	}

}
