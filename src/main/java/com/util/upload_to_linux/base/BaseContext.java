package com.util.upload_to_linux.base;

import java.util.Vector;

import com.book.jdk18.Process;
import com.jcraft.jsch.ChannelSftp;
import com.linux.ftp.SSHConnector;

public abstract class BaseContext extends BaseConstants{
	

	public static void init() {
		linux_webapp_Path = linux_idongri_Path + "webapps/" + app + "/";
		linux_class_Path = linux_idongri_Path + "webapps/" + app + "/WEB-INF/classes/";
		linux_lib_Path = linux_idongri_Path + "webapps/" + app + "/WEB-INF/lib/";
		if(app.equals("platform")){
			linux_lib_Path = linux_idongri_Path + "webapps/idongri/WEB-INF/lib/";
			linux_webapp_Path = linux_idongri_Path + "webapps/idongri/";
			linux_class_Path = linux_idongri_Path + "webapps/idongri/WEB-INF/classes/";
		}
		local_app_war_path = svn_path  + app + "\\target\\";
		local_app_webapp_Path =  svn_path  + app + "\\src\\main\\webapp\\";
		local_webapp_Path =  svn_path + app +"\\src\\main\\webapp\\";
		
		classListFilePath = current_path + "class_list.properties";
		htmlListFilePath = current_path + "html_list.properties";
		uploadHtmlFilePath = System.getProperty("user.dir") + "\\src\\main\\resources\\upload_html_list.txt";
	}
	public static void init(String s,String a,String t) {
		server = s;
		app = a;
		findType = t;
		init();
	}
	public static void init(String s,String a) {
		server = s;
		app = a;
		init();
	}
	

	public final Vector<Thread> threadList = new Vector<Thread>();

	private SSHConnector connector = new SSHConnector();

	public void executeCommand(String host, String command) {
		connector.executeCommand(host, command);
	}

	void _uploadToLinux(String dir, String file, ChannelSftp ftpChannel) throws Exception {
		System.out.println("【dir】:" + dir + "\n【file】:" + file);
		connector.upload(dir, file, ftpChannel);
	}


	void _downloadFromLinux(String dir, String file, String to, ChannelSftp ftpChannel) throws Exception {
		System.out.println("【from】:" + dir + file + "\n【to】:" + to);
		connector.download(dir, file, to, ftpChannel);
	}

	public void loopServer(Process<ChannelSftp> callback) {
		if (app.equals(admin)) {
			if (server.equals(net)) {
				callback.process(s71());
			} else {
				callback.process(s115());
			}
		} else if(app.equals(wx)){
			callback.process(s128());
		}else {
			if (server.equals(net)) {
				callback.process(s187());
			} else {
				threadCall(callback, s162(), isThread);
				threadCall(callback, s112(), isThread);
				threadCall(callback, s236(), isThread);
				try {
					Thread.sleep(10);
					for (Thread t : threadList) {
						t.join();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private void threadCall(Process<ChannelSftp> callback, ChannelSftp ftpChannel, boolean isThread) {
		if (isThread) {
			Thread t = new Thread(() -> callback.process(ftpChannel));
			t.start();
			threadList.add(t);
		} else
			callback.process(ftpChannel);
	}

	public ChannelSftp s112() {
		if (connector.getSftpChannel(host112) == null)
			connector.connect(host112, 22, "root", "DRny2015");
		return connector.getSftpChannel(host112);
	}

	public ChannelSftp s115() {
		if (connector.getSftpChannel(host115) == null)
			connector.connect(host115, 22, "root", "Idongri2016");
		return connector.getSftpChannel(host115);
	}

	public ChannelSftp s236() {
		if (connector.getSftpChannel(host236) == null)
			connector.connect(host236, 22, "root", "DRny2015");
		return connector.getSftpChannel(host236);
	}

	public ChannelSftp s162() {
		if (connector.getSftpChannel(host162) == null)
			connector.connect(host162, 22, "root", "Idongri2016");
		return connector.getSftpChannel(host162);
	}

	public ChannelSftp s187() {
		if (connector.getSftpChannel(host187) == null)
			connector.connect(host187, 22, "root", "Idongri2015");
		return connector.getSftpChannel(host187);
	}

	public ChannelSftp s71() {
		if (connector.getSftpChannel(host71) == null)
			connector.connect(host71, 22, "root", "Drny2015");
		return connector.getSftpChannel(host71);
	}
	public ChannelSftp s128() {
		if (connector.getSftpChannel(host128) == null)
			connector.connect(host128, 22, "root", "Idongri2016");
		return connector.getSftpChannel(host128);
	}
	public void closeSession() {
		for (String server : connector.getSessionMap().keySet()) {
			connector.getSession(server).disconnect();
		}
	}

}
