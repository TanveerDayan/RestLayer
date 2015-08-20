package com.juggler.action.impl;

import java.io.IOException;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.juggler.constants.JugglerConstants;
import com.juggler.pojo.ActionVO;
import com.juggler.pojo.UserCreateVO;
import com.juggler.user.impl.UserImpl;

public class ActionImpl {
	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	UserImpl userImpl;

	public List<ActionVO> getActionDetails(String type, String userId) {
		Query query = new Query(Criteria.where(type).in(userId.trim()));
		List<ActionVO> actionVOList = mongoTemplate.find(query, ActionVO.class);
		return actionVOList;
	}

	public void persistAction(String userId, ActionVO vo) throws IOException,
			JSONException {
		UserCreateVO userVO = userImpl.getUserDetails(userId);
		vo.setFriends(userVO.getFriends());
		mongoTemplate.insert(vo);
	}

}
