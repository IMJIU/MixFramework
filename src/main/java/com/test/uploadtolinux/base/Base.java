package com.test.uploadtolinux.base;

import java.io.FileOutputStream;
import com.book.jdk18.Process;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.linux.ftp.SFTP_T01;

public class Base {
	public static String server = ".net";
	public static String findType = "html";// class | html

	static SFTP_T01 sf = new SFTP_T01();
	
	static ChannelSftp sftp = null;
	static String fromPath = "d:\\svn_code\\idongriV3";

	public final static String toPath = "/root/";
	public final static String linux_class_Path = "/usr/local/idongri/webapps/idongri/WEB-INF/classes/";
	public final static String linux_idongri_Path = "/usr/local/idongri/webapps/idongri/";
	public final static String linux_lib_Path = "/usr/local/idongri/webapps/idongri/WEB-INF/lib/";
	public final static String toHtmlPath = "/usr/local/idongri/webapps/idongri/";
	
	public final static String dao_jar = "common-dao-3.0-SNAPSHOT.jar";
	public final static String service_jar = "common-service-3.0-SNAPSHOT.jar";

	public final static String classListText = "D:\\git\\MixTest\\src\\main\\resources\\class_list.properties";
	public final static String htmlListText ="D:\\git\\MixTest\\src\\main\\resources\\html_list.properties";
	public final static String uploadHtmlFilePath = "D:\\git\\MixTest\\src\\main\\resources\\upload_html_list.txt";

	public final static String moveToDir = "d:\\target\\";
	
	public static String serverIP = "";
	
	static FileOutputStream uploadHtmlListOutput = null;

	static void _uploadToLinux(String dir, String file) throws Exception {
		System.out.println("dir:" + dir);
		System.out.println("file:" + file);
		sf.upload(dir, file, sftp);
		if(sf==null)renewF();
		sf = null;
		sftp.getSession().disconnect();
		sftp.disconnect();
		sftp.exit();
		System.out.println("over");
	}
	static void _downloadFromLinux(String dir, String file,String to) throws Exception {
		System.out.println("dir:" + dir);
		System.out.println("file:" + file);
		System.out.println("to:" + to);
		sf.download(dir, file, to, sftp);
		sf = null;
		sftp.getSession().disconnect();
		sftp.disconnect();
		sftp.exit();
		System.out.println("over");
	}	
	public static void loopServer(Process callback){
		if(sf==null)renewF();
		if(server.equals(".net")){
			s187();
			callback.process(null);;
		}else{
			s162();
			callback.process(null);;
			s112();
			callback.process(null);;
			s236();
			callback.process(null);;
		}
	}
	public static void s162() {
		sftp = sf.connect("120.55.88.162", 22, "root", "Idongri2016");
		serverIP = "162";
	}
	public static void s112() {
		sftp = sf.connect("121.43.99.112", 22, "root", "DRny2015");
		serverIP = "112";
	}
	public static void s236() {
		sftp = sf.connect("121.40.224.236", 22, "root", "DRny2015");
		serverIP = "236";
	}
	public static void s187() {
		sftp = sf.connect("121.40.150.187", 22, "root", "Idongri2015");
		serverIP = "187";
	}
	public static void renewF(){
		sf = new SFTP_T01();
	}

}
