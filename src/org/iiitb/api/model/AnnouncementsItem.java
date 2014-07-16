package org.iiitb.api.model;

public class AnnouncementsItem
{
	private String name;
	private String details;
	private int interestId;
	
	public AnnouncementsItem(String name, String details, int interestId)
	{
		this.name=name;
		this.details=details;
		this.interestId=interestId;
	}
	
	public AnnouncementsItem(String name, String details)
	{
		this.name=name;
		this.details=details;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}

	public int getInterestId() {
		return interestId;
	}

	public void setInterestId(int interestId) {
		this.interestId = interestId;
	}
}
