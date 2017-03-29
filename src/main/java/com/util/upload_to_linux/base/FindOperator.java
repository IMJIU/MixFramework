package com.util.upload_to_linux.base;

import java.io.*;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jdk.jdk8.Func;
import com.jdk.jdk8.Process;
import com.linux.ftp.SSHConnector;

public class FindOperator extends BaseContext {
	/**
	 * 迭代处理文件
	 * 
	 * @param fileName
	 * @param func
	 * @throws Exception
	 */
	public void localProcessFile(File dir, Process<File> func) throws Exception {
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {
				localProcessFile(f, func);
			} else {
				func.process(f);
			}
		}
	}

	/**
	 * 处理文件入口-实调用搜索，回调处理
	 * 
	 * @param func
	 * @throws Exception
	 */
	public static void processFile(Process<String> func) throws Exception {
		String filepath = null;
		if (findType.equals("html")) {
			filepath = htmlListFilePath;
		} else {
			filepath = classListFilePath;
		}
		System.out.println(filepath);
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filepath)));
		String fileName = null;
		while ((fileName = reader.readLine()) != null) {
			System.out.println("finding:" + fileName);
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
	private static void findFile(String fileName, Process<String> func) throws Exception {
		File dir = new File(svn_path);
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {
				File target = null;
				if (findType.equals("class"))
					target = new File(f.getAbsolutePath() + "\\target\\classes");
				else
					target = new File(f.getAbsolutePath() + "\\src\\main\\webapp\\");
				if (target.exists()) {
					if (fileName.endsWith("xml")) {
						if (f.getName().equals("platform")) {
							findFile(target, fileName, func);
						}
					} else if(BaseContext.app.equals("platform")){
						if(!f.getName().equals("admin"))
							findFile(target, fileName, func);
					}else {
						findFile(target, fileName, func);
					}
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
	private static void findFile(File dir, String fileName, Process<String> func) throws Exception {
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
