package com.test.uploadtolinux.base;

import java.io.*;

import com.book.jdk18.Func;
import com.book.jdk18.Process;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.linux.ftp.SFTP_T01;

public class FindOperator extends Base {
	/**
	 * 处理文件入口-实调用搜索，回调处理
	 * 
	 * @param func
	 * @throws Exception
	 */
	public void processFile(Process<String> func) throws Exception {
		String filepath = null;
		if (findType.equals("html")) {
			filepath = htmlListText;
		} else {
			filepath = classListText;
		}
		System.out.println(filepath);
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filepath)));
		String fileName = null;
		while ((fileName = reader.readLine()) != null) {
			findFile(fileName, func);
		}
		reader.close();
	}

	/**
	 * 搜索文件父
	 * 
	 * @param fileName
	 * @param func
	 * @throws Exception
	 */
	private void findFile(String fileName, Process<String> func) throws Exception {
		File dir = new File(svn_path);
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {
				File target = null;
				if (findType.equals("class"))
					target = new File(f.getAbsolutePath() + "\\target\\classes");
				else
					target = new File(f.getAbsolutePath() + "\\src\\main\\webapp\\");
				if (target.exists()) {
					findFile(target, fileName, func);
				}
			}
		}
	}

	/**
	 * 搜索文件子循环
	 * 
	 * @param fileName
	 * @param func
	 * @throws Exception
	 */
	private void findFile(File dir, String fileName, Process<String> func) throws Exception {
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {
				findFile(f, fileName, func);
			} else if (fileName.equals(f.getName())) {
				System.out.println(f.getAbsolutePath());
				if (func != null) {
					func.process(f.getAbsolutePath());
				}
				return;
			}
		}
	}

}
