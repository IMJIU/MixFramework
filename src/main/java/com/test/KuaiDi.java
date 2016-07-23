package com.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * 快递查询
 * 
 * @author hxl
 * @version 2013-9-12
 * @see KuaiDi
 * @since
 */
public class KuaiDi {
	/**
	 * 日志
	 */
	private Logger log = Logger.getLogger(KuaiDi.class);

	public static void main(String[] args) {
		KuaiDi k = new KuaiDi();
		String content = k.searchkuaiDiInfo("6847554f86837f5a", "shentong", "3311184836360");
		System.out.println(content);
		String result = k.httpGET(content);
		System.out.println(result);
	}

	/**
	 * 快递查询接口方法
	 * 
	 * @param key
	 *            ：商家用户key值，在http://www.kuaidi100.com/openapi申请的
	 * @param com
	 *            ：快递公司代码，在http://www.kuaidi100.com/openapi网上的技术文档里可以查询到
	 * @param nu
	 *            ：快递单号，请勿带特殊符号，不支持中文（大小写不敏感）
	 * @return 快递100返回的url，然后放入页面iframe标签的src即可
	 * @see
	 */
	public String searchkuaiDiInfo(String key, String com, String nu) {
		String content = "";
		try {
			URL url = new URL("http://www.kuaidi100.com/applyurl?key=" + key + "&com=" + com + "&nu=" + nu);
			URLConnection con = url.openConnection();
			con.setAllowUserInteraction(false);
			InputStream urlStream = url.openStream();
			byte b[] = new byte[10000];
			int numRead = urlStream.read(b);
			content = new String(b, 0, numRead);
			while (numRead != -1) {
				numRead = urlStream.read(b);
				if (numRead != -1) {
					// String newContent = new String(b, 0, numRead);
					String newContent = new String(b, 0, numRead, "UTF-8");
					content += newContent;
				}
			}
			urlStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("快递查询错误");
		}
		return content;
	}

	/**
	 * 发送 get请求
	 */
	public static String httpGET(String url) {
		String result = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet(url);
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity);
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = e.getMessage();
		} catch (ParseException e) {
			e.printStackTrace();
			result = e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			result = e.getMessage();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
				result = e.getMessage();
			}
		}
		return result;
	}

}
