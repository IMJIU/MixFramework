package com.test;

import com.google.common.collect.Lists;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by laizy on 2016/6/6.
 */
public final class SignUtil {
	private static final String CHARSET_NAME = "UTF-8";
	private static String[] excludeKeys = new String[] { "returnCode", "returnMsg", "sign" };

	public static void main(String[] args) {
		if(args.length<=1){
			System.out.println("缺少参数");
			System.exit(-1);
		}
		System.out.println("p1:"+args[0]);
		System.out.println("p2:"+args[1]);
		String secret = args[0];
		String code = args[1];
		Map<String, String> paramMap = new HashMap<String, String>();
		for (String s : code.split("&")) {
			String[] kv = s.split("=");
			paramMap.put(kv[0], kv[1]);
		}
		System.out.println(SignUtil.sign(paramMap, secret));
	}

	public static String sign(Map<String, String> params, String appSecret) {
		try {
			return DigestUtils.md5Hex((createLinkString(params) + appSecret).getBytes(CHARSET_NAME)).toLowerCase();
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params 需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	private static String createLinkString(Map<String, String> params) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		List<String> strings = Lists.newArrayList();
		for (String key : keys) {
			if (isBlank(key) || contains(excludeKeys, key)) {
				continue;
			}
			String value = params.get(key);
			if (isBlank(value)) {
				continue;
			}
			strings.add(key + "=" + value);
		}
		return join(strings, "&");
	}

	private static String join(List<String> strings, String delimit) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strings.size(); i++) {
			sb.append(strings.get(i)).append(delimit);
		}
		if (strings.size() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	private static boolean contains(String[] arr, String key) {
		for (String s : arr) {
			if (s.equals(key)) {
				return true;
			}
		}
		return false;
	}

	private static boolean isBlank(String key) {
		if (key != null && !"".equals(key.trim()))
			return true;
		return false;
	}
}
