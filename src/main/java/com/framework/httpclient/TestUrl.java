package com.framework.httpclient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.util.JSONUtils;

import com.framework.httpclient.impl.HttpTestBase;

public class TestUrl extends HttpTestBase {
	
	static void login() throws Exception {
		if (userType == 2) {// 医生 11111111115 12088888888 12988888888 13159637313 13084935126 13793899137 13645041346
			exe(http + "/api/open/doctorLogin2?terminal=1&versionCode=1&phoneNumber=13645041346&password=123456", GET);
		} else {// 病人18650450008 13645041346 13011111111 13099999999
			exe(http + "/api/open/customerLogin2?phoneNumber=13099999999&password=123456&terminal=12&versionCode=10&version=12", GET);
		}
	}
	
	
	public static String doTest() throws Exception {
		
//		getDate();
		open();
		customer();
		doctor();
		/******************************************* 【微信】 *****************************************************************************************************/
	    // exe("https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token="+TOKEN, GET);
		// exe(BASE_URL+"/menu/create?access_token="+TOKEN, POST);
		return result;
    }
	
	public static void customer() throws Exception{
		/******************************************* 【病人 】 *****************************************************************************************************/
		
//		result = exe(cn,customer,"/api/customer/getDrugsPrice?terminal=1&versionCode=3003&versionName=3.0&systemType=9&mobileType=x86&imeiCode=245&drugs=[{drugId:4001,amount:100.0},{drugId:4002,amount:200.0}]",post);
		
		//开方详情
//		exe(net,customer,"/api/customer/getSolutionDetail?solutionId=729", GET);
		
		//开方详情
//		exe(net,customer,"/api/customer/getLatestDrugWeight", GET);
		
		// 追加评分
//		result = exe(local,customer,"/api/customer/publishAppendScore?commentId=556&appendScore=4",get);
		
		//发送信息
		//result = exe(local,customer,"/api/customer/sendMessage?terminal=1&versionCode=2&doctorId=140&recordContent=adfasd&type=text",get);
		
		//获取信息
//		result = exe(local,customer,"/api/customer/getMessageList?oType=1&oId=13&pageNo=1&pageSize=10",get);
		
		//获取联系人列表（最近聊天列表）
//		result = exe(cn,customer,"/api/customer/getMessageLatestList?pageNo=1&pageSize=100",get);
		
		//测试买药
//		result = exe(cn,customer,"/api/customer/createDrugOrderV33?buyNumber=1&drugTypeCode=DRUG_TYPE_YC&solutionId=3890&drugProvideCode=DRUG_PROVIDE_DRGY&addressId=67",get);
//		exe(local,customer, "/api/customer/getSolutionDetail?solutionId=381", GET);
		
		
		//获取病案
//		result = exe(local,customer,"/api/customer/getCustomerCase",get);
//		result = exe(local,customer,"/api/customer/getLogisticsTrack?recordId=1018",get);
		
		//提交病案
//		result = exe(local,customer,"/api/customer/saveAndSendCase?name=zlf&sex=1&birthday=1231231241232&height=170&weight=64&describe=lfdjalsfjd&doctorId=137&caseImageList=['1.jpg','2.jpg']",get);
		
		//最新病案
//		result = exe(net,customer,"/api/customer/getLatestCase",get);

		//v3.0  发送病案
//		result = exe(local,customer,"/api/customer/saveAndSendCase?doctorId=0&caseId=9999&name=zlf&sex=1&birthday=190231203712&work=working&height=185&weight=64&describe=zzzz&solution=none&caseImageList=['xxxx.jpg','2222.jpg']&terminal=1&doctorId=137&type=text",get);

		// 根据服务购买记录id获取评价
		//result = exe("/api/customer/getCommentListByRecord?serviceRecordId=2869",get);
		
		//获取服务流
//		result = exe(local,customer,"/api/customer/getCustomerServiceFlow?doctorId=137",get);
		
		//获取字典接口
//		result = exe(cn,customer,"/api/open/getDictionary?pCode=REASONS_RJC",get);
		
		//获取病人问诊问题
//		result = exe(net,customer,"/api/customer/getQuestions?doctorId=435",get);
		
		//服药记录
//		result = exe(local,customer,"/api/customer/getTakeMedicineRecordList?pageSize=10&pageNo=1",get);
		
		//获取最近购药重量
//		result = exe(net,customer,"/api/customer/getLatestDrugWeight",get);
		
	}
	
