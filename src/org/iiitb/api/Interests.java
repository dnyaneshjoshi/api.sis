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

import org.iiitb.api.dao.InterestDAO;
import org.iiitb.api.dao.LayoutDAO;
import org.iiitb.api.dao.impl.InterestDAOImpl;
import org.iiitb.api.dao.impl.LayoutDAOImpl;
import org.iiitb.api.model.Interest;
import org.iiitb.api.model.NewsItem;
import org.iiitb.util.ConnectionPool;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("/interests")
public class Interests
{
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public String getAllInterests() throws SQLException
	{
		Connection cn=ConnectionPool.getConnection();
		InterestDAO interestDAO=new InterestDAOImpl();
		List<Interest> r=interestDAO.getAllInterests(cn);
		ConnectionPool.freeConnection(cn);
		Gson gson=new GsonBuilder().create();
		return gson.toJson(r);
	}
	
	@GET
	@Path("/users/{userId}")
	@Produces({MediaType.APPLICATION_JSON})
	public String getInterests(@PathParam("userId") String userId) throws SQLException
	{
		Connection cn=ConnectionPool.getConnection();
		InterestDAO interestDAO=new InterestDAOImpl();
		List<Interest> r=interestDAO.getInterests(cn, Integer.parseInt(userId));
		ConnectionPool.freeConnection(cn);
		Gson gson=new GsonBuilder().create();
		return gson.toJson(r);
	}
	
	@POST
	@Path("/add")
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
	public void addInterest(String interestJson) throws SQLException
	{
		Gson gson=new GsonBuilder().create();
		Interest interest=gson.fromJson(interestJson, Interest.class);
		Connection cn=ConnectionPool.getConnection();
		InterestDAO interestDAO=new InterestDAOImpl();
		interestDAO.addInterest(cn, interest);
		ConnectionPool.freeConnection(cn);
	}
	
	@DELETE
	@Path("/delete/{interestName}")
	public void deleteInterest(@PathParam("interestName") String interestName) throws SQLException
	{
		Connection cn=ConnectionPool.getConnection();
		InterestDAO interestDAO=new InterestDAOImpl();
		interestDAO.deleteInterest(cn, interestName);
		ConnectionPool.freeConnection(cn);
	}
}