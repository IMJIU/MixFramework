package com.linux.ftp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * SSH工具类
 * 
 * @author 赵聪慧 2013-4-7
 */
public class SFTP_T00 {

	/**
	 * 远程 执行命令并返回结果调用过程 是同步的（执行完才会返回）
	 * 
	 * @param host主机名
	 * @param user用户名
	 * @param psw密码
	 * @param port端口
	 * @param command命令
	 * @return
	 */
	public static String exec(String host, String user, String psw, int port, String command) {
		String result = "";
		Session session = null;
		ChannelExec openChannel = null;
		try {
			JSch jsch = new JSch();
			session = jsch.getSession(user, host, port);
			
			Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.setPassword(psw);
			session.connect();
			
			openChannel = (ChannelExec) session.openChannel("exec");
			openChannel.setCommand(command);
			
			int exitStatus = openChannel.getExitStatus();
			System.out.println(exitStatus);
			
			openChannel.connect();
			
			InputStream in = openChannel.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String buf = null;
			while ((buf = reader.readLine()) != null) {
				result += new String(buf.getBytes("gbk"), "UTF-8") + "    <br>\r\n";
			}
		} catch (JSchException | IOException e) {
			result += e.getMessage();
		} finally {
			if (openChannel != null && !openChannel.isClosed()) {
				openChannel.disconnect();
			}
			if (session != null && session.isConnected()) {
				session.disconnect();
			}
		}
		return result;
	}

	public static void main(String args[]) {
		String exec = exec("192.168.99.166", "idongri", "idongri", 22, "ls;pwd;");
		System.out.println(exec);
	}
}