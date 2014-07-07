package org.iiitb.api;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.iiitb.api.dao.LayoutDAO;
import org.iiitb.api.dao.impl.LayoutDAOImpl;
import org.iiitb.model.layout.AnnouncementsItem;
import org.iiitb.model.layout.NewsItem;
import org.iiitb.util.ConnectionPool;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("/announcements")
public class Announcements
{
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("users/{id}")
	public String getAnnouncements(@PathParam("id") String id) throws SQLException
	{
		Gson gson=new GsonBuilder().create();
		Connection cn=ConnectionPool.getConnection();
		LayoutDAO layoutDAO=new LayoutDAOImpl();
		List<AnnouncementsItem> l=layoutDAO.getAnnouncements(cn, Integer.parseInt(id));
		ConnectionPool.freeConnection(cn);
		return gson.toJson(l);
	}

	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("users/{id}")
	public String sayXMLHello(@PathParam("id") String id) throws SQLException
	{
		return getAnnouncements(id);
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("users/{id}")
	public String sayHtmlHello(@PathParam("id") String id) throws SQLException
	{
		return getAnnouncements(id);
	}
}