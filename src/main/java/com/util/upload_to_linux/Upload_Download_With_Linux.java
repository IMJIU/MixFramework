package com.util.upload_to_linux;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jdk.jar.ZipFileUtil;
import com.util.upload_to_linux.base.BaseConstants;
import com.util.upload_to_linux.base.BaseContext;
import com.util.upload_to_linux.base.DownloadOperator;
import com.util.upload_to_linux.base.FindOperator;
import com.util.upload_to_linux.base.MoveOperate;
import com.util.upload_to_linux.base.UploadOperate;

public class Upload_Download_With_Linux extends BaseConstants {

	private UploadOperate upload;
	private DownloadOperator download;

	@Before
	public void before() {
		// 配置
		BaseContext.isThread = true;
		BaseContext.svn_path = svn_my_pc;
		// D:\\svn_code\\idongriV3\\
		// D:\\svn_code\\idongriV3_packing\\
		// G:\\svn\\idongriV3\\
		// D:\\svn_code\\V3.7.1\\
		BaseContext.svn_path = svn_com_packing;
		BaseContext.moveToDir = "d:\\target\\";
		
		BaseContext.init(net, platform, html);
		
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
		BaseContext.init(net, platform, classs);
		FindOperator.processFile((s) -> upload.doUploadClass(s));
	}

	/** 【扫描需要上传的html】 */
	@Test
	public void t3_scan_html() throws Exception {
		BaseContext.init(cn, platform, html);
		FindOperator.processFile((s) -> UploadOperate.findUploadHtmlWriteToFile(s));
	}

	/** 【上传html文件】 */
	@Test
	public void t4_upload_html() throws Exception {
		BaseContext.mkDir=false;
		BaseContext.init(cn, platform, html);
		upload.doUploadHtmlFileListToLinux();
	}

	/** 【下载jar】 */
	@Test
	public void t5_download() throws Exception {
		BaseContext.init(cn, platform);
		download.download(BaseContext.linux_lib_Path, BaseContext.service_jar, BaseContext.moveToDir);
		// DownloadOperator.download(BaseContext.linux_lib_Path,
		// BaseContext.dao_jar, BaseContext.moveToDir);
	}

	/** 【上传jar】 */
	@Test
	public void t6_upload() throws Exception {
		BaseContext.init(cn, platform);
		// upload.uploadToLinux(BaseContext.linux_lib_Path,
		// BaseContext.moveToDir + BaseContext.dao_jar);
		upload.uploadToLinux(BaseContext.linux_lib_Path, BaseContext.moveToDir + BaseContext.service_jar);

	}

	/** 【上传jar】 */
	@Test
	public void t6_upload_WAR() throws Exception {
		BaseContext.init(net, admin);
		upload.uploadWarToLinux(BaseContext.linux_idongri_Path, BaseContext.local_app_war_path);
		// upload.uploadWarToLinux(BaseContext.linux_idongri_Path,
		// BaseContext.local_admin_war_path);
	}

	/** 【上传jar】 */
	@Test
	public void t7_upload_JAR() throws Exception {
		BaseContext.init(net, platform);
		upload.uploadJarToLinux(BaseContext.moveToDir + BaseContext.host187 + "-" + BaseContext.service_jar, BaseContext.moveToDir + BaseContext.service_jar);
	}

	/** 【上传文件夹】 */
	@Test
	public void t8_upload_DIR() throws Exception {
		BaseContext.init(cn, platform, html);
		// upload.uploadDirToLinux(BaseContext.linux_webapp_Path, new
		// File(BaseContext.local_app_webapp_Path + "extension"), "daily\\S+");
//		upload.uploadDirToLinux(BaseContext.linux_webapp_Path, new File(BaseContext.local_app_webapp_Path + "html/card"), null);
		/** 【上传文件夹】 */
		// UploadOperate.uploadDirToLinux(BaseContext.linux_webapp_Path, new
		// File(BaseContext.local_platform_webapp_Path + "html/card"));
		// UploadOperate.uploadDirToLinux(BaseContext.linux_webapp_Path, new
		// File(BaseContext.local_platform_webapp_Path + "html/card"));
		// upload.uploadDirToLinux(BaseContext.linux_webapp_Path, new
		// File(BaseContext.local_platform_webapp_Path +
		// "html/activity"),"decocti\\S+");
		upload.uploadDirToLinux(BaseContext.linux_webapp_Path, new File(BaseContext.local_app_webapp_Path + "images/activity"), "qixi\\S+");
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
