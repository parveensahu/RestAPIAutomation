package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.RestClient.RestClient;
import com.qa.base.TestBase;
import com.qa.data.Users;

public class PostAPITest extends TestBase{
	
	TestBase testbase;
	String url;
	String partial_uri;
	String path_parameter;
	RestClient rclient;
	CloseableHttpResponse chttp_response;
	
	@BeforeMethod
	public void setUp() throws ClientProtocolException, IOException {
		testbase = new TestBase();
		url = prop.getProperty("URL");
		path_parameter = prop.getProperty("serviceUrl");
		
		partial_uri = url + path_parameter;
		
		
	}
	
	@Test
	public void postAPITest() throws ClientProtocolException, IOException {
		rclient = new RestClient();
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		
		//Jackson API to convert Java object to json and vice versa
		ObjectMapper om = new ObjectMapper();
		Users user = new Users("morpheus","leader"); //expected user object
		
		//now convert the java object to json
		om.writeValue(new File("C:\\Users\\parveensahu\\eclipse-workspace\\RestAPI\\src\\main\\java\\com\\qa\\data\\users.json"),user);
		
		//convert the java object to json in string
		String string_jsonobject = om.writeValueAsString(user);
		System.out.println(string_jsonobject);
		
		chttp_response = rclient.post(partial_uri, string_jsonobject, headerMap);
		
		//check status code
		int actual_status_code = chttp_response.getStatusLine().getStatusCode();
		Assert.assertEquals(actual_status_code, RESPONSE_STATUS_CODE_201);
		
		//check json string
		String responseString = EntityUtils.toString(chttp_response.getEntity(),"UTF-8");
		JSONObject jo = new JSONObject(responseString);
		
		System.out.println("Response JSON from API" + jo);
		
		//json to java object
		Users userresponseobj = om.readValue(responseString, Users.class); //actual user object
		System.out.println(userresponseobj);
		
		Assert.assertTrue(user.getName().equals(userresponseobj.getName()));
		Assert.assertTrue(user.getJob().equals(userresponseobj.getJob()));
		
		System.out.println(userresponseobj.getId());
		System.out.println(userresponseobj.getCreatedAt());
		
	}

}
