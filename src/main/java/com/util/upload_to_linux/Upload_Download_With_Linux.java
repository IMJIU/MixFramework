package com.util.upload_to_linux;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.Bean;

import com.jdk.jar.JarUtils;
import com.jdk.jar.ZipFileUtil;
import com.util.upload_to_linux.base.*;

import scala.util.regexp.Base;

public class Upload_Download_With_Linux {

	static {

	}

	UploadOperate upload;
	DownloadOperator download;

	@Before
	public void init() {
		// 配置
		BaseContext.server = ".net"; /** .net|.cn */
		BaseContext.app = "idongri"; /** admin|idongri */
		BaseContext.findType = "html"; /** class | html */
		BaseContext.isThread = true;

		BaseContext.svn_path = "D:\\svn_code\\idongriV3\\";// D:\\svn_code\\idongriV3
															// G:\\svn\\idongriV3\\
		BaseContext.moveToDir = "d:\\target\\";
		BaseContext.init();
		
		upload = new UploadOperate();
		download = new DownloadOperator();
	}

	@After
	public void close() {
		upload.closeSession();
	}

	/** 【移动文件 】 */
	@Test
	public void t1_move() throws Exception {
		FindOperator.processFile((s) -> MoveOperate.doMoveClass(s));
	}

	/** 【上传class】 */
	@Test
	public void t2_upload_class() throws Exception {
		FindOperator.processFile((s) -> upload.doUploadClass(s));
	}

	/** 【扫描需要上传的html】 */
	@Test
	public void t3_scan_html() throws Exception {
		BaseContext.findType = "html"; /** class | html */
		FindOperator.processFile((s) -> UploadOperate.findUploadHtmlWriteToFile(s));
	}

	/** 【上传html文件】 */
	@Test
	public void t4_upload_html() throws Exception {
		BaseContext.findType = "html"; /** class | html */
		upload.doUploadHtmlFileListToLinux();
	}

	/** 【下载jar】 */
	@Test
	public void t5_download() throws Exception {
		// DownloadOperator.download(BaseContext.linux_lib_Path,
		// BaseContext.dao_jar, BaseContext.moveToDir);
		download.download(BaseContext.linux_lib_Path, BaseContext.service_jar, BaseContext.moveToDir);
	}

	/** 【上传jar】 */
	@Test
	public void t6_upload() throws Exception {
		// upload.uploadToLinux(BaseContext.linux_lib_Path,
		// BaseContext.moveToDir + BaseContext.dao_jar);
		upload.uploadToLinux(BaseContext.linux_lib_Path, BaseContext.moveToDir + BaseContext.service_jar);
		// upload.uploadWarToLinux(BaseContext.linux_idongri_Path,
		// BaseContext.local_admin_war_path);
		// upload.uploadWarToLinux(BaseContext.linux_idongri_Path,
		// BaseContext.local_platform_war_path);

	}

	/** 【上传jar】 */
	@Test
	public void t7_upload_jar() throws Exception {
		upload.uploadJarToLinux(BaseContext.moveToDir + BaseContext.host236 + "-" + BaseContext.service_jar, BaseContext.moveToDir + BaseContext.service_jar);
	}

	/** 【上传文件夹】 */
	@Test
	public void t8_upload_dir() throws Exception {
		// UploadOperate.uploadDirToLinux(BaseContext.linux_webapp_Path, new
		// File(BaseContext.local_platform_webapp_Path + "html/card"));
		// upload.uploadDirToLinux(BaseContext.linux_webapp_Path, new
		// File(BaseContext.local_platform_webapp_Path +
		// "html/activity"),"decocti\\S+");
		upload.uploadDirToLinux(BaseContext.linux_webapp_Path, new File(BaseContext.local_platform_webapp_Path + "images/activity"), "daily\\S+");
		/** 【上传文件夹】 */
		// UploadOperate.uploadDirToLinux(BaseContext.linux_webapp_Path, new
		// File(BaseContext.local_platform_webapp_Path + "html/card"));
	}

	public static void main(String[] args) throws Exception {
		new UploadOperate().uploadJarToLinux(BaseContext.linux_lib_Path, BaseContext.moveToDir + BaseContext.service_jar);
		/** 【移动文件 】 */
		// FindOperator.processFile((s) -> MoveOperate.doMoveClass(s));

		/** 【上传class】 */
		// FindOperator.processFile((s) -> UploadOperate.doUploadClass(s));

		/** 【扫描需要上传的html】 */
		// FindOperator.processFile((s) ->
		// UploadOperate.findUploadHtmlWriteToFile(s));

		/** 【上传html文件】 */
		// UploadOperate.doUploadHtmlFileListToLinux();

		/** 【下载jar】 */
		// DownloadOperator.download(Base.linux_lib_Path, Base.dao_jar,
		// Base.moveToDir);
		// DownloadOperator.download(Base.linux_lib_Path, Base.service_jar,
		// Base.moveToDir);

		/** 【上传jar】 */
		// UploadOperate.uploadToLinux(Base.linux_lib_Path, Base.moveToDir +
		// Base.dao_jar);
		// UploadOperate.uploadToLinux(Base.linux_lib_Path, Base.moveToDir +
		// Base.service_jar);
		// UploadOperate.uploadWarToLinux(Base.linux_idongri_Path,
		// Base.local_platform_war_path);

		// BaseContext.closeSession();
	}

	public static String path1 = "d://target//121.40.150.187-common-service-3.0-SNAPSHOT.jar";
	public static String path2 = "d://target//common-service-3.0-SNAPSHOT2.jar";

	@Test
	public void testCopyJar() throws Exception {
		ZipFileUtil.decompressJarToJar(path1, path2, (jOutputStream) -> {
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
	}

}
