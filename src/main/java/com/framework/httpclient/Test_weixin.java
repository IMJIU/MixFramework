package com.framework.httpclient;

import java.util.Map;
import com.framework.httpclient.impl.HttpWeiXinBase;

public class Test_weixin extends HttpWeiXinBase {


	private static String getServerIPListUrl = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=ACCESS_TOKEN";

	private static String addKfUrl = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token=ACCESS_TOKEN";

	private static String getCustomerListUrl = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN";

	private static String getCustomerInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info?lang=zh_CN&access_token=ACCESS_TOKEN";

	private static String requestOauth2Url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect&redirect_uri=";

	private static String oauth2Url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+appsecret+"&grant_type=authorization_code&code=";
	
	private static String removeMenuUrl = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	
	private static String getUnionId =  "https://api.weixin.qq.com/cgi-bin/user/info?lang=zh_CN&access_token=ACCESS_TOKEN";
	private static String getUnionId2 = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
	
	private static String getMediaList = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";
	private static String getMedia = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=ACCESS_TOKEN";
	private static String getMediaCount = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=ACCESS_TOKEN";
	private static String addMedia = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN";
	private static String getMediaTemp = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN";
	
	
//	private static String getUnionId = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	public static void main(String[] args) throws Exception {
		isWeixin = true;
		// 服务器ip
		// getServerIp();

		// 客服
//		 Map<String,String>param = new HashMap<String, String>();
//		 param.put("kf_account","test1@zlfkyo");
//		 param.put("nickname","test1");
//		 addkf(param);
			
//		System.out.println("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8517971af979361b&redirect_uri="+URLEncoder.encode("http://www.idongri.cn/wx/page/consult.do")+"&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect");
		// 获取用户列表（openid)
//		 getCustomerList();

		// 获取用户信息
		// "olWfQvnUV9f2pqPZIytGe8fadCjM", "olWfQvq3N-MbcORz0F3Io8Jlm7gI", "olWfQvrcCc4MshqJBQNaSAdKlHE8","olWfQviWmcItsYioOWzK-4AWpuo0",
		// "olWfQvkR37KZmP9FkGaKVozunE94", "olWfQvqbEOdzEavbHpNS3N16UScY", "olWfQvkBHUPRGsA9oK61BuF0cnkY"
//		getUserInfo("o1jbawvprUdxodOgZ7kJtiP5cRw8");// "unionid" : "o6xZEswwSR1gOxRMyzJEBdyclu0A",o1jbawvprUdxodOgZ7kJtiP5cRw8
//		微信 o1jbawvprUdxodOgZ7kJtiP5cRw8  第三方 okROztxflbQLOLJt636lWkB87eik
		
		//请求token
//		requestToken();
		
		//删除菜单
//		removeMenu();
		 
		 //获取union id okROztxflbQLOLJt636lWkB87eik
//		getUnionId("okROztxflbQLOLJt636lWkB87eik");//olWfQvkgTmrK600Lni0vmvIHmlMo o6xZEs7nMA3lWoSK2g42UP7DXhUo
//		getUnionId2(getToken(),"okROztxflbQLOLJt636lWkB87eik");
//		getMedia();
		/**  "type":TYPE,"offset":OFFSET,"count":COUNT
    		 图片（image）、视频（video）、语音 （voice）、图文（news）
		 */
//		go(getMediaList+"&" , post);
		doPost(getMediaList, "type=image&offset=0&count=232");
//		go(getMediaTemp+"&media_id=L7Nk65u5MDkHHEU6dmoXJuwK6oIfIkTWA4SQ0KZI1xg",get);
//		doPost(getMedia, "media_id=L7Nk65u5MDkHHEU6dmoXJuwK6oIfIkTWA4SQ0KZI1xg");
//		go(getMediaCount , get);

//		String result = httpRequest(getMediaList.replaceAll("ACCESS_TOKEN", getToken()), "POST", "&type=news&offset=0&count=20");
//		System.out.println(result);
	}
	
	public static void getMedia() throws Exception {
		go(getMedia , get);
	}
	
	public static void getServerIp() throws Exception {
		go(getServerIPListUrl , get);
	}
	
	public static void getUnionId(String openId) throws Exception {
		go(getUnionId +"&openid=" + openId, get);
	}
	
	public static void getUnionId2(String token,String openId) throws Exception {
		go(getUnionId2.replaceAll("ACCESS_TOKEN", token).replaceAll("OPEN_ID", openId), get);
	}
	
	public static void getCustomerList() throws Exception {
		go(getCustomerListUrl, get);
	}
	
	public static void removeMenu() throws Exception {
		go(removeMenuUrl , get);
	}
	
	public static void addkf(Map<String, String> param) throws Exception {
		check(executePost(addKfUrl+"access_token=" +getToken() , param, true));
	}
	
	public static void getUserInfo(String openId) throws Exception {
		go(getCustomerInfoUrl +  "&openid=" + openId, post);
	}

}
