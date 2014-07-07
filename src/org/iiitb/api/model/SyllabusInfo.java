package org.iiitb.api.model;

/**
 * 
 * @author rahul
 *
 */
public class SyllabusInfo {
	
	private String topicId;
	private String topicName;
	
	public SyllabusInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SyllabusInfo(String topicId, String topicName) {
		super();
		this.topicId = topicId;
		this.topicName = topicName;
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	
}
