package org.iiitb.api;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.iiitb.api.dao.LayoutDAO;
import org.iiitb.api.dao.impl.LayoutDAOImpl;
import org.iiitb.api.model.AnnouncementsItem;
import org.iiitb.api.model.NewsItem;
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
	
	@POST
	@Path("/add")
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
	public void addAnnouncement(String announcementJson) throws SQLException
	{
		Gson gson=new GsonBuilder().create();
		AnnouncementsItem announcementsItem=gson.fromJson(announcementJson, AnnouncementsItem.class);
		Connection cn=ConnectionPool.getConnection();
		LayoutDAO layoutDAO=new LayoutDAOImpl();
		layoutDAO.addAnnouncement(cn, announcementsItem.getName(),
				announcementsItem.getDetails(), announcementsItem.getInterestId());
		ConnectionPool.freeConnection(cn);
	}
}