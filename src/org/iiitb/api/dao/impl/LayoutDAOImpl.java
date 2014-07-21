package org.iiitb.api.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.iiitb.api.dao.LayoutDAO;
import org.iiitb.api.model.AnnouncementsItem;
import org.iiitb.api.model.NewsItem;

public class LayoutDAOImpl implements LayoutDAO
{
	private static final String ALL_NEWS_QUERY = "select * from news order by news_id desc;";

	private static final String ANNOUNCEMENTS_QUERY = "select a.announcement_id, a.name, a.details "
			+ "from announcement a, "
			+ "	announcement_interest ai, "
			+ "	interest i, "
			+ "	student_interest si, "
			+ "	student s "
			+ "where s.student_id=? "
			+ "	and si.student_id=s.student_id "
			+ "	and i.interest_id=si.interest_id "
			+ "	and ai.interest_id=i.interest_id "
			+ "	and a.announcement_id=ai.announcement_id "
			+ "order by a.announcement_id desc;";

	private static final String LAST_LOGGED_ON_QUERY = "select "
			+ "user.last_logged_on " + "from user " + "where user.user_id=?";

	private static final String UPDATE_LAST_LOGGED_ON = "update user "
			+ "set last_logged_on=NOW() " + "where user_id=?";

	private static final String ADD_NEWS_QUERY=
			"insert into news(name,details) values(?,?);";
	
	private static final String REMOVE_NEWS_QUERY=
			"delete from news where name=?;";
	
	private static final String ADD_ANNOUNCEMENT_QUERY=
			"insert into announcement(name,details) values(?,?);";
	
	private static final String ADD_ANNOUNCEMENT_INTEREST_QUERY=
			"insert into announcement_interest(interest_id, announcement_id) values(?, (select max(announcement_id) from announcement));";
	
	@Override
	public List<NewsItem> getAllNews(Connection connection) throws SQLException
	{
		ResultSet rs = connection.prepareStatement(ALL_NEWS_QUERY)
				.executeQuery();
		List<NewsItem> ln = new ArrayList<NewsItem>();
		while (rs.next())
		{
			ln.add(new NewsItem(rs.getString(2), rs.getString(3)));
		}
		rs.close();
		return ln;
	}

	@Override
	public List<AnnouncementsItem> getAnnouncements(Connection connection,
			int userId) throws SQLException
	{
		PreparedStatement ps = connection.prepareStatement(ANNOUNCEMENTS_QUERY);
		ps.setInt(1, userId);
		ResultSet rs = ps.executeQuery();
		List<AnnouncementsItem> la = new ArrayList<AnnouncementsItem>();
		while (rs.next())
		{
			la.add(new AnnouncementsItem(rs.getString(2), rs.getString(3)));
		}
		rs.close();
		return la;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.iiitb.action.dao.LayoutDAO#getLastLoggedOn(java.sql.Connection,
	 * int)
	 */
	@Override
	public String getLastLoggedOn(Connection connection, int userId)
			throws SQLException
	{
		PreparedStatement ps = connection
				.prepareStatement(LAST_LOGGED_ON_QUERY);
		ps.setInt(1, userId);
		ResultSet rs = ps.executeQuery();

		String lastLoggedOn = "-";
		rs.next();
		if (rs.getDate("last_logged_on") != null)
		{
			lastLoggedOn = rs.getTimestamp("last_logged_on").toString();
			lastLoggedOn = lastLoggedOn.substring(0, lastLoggedOn.length() - 5);
			System.out.println("Last logged on : " + lastLoggedOn);
		}
		rs.close();
		return lastLoggedOn;
	}

	public void setLastLoggedOn(Connection connection, int userId)
			throws SQLException
	{
		PreparedStatement ps = connection
				.prepareStatement(UPDATE_LAST_LOGGED_ON);
		ps.setInt(1, userId);
		ps.executeUpdate();
	}

	@Override
	public void addNews(Connection connection, String name, String details) throws SQLException
	{
		PreparedStatement ps=connection.prepareStatement(ADD_NEWS_QUERY);
		ps.setString(1, name);
		ps.setString(2, details);
		ps.executeUpdate();
	}

	@Override
	public boolean removeNews(Connection connection, String name)
			throws SQLException
	{
		PreparedStatement ps=connection.prepareStatement(REMOVE_NEWS_QUERY);
		ps.setString(1, name);
		if(ps.executeUpdate()>0)
			return true;
		else
			return false;
	}

	@Override
	public void addAnnouncement(Connection connection, String name,
			String details, int interestId) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement preStmt=connection.prepareStatement(ADD_ANNOUNCEMENT_QUERY);
		preStmt.setString(1, name);
		preStmt.setString(2, details);
		
		if (preStmt.executeUpdate() > 0)
		{
			PreparedStatement p=connection.prepareStatement(ADD_ANNOUNCEMENT_INTEREST_QUERY);
			p.setInt(1, interestId);
			p.executeUpdate();
			p.close();
		}
		
		preStmt.close();
	}

}
