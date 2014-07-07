package org.iiitb.api.dao;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;

import org.iiitb.util.ConnectionPool;

public class EditProfileDAO {
String name;
String password;
String userid;
String photo;
List<String> defaultInterests;
public EditProfileDAO()
{
	name=null;
	password=null;
	userid=null;
	defaultInterests=null;
	photo=null;
}
public EditProfileDAO(String userid)
{
	this.userid=userid;
	name=null;
	password=null;
	defaultInterests=null;
	photo=null;
}
public String getPhoto()
{
	return photo;
}
public void setPhoto(InputStream photo)
{
	Connection con = ConnectionPool.getConnection();
	PreparedStatement ps;
	ResultSet rs;
	try
	{
		ps=con.prepareStatement("update student set photo = ? where student_id = ?");
		ps.setBlob(1, photo);
		ps.setString(2, userid);
		int s = ps.executeUpdate();
	}
	catch (SQLException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally {
	  ConnectionPool.freeConnection(con);
	}
}
public String getName() {
	return name;
}
public void setName(String name) {
	Connection con = ConnectionPool.getConnection();
	PreparedStatement ps;
	ResultSet rs;
	try
	{
		ps=con.prepareStatement("update user set name = ? where user_id = ?");
		ps.setString(1, name);
		ps.setString(2, userid);
		int s = ps.executeUpdate();
	}
	catch (SQLException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally {
	  ConnectionPool.freeConnection(con);
	}
	
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	Connection con = ConnectionPool.getConnection();
	PreparedStatement ps;
	try
	{
		ps=con.prepareStatement("update user set password = ? where user_id = ?" );
		ps.setString(1, password);
		ps.setString(2, userid);
		int s=ps.executeUpdate();
	}
	catch (SQLException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
	  ConnectionPool.freeConnection(con);
	}
		
}
public String getUserid() {
	return userid;
}
public void setUserid(String userid) {
	this.userid=userid;
}
public List<String> getDefaultInterests() {
	return defaultInterests;
}
public void setDefaultInterests(List<String> defaultInterests) {
	Connection con = ConnectionPool.getConnection();
	PreparedStatement ps;
	ResultSet rs;
	Iterator<String> iterator = defaultInterests.iterator();
	try {
		ps=con.prepareStatement("delete from student_interest where student_id=?");
		ps.setString(1, userid);	
		ps.executeUpdate();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	try
	{
		while(iterator.hasNext())
		{
		ps=con.prepareStatement("select interest_id from interest where name=?");
		ps.setString(1, (String)iterator.next());
		rs=ps.executeQuery();
		String id=null;
		if(rs.next())
		id=rs.getString("interest_id");
		ps=con.prepareStatement("insert into student_interest values(null,?,? )");
		ps.setString(1,userid );
		ps.setString(2,id);
		int s = ps.executeUpdate();		
		}
	}
	catch (SQLException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
	  ConnectionPool.freeConnection(con);
	}
	
	
}
}