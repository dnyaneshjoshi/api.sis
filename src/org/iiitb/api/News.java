package org.iiitb.api;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.iiitb.api.dao.LayoutDAO;
import org.iiitb.api.dao.impl.LayoutDAOImpl;
import org.iiitb.model.layout.NewsItem;
import org.iiitb.util.ConnectionPool;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("/news")
public class News
{
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getNews() throws SQLException
	{
		Gson gson=new GsonBuilder().create();
		Connection cn=ConnectionPool.getConnection();
		LayoutDAO layoutDAO=new LayoutDAOImpl();
		List<NewsItem> l=layoutDAO.getAllNews(cn);
		ConnectionPool.freeConnection(cn);
		return gson.toJson(l);
	}

	@GET
	@Produces(MediaType.TEXT_XML)
	public String sayXMLHello() throws SQLException
	{
		return getNews();
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHello() throws SQLException
	{
		return getNews();
	}
}