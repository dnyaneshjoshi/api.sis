/**
 * 
 */
package org.iiitb.api.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.text.ParseException;

import org.iiitb.api.dao.CourseDAO;
import org.iiitb.api.model.SubjectInfo;
import org.iiitb.util.ConnectionPool;


/**
 * @author arjun
 * 
 */
public class CourseDAOImpl implements CourseDAO {

  private static final String ALL_COURSES_QUERY = "SELECT  "
      + "    SUBJECTS_AVAILABLE.course_id as course_id, "
      + "    subject_code, "
      + "    subject_name, "
      + "    faculty_name, "
      + "    semester_term, "
      + "    student_id, "
      + "    grade_name "
      + "FROM "
      + "    (SELECT  "
      + "        course.course_id as course_id, "
      + "            course.code as subject_code, "
      + "            course.name as subject_name, "
      + "            teacher.name as faculty_name, "
      + "            semester.term as semester_term "
      + "    FROM "
      + "        course course "
      + "    LEFT OUTER JOIN semester semester ON semester.semester_id = course.semester_id "
      + "    LEFT OUTER JOIN faculty faculty ON course.faculty_id = faculty.faculty_id "
      + "    LEFT OUTER JOIN user teacher ON faculty.faculty_id = teacher.user_id) SUBJECTS_AVAILABLE "
      + "        LEFT OUTER JOIN "
      + "    (SELECT  "
      + "        course.course_id, "
      + "            student.user_id as student_id, "
      + "            student.name as student_name, "
      + "            grade.name as grade_name "
      + "    FROM "
      + "        course course "
      + "    LEFT OUTER JOIN faculty faculty ON course.faculty_id = faculty.faculty_id "
      + "    LEFT OUTER JOIN user teacher ON faculty.faculty_id = teacher.user_id "
      + "    LEFT OUTER JOIN result result ON result.course_id = course.course_id "
      + "    LEFT OUTER JOIN user student ON student.user_id = result.student_id "
      + "    LEFT OUTER JOIN grade grade ON grade.grade_id = result.grade_id "
      + "    LEFT OUTER JOIN semester semester ON semester.semester_id = course.semester_id "
      + "    WHERE "
      + "        student.user_id = ?) STUDENT_SELECTION ON STUDENT_SELECTION.course_id = SUBJECTS_AVAILABLE.course_id "
      + "ORDER BY subject_code;";

  private static final String ENROLLED_COURSE_QUERY = "SELECT  "
      + "    course.course_id as course_id, " + "    student.user_id as student_id, "
      + "    student.name as student_name, " + "    grade.name as grade_name, "
      + "    course.code as subject_code, "
      + "    course.name as subject_name, "
      + "    teacher.name as faculty_name, "
      + "    semester.term as semester_term " + "FROM " + "    course course "
      + "        LEFT OUTER JOIN "
      + "    faculty faculty ON course.faculty_id = faculty.faculty_id "
      + "        LEFT OUTER JOIN "
      + "    user teacher ON faculty.faculty_id = teacher.user_id "
      + "        LEFT OUTER JOIN "
      + "    result result ON result.course_id = course.course_id "
      + "        LEFT OUTER JOIN "
      + "    user student ON student.user_id = result.student_id "
      + "        LEFT OUTER JOIN "
      + "    grade grade ON grade.grade_id = result.grade_id "
      + "        LEFT OUTER JOIN "
      + "    semester semester ON semester.semester_id = course.semester_id "
      + "WHERE " + "    student.user_id = ?";

  private static final String ENROLLED_COURSE_QUERY_BY_NAME = "SELECT  "
	      + "    course.course_id as course_id, " + "    student.user_id as student_id, "
	      + "    student.name as student_name, " + "    grade.name as grade_name, "
	      + "    course.code as subject_code, "
	      + "    course.name as subject_name, "
	      + "    teacher.name as faculty_name, "
	      + "    semester.term as semester_term " + "FROM " + "    course course "
	      + "        LEFT OUTER JOIN "
	      + "    faculty faculty ON course.faculty_id = faculty.faculty_id "
	      + "        LEFT OUTER JOIN "
	      + "    user teacher ON faculty.faculty_id = teacher.user_id "
	      + "        LEFT OUTER JOIN "
	      + "    result result ON result.course_id = course.course_id "
	      + "        LEFT OUTER JOIN "
	      + "    user student ON student.user_id = result.student_id "
	      + "        LEFT OUTER JOIN "
	      + "    grade grade ON grade.grade_id = result.grade_id "
	      + "        LEFT OUTER JOIN "
	      + "    semester semester ON semester.semester_id = course.semester_id "
	      + "WHERE " + "    student.name = ?";

	private static final String GET_NAMES_QUERY = "select distinct course.name"
			+ " from result, course "
			+ "where result.course_id = course.course_id "
			+ "and result.student_id=?";

	private static final String GET_NAMES_QUERY_TERM = "select distinct course.name "
			+ "from result, course, semester "
			+ "where result.course_id = course.course_id "
			+ "and course.semester_id = semester.semester_id "
			+ "and result.student_id = ? and semester.term = ?";
	
	private static final String GET_FACULTY_ID = "SELECT " +
			"user_id " +
			"FROM user " +
			"WHERE name = ? " +
			"AND user_type = 'F'";
		
	private static final String SET_COURSE = "INSERT INTO course" +
			"(course_id, code, name, credits, lastdate, faculty_id, semester_id) " +
			"VALUES " +
			"(course_id, ?, ?, ?, ?, ?, ?)";
	
