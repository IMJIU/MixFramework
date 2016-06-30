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
import com.test.uploadtolinux.base.FindOperator;
import com.test.uploadtolinux.base.MoveOperate;
import com.test.uploadtolinux.base.UploadOperate;

public class T5_upload_to_linux {

	public static void main(String[] args) throws Exception {
		Base.server = ".net";
		Base.findType = "html";// class | html
		FindOperator find = new FindOperator();
		// find.processFile((s) -> MoveOperate.doMoveClass(s));
		// find.processFile((s) -> UploadOperate.doUploadClass(s));
		find.processFile((s) -> UploadOperate.findUploadHtmlWriteToFile(s));
		UploadOperate.doUploadHtmlFileListToLinux();
	}

}
