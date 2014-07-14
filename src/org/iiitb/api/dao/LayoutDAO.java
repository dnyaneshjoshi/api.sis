package org.iiitb.api.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.iiitb.api.model.AnnouncementsItem;
import org.iiitb.api.model.NewsItem;

public interface LayoutDAO
{
	public List<NewsItem> getAllNews(Connection connection) throws SQLException;
	public List<AnnouncementsItem> getAnnouncements(Connection connection, int userId) throws SQLException;
	public String getLastLoggedOn(Connection connection, int userId) throws SQLException;
	public void setLastLoggedOn(Connection connection, int userId) throws SQLException;
	public void addNews(Connection connection, String name, String details) throws SQLException;
	public void removeNews(Connection connection, String name) throws SQLException;
}
