package com.juggler.pojo;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "activity")
public class ActivityVO {
	@Id
	String id;
	String activityName;
	String activityOwnerId;
	String activityStartTime;
	String activityEndTime;
	String activityCity;
	String activityLocality;
	String activityAddress;
	List<String> hobbies;
	List<String> participants;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityOwnerId() {
		return activityOwnerId;
	}

	public void setActivityOwnerId(String activityOwnerId) {
		this.activityOwnerId = activityOwnerId;
	}

	public String getActivityStartTime() {
		return activityStartTime;
	}

	public void setActivityStartTime(String activityStartTime) {
		this.activityStartTime = activityStartTime;
	}

	public String getActivityEndTime() {
		return activityEndTime;
	}

	public void setActivityEndTime(String activityEndTime) {
		this.activityEndTime = activityEndTime;
	}

	public String getActivityCity() {
		return activityCity;
	}

	public void setActivityCity(String activityCity) {
		this.activityCity = activityCity;
	}

	public String getActivityLocality() {
		return activityLocality;
	}

	public void setActivityLocality(String activityLocality) {
		this.activityLocality = activityLocality;
	}

	public String getActivityAddress() {
		return activityAddress;
	}

	public void setActivityAddress(String activityAddress) {
		this.activityAddress = activityAddress;
	}


	public List<String> getHobbies() {
		return hobbies;
	}

	public void setHobbies(List<String> hobbies) {
		this.hobbies = hobbies;
	}

	

	public List<String> getParticipants() {
		return participants;
	}

	public void setParticipants(List<String> participants) {
		this.participants = participants;
	}

	@Override
	public String toString() {
		return "ActivityVO [id=" + id + ", activityName=" + activityName
				+ ", activityOwnerId=" + activityOwnerId
				+ ", activityStartTime=" + activityStartTime
				+ ", activityEndTime=" + activityEndTime + ", activityCity="
				+ activityCity + ", activityLocality=" + activityLocality
				+ ", activityAddress=" + activityAddress + ", hobbies="
				+ hobbies + ", participants=" + participants + "]";
	}

}
