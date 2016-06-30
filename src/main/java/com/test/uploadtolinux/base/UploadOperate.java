package com.test.uploadtolinux.base;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UploadOperate extends FindOperator {
	/**
	 * 读取文件中需要上传的文件  上传到linux
	 * @param classFilePath
	 */
	public static void doUploadClass(String classFilePath) {
		loopServer((t) -> {
			try {
				String tmpFile = classFilePath.substring(classFilePath.indexOf("classes") + 8);
				String tmpDir = tmpFile.substring(0, tmpFile.lastIndexOf("\\")).replaceAll("\\\\", "/");
				_uploadToLinux(linux_class_Path + tmpDir, classFilePath);
				System.out.println("finished");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		System.out.println("finished");
	}
	public static void uploadToLinux(String dir,String file) {
		loopServer((t) -> {
			try {
				_uploadToLinux(dir,file);
				System.out.println("finished");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		System.out.println("finished");
	}
	/**
	 * 读取文件中需要上传的文件  上传到linux
	 */
	public static void doUploadHtmlFileListToLinux() {
		loopServer((t) -> {
			try {
				String path = null;
				for (String filePath : Files.readAllLines(Paths.get(uploadHtmlFilePath))) {
					path = filePath.substring(filePath.indexOf("webapp") + 6).replaceAll("\\\\", "/");
					path = path.substring(0, path.lastIndexOf("/"));
					System.out.println(toHtmlPath + path);
					_uploadToLinux(toHtmlPath + path, filePath);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		System.out.println("finished");
	}
	
	/**
	 * 搜索需要上传的html
	 * @param file
	 * @return
	 */
	public static void findUploadHtmlWriteToFile(String file) {
		try {
//			System.out.println("i get!"+file);
			try {
				if(uploadHtmlListOutput == null)
					uploadHtmlListOutput = new FileOutputStream(uploadHtmlFilePath);
				uploadHtmlListOutput.write((file+"\n").getBytes("utf-8"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
