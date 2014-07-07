package org.iiitb.model;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.iiitb.util.ConnectionPool;
import org.iiitb.util.Constants;

/**
 * @author prashanth
 * 
 */
public class StudentInfo
{
	int studentId;
	public int getStudentId()
	{
		return studentId;
	}

	public void setStudentId(int studentId)
	{
		this.studentId = studentId;
	}

	String rollNo;
	String dob;
	InputStream photo;
	String name;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getRollNo()
	{
		return rollNo;
	}

	public void setRollNo(String rollNo)
	{
		this.rollNo = rollNo;
	}

	public String getDob()
	{
		return dob;
	}

	public void setDob(String dob)
	{
		this.dob = dob;
	}

   public InputStream getPhoto()
   {
   return photo;
   }
  
   public void setPhoto(InputStream photo)
   {
   this.photo = photo;
   }

}
