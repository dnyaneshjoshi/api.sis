package org.iiitb.api.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.iiitb.api.model.Interest;

public interface InterestDAO
{
	public List<Interest> getInterests(Connection cn, int studentId) throws SQLException;
	public List<Interest> getAllInterests(Connection cn) throws SQLException;
	public void addInterest(Connection cn, Interest i) throws SQLException;
	public void deleteInterest(Connection cn, String name) throws SQLException;
}
