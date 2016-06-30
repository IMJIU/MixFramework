<<<<<<< HEAD
package com.framework.httpclient;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.util.locale.StringTokenIterator;

public class TestString {

	public static void main(String[] args) throws Exception {
		// assert_test();
		// split();
		// String l =
		// "/api/open/files/video_image/8ce22f79-67ed-4096-91c9-4af67bd7f5bd.jpg
		// HTTP/1.1 302 -";
		// System.out.println(l.substring(0,l.indexOf("/",16)));

		// qr_string();

		// String s = "d2132-A3";
		// System.out.println(s.matches("\\S+-\\d+"));

		// System.out.println("http://www.idongri.net".substring("http://www.idongri.net".indexOf(".")));;
		// for (int i = 0; i < 100; i++) {
		// System.out.println(System.currentTimeMillis() / 1000);
		// Thread.sleep(1000);
		// }

		// System.out.println(getCommonString("🚹IM JIU🎤"));

		System.out.println("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxec2698856db6a0f6&redirect_uri="
				+ URLEncoder.encode("http://weixin.idongri.cn/wx/web/auth/consult.do") + "&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect");

	}

	public static String getCommonString(String str) {
		StringBuilder builder = new StringBuilder();
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]|[a-zA-Z]|[0-9]");
		for (int i = 0; i < str.length(); i++) {
			Matcher m = p.matcher("" + str.charAt(i));
			if (m.find()) {
				builder.append(str.charAt(i));
			}
		}
		return builder.toString();
	}

	private static void qr_string() {
		String s = "qrscene_10011";
		int idx = s.indexOf("_") + 1;
		System.out.println(idx);
		Integer scene = Integer.parseInt(s.substring(idx, idx + 1));
		System.out.println(scene);
		Integer id = Integer.parseInt(s.substring(idx + 2));
		System.out.println(id);
	}

	private static void assert_test() {
		// 断言1结果为true，则继续往下执行
		assert true;
		System.out.println("断言1没有问题，Go！");
		System.out.println("\n-----------------\n");
		// 断言2结果为false,程序终止
		assert false : "断言失败，此表达式的信息将会在抛出异常的时候输出！";
		System.out.println("断言2没有问题，Go！");
	}

	private static void split() {
		String rule4XxsRegEx = "<script| </script|<iframe|</iframe|<frame|</frame|set-cookie|"
				+ "%3cscript|%3c/script|%3ciframe|%3c/iframe|%3cframe| %3c/frame|" + "<body|</body|%3cbody|%3c/body|eval|<|>|\\|(|)";
		StringTokenIterator token = new StringTokenIterator(rule4XxsRegEx, "\\|");
		while (token.hasNext()) {
			System.out.println(token.next());
		}

	}
}
=======
package com.framework.httpclient;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.util.locale.StringTokenIterator;

public class TestString {


	public static void main(String[] args) throws Exception {
		// assert_test();
		// split();
		// String l = "/api/open/files/video_image/8ce22f79-67ed-4096-91c9-4af67bd7f5bd.jpg HTTP/1.1 302 -";
		// System.out.println(l.substring(0,l.indexOf("/",16)));

		// qr_string();

		// String s = "d2132-A3";
		// System.out.println(s.matches("\\S+-\\d+"));

		// System.out.println("http://www.idongri.net".substring("http://www.idongri.net".indexOf(".")));;
//		for (int i = 0; i < 100; i++) {
//			System.out.println(System.currentTimeMillis() / 1000);
//			Thread.sleep(1000);
//		}

//		System.out.println(getCommonString("🚹IM JIU🎤"));

		System.out.println("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxec2698856db6a0f6&redirect_uri=" + URLEncoder.encode("http://weixin.idongri.cn/wx/web/auth/consult.do") + "&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect");

	}

	public static String getCommonString(String str) {
		StringBuilder builder = new StringBuilder();
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]|[a-zA-Z]|[0-9]");
		for (int i = 0; i < str.length(); i++) {
			Matcher m = p.matcher("" + str.charAt(i));
			if (m.find()) {
				builder.append(str.charAt(i));
			}
		}
		return builder.toString();
	}

	private static void qr_string() {
		String s = "qrscene_10011";
		int idx = s.indexOf("_") + 1;
		System.out.println(idx);
		Integer scene = Integer.parseInt(s.substring(idx, idx + 1));
		System.out.println(scene);
		Integer id = Integer.parseInt(s.substring(idx + 2));
		System.out.println(id);
	}

	private static void assert_test() {
		// 断言1结果为true，则继续往下执行
		assert true;
		System.out.println("断言1没有问题，Go！");
		System.out.println("\n-----------------\n");
		// 断言2结果为false,程序终止
		assert false : "断言失败，此表达式的信息将会在抛出异常的时候输出！";
		System.out.println("断言2没有问题，Go！");
	}

	private static void split() {
		String rule4XxsRegEx = "<script| </script|<iframe|</iframe|<frame|</frame|set-cookie|" + "%3cscript|%3c/script|%3ciframe|%3c/iframe|%3cframe| %3c/frame|"
				+ "<body|</body|%3cbody|%3c/body|eval|<|>|\\|(|)";
		StringTokenIterator token = new StringTokenIterator(rule4XxsRegEx, "\\|");
		while (token.hasNext()) {
			System.out.println(token.next());
		}

	}
}
>>>>>>> 5cb87eab4cde829fa9f34129d3a70b8d2d62c160
