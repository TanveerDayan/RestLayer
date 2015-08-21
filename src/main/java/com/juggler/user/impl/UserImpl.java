package com.juggler.user.impl;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.juggler.constants.JugglerConstants;
import com.juggler.pojo.UserCreateVO;

public class UserImpl {
	@Autowired
	MongoTemplate mongoTemplate;

	public void createUser(String userJson) throws IOException, JSONException {
		UserCreateVO userCreateVO = new ObjectMapper().readValue(userJson,
				UserCreateVO.class);
		mongoTemplate.save(userCreateVO);

	}

	

	public UserCreateVO getUserDetails(String userId) throws IOException,
			JSONException {
		Query searchUserQuery = new Query(Criteria.where(
				JugglerConstants.MONGO_ID).is(userId.trim()));

		UserCreateVO userCreateVO = mongoTemplate.findOne(searchUserQuery,
				UserCreateVO.class);
		return userCreateVO;
	}

	public List<UserCreateVO> getUserFriendSuggestion(String userId,
			List<String> hobbies) {
		Query hobbiesQuery = new Query(Criteria.where(
				JugglerConstants.JSON_HOBBIES).in(hobbies));
		hobbiesQuery.addCriteria(Criteria
				.where(JugglerConstants.JSON_ID)
				.ne(userId.trim())
				.andOperator(
						Criteria.where(
								JugglerConstants.JSON_SENT_FRIEND_REQUESTS).ne(
								userId.trim()),
						Criteria.where(
								JugglerConstants.JSON_RECIEVED_FRIEND_REQUESTS)
								.ne(userId.trim()),
						Criteria.where(JugglerConstants.JSON_FRIENDS).ne(
								userId.trim())));

		hobbiesQuery.fields().exclude(JugglerConstants.JSON_PASSWORD);
		List<UserCreateVO> userList = mongoTemplate.find(hobbiesQuery,
				UserCreateVO.class);
		return userList;
	}

	public void addFriend(String userId, String friendId) {
		Query searchUserQuery = new Query(Criteria.where(
				JugglerConstants.MONGO_ID).is(userId.trim()));
		Update userUpdate = new Update();
		userUpdate.addToSet(JugglerConstants.JSON_SENT_FRIEND_REQUESTS,
				friendId.trim());
		mongoTemplate.upsert(searchUserQuery, userUpdate,
				JugglerConstants.JSON_COLLECTION_USER);
		Query searchFriendQuery = new Query(Criteria.where(
				JugglerConstants.MONGO_ID).is(friendId.trim()));
		Update friendUpdate = new Update();
		friendUpdate.addToSet(JugglerConstants.JSON_RECIEVED_FRIEND_REQUESTS,
				userId.trim());
		mongoTemplate.upsert(searchFriendQuery, friendUpdate,
				JugglerConstants.JSON_COLLECTION_USER);
	}

	public void acceptFriend(String userId, String friendId) {
		Query searchUserQuery = new Query(Criteria.where(
				JugglerConstants.MONGO_ID).is(userId.trim()));
		Update userUpdate = new Update();
		userUpdate.pull(JugglerConstants.JSON_RECIEVED_FRIEND_REQUESTS,
				friendId.trim());
		userUpdate.addToSet(JugglerConstants.JSON_FRIENDS, friendId.trim());
		mongoTemplate.upsert(searchUserQuery, userUpdate,
				JugglerConstants.JSON_COLLECTION_USER);

		Query searchFriendQuery = new Query(Criteria.where(
				JugglerConstants.MONGO_ID).is(friendId.trim()));
		Update friendUpdate = new Update();
		friendUpdate.pull(JugglerConstants.JSON_SENT_FRIEND_REQUESTS,
				userId.trim());
		friendUpdate.addToSet(JugglerConstants.JSON_FRIENDS, userId.trim());

		mongoTemplate.upsert(searchFriendQuery, friendUpdate,
				JugglerConstants.JSON_COLLECTION_USER);
	}

	public void cancelFriend(String userId, String friendId) {
		Query searchUserQuery = new Query(Criteria.where(
				JugglerConstants.MONGO_ID).is(userId.trim()));
		Update userUpdate = new Update();
		userUpdate.pull(JugglerConstants.JSON_SENT_FRIEND_REQUESTS,
				friendId.trim());
		mongoTemplate.upsert(searchUserQuery, userUpdate,
				JugglerConstants.JSON_COLLECTION_USER);

		Query searchFriendQuery = new Query(Criteria.where(
				JugglerConstants.MONGO_ID).is(friendId.trim()));
		Update friendUpdate = new Update();
		friendUpdate.pull(JugglerConstants.JSON_RECIEVED_FRIEND_REQUESTS,
				userId.trim());

		mongoTemplate.upsert(searchFriendQuery, friendUpdate,
				JugglerConstants.JSON_COLLECTION_USER);
	}

	public void removeFriend(String userId, String friendId) {
		Query searchUserQuery = new Query(Criteria.where(
				JugglerConstants.MONGO_ID).is(userId.trim()));
		Update userUpdate = new Update();
		userUpdate.pull(JugglerConstants.JSON_FRIENDS, friendId.trim());
		mongoTemplate.upsert(searchUserQuery, userUpdate,
				JugglerConstants.JSON_COLLECTION_USER);

		Query searchFriendQuery = new Query(Criteria.where(
				JugglerConstants.MONGO_ID).is(friendId.trim()));
		Update friendUpdate = new Update();
		friendUpdate.pull(JugglerConstants.JSON_FRIENDS, userId.trim());

		mongoTemplate.upsert(searchFriendQuery, friendUpdate,
				JugglerConstants.JSON_COLLECTION_USER);
	}

	public List<UserCreateVO> getFriendRequestDetails(String type, String userId) {
		Query query = new Query(Criteria.where(type).in(userId.trim()));
		List<UserCreateVO> userList = mongoTemplate.find(query,
				UserCreateVO.class);
		return userList;
	}

}
