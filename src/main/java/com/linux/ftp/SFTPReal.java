package com.linux.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.oro.text.regex.MalformedPatternException;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.linux.ftp.Shell.localUserInfo;

import expect4j.Closure;
import expect4j.Expect4j;
import expect4j.ExpectState;
import expect4j.matches.EofMatch;
import expect4j.matches.Match;
import expect4j.matches.RegExpMatch;
import expect4j.matches.TimeoutMatch;


public class SFTPReal {
	private static Logger log = Logger.getLogger(SFTPReal.class);
	private Session session;
	private Expect4j expect = null;
	private ChannelShell channel;
	private StringBuffer buffer = new StringBuffer();
	private static final long defaultTimeOut = 1000;
	public static final int COMMAND_EXECUTION_SUCCESS_OPCODE = -2;
	public static String[] errorMsg = new String[] { "could not acquire the config lock " };
	// 正则匹配，用于处理服务器返回的结果
	public static String[] linuxPromptRegEx = new String[] { "~]#", "~#", "#", ":~#", "/$", ">" };

	public static void main(String[] args) {
		SFTPReal sf = new SFTPReal();
		String host = "192.168.99.166";
		int port = 22;
		String username = "idongri";
		String password = "idongri";
		String directory = "/home/idongri/";
//		String uploadFile = "e:\\tmp\\a.txt";
		String downloadFile = "mysql-bin.log";
		String saveFile = "e:\\tmp\\download.txt";
//		String deleteFile = "delete.txt";
		ChannelSftp sftp = sf.connect(host, port, username, password);
		// sf.upload(directory, uploadFile, sftp);
		sf.download(directory, downloadFile, saveFile, sftp);
		// sf.delete(directory, deleteFile, sftp);
		try {
			// sftp.cd(directory);
			// sftp.mkdir("ss");
			System.out.println("finished");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 连接sftp服务器
	 * 
	 * @param host主机
	 * @param port端口
	 * @param username用户名
	 * @param password密码
	 * @return
	 */
	public ChannelSftp connect(String host, int port, String username, String password) {
		ChannelSftp sftp = null;
		try {
			JSch jsch = new JSch();
			jsch.getSession(username, host, port);
			Session sshSession = jsch.getSession(username, host, port);
			session = sshSession;
			System.out.println("Session created.");
			sshSession.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			System.out.println("Session connected.");
			System.out.println("Opening Channel.");
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			System.out.println("Connected to " + host + ".");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sftp;
	}

	// 获得Expect4j对象，该对用可以往SSH发送命令请求
	private Expect4j getExpect() {
		try {
			channel = (ChannelShell) session.openChannel("shell");
			Expect4j expect = new Expect4j(channel.getInputStream(), channel.getOutputStream());
			channel.connect();
			log.info(String.format("Logging shell successfully!"));
			return expect;
		} catch (Exception ex) {
			log.error("failed,please check your username and password!");
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 上传文件
	 * 
	 * @param directory上传的目录
	 * @param uploadFile要上传的文件
	 * @param sftp
	 */
	public void upload(String directory, String uploadFile, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			File file = new File(uploadFile);
			sftp.put(new FileInputStream(file), file.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param directory下载目录
	 * @param downloadFile下载的文件
	 * @param saveFile存在本地的路径
	 * @param sftp
	 */
	public void download(String directory, String downloadFile, String saveFile, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			File file = new File(saveFile);
			sftp.get(downloadFile, new FileOutputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param directory
	 *            要删除文件所在目录
	 * @param deleteFile
	 *            要删除的文件
	 * @param sftp
	 */
	public void delete(String directory, String deleteFile, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			sftp.rm(deleteFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 列出目录下的文件
	 * 
	 * @param directory
	 *            要列出的目录
	 * @param sftp
	 * @return
	 * @throws SftpException
	 */
	public Vector listFiles(String directory, ChannelSftp sftp) throws SftpException {
		return sftp.ls(directory);
	}

	/**
	 * 执行配置命令
	 * 
	 * @param commands
	 *            要执行的命令，为字符数组
	 * @return 执行是否成功
	 */
	public void executeCommand(String command) {
		if (expect == null) {
			expect = getExpect();
		}
		log.debug("----------Running commands are listed as follows:----------");
		log.debug(command);
		try {
			expect.send(command);
			expect.send("\r");
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.debug("----------End----------");
	}
}