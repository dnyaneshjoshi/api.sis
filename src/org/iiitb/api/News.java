package org.iiitb.api;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.iiitb.api.dao.LayoutDAO;
import org.iiitb.api.dao.impl.LayoutDAOImpl;
import org.iiitb.api.model.NewsItem;
import org.iiitb.util.ConnectionPool;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("/news")
public class News
{
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllNews() throws SQLException
	{
		Gson gson=new GsonBuilder().create();
		Connection cn=ConnectionPool.getConnection();
		LayoutDAO layoutDAO=new LayoutDAOImpl();
		List<NewsItem> l=layoutDAO.getAllNews(cn);
		ConnectionPool.freeConnection(cn);
		return gson.toJson(l);
	}
	
	@POST
	@Path("/add")
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
	public void addNewsItem(String newsItemJson) throws SQLException
	{
		Gson gson=new GsonBuilder().create();
		NewsItem newsItem=gson.fromJson(newsItemJson, NewsItem.class);
		Connection cn=ConnectionPool.getConnection();
		LayoutDAO layoutDAO=new LayoutDAOImpl();
		layoutDAO.addNews(cn, newsItem.getName(), newsItem.getDetails());
		ConnectionPool.freeConnection(cn);
	}
	
	@DELETE
	@Path("/delete/{newsItemName}")
	public Response deleteNewsItem(@PathParam("newsItemName") String newsItemName) throws SQLException
	{
		int statusCode=500;
		Connection cn=ConnectionPool.getConnection();
		LayoutDAO layoutDAO=new LayoutDAOImpl();
		if(layoutDAO.removeNews(cn, newsItemName))
			statusCode=200;
		ConnectionPool.freeConnection(cn);
		return Response.status(statusCode).build();
	}
}