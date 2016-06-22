package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import com.book.jdk18.Func;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.linux.ftp.SFTP_T01;

public class T5_上传文件到服务器 {

	static SFTP_T01 sf = new SFTP_T01();

	static ChannelSftp sftp = null;

	static String fromPath = "G:\\svn\\idongriV3";

	// static String toPath = "/usr/local/idongri/webapps/idongri/WEB-INF/classes/";
	static String toPath = "/root/";

	static String fileListText = "G:\\workspace_luna\\Mix_Project\\src\\main\\resources\\class_list.properties";

	static String moveToDir = "d:\\target";

	public static void main(String[] args) throws Exception {
		dealFile((s) -> doMove(s));
		// dealFile((s) -> doUpload(s));
	}
	public static void dealFile(Func<String, String> func) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileListText)));
		String fileName = null;
		while ((fileName = reader.readLine()) != null) {
			findFile(fileName, func);
		}
		reader.close();
	}
	public static void upload() throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileListText)));
		String fileName = null;
		while ((fileName = reader.readLine()) != null) {
			findFile(fileName, (s) -> doUpload(s));
		}
		reader.close();
	}
	public static void move() throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileListText)));
		String fileName = null;
		while ((fileName = reader.readLine()) != null) {
			findFile(fileName, (s) -> doMove(s));
		}
		reader.close();
	}
	public static void findFile(String fileName, Func<String, String> func) throws Exception {
		File dir = new File(fromPath);
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {
				File target = new File(f.getAbsolutePath() + "\\target\\classes");
				// System.out.println(target.getAbsolutePath()+"  target-"+fileName);
				// System.out.println(target.exists());
				if (target.exists()) {
					findFile(target, fileName, func);
				}
			}
		}
	}
	public static void findFile(File dir, String fileName, Func<String, String> func) throws Exception {
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {
				findFile(f, fileName, func);
			} else if (fileName.equals(f.getName())) {
				System.out.println(f.getAbsolutePath());
				if (func != null) {
					func.convert(f.getAbsolutePath());
				}
				return;
			}
		}
	}
	public static String doUpload(String uploadFile) {
		try {
			String tmpFile = uploadFile.substring(uploadFile.indexOf("classes") + 8);
			String tmpDir = tmpFile.substring(0, tmpFile.lastIndexOf("\\")).replaceAll("\\\\", "/");
			_uploadToLinux(toPath + tmpDir, uploadFile);
			System.out.println("finished");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public static String doMove(String uploadFile) {
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			String to = moveToDir + "\\" + uploadFile.substring(uploadFile.lastIndexOf("\\") + 1);
//			System.out.println("from:"+uploadFile+" to:"+to);
//			Files.copy(Paths.get(uploadFile), new FileOutputStream(to));
			 in = new FileInputStream(uploadFile);
			 out = new FileOutputStream(to);
			byte[] b = new byte[8096];
			int len = 0;
			while ((len = in.read(b))>0) {
	            out.write(b,0,len);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
	            in.close();
	            out.close();
            } catch (IOException e) {
	            e.printStackTrace();
            }
		}
		return "";
	}
	private static void _uploadToLinux(String dir, String file) throws JSchException {
		System.out.println("dir:" + dir);
		System.out.println("file:" + file);
		sf.upload(dir, file, sftp);
		sf = null;
		sftp.getSession().disconnect();
		sftp.disconnect();
		sftp.exit();
		System.out.println("over");
	}

	static {
		sftp = sf.connect("120.55.88.162", 22, "root", "Idongri2016");
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

}
