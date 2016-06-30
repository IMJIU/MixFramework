package com.framework.httpclient.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import com.framework.httpclient.JsonTool;

public  class HttpTestBase {

//	public static final String COOKIE_IDONGRI = "Set-Cookie/tidongriSessionId";
	public static int env = 2; // 0.com 生产 环境 1.net环境 2.local本地
	public static int userType = 1; // 0.admin 1.customer 2.doctor
	public static final int customer = 1;
	public static final int doctor = 2;
	public static final int local = 0;
	public static final int net = 1;
	public static final int com = 2;
	public static final int open = 3;
	public static final int cn = 4;
	public static String http="";
	public static String exe(int en,int uType,String url, Map<String,String>kv) throws Exception {
		initEnv(en, uType);
		String result =  exePost(http+url, true,kv);
		if(uType == open){
			System.out.println(result);
			return "result";
		}else{
			return result;
		}
	}
	public static String exe(int en,int uType,String url, boolean isPost) throws Exception {
		initEnv(en, uType);
		String result =  exe(http+url, isPost);
		if(uType == open){
			System.out.println(result);
			return "result";
		}else{
			return result;
		}
	}
	private static void initEnv(int en, int uType) {
	    userType = uType;
		env = en;
		if (env == com)
			http = "http://www.idongri.com";
		else if (env == net)
			http = "http://www.idongri.net";
		else if (env == local)
//			http = "http://192.168.99.92:8089";
		http = "http://localhost";
		else if (env == cn)
//			http = "http://www.idongri.cn";
			http = "http://121.40.224.236:8080";
    }
	public static String exe(String url, boolean isPost) throws Exception {
		if (isPost) return executePost(url, true);
		else return executeGet(url, true);
	}
	public static String exe(String url, boolean isPost, boolean isFormat) throws Exception {
		if (isPost) return executePost(url, isFormat);
		else return executeGet(url, isFormat);
	}
	public static String exePost(String url,  boolean isFormat, Map<String,String>kv) throws Exception {
		return executePost(url,kv, isFormat);
	}

	public static String executeGet(String url, boolean isFormat) throws Exception {
		String result = null;
		HttpClient client = new DefaultHttpClient();
		HttpGet req = new HttpGet(url);
		System.out.println("GET:"+url);
		addCookieHeader(req);
		HttpResponse response = client.execute(req);
		addCookieHeader(response);
		HttpEntity entity = response.getEntity();
		String pageContent = "";
		try {
			pageContent = getContent(entity, pageContent);
			System.out.println("返回结果-----------------------------------------------------------------------------------");
			if (isFormat)
				System.out.println(JsonTool.formatJson(pageContent.trim(), "  "));
			else
				System.out.println(pageContent.trim());
			result = pageContent;
			if (pageContent.trim().equals("")) {
				System.out.println("relogin....");
				// executeGet(url, isFormat);
			}
		} catch (IOException ioe) {
			System.out.println("IOException");
		}
		req.abort();
		client.getConnectionManager().shutdown();
		return result;
	}
	public static String executePost(String url,Map<String,String>kv, boolean isFormat) throws Exception {
		return executePost(generateHttp(url,kv), isFormat);
	}
	public static String executePost(String urlAndParameters, boolean isFormat) throws Exception {
		return executePost(generateHttp(urlAndParameters), isFormat);
	}
	private static String executePost(HttpPost req, boolean isFormat) throws Exception {
		String result = null;
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BEST_MATCH);
		addCookieHeader(req);
		HttpResponse response = client.execute(req);
		addCookieHeader(response);
		HttpEntity entity = response.getEntity();
		String pageContent = "";
		try {
			pageContent = getContent(entity, pageContent);
			System.out.println("返回结果-----------------------------------------------------------------------------------");
			if (isFormat)
				System.out.println(JsonTool.formatJson(pageContent.trim(), "  "));
			else
				System.out.println(pageContent.trim());
			result = pageContent;
		} catch (IOException ioe) {
			System.out.println("IOException");
		}
		req.abort();
		client.getConnectionManager().shutdown();
		return result;
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
	private static HttpPost generateHttp(String url,Map<String, String> paramMap) throws UnsupportedEncodingException {
		HttpPost http = http = new HttpPost(url);;
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		for (String key : paramMap.keySet()) {
			nameValuePairs.add(new BasicNameValuePair(key, paramMap.get(key)));
		}
		http.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
		return http;
	}
	private static HttpPost generateHttp(String urlAndParameters) throws UnsupportedEncodingException {
		int index = urlAndParameters.indexOf('?');
		HttpPost http = null;
		if (index == -1) {
			http = new HttpPost(urlAndParameters);
		} else {
			String url = urlAndParameters.substring(0, index);
			String parameters = urlAndParameters.substring(index + 1);
			http = new HttpPost(url);

			parameters = parameters.trim();
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			while (true) {
				int index_and = parameters.indexOf('&');
				String temp = null;
				if (index_and < 0) {
					if (parameters.equals("")) {
						break;
					} else {
						temp = parameters;
						parameters = "";
					}
				} else {
					temp = parameters.substring(0, index_and);
					parameters = parameters.substring(index_and + 1);
				}

				int index_equal = temp.indexOf('=');
				String key = temp.substring(0, index_equal);
				String value = temp.substring(index_equal + 1);

				nameValuePairs.add(new BasicNameValuePair(key, value));
			}

			http.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
		}
		return http;
	}

	public static void addCookieHeader(HttpResponse httpResponse) {
		for (Header h : httpResponse.getAllHeaders()) {
			if (h.getName().equals("Set-Cookie") && h.getValue().indexOf("idongri") != -1) {
				System.out.println("response cookie!");
				String setCookie = httpResponse.getFirstHeader("Set-Cookie").getValue();
				String idongriSessionId = setCookie.substring("idongriSessionId=".length(), setCookie.indexOf(";"));
				writeCookie(h.getValue());
				System.out.println("idongriSessionId:" + idongriSessionId);
			}
		}
	}

	public static void addCookieHeader(HttpRequest request) {
		String cookie = readFileContent("cookie.tmp");
		if (cookie != null) {
			String idongriSessionId = cookie.substring("idongriSessionId=".length(), cookie.indexOf(";"));
			request.addHeader("Cookie", "idongriSessionId=" + idongriSessionId);
		}

	}
	private static void writeCookie(String value) {
		File f1 = new File("cookie.tmp");
		FileOutputStream fo = null;
		try {
			if (!f1.exists())
				f1.createNewFile();
			fo = new FileOutputStream(f1);
			fo.write(value.getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fo.flush();
				fo.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void writeToken(String value) {
		File f1 = new File("token.tmp");
		FileOutputStream fo = null;
		try {
			if (!f1.exists())
				f1.createNewFile();
			fo = new FileOutputStream(f1);
			fo.write(value.getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fo.flush();
				fo.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String readFileContent(String filename) {
		File f1 = new File(filename);
		FileInputStream fi = null;
		byte[] b = new byte[1024];
		try {
			if (!f1.exists())
				f1.createNewFile();
			fi = new FileInputStream(f1);
			int len = fi.read(b);
			if (len > 0)
				return new String(b, 0, len);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fi.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static boolean hasMsg;

	public static boolean isAnonymous;

	public static String loginResult; // 登陆或获取游客ID返回的json串

	public static String hostAddress;

	public static final boolean POST = true;

	public static final boolean GET = false;

	public static final boolean post = true;

	public static final boolean get = false;

	public static final boolean noformat = false;

	public static final boolean format = true;
	
	public static String result;
}
