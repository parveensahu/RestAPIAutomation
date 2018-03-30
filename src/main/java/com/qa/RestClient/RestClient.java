package com.qa.RestClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.xml.sax.HandlerBase;

public class RestClient {

	//Get Method without Headers
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//http Get Request
		HttpGet httpget = new HttpGet(url); 
		CloseableHttpResponse chttp_response = httpClient.execute(httpget);
		return chttp_response;
		
		
	}
	//Get Method with Header
	public CloseableHttpResponse get(String url, HashMap<String,String> headerMap) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//http Get Request
		HttpGet httpget = new HttpGet(url); 
		for(Map.Entry<String, String> entry: headerMap.entrySet()) {
			httpget.addHeader(entry.getKey(),entry.getValue());
		}
		CloseableHttpResponse chttp_response = httpClient.execute(httpget);
		return chttp_response;
		
		
	}
	
	//POST Method
	public CloseableHttpResponse post(String url, String entityString, HashMap<String,String> headerMap) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//Http Post Request
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new StringEntity(entityString)); //for payload
		
		//for Headers
		for(Map.Entry<String, String> entry:headerMap.entrySet()) {
			httpPost.addHeader(entry.getKey(),entry.getValue());
		}
		CloseableHttpResponse chttp_response = httpClient.execute(httpPost);
		return chttp_response;
		
	}
}
