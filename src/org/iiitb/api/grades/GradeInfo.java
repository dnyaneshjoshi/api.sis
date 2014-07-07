/**
 * 
 */
package org.iiitb.api.grades;

/**
 * @author kempa
 *
 */
public class GradeInfo
{

	private String subjectName;
	private String subjectCode;
	private String grade;
	private String result;
	
	/**
	 * @param subjectName
	 * @param subjectCode
	 * @param grade
	 * @param result
	 */
	public GradeInfo(String subjectName, String subjectCode, String grade,
			String result)
	{
		this.subjectName = subjectName;
		this.subjectCode = subjectCode;
		this.grade = grade;
		this.result = result;
	}
	
	public String getSubjectName()
	{
		return subjectName;
	}
	public void setSubjectName(String subjectName)
	{
		this.subjectName = subjectName;
	}
	public String getSubjectCode()
	{
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode)
	{
		this.subjectCode = subjectCode;
	}
	public String getGrade()
	{
		return grade;
	}
	public void setGrade(String grade)
	{
		this.grade = grade;
	}
	public String getResult()
	{
		return result;
	}
	public void setResult(String result)
	{
		this.result = result;
	}
	

}
