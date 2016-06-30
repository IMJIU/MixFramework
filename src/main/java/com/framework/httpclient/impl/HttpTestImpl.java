package com.framework.httpclient.impl;


public class HttpTestImpl extends HttpTestBase{

	public static final String COOKIE_IDONGRI = "Set-Cookie/tidongriSessionId";

	public static int env = 2; // 0.com 生产 环境 1.net环境 2.local本地

	public static int userType = 1; // 0.admin 1.customer 2.doctor

	public static int getEnv() {
		return env;
	}

	public static int getUserType() {
		return userType;
	}

	public static String http = "http://localhost:9000";

	static {
		if (getEnv() == 0)
			http = "http://www.idongri.com";
		else if (getEnv() == 1)
			http = "http://www.idongri.net";
		else if (getEnv() == 2)
			http = "http://localhost:9000";
	}

	public static void login() throws Exception {
		if (getUserType() == 2) {// 医生
			exe(http + "/api/open/doctorLogin2?terminal=1&versionCode=1&phoneNumber=11111111115&password=123456", GET);
		} else {// 病人
			exe(http + "/api/open/customerLogin2?phoneNumber=18650450008&password=123456&terminal=12&versionCode=10&version=12", GET);
		}
	}


}
