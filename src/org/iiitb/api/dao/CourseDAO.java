package org.iiitb.api.dao;

import java.sql.Connection;
import java.util.List;

import org.iiitb.api.subjects.SubjectInfo;

public interface CourseDAO {

  /**
   * Gets all the courses
   * 
   * @param connection
   * @param userId
   * @return list of subjectInfo objects
   */
  public List<SubjectInfo> getAllCourses(Connection connection, int userId);

  /**
   * Get Enrolled Courses
   * 
   * @param connection
   * @param userId
   * @return list of subjectInfo objects
   */
  public List<SubjectInfo> getEnrolledCourses(Connection connection, int userId);
  
  /**
   * 
   * @param studentID
   * @return
   */
  public List<String> getNames(int studentID);
  
  /**
   * 
   * @param studentID
   * @param term
   * @return
   */
  public List<String> getNames(int studentID, int term);
  
  public boolean setCourse(Connection connection, String code, String courseName, String credits, String lastDate, 
		  											String semester, String facultyName);
}
