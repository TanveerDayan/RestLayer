package com.juggler.rest;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juggler.action.impl.ActionImpl;
import com.juggler.activity.impl.ActivityImpl;
import com.juggler.constants.JugglerConstants;
import com.juggler.pojo.ActionVO;
import com.juggler.pojo.ActivityVO;
import com.juggler.pojo.UserCreateVO;
import com.juggler.user.impl.UserImpl;

@Controller
@RequestMapping("/RestServlet")
public class RestServiceCall {

	@Autowired
	ActivityImpl activityImpl;
	@Autowired
	UserImpl userImpl;

	@Autowired
	ActionImpl actionImpl;

	ObjectMapper mapper = new ObjectMapper();

	Logger logger = Logger.getLogger(RestServiceCall.class);

	@RequestMapping(value = "/addActivity", method = RequestMethod.POST)
	public @ResponseBody void addActivity(@RequestParam("activityJson") String activityJson) {
		try {
			activityImpl.addActivity(activityJson);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/persistAction", method = RequestMethod.POST)
	public @ResponseBody void persistAction(@RequestParam("userId") String userId,
			@RequestParam("action") String action, @RequestParam("activityId") String activityId) {
		long timeStamp = System.currentTimeMillis();
		try {
			ActionVO actionVO = new ActionVO();
			actionVO.setAction(action.trim());
			actionVO.setActivityId(activityId.trim());
			actionVO.setTimeStamp(timeStamp);
			actionVO.setUserId(userId.trim());
			actionImpl.persistAction(userId.trim(), actionVO);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/addParticipant", method = RequestMethod.POST)
	public @ResponseBody void addParticipant(@RequestParam("activityId") String activityId,
			@RequestParam("userId") String userId) {
		try {
			activityImpl.addParticipant(activityId, userId);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/getActionDetails", method = RequestMethod.POST)
	public @ResponseBody String getActionDetails(@RequestParam("retrievalType") String type,
			@RequestParam("userId") String userId) {
		JSONArray actionArray = null;
		StringWriter writer = new StringWriter();
		try {
			List<ActionVO> actionVOList = actionImpl.getActionDetails(type, userId);
			mapper.writeValue(writer, actionVOList);
			actionArray = new JSONArray(writer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionArray.toString();
	}

	@RequestMapping(value = "/getActivity", method = RequestMethod.POST)
	public @ResponseBody String getActivity(@RequestParam("activityId") String activityId) {
		JSONObject returnObject = null;
		ActivityVO actVO = null;
		StringWriter writer = new StringWriter();
		try {
			actVO = activityImpl.getActivityBasedOnActivityId(activityId);
			if (actVO == null) {
				return null;
			}
			mapper.writeValue(writer, actVO);
			returnObject = new JSONObject(writer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return returnObject.toString();
	}

	@RequestMapping(value = "/getSuggestedActivity", method = RequestMethod.POST)
	public @ResponseBody String getSuggestedActivity(@RequestParam("retrievalType") String type,
			@RequestParam("userId") String userId) {
		JSONArray activityArray = null;
		StringWriter writer = new StringWriter();
		try {
			UserCreateVO userVO = userImpl.getUserDetails(userId);
			List<ActivityVO> activityVO = activityImpl.getActivityBasedOnList(userId, type, userVO.getHobbies());
			mapper.writeValue(writer, activityVO);
			activityArray = new JSONArray(writer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return activityArray.toString();
	}

	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public @ResponseBody void createUser(@RequestParam("userJson") String userJson) {
		try {
			userImpl.createUser(userJson);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public @ResponseBody void updateUser(@RequestParam("userJson") String userJson) {
		try {
			JSONObject object = new JSONObject(userJson);
			JSONObject existingObject = new JSONObject(getUserDetail(object.getString(JugglerConstants.JSON_ID)));
			Iterator<String> iterator = object.keys();
			while (iterator.hasNext()) {
				String key = iterator.next();
				Object value = object.get(key);
				if (!value.equals(null)) {
					existingObject.put(key, value);
				}
			}
			userImpl.createUser(existingObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/activateUser", method = RequestMethod.POST)
	public @ResponseBody void activateUser(@RequestParam("userJson") String userJson) {
		try {

			JSONObject object = new JSONObject(userJson);
			StringWriter writer = new StringWriter();
			UserCreateVO vo = userImpl.getUserDetails(object.getString(JugglerConstants.JSON_EMAILID));

			if (vo.getActivationId().equals(object.get("activationId").toString())) {
				object.put("userStatus", "Active");
				updateUser(object.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/getUserDetail", method = RequestMethod.POST)
	public @ResponseBody String getUserDetail(@RequestParam("userId") String userId) {
		StringWriter writer = new StringWriter();
		UserCreateVO userCreateVO = null;
		JSONObject userDetailObject = null;
		try {
			userCreateVO = userImpl.getUserDetails(userId);
			mapper.writeValue(writer, userCreateVO);
			userDetailObject = new JSONObject(writer.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return userDetailObject.toString();
	}

	@RequestMapping(value = "/validateUser", method = RequestMethod.POST)
	public @ResponseBody String validateUser(@RequestParam("userId") String userId) {
		UserCreateVO userCreateVO = null;

		JSONObject loginObject = new JSONObject();
		try {
			userCreateVO = userImpl.getUserDetails(userId);
			if (userCreateVO != null) {
				loginObject.put(JugglerConstants.JSON_PASSWORD, userCreateVO.getPassword());
				loginObject.put(JugglerConstants.JSON_EMAILID, userCreateVO.getEmailId());
				loginObject.put(JugglerConstants.JSON_ROLENAME, userCreateVO.getRoleName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginObject.toString();
	}

	@RequestMapping(value = "/friendSuggestion", method = RequestMethod.POST)
	public @ResponseBody String friendSuggestion(@RequestParam("userId") String userId) {
		JSONArray friendSuggestionArray = null;
		UserCreateVO userCreateVO = null;
		List<UserCreateVO> friendSuggestionList = null;
		StringWriter writer = new StringWriter();
		try {
			userCreateVO = userImpl.getUserDetails(userId);
			List<String> userHobbies = userCreateVO.getHobbies();
			friendSuggestionList = userImpl.getUserFriendSuggestion(userId, userHobbies);
			mapper.writeValue(writer, friendSuggestionList);
			friendSuggestionArray = new JSONArray(writer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return friendSuggestionArray.toString();
	}

	@RequestMapping(value = "/addFriend", method = RequestMethod.POST)
	public @ResponseBody String addFriend(@RequestParam("userId") String userId,
			@RequestParam("friendId") String friendId) {
		userImpl.addFriend(userId, friendId);
		return friendId;
	}

	@RequestMapping(value = "/acceptFriend", method = RequestMethod.POST)
	public @ResponseBody String acceptFriend(@RequestParam("userId") String userId,
			@RequestParam("friendId") String friendId) {
		userImpl.acceptFriend(userId, friendId);
		return friendId;
	}

	@RequestMapping(value = "/removeFriend", method = RequestMethod.POST)
	public @ResponseBody String removeFriend(@RequestParam("userId") String userId,
			@RequestParam("friendId") String friendId) {
		userImpl.removeFriend(userId, friendId);
		return friendId;
	}

	@RequestMapping(value = "/cancelFriend", method = RequestMethod.POST)
	public @ResponseBody String cancelFriend(@RequestParam("userId") String userId,
			@RequestParam("friendId") String friendId) {
		userImpl.cancelFriend(userId, friendId);
		return friendId;
	}

	@RequestMapping(value = "/getUserFriendDetails", method = RequestMethod.POST)
	public @ResponseBody String getSentFriendRequests(@RequestParam("retrievalType") String type,
			@RequestParam("userId") String userId) {
		JSONArray friendArray = null;
		StringWriter writer = new StringWriter();
		try {
			List<UserCreateVO> userVO = userImpl.getFriendRequestDetails(type, userId);
			mapper.writeValue(writer, userVO);
			friendArray = new JSONArray(writer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return friendArray.toString();
	}

}
