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

import com.alibaba.fastjson.JSONObject;
import com.framework.httpclient.JsonTool;
import com.framework.httpclient.Test_weixin;

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
		return readFileContent("token.tmp");
	}
}
