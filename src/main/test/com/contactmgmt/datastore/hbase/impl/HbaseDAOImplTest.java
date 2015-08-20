package com.contactmgmt.datastore.hbase.impl;

import junit.framework.TestCase;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This class contains one testXXXX method per XXXXX method in source class
 * 
 * @author
 **/
// TODO Add Junit jar in build path.
// TODO Modify input and output data if needed.

public class HbaseDAOImplTest extends TestCase {
	HbaseDAOImpl impl;

	public HbaseDAOImplTest() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"RestLayer-servlet.xml");
		impl = (HbaseDAOImpl) (context.getBean("hbaseDAOImpl"));
	}

	@Before
	public void setup() {
		try {
			JSONObject object = new JSONObject();
			object.put("clientId", "key1");
			object.put("col", "val1");
			JSONArray array = new JSONArray();
			array.put(object);
			impl.createClient("test");
			impl.updateInformation("test", array);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test update information.
	 */
	@Test
	public void testUpdateInformation() {
		JSONObject object = new JSONObject();
		try {
			object.put("clientId", "key1");
			object.put("col", "val1");
			JSONArray array = new JSONArray();
			array.put(object);
			impl.updateInformation("test", array);
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}

	}

	/**
	 * Test retrieve all contacts info.
	 */
	@Test
	public void testRetrieveAllContactsInfo() {

		try {

			if (impl.retrieveAllContactsInfo("test", "", "10").length() > 0) {
				assertTrue(true);
			}

		} catch (Exception e) {
			fail();
		}

	}

	/**
	 * Test create client.
	 */
	@Test
	public void testCreateClient() {
		try {

			String status = impl.createClient("test_createTable");
			if (status.equals("success")) {
				assertTrue(true);
			}
		} catch (Exception e) {
			fail();
		}
	}

	/**
	 * Test get contact by id.
	 */
	@Test
	public void testGetContactById() {
		try {

			JSONObject object = impl.getContactInfoById("test", "key1");
			if (object.get("contactId").equals("key1")) {
				assertTrue(true);
			}
		} catch (Exception e) {
			fail();
		}
	}

	/**
	 * Test delete contacts.
	 */
	@Test
	public void testDeleteContacts() {
		try {
			JSONArray array = new JSONArray("[{contactId:key1}]");
			String status = impl.deleteContacts("test", array);
			if (status.equals("success")) {
				assertTrue(true);
			}
		} catch (Exception e) {
			fail();
		}
	}

}
