package com.util;

public class StringUtils {

	public static String getEncoding(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s = encode;
				return s;
			}
		} catch (Exception exception) {}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s1 = encode;
				return s1;
			}
		} catch (Exception exception1) {}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s2 = encode;
				return s2;
			}
		} catch (Exception exception2) {}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s3 = encode;
				return s3;
			}
		} catch (Exception exception3) {}
		return "";
	}

	public static void encode(String destination) {
		try {
			System.out.println(destination);

			System.out.println(destination.getBytes());

			System.out.println(destination.getBytes("GB2312"));

			System.out.println(destination.getBytes("ISO8859_1"));

			System.out.println(new String(destination.getBytes()));

			System.out.println(new String(destination.getBytes(), "GB2312"));

			System.out.println(new String(destination.getBytes(), "ISO8859_1"));

			System.out.println(new String(destination.getBytes("GB2312")));

			System.out.println(new String(destination.getBytes("GB2312"), "GB2312"));

			System.out.println(new String(destination.getBytes("GB2312"), "ISO8859_1"));

			System.out.println(new String(destination.getBytes("ISO8859_1")));

			System.out.println(new String(destination.getBytes("ISO8859_1"), "GB2312"));

			System.out.println(new String(destination.getBytes("ISO8859_1"), "ISO8859_1"));

			// eg:判断当前字符串的编码格式。
			// 判断当前字符串的编码格式
			if (destination.equals(new String(destination.getBytes("iso8859-1"), "iso8859-1"))) {
				destination = new String(destination.getBytes("iso8859-1"), "utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
