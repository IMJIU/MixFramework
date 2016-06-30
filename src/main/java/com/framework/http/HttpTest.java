package com.framework.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.framework.httpclient.JsonTool;

public class HttpTest {

	public static void main(String[] args)  throws Exception {
//		String result = t2("http://localhost:9000/api/open/refreshParam?pass=1234",false);
//		System.out.println(result);
		get("http://localhost:9000/api/open/refreshParam?pass=1234");
	}
	private static String getContent(HttpEntity entity, String pageContent) throws UnsupportedEncodingException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
		while (true) {
			String strTmp = br.readLine();
			if (strTmp == null)
				break;
			else {
				pageContent += strTmp;
				pageContent += "\n";
			}
		}
		return pageContent;
	}
	/**
	 * 发送 get请求
	 */
	public static void get(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet(url);
			System.out.println("executing request " + httpget.getURI());
			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				System.out.println("--------------------------------------");
				// 打印响应状态
				System.out.println(response.getStatusLine());
				if (entity != null) {
					// 打印响应内容长度
					System.out.println("Response content length: " + entity.getContentLength());
					// 打印响应内容
					System.out.println("Response content: " + EntityUtils.toString(entity));
				}
				System.out.println("------------------------------------");
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static String t2(String url, boolean isFormat) throws Exception {
		String result = null;
		HttpClient client = new DefaultHttpClient();
		HttpGet req = new HttpGet(url);
		HttpResponse response = client.execute(req);
		HttpEntity entity = response.getEntity();
		String pageContent = "";
		try {
			pageContent = getContent(entity, pageContent);
			result = pageContent;
		} catch (IOException ioe) {
			System.out.println("IOException");
		}
		req.abort();
		client.getConnectionManager().shutdown();
		return result;
	}

	

}
