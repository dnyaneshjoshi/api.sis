package org.iiitb.model.layout;

public class NewsItem
{
	private String name;
	private String details;
	
	public NewsItem(String name, String details)
	{
		this.name=name;
		this.setDetails(details);
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

}
