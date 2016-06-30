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

	// static String toPath = "/usr/local/idongri/webapps/idongri/WEB-INF/classes/";
	static String toPath = "/root/";
	static String toHtmlPath = "/usr/local/idongri/webapps/idongri";

	static String classListText = "D:\\git\\MixTest\\src\\main\\resources\\class_list.properties";
	static String htmlListText ="D:\\git\\MixTest\\src\\main\\resources\\html_list.properties";
	static String uploadHtmlFilePath = "D:\\git\\MixTest\\src\\main\\resources\\upload_html_list.txt";

	static String moveToDir = "d:\\target";
	
	
	static FileOutputStream uploadHtmlListOutput = null;

	static void _uploadToLinux(String dir, String file) throws JSchException {
		System.out.println("dir:" + dir);
		System.out.println("file:" + file);
		sf.upload(dir, file, sftp);
		sf = null;
		sftp.getSession().disconnect();
		sftp.disconnect();
		sftp.exit();
		System.out.println("over");
	}
	
	public static void loopServer(Process callback){
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
	}
	public static void s112() {
		sftp = sf.connect("121.43.99.112", 22, "root", "DRny2015");
	}
	public static void s236() {
		sftp = sf.connect("121.40.224.236", 22, "root", "DRny2015");
	}
	public static void s187() {
		sftp = sf.connect("121.40.150.187", 22, "root", "Idongri2015");
	}

}
