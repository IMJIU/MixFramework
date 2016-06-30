package com.framework.httpclient;



import static com.framework.httpclient.impl.HttpTestImpl.login;

import com.framework.httpclient.impl.HttpTestImpl;

public class TestDoctor extends HttpTestImpl {
	
	public static int getEnv(){
		// 0.com 生产 环境 1.net环境 2.local本地
		return 2;
	}
	
	public static int getUserType(){
		// 0.admin 1.customer 2.doctor
		return 2;
	}

	/**
	 * @author zlf 
	 * @date 2015年11月27日 下午2:04:12 
	 * @return
	 * @throws Exception
	 */
	public static String doTest() throws Exception {
	    // exe("https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token="+TOKEN, GET);
		// exe(BASE_URL+"/menu/create?access_token="+TOKEN, POST);
		
		/******************************************* 【医生】 *****************************************************************************************************/
		// 医生登陆
		// result = exe(http + "/api/open/doctorLogin2?terminal=1&versionCode=1&phoneNumber=11111111115&password=123456", GET);

		// 获取病人病案
		// result = exe(http + "/api/doctor/getCaseFamilyList?customerId=3861&pageNO=1&pageSize=10", GET);
		
		// v3.0  获取病人病案列表
		 result = exe(http + "/api/doctor/getCustomerCaseList?customerId=3861&pageNo=1&pageSize=10", GET);
		// v3.0  获取病人详细病案
		// result = exe(http + "/api/doctor/getCustomerCaseDetail?customerId=3816&caseMirrorId=1879", GET);
		
		// 医生扩展表
		// result = exe(http +"/getDoctorExtendInfo",GET);

		// 获取病人病案
		// result = exe(http + "/api/doctor/getCaseList?customerId=58&pageNO=1&pageSize=10", GET);

		//
		// result = exe(http + "/api/doctor/getCaseMirrorById?caseMirrorId=1991", get);
		
//		result = exe(http + "/api/doctor/getInquiryQuestionList", get);
		/******************************************* 【open】 *****************************************************************************************************/
		// exe(http + "/api/open/updateDrugFile?order=962",get,noformat);
		return result;
    }
	public static void main(String[] args) throws Exception {
		doTest();
		redo();
	}

	public static String TOKEN = "Eyw0tPdtL0q1eQV8OLEgoSeVtec4YzaKtnhjggsnh2kLCgMK5l_acgzWFfPZRZHn7JqzCIlPiGFxSfzQXJ5SGc51N0bgVo4J5-iIC0vgggI";

	public static final String BASE_URL = "https://api.weixin.qq.com/cgi-bin";
	

	public static void redo() throws Exception {
		if (result == null || "".equals(result) || "null".equals(result)) {
			System.out.println("#### error ####\n" + result);
			login();
			System.out.println("relogin... relogin..................");
			doTest();
		} else {
			System.out.println("#### result ####\n" + result);
		}
	}

}
