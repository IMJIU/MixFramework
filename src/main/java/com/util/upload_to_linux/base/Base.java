package com.util.upload_to_linux.base;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.book.jdk18.Process;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.linux.ftp.SFTPReal;

public class Base {

	public static String server = ".net";
	public static String findType = "html";// class | html

	public final static String svn_path = "d:\\svn_code\\idongriV3\\";
	public final static String moveToDir = "d:\\target\\";

	public final static String linux_idongri_Path = "/usr/local/idongri/";
	public final static String linux_webapp_Path = linux_idongri_Path + "webapps/idongri/";
	public final static String linux_class_Path = linux_idongri_Path + "webapps/idongri/WEB-INF/classes/";
	public final static String linux_lib_Path = linux_idongri_Path + "webapps/idongri/WEB-INF/lib/";

	public final static String current_path = Class.class.getClass().getResource("/").getPath();
	public final static String classListFilePath = current_path + "class_list.properties";
	public final static String htmlListFilePath = current_path + "html_list.properties";
	// public final static String uploadHtmlFilePath = current_path +
	// "upload_html_list.txt";
	public final static String uploadHtmlFilePath = "D:\\git\\bak3\\MixTest\\src\\main\\resources\\upload_html_list.txt";

	public final static String local_platform_webapp_Path = "D:\\svn_code\\idongriV3\\platform\\src\\main\\webapp\\";
	public final static String local_platform_war_path = svn_path + "platform\\target\\";
	public final static String local_admin_war_path = svn_path + "admin\\target\\";

	public final static String dao_jar = "common-dao-3.0-SNAPSHOT.jar";
	public final static String service_jar = "common-service-3.0-SNAPSHOT.jar";
	public final static String lib_jar = "common-lib-3.0-SNAPSHOT.jar";
	public final static String core_jar = "common-core-3.0-SNAPSHOT.jar";
	public final static String util_jar = "common-util-3.0-SNAPSHOT.jar";

	static SFTPReal sftpReal = new SFTPReal();
	static ChannelSftp channel;
	static Map<Integer,ChannelSftp> channelMap = new HashMap<>();

	static void _uploadToLinux(String dir, String file, ChannelSftp session) throws Exception {
		System.out.print("【dir】:" + dir + "\n【file】:" + file);
		sftpReal.upload(dir, file, session);
//		session.getSession().disconnect();
//		session.disconnect();
//		session.exit();
		System.out.println("->upload over");
	}

	static HashSet<String> set = new HashSet<String>();

	static void _mkDir(String dir) {
		loopServer((session) -> {
			try {
				if (!set.contains(dir)) {
					System.out.print("mkdir " + dir);
					sftpReal.executeCommand("mkdir " + dir);
					System.out.println("->mk finished");
					set.add(dir);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}

	static void _downloadFromLinux(String dir, String file, String to, ChannelSftp session) throws Exception {
		System.out.print("【from】:" + dir + file + "\n【to】:" + to);
		sftpReal.download(dir, file, to, session);
		session.getSession().disconnect();
//		session.disconnect();
//		session.exit();
		System.out.println("->download over");
	}

	public static void loopServer(Process<ChannelSftp> callback) {
		if (server.equals(".net")) {
			callback.process(s187());
		} else {
			threadCall(callback, s162());
			threadCall(callback, s112());
			threadCall(callback, s236());
		}
	}

	private static void threadCall(Process<ChannelSftp> callback, ChannelSftp session) {
		new Thread(() -> callback.process(session)).start();
	}

	public static ChannelSftp s162() {
		if (channelMap.get(162) == null)
			channelMap.put(162,sftpReal.connect("120.55.88.162", 22, "root", "Idongri2016"));
		return channelMap.get(162);
	}

	public static ChannelSftp s112() {
		if (channelMap.get(112) == null)
			channelMap.put(112,sftpReal.connect("121.43.99.112", 22, "root", "DRny2015"));
		return channelMap.get(112);
	}

	public static ChannelSftp s236() {
		if (channelMap.get(236) == null)
			channelMap.put(236,sftpReal.connect("121.40.224.236", 22, "root", "DRny2015"));
		return channelMap.get(236);
	}

	public static ChannelSftp s187() {
		if (channelMap.get(187) == null)
			channelMap.put(187,sftpReal.connect("121.40.150.187", 22, "root", "Idongri2015"));
		return channelMap.get(187);
	}
	
	public static void closeChannel(){
		for (Integer server : channelMap.keySet()) {
			ChannelSftp ch = channelMap.get(server);
			try {
				ch.getSession().disconnect();
			} catch (JSchException e) {
				e.printStackTrace();
			}
			ch.disconnect();
			ch.exit();
		}
	}

}
