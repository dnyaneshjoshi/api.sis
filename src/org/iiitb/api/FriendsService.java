package org.iiitb.api;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import org.iiitb.api.dao.StudentDAO;
import org.iiitb.api.dao.impl.StudentDAOImpl;
import org.iiitb.api.model.StudentInfo;

@Path("/friends")
public class FriendsService
{

	@GET
	@Path("/studentrollno/{rollno}")
	public Response getStudentByRollNo(@PathParam("rollno") String rollno) throws JSONException
	{
		String output = "";
		StudentDAO dao = new StudentDAOImpl();
		StudentInfo info = null;
		int status_code = -1;
		try
		{
			info = dao.getStudentByRollNo(rollno);

			JSONObject jsonObject = null;

			if (info != null)
			{
				jsonObject = new JSONObject();
				jsonObject.put("studentId", info.getStudentId());
				jsonObject.put("rollNo", info.getRollNo());
				jsonObject.put("dob", info.getDob());
				jsonObject.put("name", info.getName());

			}

			if (jsonObject != null)
			{
				status_code = 200;
				output = jsonObject.toString();
			}
			else
			{
				status_code = 500;
				output = "Unable to find user";
			}

			System.out.println(output);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.status(status_code).entity(output).build();

	}

	@GET
	@Path("/studentid/{id}")
	public Response getStudentById(@PathParam("id") String id) throws JSONException
	{
		String output = "";
		StudentDAO dao = new StudentDAOImpl();
		StudentInfo info = null;
		int status_code = -1;
		try
		{
			info = dao.getStudentByUserId(id);

			JSONObject jsonObject = null;

			if (info != null)
			{
				jsonObject = new JSONObject();
				jsonObject.put("studentId", info.getStudentId());
				jsonObject.put("rollNo", info.getRollNo());
				jsonObject.put("dob", info.getDob());
				jsonObject.put("name", info.getName());

			}

			if (jsonObject != null)
			{
				status_code = 200;
				output = jsonObject.toString();
			}
			else
			{
				status_code = 500;
				output = "Unable to find user";
			}

			System.out.println(output);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.status(status_code).entity(output).build();

	}

	@GET
	@Path("/relation")
	public Response checkFriendsRelationShip(@MatrixParam("rollNo") String rollNo, @MatrixParam("friendNo") String friendNo) throws JSONException
	{

		System.out.println("********************checkFriendsRelationShip RELATIONSHIP********************************");

		System.out.println("rollNo:" + rollNo);
		System.out.println("friendNo:" + friendNo);
		System.out.println("********************checkFriendsRelationShip RELATIONSHIP********************************");

		String output = "";
		StudentDAO dao = new StudentDAOImpl();
		int status_code = -1;
		try
		{
			String friendsStatus = dao.findRelationShip(rollNo, friendNo);

			JSONObject jsonObject = null;

			if (friendsStatus != null)
			{
				jsonObject = new JSONObject();
				jsonObject.put("friendsStatus", friendsStatus);

			}

			if (jsonObject != null)
			{
				status_code = 200;
				output = jsonObject.toString();
			}
			else
			{
				status_code = 500;
				output = "Unable to get friends relation user";
			}

			System.out.println(output);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.status(status_code).entity(output).build();

	}

	@GET
	@Path("/studentfriends/{rollno}")
	public Response getFriends(@PathParam("rollno") String rollno) throws JSONException
	{
		String output = "";
		StudentDAO dao = new StudentDAOImpl();
		List<StudentInfo> friends = null;
		int status_code = -1;
		try
		{
			friends = dao.getFriends(rollno);

			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = null;

			for (StudentInfo friend : friends)
			{
				jsonObject = new JSONObject();
				jsonObject.put("studentId", friend.getStudentId());
				jsonObject.put("rollNo", friend.getRollNo());
				jsonObject.put("dob", friend.getDob());
				jsonObject.put("name", friend.getName());
				jsonArray.put(jsonObject);
			}

			if (jsonObject != null)
			{
				status_code = 200;
				output = jsonArray.toString();
			}
			else
			{
				status_code = 500;
				output = "Unable to find friends";
			}

			System.out.println(output);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.status(status_code).entity(output).build();

	}

	@POST
	@Path("/add")
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response addfriends(String inputdata) throws JSONException	{
		
		JSONObject jsonData = new JSONObject(inputdata);			
		int status_code = -1;
		JSONObject jsonObject = new JSONObject();

		String returnString = "";

		try
		{
			System.out.println(jsonData.optString("rollNo"));
			
			System.out.println(jsonData.optString("friendNo"));
			StudentDAO dao = new StudentDAOImpl();
			boolean success = dao.addFriend(jsonData.optString("rollNo"), jsonData.optString("friendNo"));

			if (success)
			{
				jsonObject.put("msg", "success");
				status_code = 200;
				returnString = jsonObject.toString();
			}
			else
			{
				status_code = 500;
				returnString = "Unable to add friends";
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			status_code = 500;
			returnString = "Server was not able to process your request";

		}

		return Response.status(status_code).entity(returnString).build();

	}
}
