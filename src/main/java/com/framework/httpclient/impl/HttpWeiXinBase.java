package com.framework.httpclient.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.framework.httpclient.JsonTool;
import com.framework.weixin.MyTrustManager;

public class HttpWeiXinBase extends HttpTestBase {

	//测试
	public static String appid = "wx8517971af979361b";
	public static String appsecret = "d4624c36b6795d1d99dcf0547af5443d";
	
	//线上
//	public static String appid = "wxec2698856db6a0f6";
//	public static String appsecret = "9ccc25b7468a87f3ab317ae46c016a1d";
	
	//app线上
//	public static String appid = "wx33b3f9fc372960ce";
//	public static String appsecret = "39247590d460e1190995df1bb2b4835a";

	private static String getTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + appsecret;

	public static void go(String url, boolean method) throws Exception {
		boolean isPass = false;
		url = url.replaceAll("ACCESS_TOKEN", getToken());
		if (method == get) {
			isPass = check(executeGet(url, true));
		} else {
			isPass = check(executePost(url, true));
		}
		if (!isPass) {
			if (method == get) {
				isPass = check(executeGet(url, true));
			} else {
				isPass = check(executePost(url, true));
			}
		}
	}
	
	public static void doGet(String url) throws Exception {
		url = url.replaceAll("ACCESS_TOKEN", getToken());
		if (!check(executeGet(url, true))) {
			executeGet(url, true);
		}
	}
	public static void doPost(String url,String param) throws Exception {
		String[] ps = param.split("&");
		Map map = new HashMap<>();
		for (String pp : ps) {
			String[] kv = pp.split("=");
			map.put(kv[0], kv[1]);
		}
		url = url.replaceAll("ACCESS_TOKEN", getToken());
		if(!check(executePost(url, map, true))){
			executePost(url, map, true);
		}
	}
	public static void doPost2(String url,String param) throws Exception {
		System.out.println(url);
		System.out.println(param);
		String ps = convertUrlParamToJson(param);
		System.out.println(ps);
		CloseableHttpClient httpClient = getHttpClient();
		try {
			HttpPost post = new HttpPost(url.replaceAll("ACCESS_TOKEN", getToken())); // 这里用上本机的某个工程做测试
			post.setEntity(new StringEntity(ps, "utf-8"));
			// 执行请求
			CloseableHttpResponse httpResponse = httpClient.execute(post);
			try {
				HttpEntity entity = httpResponse.getEntity();
				if (null != entity) {
					System.out.println(JsonTool.formatJson(EntityUtils.toString(entity,"utf-8").trim(), "  "));
				}
			} finally {
				httpResponse.close();
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				closeHttpClient(httpClient);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private static CloseableHttpClient getHttpClient() {
		return HttpClients.createDefault();
	}

	private static void closeHttpClient(CloseableHttpClient client) throws IOException {
		if (client != null) {
			client.close();
		}
	}
	public static String convertUrlParamToJson(String param){
		String[] ps = param.split("&");
		Map map = new HashMap();
		for (String pp : ps) {
			String[] kv = pp.split("=");
			map.put(kv[0], kv[1]);
		}
		return JSONObject.toJSONString(map);
	}
	public static boolean check(String result) throws Exception {
		// {"errcode":42001,"errmsg":"access_token expired hint: [_azUMA0760vr19]"}
		Object obj = JSONObject.parseObject(result).get("errcode");
		if (obj != null && (obj.toString().equals("42001") || obj.toString().equals("40001"))) {
			System.out.println("expired!");
			requestToken();
			return false;
		}
		return true;
	}
	public static void requestToken() throws Exception {
		System.out.println(getTokenUrl);
		JSONObject obj = JSONObject.parseObject(executeGet(getTokenUrl, true));
		String token = obj.get("access_token").toString();
		writeToken(token);
	}
	/**
	 * { "errcode" : 42001, "errmsg" : "access_token expired hint: [D2Zroa0750vr22]" }
	 * 
	 * @author zlf
	 * @date 2016年4月27日 下午3:06:07
	 * @return
	 */
	public static String getToken() {
		String token =  readFileContent("token.tmp");
		if(token == null){
			try {
				requestToken();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return readFileContent("token.tmp");
		}else{
			return token;
		}
		
		
	}
	
	
	/**
	 */
	public static String httpRequest(String requestUrl, String requestMethod, String outputStr) {
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyTrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			return buffer.toString();
		} catch (ConnectException ce) {
			ce.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