	public static void doctor() throws Exception{
		/******************************************* 【医生】 *****************************************************************************************************/
		
		// 医生登陆
		// exe(local,doctor, "/api/open/doctorLogin2?terminal=1&versionCode=1&phoneNumber=11111111115&password=123456", GET);

		// 获取病人病案
		// exe(local,doctor, "/api/doctor/getCaseFamilyList?customerId=3861&pageNO=1&pageSize=10", GET);
//		result = exe(local,doctor, "/api/doctor/getCustomerCaseList?customerId=23&pageNo=1&pageSize=10", GET);
		
		// v3.0  获取病人病案列表
//		 result = exe(local,doctor, "/api/doctor/getCustomerCaseList?customerId=3859&pageNo=1&pageSize=10", GET);
		
		//获取最新消息列表
//		 result = exe(net,doctor, "/api/doctor/getMessageLatestList?pageNo=1&pageSize=1000", GET);
		
		//获取对方信息
//		 result = exe(local,doctor, "/api/doctor/getObjectInfo?oType=1&oId=32134", post);
		
		//追加诊聊记录
//		 result = exe(cn,doctor, "/api/doctor/appendInquiryRecord?content=slkjdfklajf&caseMirrorId=29802", post);
		 
	     //追加问题到 诊聊记录
//		 result = exe(local,doctor, "/api/doctor/addFollowUpQuestion?questionContent=slkjdfklajf&customerId=190538&answerContent=answer12121", post);
		 
		 //获取二维码
//		 result = exe(cn,doctor,"/api/doctor/getQrcode",get);
//		 result = exe(local,doctor,"/api/doctor/shareQrcodeInfo",get);
		
		// v3.0  获取病人详细病案
//		 exe(net,doctor, "/api/doctor/getCustomerCaseDetail?customerId=3816&caseMirrorId=2110", GET);
		
		
		 // v3.0  获取问诊问题
//		result = exe(net,doctor,"/api/doctor/getInquiryQuestionList?customerId=3861&pageNo=1&pageSize=10&questionVersion=0", GET);
		
//		result = exe(local,doctor, "/api/doctor/removeInquiryGroup?groupId=15", GET);
		//result = exe( "/api/doctor/addInquiryGroup?groupName=zlf", GET);
//		result = exe(local,doctor, "/api/doctor/addInquiryQuestion?groupId=17&questionContent=的放辣椒", POST);
//		result = exe(local,doctor, "/api/doctor/renameInquiryGroup?groupId=17&groupName=dddd", GET);
//		result = exe(local,doctor, "/api/doctor/modifyInquiryQuestion?questionId=62&questionContent=dddd", GET);
//		result = exe(local,doctor, "/api/doctor/removeInquiryQuestion?questionId=62&questionContent=dddd", GET);
		 
		// v3.0  获取病人简要病案列表
//		 result = exe(net,doctor, "/api/doctor/getCustomerSimpleCaseList?customerId=13&pageNo=1&pageSize=100", GET);

		// v3.0 开方案  //审核 4139 4123
//		result = exe(local,doctor,"/api/doctor/addSolution?caseMirrorId=2043&customerId=58&traceRemark=kldfjl&diagnose=dljfa&solutionThinking=dkfajl&recommendDrugType=DRUG_TYPE_CJ&supportDrugType=DRUG_TYPE_CJ;DRUG_TYPE_SW&dosage=2&attention=kldjfa&enjoin=djfla&revisitTime=12312312313&revisitDiscount=3&drugs=[{'drugId':4001,'amount':1},{'drugId':4002,'amount':2}]",post);

		// 方案详情
//		result = exe(local,doctor, "/api/doctor/getSolutionDetail?solutionId=65&pageNo=1&pageSize=10", GET);
		
		// 医生扩展表
//		 exe(local,doctor,"/getDoctorExtendInfo",GET);

		// 获取病人病案
		// exe(local,doctor, "/api/doctor/getCaseList?customerId=58&pageNO=1&pageSize=10", GET);

		// result = exe(local,doctor, "/api/doctor/getCaseMirrorById?caseMirrorId=1991", get);
		
//		result = exe(local,doctor, "/api/doctor/getInquiryQuestionList", get);
		
		//获取医生首页统计信息
//		result = exe(local,doctor,"/api/doctor/getDoctorStatistics",get);
		
		//获取网诊流程
//		result = exe(net,doctor,"/api/doctor/getInquiryFlowQuestionList",get);
		
		//获取随诊问题列表
//		result = exe(local,doctor,"/api/doctor/getFollowUpQuestionList?customerId=58&pageNo=1&pageSize=10",get);
		
		//获取病人随诊问诊单详情 26917 26887
//		result = exe(local,doctor,"/api/doctor/getFollowUpAnswerDetail?caseMirrorId=26917&pageNo=1&pageSize=10",get);
		
		//发送问诊单
//		result = exe(local,doctor,"/api/doctor/sendFollowupQuestionList?questionIdList=[17,18,19]&customerId=135",get);
		
		//修改病人问诊单问题与回答
//		result = exe(local,doctor,"/api/doctor/updateFollowUpQuestionAnswer?answerId=1&answerContent=ddddddd",get);
		
		//增加问题到问诊单
//		result = exe(local,doctor,"/api/doctor/addFollowUpQuestionToList?customerId=135&questionContent=ddddddd",get);
		
//		result = exe(local,doctor,"/api/doctor/removeFollowUpQuestion?answerId=1",get);
		
//		result = exe(local,doctor,"/api/doctor/addFollowUpQuestion?customerId=18&questionContent=ddddddd&answerContent=dsjlfl;asdjf",get);
		
	}
	
