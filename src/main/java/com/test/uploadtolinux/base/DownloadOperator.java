package com.test.uploadtolinux.base;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.jcraft.jsch.JSchException;

public class DownloadOperator extends FindOperator {
	public static void download(String dir,String file,String to) throws Exception{
		loopServer((t) -> {
			try {
				_downloadFromLinux(dir, file, to+"\\"+serverIp+"-"+file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		System.out.println("finished");
	}

}
