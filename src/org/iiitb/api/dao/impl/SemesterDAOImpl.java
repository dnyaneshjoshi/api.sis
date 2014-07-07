package org.iiitb.api.dao.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.iiitb.api.dao.SemesterDAO;
import org.iiitb.api.syllabus.SyllabusInfo;
import org.iiitb.util.ConnectionPool;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author kempa
 * 
 */
public class SemesterDAOImpl implements SemesterDAO
{
	private List<String> termList;
	private List<String> semesterList;
	
	private static final String GET_TERMS_QUERY = "select distinct term"
			+ " from semester,course,result"
			+ " where course.semester_id = semester.semester_id "
			+ "and course.course_id = result.course_id "
			+ "and result.student_id=?";
	
	private static final String GET_TERMS = "SELECT  " + 
			"    semester.term " + 
			"FROM " + 
			"    course " + 
			"        JOIN " + 
			"    semester ON course.semester_id = semester.semester_id " + 
			"WHERE " + 
			"    course.lastdate > CURDATE() " + 
			"GROUP BY semester.term;";
	
	private static final String GET_TERMS_FOR_YEAR = "SELECT DISTINCT " +
			"term " +
			"FROM " +
			"semester " +
			"WHERE year = ?";
	
	public SemesterDAOImpl()
	{
		termList = new LinkedList<String>();
	}

	public List<String> getTerms(int studentID)
	{
		Connection con = ConnectionPool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs;
		try
		{
			ps = con.prepareStatement(GET_TERMS_QUERY);
			ps.setInt(1, studentID);
			rs = ps.executeQuery();
			while(rs.next())
				termList.add(new Integer((rs.getInt(1))).toString());

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (ps != null)
				try
				{
					ps.close();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			ConnectionPool.freeConnection(con);
		}
		return termList;
	}

	public List<String> getTerms()
	{
		Connection con = ConnectionPool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs;
		try
		{
			ps = con.prepareStatement(GET_TERMS);
			rs = ps.executeQuery();
			while(rs.next())
				termList.add(new Integer((rs.getInt(1))).toString());			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (ps != null)
				try
				{
					ps.close();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			ConnectionPool.freeConnection(con);
		}
		return termList;
	}

	public List<String> getSemester(Connection connection, int studentId, String year) {
		// TODO Auto-generated method stub
	    PreparedStatement ps = null;
	    int index;
	    try {
	    	semesterList = new ArrayList<String>();
	    	if ( year == null) {
	    		ps = connection.prepareStatement(GET_TERMS);
	    	} else {
	    		ps = connection.prepareStatement(GET_TERMS_FOR_YEAR);
		    	index = 1;
		    	ps.setInt(index, Integer.parseInt(year));
	    	}
	    	ResultSet rs = ps.executeQuery();
	    	
	    	while( rs.next() ) {
	    		semesterList.add( rs.getString("term") );
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
	    return semesterList;
	}
	
	private static final String GET_ALL_RELEVANT_TERMS="select term from semester where year in (select distinct(sem.year) from student s, result r, course c, semester sem where s.student_id=r.student_id and r.course_id=c.course_id and c.semester_id=sem.semester_id and s.student_id=?);";
	
	public List<String> getAllRelevantTerms(Connection cn, int studentID) throws SQLException
	{
		List<String> l=new ArrayList<String>();
		l.add("Select");
		PreparedStatement ps=cn.prepareStatement(GET_ALL_RELEVANT_TERMS);
		ps.setInt(1, studentID);
		ResultSet rs=ps.executeQuery();
		while(rs.next())
			l.add(rs.getString(1));
		rs.close();
		return l;
	}
}
