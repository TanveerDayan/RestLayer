package com.juggler.activity.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.juggler.constants.JugglerConstants;
import com.juggler.pojo.ActionVO;
import com.juggler.pojo.ActivityVO;
import com.juggler.pojo.UserCreateVO;
import com.juggler.user.impl.UserImpl;

public class ActivityImpl {

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	UserImpl userImpl;

	public void addActivity(String activityJson) throws JsonParseException,
			JsonMappingException, IOException, JSONException {
		ActivityVO activityVO = new ObjectMapper().readValue(activityJson,
				ActivityVO.class);

		String ownerId = activityVO.getActivityOwnerId();
		List<String> participants = new ArrayList<String>();
		participants.add(ownerId);
		activityVO.setParticipants(participants);
		long timeStamp = System.currentTimeMillis();
		String id = ownerId + JugglerConstants.UNDERSCORE + timeStamp;
		UserCreateVO ownerVO = userImpl.getUserDetails(ownerId);
		activityVO.setId(id);
		ActionVO actionVO = new ActionVO();
		actionVO.setUserId(ownerId);
		actionVO.setTimeStamp(timeStamp);
		actionVO.setFriends(ownerVO.getFriends());
		actionVO.setActivityId(id);
		actionVO.setAction(JugglerConstants.ACTION_CREATED);
		mongoTemplate.save(activityVO);
		mongoTemplate.save(actionVO);
	}

	public List<ActivityVO> getActivitDetails(String type, String userId) {
		Query query = new Query(Criteria.where(type).in(userId.trim()));
		List<ActivityVO> ActivityList = mongoTemplate.find(query,
				ActivityVO.class);
		return ActivityList;
	}

	public List<ActivityVO> getActivityBasedOnList(String userId, String type,
			List<String> stringList) {
		Query query = new Query(Criteria
				.where(type)
				.in(stringList)
				.andOperator(
						Criteria.where(JugglerConstants.JSON_ACTIVITY_OWNER_ID)
								.ne(userId.trim())));
		List<ActivityVO> ActivityList = mongoTemplate.find(query,
				ActivityVO.class);
		return ActivityList;
	}

	public ActivityVO getActivityBasedOnActivityId(String activityId) {
		Query query = new Query(Criteria.where("id").is(activityId.trim()));
		ActivityVO activityVO = mongoTemplate.findOne(query, ActivityVO.class);
		return activityVO;
	}

	public void addParticipant(String activityId, String userId) {
		Query searchFriendQuery = new Query(Criteria.where(
				JugglerConstants.MONGO_ID).is(activityId.trim()));
		Update participantUpdate = new Update();
		participantUpdate.addToSet("participants", userId.trim());
		mongoTemplate.upsert(searchFriendQuery, participantUpdate, "activity");
	}
}
