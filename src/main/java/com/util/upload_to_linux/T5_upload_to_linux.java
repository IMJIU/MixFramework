package com.util.upload_to_linux;

import java.io.File;

import org.junit.After;
import org.junit.Test;

import com.util.upload_to_linux.base.*;

public class T5_upload_to_linux {

	static {
		// 配置
		Base.server = ".net";
		Base.findType = "class";// class | html
	}

	public static void main(String[] args) throws Exception {

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
		
		/** 【上传文件夹】*/
		UploadOperate.uploadDirToLinux(Base.linux_webapp_Path, new File(Base.local_platform_webapp_Path + "html/card"));
		Base.closeChannel();
	}

	@After
	public void close() {
		Base.closeChannel();
	}

	/** 【移动文件 】 */
	@Test
	public void t1_move() throws Exception {
		FindOperator.processFile((s) -> MoveOperate.doMoveClass(s));
	}

	/** 【上传class】 */
	@Test
	public void t2_upload_class() throws Exception {
		FindOperator.processFile((s) -> UploadOperate.doUploadClass(s));
	}

	/** 【扫描需要上传的html】 */
	@Test
	public void t3_scan_html() throws Exception {
		FindOperator.processFile((s) -> UploadOperate.findUploadHtmlWriteToFile(s));
	}

	/** 【上传html文件】 */
	@Test
	public void t4_upload_html() throws Exception {
		UploadOperate.doUploadHtmlFileListToLinux();
	}

	/** 【下载jar】 */
	@Test
	public void t5_download() throws Exception {
		DownloadOperator.download(Base.linux_lib_Path, Base.dao_jar, Base.moveToDir);
		DownloadOperator.download(Base.linux_lib_Path, Base.service_jar, Base.moveToDir);
	}

	/** 【上传jar】 */
	@Test
	public void t6_upload() throws Exception {
		UploadOperate.uploadToLinux(Base.linux_lib_Path, Base.moveToDir + Base.dao_jar);
		UploadOperate.uploadToLinux(Base.linux_lib_Path, Base.moveToDir + Base.service_jar);
		// UploadOperate.uploadWarToLinux(Base.linux_idongri_Path,
		// Base.local_platform_war_path);
	}

	/** 【上传文件夹】 */
	@Test
	public void t7_upload_dir() throws Exception {
		UploadOperate.uploadDirToLinux(Base.linux_webapp_Path, new File(Base.local_platform_webapp_Path + "html/card"));
	}
}
