package com.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

public class UrlConnection {

	public static void main(String[] args) throws Exception {
		// String s = getPage("http://zyan.cc/book/nginx/code/", "gbk");
		String txt = "11.3.4.txt";
		String s = getPage("http://zyan.cc/book/nginx/code/%B5%DA11%D5%C2/" + txt, "gbk");
		System.out.println(s);
		File f = new java.io.File("F:\\Temp\\PDF\\Nginx\\[实战Nginx_取代Apache的高性能Web服务器].代码\\11\\" + txt);
		if (!f.exists())
			f.createNewFile();
		FileOutputStream fo = new FileOutputStream(f);
		fo.write(s.getBytes());
	}
	public static String getPage(String url, String charset) {
		if (url == null) {
			System.out.println("search null");
			return null;
		}
		String result = "";
		BufferedReader in = null;
		int op = 0;
		StringBuilder piple = new StringBuilder();
		while (op++ <= 10) {
			try {
				URL realUrl = new URL(url);
				// 打开和URL之间的连接
				URLConnection connection = realUrl.openConnection();
				// 设置通用的请求属性
				connection.setRequestProperty("accept", "*/*");
				connection.setRequestProperty("Charset", charset);
				connection.setRequestProperty("connection", "Keep-Alive");
				connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
				// 建立实际的连接
				try {
					connection.connect();
				} catch (Exception e) {
					System.out.println("not connection");
				}
				// 获取所有响应头字段
				Map<String, List<String>> map = connection.getHeaderFields();
				// 遍历所有的响应头字段
				for (String key : map.keySet()) {
					System.out.println(key + "--->" + map.get(key));
				}
				// 定义 BufferedReader输入流来读取URL的响应
				in = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
				String line;
				while ((line = in.readLine()) != null) {
					// result += line+"\n";
					piple.append(line).append("\n");
				}
				break;
			} catch (UnknownHostException e0) {
				System.out.println("无法解析域名");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				System.out.println("发送GET请求出现异常！" + e);
				e.printStackTrace();
			} finally {// 使用finally块来关闭输入流
				try {
					if (in != null) {
						in.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return piple.toString();
	}
}
