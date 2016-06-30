package com.framework.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClient2 {

	public static void main(String args[]) throws IOException {
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("account", ""));
		formparams.add(new BasicNameValuePair("password", ""));
		HttpEntity reqEntity = new UrlEncodedFormEntity(formparams, "utf-8");

		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).setConnectionRequestTimeout(5000).build();

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://cnivi.com.cn/login");
		post.setEntity(reqEntity);
		post.setConfig(requestConfig);
		HttpResponse response = client.execute(post);

		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity resEntity = response.getEntity();
			String message = EntityUtils.toString(resEntity, "utf-8");
			System.out.println(message);
		} else {
			System.out.println("请求失败");
		}
	}
}