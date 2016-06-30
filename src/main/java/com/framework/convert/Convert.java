package com.framework.convert;

public class Convert {

	public static void main(String[] args) {
		String code = "{\"code\":0,\"data\":{\"country\":\"\u4e2d\u56fd\",\"country_id\":\"CN\",\"area\":\"\u534e\u4e1c\",\"area_id\":\"300000\",\"region\":\"\u798f\u5efa\u7701\",\"region_id\":\"350000\",\"city\":\"\u53a6\u95e8\u5e02\",\"city_id\":\"350200\",\"county\":\"\",\"county_id\":\"-1\",\"isp\":\"\u7535\u4fe1\",\"isp_id\":\"100017\",\"ip\":\"222.79.117.147\"}}";
		System.out.println(code);
		System.out.println(encodeUnicode("华东"));
//		System.out.println(decodeUnicode(code));

	}
	/**
	 * 字符串转化为unicode
	 * 
	 * @param gbString
	 * @return
	 */
	public static String encodeUnicode(final String gbString) {
		char[] utfBytes = gbString.toCharArray();
		String unicodeBytes = "";
		for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
			String hexB = Integer.toHexString(utfBytes[byteIndex]);
			if (hexB.length() <= 2) {
				hexB = "00" + hexB;
			}
			unicodeBytes = unicodeBytes + "\\u" + hexB;
			// unicodeBytes = unicodeBytes + hexB;
		}
		System.out.println(unicodeBytes);
		return unicodeBytes;
	}

	// unicode转化汉字
	public static String decodeUnicode(String utfStr) {
		System.out.println(utfStr);
		final StringBuffer buffer = new StringBuffer();
		String charStr = "";
		String operStr = utfStr;
		for (int i = 0; i < utfStr.length(); i = +4) {
			charStr = operStr.substring(0, 4);
			operStr = operStr.substring(4, operStr.length());
			char letter = (char) Integer.parseInt(charStr, 16);
			buffer.append(new Character(letter).toString());
		}
		return buffer.toString();
	}
}
