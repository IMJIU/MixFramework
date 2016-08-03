package com.util.upload_to_linux.base;


public abstract class BaseConstants {
	public static final String platform = "platform";
	public static final String admin = "admin";
	public static final String net = ".net";
	public static final String cn = ".cn";
	public static final String html = "html";
	public static final String classs = "class";
	
	public final static String dao_jar = "common-dao-3.0-SNAPSHOT.jar";
	public final static String service_jar = "common-service-3.0-SNAPSHOT.jar";
	public final static String lib_jar = "common-lib-3.0-SNAPSHOT.jar";
	public final static String core_jar = "common-core-3.0-SNAPSHOT.jar";
	public final static String util_jar = "common-util-3.0-SNAPSHOT.jar";

	public static final String host162 = "120.55.88.162";
	public static final String host112 = "121.43.99.112";
	public static final String host236 = "121.40.224.236";
	public static final String host187 = "121.40.150.187";
	public static final String host115 = "120.26.92.115";
	public static final String host71 = "121.41.55.71";
	
	public final static String linux_idongri_Path = "/usr/local/idongri/";
	public final static String current_path = Class.class.getClass().getResource("/").getPath();
	
	public static String server = net;
	public static String app = platform;
	public static String findType = html;// class | html
	
	public static boolean isThread = false;
	public static boolean mkDir = false;
	
	public  static String svn_path = "G:\\svn\\idongriV3\\";
	public  static String moveToDir = "d:\\target\\";


	public static String linux_webapp_Path = linux_idongri_Path + "webapps/" + app + "/";
	public static String linux_class_Path = linux_idongri_Path + "webapps/" + app + "/WEB-INF/classes/";
	public static String linux_lib_Path = linux_idongri_Path + "webapps/" + app + "/WEB-INF/lib/";

	
	public static String classListFilePath = current_path + "class_list.properties";
	public static String htmlListFilePath = current_path + "html_list.properties";

	public static String uploadHtmlFilePath = System.getProperty("user.dir") + "\\src\\main\\resources\\upload_html_list.txt";

	public  static String local_app_webapp_Path =  svn_path  + app + "\\src\\main\\webapp\\";
	public static String local_app_war_path = svn_path  + app + "\\target\\";
	
	public static String local_webapp_Path;
	
	


}
