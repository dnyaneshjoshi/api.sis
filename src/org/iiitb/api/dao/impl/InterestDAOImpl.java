package org.iiitb.api.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.iiitb.api.dao.InterestDAO;
import org.iiitb.model.layout.Interest;

public class InterestDAOImpl implements InterestDAO
{
	private static final String GET_INTERESTS_QUERY=
			"select i.interest_id as interest_id, name, details from interest i, student_interest si where si.student_id=? and i.interest_id=si.interest_id;";
	
	private static final String GET_ALL_INTERESTS_QUERY=
			"select interest_id, name, details from interest;";
	
	private static final String ADD_INTEREST_QUERY=
			"insert into interest(name, details) values(?, ?);";
	
	@Override
	public List<Interest> getInterests(Connection cn, int studentId) throws SQLException
	{
		ArrayList<Interest> ai=new ArrayList<Interest>();
		PreparedStatement p=cn.prepareStatement(GET_INTERESTS_QUERY);
		p.setInt(1, studentId);
		ResultSet rs=p.executeQuery();
		while(rs.next())
		{
			ai.add(new Interest(rs.getInt("interest_id"), rs.getString("name"),rs.getString("details")));
		}
		rs.close();
		return ai;
	}

	@Override
	public List<Interest> getAllInterests(Connection cn) throws SQLException
	{
		ArrayList<Interest> ai=new ArrayList<Interest>();
		PreparedStatement p=cn.prepareStatement(GET_ALL_INTERESTS_QUERY);
		ResultSet rs=p.executeQuery();
		while(rs.next())
		{
			ai.add(new Interest(rs.getInt("interest_id"), rs.getString("name"),rs.getString("details")));
		}
		rs.close();
		return ai;
	}
	
	@Override
	public void addInterest(Connection cn, Interest i) throws SQLException
	{
		PreparedStatement p=cn.prepareStatement(ADD_INTEREST_QUERY);
		p.setString(1, i.getName());
		p.setString(2, i.getDetails());
		p.executeUpdate();
	}
}
