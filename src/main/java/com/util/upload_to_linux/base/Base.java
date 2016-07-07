package com.util.upload_to_linux.base;

import java.util.HashSet;

import com.book.jdk18.Process;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.linux.ftp.SSHConnector;

public class Base {

	public static String server = ".cn";
	public static String findType = "html";// class | html

	public static boolean isThread = false;

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

	public static final String host162 = "120.55.88.162";
	public static final String host112 = "121.43.99.112";
	public static final String host236 = "121.40.224.236";
	public static final String host187 = "121.40.150.187";

	private static SSHConnector connector = new SSHConnector();
	static ChannelSftp channel;

	public static void executeCommand(String host, String command) {
		connector.executeCommand(host, command);
	}

	static void _uploadToLinux(String dir, String file, ChannelSftp ftpChannel) throws Exception {
		System.out.print("【dir】:" + dir + "\n【file】:" + file);
		connector.upload(dir, file, ftpChannel);
		System.out.println("->upload over");
	}

	static HashSet<String> set = new HashSet<String>();

	static void _downloadFromLinux(String dir, String file, String to, ChannelSftp ftpChannel) throws Exception {
		System.out.print("【from】:" + dir + file + "\n【to】:" + to);
		connector.download(dir, file, to, ftpChannel);
		System.out.println("->download over");
	}

	public static void loopServer(Process<ChannelSftp> callback) {
		if (server.equals(".net")) {
			callback.process(s187());
		} else {
			threadCall(callback, s162(), isThread);
			threadCall(callback, s112(), isThread);
			threadCall(callback, s236(), isThread);
		}
	}

	private static void threadCall(Process<ChannelSftp> callback, ChannelSftp ftpChannel, boolean isThread) {
		if (isThread)
			new Thread(() -> callback.process(ftpChannel)).start();
		else
			callback.process(ftpChannel);
	}

	public static ChannelSftp s112() {
		if (connector.getSftpChannel(host112) == null)
			connector.connect(host112, 22, "root", "DRny2015");
		return connector.getSftpChannel(host112);
	}

	public static ChannelSftp s236() {
		if (connector.getSftpChannel(host236) == null)
			connector.connect(host236, 22, "root", "DRny2015");
		return connector.getSftpChannel(host236);
	}

	public static ChannelSftp s162() {
		if (connector.getSftpChannel(host162) == null)
			connector.connect(host162, 22, "root", "Idongri2016");
		return connector.getSftpChannel(host162);
	}

	public static ChannelSftp s187() {
		if (connector.getSftpChannel(host187) == null)
			connector.connect(host187, 22, "root", "Idongri2015");
		return connector.getSftpChannel(host187);
	}

	public static void closeSession() {
		for (String server : connector.getSessionMap().keySet()) {
			connector.getSession(server).disconnect();
		}
	}

}
