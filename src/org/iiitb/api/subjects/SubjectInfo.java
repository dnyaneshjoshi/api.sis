package org.iiitb.api.subjects;

/**
 * Subject Info
 * @author arjun
 *
 */
public class SubjectInfo {

  private String courseId;
  private String subjectCode;
  private String subjectName;
  private String facultyName;
  private int semester;
  private String enrolled;
  private String grade;

  public SubjectInfo() {
    super();
  }

  public SubjectInfo(String courseId, String subjectCode, String subjectName,
      String facultyName, int semester, String enrolled, String grade) {
    super();
    this.courseId = courseId;
    this.subjectCode = subjectCode;
    this.subjectName = subjectName;
    this.facultyName = facultyName;
    this.semester = semester;
    this.enrolled = enrolled;
    this.grade = grade;
  }

  
  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getEnrolled() {
    return enrolled;
  }

  public String getSubjectCode() {
    return subjectCode;
  }

  public void setSubjectCode(String subjectCode) {
    this.subjectCode = subjectCode;
  }

  public String getFacultyName() {
    return facultyName;
  }

  public void setFacultyName(String facultyName) {
    this.facultyName = facultyName;
  }

  public int getSemester() {
    return semester;
  }

  public void setSemester(int semester) {
    this.semester = semester;
  }

  public void setEnrolled(String enrolled) {
    this.enrolled = enrolled;
  }

  public String getGrade() {
    return grade;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }

  public String getSubjectName() {
    return subjectName;
  }

  public void setSubjectName(String subjectName) {
    this.subjectName = subjectName;
  }

  @Override
  public String toString() {
    return this.subjectName;
  }
}
