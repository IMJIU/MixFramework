package com.framework.weixin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * 
 * @author 简爱微萌
 * @Email zyw205@gmail.com 接口权限中设置OAuth2.0网页授权 域名 如：www.wechat68.com
 *        授权访问的URL：https://open.weixin.qq.com/connect/oauth2/authorize?appid=
 *        wx614c453e0d1dcd12
 *        &redirect_uri=http://www.wechat68.com/Javen/OauthTest
 *        &response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect
 */
public class Oauth2Servlet extends HttpServlet {

	private static Logger log = Logger.getLogger(Oauth2Servlet.class);

	private String get_access_token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?" + "appid=APPID" + "&secret=SECRET&"
	        + "code=CODE&grant_type=authorization_code";

	private String get_userinfo = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	private static final long serialVersionUID = -644518508267758016L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String code = request.getParameter("code");

		get_access_token_url = get_access_token_url.replace("APPID", "wx614c453e0d1dcd12");
		get_access_token_url = get_access_token_url.replace("SECRET", "fd00642f7a2fea32c5a7b060d9c37db1");
		get_access_token_url = get_access_token_url.replace("CODE", code);

		String json = getUrl(get_access_token_url);

		JSONObject jsonObject = JSONObject.fromObject(json);
		String access_token = jsonObject.getString("access_token");
		String openid = jsonObject.getString("openid");

		get_userinfo = get_userinfo.replace("ACCESS_TOKEN", access_token);
		get_userinfo = get_userinfo.replace("OPENID", openid);

		String userInfoJson = getUrl(get_userinfo);

		JSONObject userInfoJO = JSONObject.fromObject(userInfoJson);

		String user_openid = userInfoJO.getString("openid");
		String user_nickname = userInfoJO.getString("nickname");
		String user_sex = userInfoJO.getString("sex");
		String user_province = userInfoJO.getString("province");
		String user_city = userInfoJO.getString("city");
		String user_country = userInfoJO.getString("country");
		String user_headimgurl = userInfoJO.getString("headimgurl");

		// UserInfo_weixin userInfo=new UserInfo_weixin(user_openid,
		// user_nickname, user_sex, user_province, user_city, user_country,
		// user_headimgurl);
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method \n");
		out.println("openid:" + user_openid + "\n\n");
		out.println("nickname:" + user_nickname + "\n\n");
		out.println("sex:" + user_sex + "\n\n");
		out.println("province:" + user_province + "\n\n");
		out.println("city:" + user_city + "\n\n");
		out.println("country:" + user_country + "\n\n");
		out.println("<img src=/" + user_headimgurl + "/");
		out.println(">");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}
	public static String getUrl(String url) {
		String result = null;
		try {
			// 根据地址获取请求
			HttpGet request = new HttpGet(url);
			// 获取当前客户端对象
			HttpClient httpClient = new DefaultHttpClient();
			// 通过请求对象获取响应对象
			HttpResponse response = httpClient.execute(request);

			// 判断网络连接状态码是否正常(0--200都数正常)
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * 
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
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		return null;
	}

}