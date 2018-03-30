package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.RestClient.RestClient;
import com.qa.base.TestBase;
import com.qa.utils.TestUtils;

public class GetAPITest extends TestBase{

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
	
	@Test(priority = 1)
	public void getAPITestwithoutHeaders() throws ClientProtocolException, IOException {
		rclient = new RestClient();
		chttp_response = rclient.get(partial_uri);
		
		
				//Get Status Code
				int status_code = chttp_response.getStatusLine().getStatusCode();
				
				Assert.assertEquals(status_code, RESPONSE_STATUS_CODE_200);
				
				//Get ResponseString Body
				String responseString = EntityUtils.toString(chttp_response.getEntity(),"UTF-8");
				JSONObject responsejson = new JSONObject(responseString);
				System.out.println("Response json from API" + responsejson);
				//Single Value Assertion
				//pre_page
				String perPageValue = TestUtils.getValueByJPath(responsejson,"/per_page");
				System.out.println("Value of per page is :" + perPageValue);
				
				Assert.assertEquals(Integer.parseInt(perPageValue), 3);
				
				//total
				String totalValue = TestUtils.getValueByJPath(responsejson,"/total");
				System.out.println("Value of per page is :" + totalValue);
				
				Assert.assertEquals(Integer.parseInt(totalValue), 12);
				
				
				//get json array
				String lastName = TestUtils.getValueByJPath(responsejson, "/data[0]/last_name");
				String id = TestUtils.getValueByJPath(responsejson, "/data[0]/id");
				String avatar = TestUtils.getValueByJPath(responsejson, "/data[0]/avatar");
				String firstName = TestUtils.getValueByJPath(responsejson, "/data[0]/first_name");
				
				System.out.println(lastName);
				System.out.println(id);
				System.out.println(avatar);
				System.out.println(firstName);
				
				Assert.assertEquals(Integer.parseInt(id), 1);
				
				//Get Headers
				Header[] headerArray = chttp_response.getAllHeaders();
				
				//Convert headers into HashMap
				HashMap<String,String> hm = new HashMap<String,String>();
				
				for(Header header:headerArray) {
					hm.put(header.getName(), header.getValue());
				}
				System.out.println(hm);
				
				//Validate Headers
				/*
				String contenttype = TestUtils.getValueByJPath(responsejson, "/content-type");
				
				System.out.println(contenttype);
				
				Assert.assertEquals(contenttype, "application/json; charset=utf-8");
				*/
				
	}
	//TestCase with Headers
	@Test(priority = 2)
	public void getAPITestwithHeaders() throws ClientProtocolException, IOException {
		rclient = new RestClient();
		
		HashMap<String,String> headerMap = new HashMap<String,String>();
		headerMap.put("Content-Type", "application/json");
		
		chttp_response = rclient.get(partial_uri,headerMap);
		
		
				//Get Status Code
				int status_code = chttp_response.getStatusLine().getStatusCode();
				
				Assert.assertEquals(status_code, RESPONSE_STATUS_CODE_200);
				
				//Get ResponseString Body
				String responseString = EntityUtils.toString(chttp_response.getEntity(),"UTF-8");
				JSONObject responsejson = new JSONObject(responseString);
				System.out.println("Response json from API" + responsejson);
				//Single Value Assertion
				//pre_page
				String perPageValue = TestUtils.getValueByJPath(responsejson,"/per_page");
				System.out.println("Value of per page is :" + perPageValue);
				
				Assert.assertEquals(Integer.parseInt(perPageValue), 3);
				
				//total
				String totalValue = TestUtils.getValueByJPath(responsejson,"/total");
				System.out.println("Value of per page is :" + totalValue);
				
				Assert.assertEquals(Integer.parseInt(totalValue), 12);
				
				
				//get json array
				String lastName = TestUtils.getValueByJPath(responsejson, "/data[0]/last_name");
				String id = TestUtils.getValueByJPath(responsejson, "/data[0]/id");
				String avatar = TestUtils.getValueByJPath(responsejson, "/data[0]/avatar");
				String firstName = TestUtils.getValueByJPath(responsejson, "/data[0]/first_name");
				
				System.out.println(lastName);
				System.out.println(id);
				System.out.println(avatar);
				System.out.println(firstName);
				
				Assert.assertEquals(Integer.parseInt(id), 1);
				
				//Get Headers
				Header[] headerArray = chttp_response.getAllHeaders();
				
				//Convert headers into HashMap
				HashMap<String,String> hm = new HashMap<String,String>();
				
				for(Header header:headerArray) {
					hm.put(header.getName(), header.getValue());
				}
				System.out.println(hm);
				
				//Validate Headers
				/*
				String contenttype = TestUtils.getValueByJPath(responsejson, "/content-type");
				
				System.out.println(contenttype);
				
				Assert.assertEquals(contenttype, "application/json; charset=utf-8");
				*/
				
	}
}
