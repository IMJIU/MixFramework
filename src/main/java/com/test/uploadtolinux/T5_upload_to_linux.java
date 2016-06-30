package com.test.uploadtolinux;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.book.jdk18.Func;
import com.book.jdk18.Process;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.linux.ftp.SFTP_T01;
import com.test.uploadtolinux.base.Base;
import com.test.uploadtolinux.base.DownloadOperator;
import com.test.uploadtolinux.base.FindOperator;
import com.test.uploadtolinux.base.MoveOperate;
import com.test.uploadtolinux.base.UploadOperate;

public class T5_upload_to_linux {

	public static void main(String[] args) throws Exception {
		System.out.println(Class.class.getClass().getResource("/").getPath());
		Base.server = ".net";
		Base.findType = "class";// class | html
		FindOperator find = new FindOperator();
//		 find.processFile((s) -> MoveOperate.doMoveClass(s));
//		 find.processFile((s) -> UploadOperate.doUploadClass(s));
//		find.processFile((s) -> UploadOperate.findUploadHtmlWriteToFile(s));
//		UploadOperate.doUploadHtmlFileListToLinux();
		
		
		
		//下载jar
//		DownloadOperator.download(Base.linux_lib_Path, Base.dao_jar, Base.moveToDir);
//		DownloadOperator.download(Base.linux_lib_Path, Base.service_jar, Base.moveToDir);
		
		//上传jar
//		UploadOperate.uploadToLinux(Base.linux_lib_Path, Base.moveToDir + Base.dao_jar);
		UploadOperate.uploadToLinux(Base.linux_lib_Path, Base.moveToDir + Base.service_jar);
	}

}
