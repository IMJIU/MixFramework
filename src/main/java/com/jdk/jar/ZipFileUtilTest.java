package com.jdk.jar;

import java.io.File;

import org.junit.Test;


/**
 * 测试ZipFileUtil的压缩和解压缩方法
 * 
 * @author Luxh
 */
public class ZipFileUtilTest {

	public static String path1 = "d://target//121.40.150.187-common-service-3.0-SNAPSHOT.jar";
	public static String path2 = "d://target//common-service-3.0-SNAPSHOT2.jar";
	@Test
	public void testCompressFiles2Zip() {
		// 存放待压缩文件的目录
		File srcFile = new File("D:/target");
		// 压缩后的zip文件路径
		String zipFilePath = "d:/test2/test.zip";
		if (srcFile.exists()) {
			File[] files = srcFile.listFiles();
			ZipFileUtil.compressFiles2Zip(files, zipFilePath);
		}
	}

	@Test
	public void testDecompressZip() {
		// 压缩包所在路径
		String zipFilePath = path1;
		// 解压后的文件存放目录
		String saveFileDir = "d:/target/unzip/";
		// 调用解压方法
		ZipFileUtil.decompressZip(zipFilePath, saveFileDir);
	}
}