  private void createSubjectInfoListFromResultSet(ResultSet rs,
      List<SubjectInfo> subjectInfoList) throws SQLException {
    while (rs.next()) {
      String courseId = rs.getString("course_id");
      String subjectCode = rs.getString("subject_code");
      String subjectName = rs.getString("subject_name");
      String facultyName = rs.getString("faculty_name");
      int semester = rs.getInt("semester_term");
      String enrolledVal = rs.getString("student_id");
      String enrolled = (null == enrolledVal) ? "N" : "Y";
      String gradeVal = rs.getString("grade_name");
      String grade = (null == gradeVal) ? "NA" : gradeVal;
      SubjectInfo subjectInfo = new SubjectInfo(courseId, subjectCode, subjectName,
          facultyName, semester, enrolled, grade);
      subjectInfoList.add(subjectInfo);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.iiitb.action.dao.CourseDAO#getAllCourses(java.sql.Connection, int)
   */
  @Override
  public List<SubjectInfo> getAllCourses(Connection connection, int userId) {
    List<SubjectInfo> subjectInfoList = null;
    PreparedStatement ps = null;
    try {
      subjectInfoList = new ArrayList<SubjectInfo>();
      ps = connection.prepareStatement(ALL_COURSES_QUERY);
      int index = 1;
      ps.setInt(index, userId);
      ResultSet rs = ps.executeQuery();
      createSubjectInfoListFromResultSet(rs, subjectInfoList);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (null != ps) {
        try {
          ps.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return subjectInfoList;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.iiitb.action.dao.CourseDAO#getEnrolledCourses(java.sql.Connection,
   * int)
   */
  @Override
  public List<SubjectInfo> getEnrolledCourses(Connection connection, int userId) {
    List<SubjectInfo> subjectInfoList = null;
    PreparedStatement ps = null;
    try {
      subjectInfoList = new ArrayList<SubjectInfo>();
      ps = connection.prepareStatement(ENROLLED_COURSE_QUERY);
      int index = 1;
      ps.setInt(index, userId);
      ResultSet rs = ps.executeQuery();
      createSubjectInfoListFromResultSet(rs, subjectInfoList);

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (null != ps) {
        try {
          ps.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return subjectInfoList;
  }

  public List<SubjectInfo> getEnrolledCourses(Connection connection, String student) {
	    List<SubjectInfo> subjectInfoList = null;
	    PreparedStatement ps = null;
	    try {
	      subjectInfoList = new ArrayList<SubjectInfo>();
	      ps = connection.prepareStatement(ENROLLED_COURSE_QUERY_BY_NAME);
	      int index = 1;
	      ps.setString(index, student);
	      ResultSet rs = ps.executeQuery();
	      createSubjectInfoListFromResultSet(rs, subjectInfoList);

	    } catch (SQLException e) {
	      e.printStackTrace();
	    } finally {
	      if (null != ps) {
	        try {
	          ps.close();
	        } catch (SQLException e) {
	          e.printStackTrace();
	        }
	      }
	    }
	    return subjectInfoList;
	  }

	public List<String> getNames(int studentID)
	{
		List<String> courseList = new LinkedList<String>();
		Connection con = ConnectionPool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs;
		try
		{		
			ps = con.prepareStatement(GET_NAMES_QUERY);
			rs = ps.executeQuery();
			ps.setInt(1,  studentID);
			while(rs.next())
				courseList.add(rs.getString(1));			
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
		
		return courseList;
	}
	
	public List<String> getNames(int studentID, int term)
	{
		List<String> courseList = new LinkedList<String>();
		Connection con = ConnectionPool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs;
		try
		{		
			ps = con.prepareStatement(GET_NAMES_QUERY_TERM);
			ps.setInt(1, studentID);
			ps.setInt(2, term);
			rs = ps.executeQuery();
			while(rs.next())
				courseList.add(rs.getString(1));
			
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
		
		return courseList;
	}

	@Override
	public boolean setCourse(Connection connection, String code, String courseName, String credits, String lastDate,
								String semester, String facultyName) {

		int index, is;
		String faculty_id = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Date lastDateForEnrollnment = null;
		
		try {
			ps = connection.prepareStatement(GET_FACULTY_ID);
	    	
	    	index = 1;
	    	ps.setString(index, facultyName);
	    	rs = ps.executeQuery();
	    	
	    	while (rs.next()) {
		    	faculty_id = rs.getString("user_id");
		    	System.out.println("faculty_id::" + faculty_id);
	    	}
	    	
	    	// Insert Course Details
	    	ps = connection.prepareStatement(SET_COURSE);
	    	
	    	index = 1;
	    	ps.setString(index, code);
	    	index = 2;
	    	ps.setString(index, courseName);
	    	index = 3;
	    	ps.setInt(index, Integer.parseInt(credits));
	    	
	    	index = 4;
	    	if (lastDate == null) lastDate = "31-12-2014";
	    	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	    	try {
				lastDateForEnrollnment = new Date( format.parse(lastDate).getTime() );
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	ps.setDate(index, lastDateForEnrollnment);
	    	
	    	index = 5;
	    	ps.setInt(index, Integer.parseInt(faculty_id));
	    	index = 6;
	    	ps.setInt(index, Integer.parseInt(semester));
	    	is = ps.executeUpdate();
	    	
	    	if (is != 1) return false; 
		} 
		catch (SQLException e) {
    		e.printStackTrace();
		} 	
		finally {
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
}