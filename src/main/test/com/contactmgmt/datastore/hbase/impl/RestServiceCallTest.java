package com.contactmgmt.datastore.hbase.impl;

import junit.framework.TestCase;

import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import com.contactmgmt.rest.RestServiceCall;

/**
 * This class contains one testXXXX method per XXXXX method in source class
 * 
 * @author
 **/
// TODO Add Junit jar in build path.
// TODO Modify input and output data if needed.

public class RestServiceCallTest extends TestCase {

	@Test
	// public String updateInfo(String)
	public void testUpdateInfo() {
		RestServiceCall e0Obj = new RestServiceCall();
		try {
			JSONObject object = new JSONObject();
			object.put("id", "test1");
			object.put("col", "val1");
		//	String e0 = e0Obj.updateInfo(object.toString());
		//	assertEquals("success", e0);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	// public String retrieveInfo()
	public void testRetrieveInfo() {
		RestServiceCall e0Obj = new RestServiceCall();
		//String e0 = e0Obj.retrieveInfo();
		/*if (e0 != null && !e0.isEmpty()) {
			assertTrue(true);
		}*/
	}

}
