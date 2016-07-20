package com.util.upload_to_linux.base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

import com.jdk.jar.ZipFileUtil;

public class UploadOperate extends FindOperator {
	/**
	 * 读取文件中需要上传的文件 上传到linux
	 * 
	 * @param classFilePath
	 */
	public void doUploadClass(String classFilePath) {
		loopServer((channelSftp) -> {
			try {
				String tmpFile = classFilePath.substring(classFilePath.indexOf("classes") + 8);
				String tmpDir = tmpFile.substring(0, tmpFile.lastIndexOf("\\")).replaceAll("\\\\", "/");
				_uploadToLinux(linux_class_Path + tmpDir, classFilePath, channelSftp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * 上传war
	 * 
	 * @param dir
	 * @param file
	 */
	public void uploadWarToLinux(String dir, String file) {
		// System.out.println(file);
		try {
			String war = Files.list(Paths.get(file)).filter(f -> f.getFileName().toString().endsWith("war")).findFirst().get().toString();
			if (war != null) {
				String rename = war.replaceAll("-test\\.war", "\\.war").replaceAll("-prod\\.war", "\\.war");
				new File(war).renameTo(new File(rename));
				uploadToLinux(dir, rename);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void _mkDir(String dir) {
		loopServer((channelSftp) -> {
			try {
				String host = channelSftp.getSession().getHost();
				if (!set.contains(host + dir)) {
					System.out.print("mkdir " + dir);
					executeCommand(host, "mkdir " + dir);
					System.out.println("->mkdir over");
					set.add(host + dir);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}

	public void uploadDirToLinux(String baseDir, File file) {
		uploadDirToLinux(baseDir, file, null);
	}

	/**
	 * 上传war
	 * 
	 * @param dir
	 * @param file
	 */
	public void uploadDirToLinux(String baseDir, File file, String regex) {
		String name1 = baseDir + file.getAbsolutePath().replace(BaseContext.local_platform_webapp_Path, "").replaceAll("\\\\", "/");
		String path1 = name1.substring(0, name1.lastIndexOf("/"));
		if (file.isDirectory()) {
			_mkDir(path1);
			for (File f : file.listFiles()) {
				String name = baseDir + f.getAbsolutePath().replace(BaseContext.local_platform_webapp_Path, "").replaceAll("\\\\", "/");
				String path = name.substring(0, name.lastIndexOf("/"));
				_mkDir(path);
				if (f.isDirectory()) {
					uploadDirToLinux(baseDir, f, regex);
				} else if (regex != null && f.getName().matches(regex)) {
					uploadToLinux(path, f.getAbsolutePath());
				}
			}
		} else {
			uploadToLinux(path1, file.getAbsolutePath());
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param dir
	 * @param file
	 */
	public void uploadToLinux(String dir, String file) {
		loopServer((channelSftp) -> {
			try {
				// System.out.println(file+"-"+channelSftp.getSession().getHost());
				_uploadToLinux(dir, file, channelSftp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}

	/**
	 * 读取文件中需要上传的文件 上传到linux
	 */
	public void doUploadHtmlFileListToLinux() {
		loopServer((channelSftp) -> {
			try {
				String path = null;
				for (String filePath : Files.readAllLines(Paths.get(uploadHtmlFilePath))) {
					path = filePath.substring(filePath.indexOf("webapp") + 6).replaceAll("\\\\", "/");
					path = path.substring(1, path.lastIndexOf("/"));
					System.out.println(linux_webapp_Path + "00000" + path);
					_uploadToLinux(linux_webapp_Path + path, filePath, channelSftp);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private static FileOutputStream uploadHtmlListOutput = null;

	/**
	 * 搜索需要上传的html
	 * 
	 * @param file
	 * @return
	 */
	public static void findUploadHtmlWriteToFile(String file) {
		try {
			// System.out.println(uploadHtmlFilePath);
			System.out.println("i get!" + file);
			try {
				if (uploadHtmlListOutput == null)
					uploadHtmlListOutput = new FileOutputStream(uploadHtmlFilePath);
				uploadHtmlListOutput.write((file + "\n").getBytes("utf-8"));
				uploadHtmlListOutput.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void uploadJarToLinux(String fromJar, String toJar) throws Exception {
		ZipFileUtil.decompressJarToJar(fromJar, toJar, (jOutputStream) -> {
			try {
				FindOperator.processFile((s) -> {
					try {
						System.out.println(s);
						System.out.println(s.substring(s.indexOf("com\\idongri")));
						jOutputStream.putNextEntry(new ZipEntry(s.substring(s.indexOf("com\\idongri"))));
						byte[] bytes = Files.readAllBytes(Paths.get(s));
						jOutputStream.write(bytes, 0, bytes.length);
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		uploadToLinux(linux_lib_Path, toJar);
//		writeJar2("d://target//121.40.150.187-common-service-3.0-SNAPSHOT.jar","d://target//common-service-3.0-SNAPSHOT.jar");
//		writeJar("D:\\target\\PlanFlowServiceImpl.class","d://target//common-service-3.0-SNAPSHOT.jar");
	}

	public static void writeJar(String addclassFile, String path) throws Exception {
		// 定义一个jaroutputstream流
		JarOutputStream stream = new JarOutputStream(new FileOutputStream(path));
		// jar中的每一个文件夹 每一个文件 都是一个jarEntry
		// 如下表示在jar文件中创建一个文件夹bang bang下创建一个文件jj.txt
		JarEntry entry = new JarEntry("cc/aa.txt");
		// 表示将该entry写入jar文件中 也就是创建该文件夹和文件
		stream.putNextEntry(entry);
		// 然后就是往entry中的jj.txt文件中写入内容
		stream.write(Files.readAllBytes(Paths.get(addclassFile)));
		// 最后不能忘记关闭流
		stream.close();
	}

	public static void writeJar2(String path, String path2) throws Exception {
		// 定义一个jaroutputstream流
		JarOutputStream stream = new JarOutputStream(new FileOutputStream(path2));
		JarFile file = new JarFile(path);
		
		// 这里entry的集合中既包括文件夹 又包括文件 所以需要到下面做判断 如e.isDirectory()
		Enumeration<JarEntry> entry = file.entries();
		while (entry.hasMoreElements()) {
			JarEntry e = entry.nextElement();
			System.out.println(e.getName());
			stream.putNextEntry(e);
		}
		// jar中的每一个文件夹 每一个文件 都是一个jarEntry
		// 如下表示在jar文件中创建一个文件夹bang bang下创建一个文件jj.txt
		JarEntry e = new JarEntry("cc/aa.txt");
		// 表示将该entry写入jar文件中 也就是创建该文件夹和文件
		stream.putNextEntry(e);
		// 然后就是往entry中的jj.txt文件中写入内容
		stream.write(Files.readAllBytes(Paths.get("D:\\target\\PlanFlowServiceImpl.class")));
		// 最后不能忘记关闭流
		stream.close();
	}

	// 读取jar包中的所有文件
	public static void readAll() throws Exception {
		String path = "E://tomcat//webapps//bdlp//WEB-INF//lib//ant.jar";
		JarFile file = new JarFile(path);
		// 这里entry的集合中既包括文件夹 又包括文件 所以需要到下面做判断 如e.isDirectory()
		Enumeration<JarEntry> entry = file.entries();

		while (entry.hasMoreElements()) {
			JarEntry e = entry.nextElement();
			if (!e.isDirectory() && !e.getName().endsWith(".class") && !e.getName().endsWith(".gif")) {
				InputStream stream = file.getInputStream(e);
				byte[] bb = new byte[stream.available()];
				stream.read(bb);
				System.out.println(new String(bb));
			}
		}
	}

}
