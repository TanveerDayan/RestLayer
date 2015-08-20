package com.juggler.pojo;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class UserCreateVO {
	@Id
	private String id;
	private String password;
	private String emailId;
	private String userName;
	private String dateOfBirth;
	private String city;
	private String locality;
	private String roleName;
	private String userStatus;
	private String activationId;
	private List<String> hobbies;
	private List<String> friends;
	private List<String> recievedFriendRequests;
	private List<String> sentFriendRequests;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getActivationId() {
		return activationId;
	}

	public void setActivationId(String activationId) {
		this.activationId = activationId;
	}

	public List<String> getHobbies() {
		return hobbies;
	}

	public void setHobbies(List<String> hobbies) {
		this.hobbies = hobbies;
	}

	public List<String> getFriends() {
		return friends;
	}

	public void setFriends(List<String> friends) {
		this.friends = friends;
	}

	public List<String> getRecievedFriendRequests() {
		return recievedFriendRequests;
	}

	public void setRecievedFriendRequests(List<String> recievedFriendRequests) {
		this.recievedFriendRequests = recievedFriendRequests;
	}

	public List<String> getSentFriendRequests() {
		return sentFriendRequests;
	}

	public void setSentFriendRequests(List<String> sentFriendRequests) {
		this.sentFriendRequests = sentFriendRequests;
	}

	@Override
	public String toString() {
		return "UserCreateVO [id=" + id + ", password=" + password
				+ ", emailId=" + emailId + ", userName=" + userName
				+ ", dateOfBirth=" + dateOfBirth + ", city=" + city
				+ ", locality=" + locality + ", roleName=" + roleName
				+ ", userStatus=" + userStatus + ", activationId="
				+ activationId + ", hobbies=" + hobbies + ", friends="
				+ friends + ", recievedFriendRequests="
				+ recievedFriendRequests + ", sentFriendRequests="
				+ sentFriendRequests + "]";
	}

}
