package com.juggler.constants;

public interface JugglerConstants {

	// Special Character Constants
	String UNDERSCORE = "_";

	// Json Operator Constants
	String JSON_OPERATOR_STRING_ARRAYS = "stringArrays";
	String JSON_OPERATOR_INT_ARRAYS = "intArrays";
	String JSON_OPERATOR_ADD_TO_STRING_ARRAY = "addToStringArray";
	String JSON_OPERATOR_REMOVE_FROM_STRING_ARRAY = "removeFromStringArray";
	String JSON_OPERATOR_SORTON = "sortOn";

	// Mongo Operator Constants
	String MONGO_OPERATOR_PUSH = "$push";
	String MONGO_OPERATOR_PULL = "$pull";

	// Json Qualifier Constants
	String JSON_COLLECTION_USER = "user";
	String JSON_ID = "id";
	String JSON_HOBBIES = "hobbies";
	String JSON_FRIENDS = "friends";
	String JSON_TIMESTAMP = "timeStamp";
	String JSON_ACTIVITY_OWNER_ID = "activityOwnerId";
	String JSON_LOGIN_ID = "login_id";
	String JSON_LOGIN_PASSWORD = "login_password";
	String JSON_PASSWORD = "password";
	String JSON_EMAILID = "emailId";
	String JSON_ROLENAME = "roleName";
	String JSON_SENT_FRIEND_REQUESTS = "sentFriendRequests";
	String JSON_RECIEVED_FRIEND_REQUESTS = "recievedFriendRequests";
	// Mongo Qualifier Constants
	String MONGO_ID = "_id";

	// Action Types
	String ACTION_CREATED = "Created";

}