	public static void open() throws Exception{
		/******************************************* 【open】 *****************************************************************************************************/
		// exe(local,customer,"/api/open/updateDrugFile?order=962",get,noformat);
		
		//获取推荐列表
//		result = exe(net,customer,"/api/open/recommendCaseList?terminal=24&pageNo=1&pageSize=20&versionCode=213&mobileType=1&systemType=ios&imeiCode=123&versionName=123",get);
//		result = exe(local,customer,"/api/open/recommendCaseMsgList?recommendCaseId=17&pageNo=1&pageSize=20&terminal=24&versionCode=213&mobileType=1&systemType=ios&imeiCode=123&versionName=123",get);
		
		//idfa
//		result = exe(local,open,"/IdfaSubmit?appid=idongri&source=idongri&idfa=gggggggg",get);
//		result = exe(local,open,"/openLog?idfa=gggggggg&imei=123123123",get);
//		result = exe(local,open,"/IdfaRepeat?appid=idongri&source=idongri&idfa=gggggggg",get);
		
		//医生服务详情
//		result = exe(cn,customer,"/api/open/getServiceDetail?serviceMirrorId=1343",get);
		
		//获取bbs列表
//		result = exe(local,open,"/api/open/bbs/getTopicById?topicId=13",get);
		 
		//获取医生服务列表
//		result = exe(local,open,"/api/open/getDoctorServiceList?doctorId=64",get);
		
		//义诊列表
//		result = exe(cn,open,"/api/open/getPromotionServiceList?terminal=1&versionCode=3",get);
		
		result = exe(net,open,"/api/open/getFirst?useType=2&terminal=2&pageNo=1&pageSize=20&versionCode=213&mobileType=1&systemType=ios&imeiCode=123&versionName=123",get);
		
//		result = exe(local,open,"/api/open/createServiceOrderV36?customerPhone=13645041346",get);
		//获取支持药状
//		result = exe(net,open,"/api/open/getSupportDrugType?terminal=1&versionCode=3",get);
		
		//获取文案
//		result = exe(net,open,"/api/open/getCopywriterText?key=express_text&terminal=1&versionCode=3",get);
//		result = exe(local,open,"/api/open/getCopywriterText?group=hints&terminal=1&versionCode=3",get);
		
		//字典
//		result = exe(local,open,"/api/open/getDictionary?pCode=DOCTOR_SKILL_OPTION&terminal=1&versionCode=3",get);
//		result = exe(local,open,"/api/open/createTokenByValue?value=82&terminal=1&versionCode=3",get);
//		result = exe(local,open,"/api/open/createTokenByValue?token=1234&terminal=1&versionCode=3",get);
		
//		result = exe(local,open,"/api/open/getValueByToken",toMap2("token=^\"6^$:U/F)=*H~7!;+<^T]&<T,G[H[%9ZQU]_^A-:.I&6'>&&terminal=1&&versionCode=3"));
		
//		result = exe(local,open,"/api/open/addFollowUpCustomer",toMap("token=^\"6^$:U/F)=*H~7!;+<^T]&<T,G[H[%9ZQU]_^A-:.I&6'>&&phoneNumber=18559229820&&name=jj"));
		
		//医生活动-收件
//		result = exe(local,open,"/api/open/addReceiveAddress?doctorId=13&style=样式1&address=street01",post);
		
		//注册患者
//		result = exe(net,open,"/api/open/customerRegister2?phoneNumber=18559229825&password=123456&code=chanel&useType=2&terminal=1&pageNo=1&pageSize=20&versionCode=213&mobileType=1&systemType=ios&imeiCode=123&versionName=123",post);
        
		
	}

	private static Map<String, String> toMap2(String string) {
		Deal deal = (str) -> {
			Map<String,String> param = new HashMap<>();
			Arrays.asList(str.split("&&")).forEach(s->{
				String[] kv = s.split("=",2);
				param.put(kv[0],kv[1]);
			});
			return param;
		};
		return deal.convert(string);
    }
	@FunctionalInterface
	interface Deal {
		Map<String,String> convert(String string);
	}
	public static void main(String[] args) throws Exception {
		doTest();
		redo();
	}

	public static String TOKEN = "Eyw0tPdtL0q1eQV8OLEgoSeVtec4YzaKtnhjggsnh2kLCgMK5l_acgzWFfPZRZHn7JqzCIlPiGFxSfzQXJ5SGc51N0bgVo4J5-iIC0vgggI";

	public static final String BASE_URL = "https://api.weixin.qq.com/cgi-bin";

	public static void redo() throws Exception {
		System.out.println("result"+result);
		if (result == null || "".equals(result) || "null".equals(result)|| result.indexOf("<html>")!=-1) {
			System.out.println("#### error ####\n" + result);
			login();
			System.out.println("relogin... relogin..................");
			doTest();
		} else {
			System.out.println("#### result ####\n" + result);
		}
	}
	

}
