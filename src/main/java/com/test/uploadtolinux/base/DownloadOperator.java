package com.test.uploadtolinux.base;

public class DownloadOperator extends FindOperator {
	public static void download(String dir,String file,String to) throws Exception{
		loopServer((t) -> {
			try {
				_downloadFromLinux(dir, file, to+"\\"+serverIP+"-"+file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		System.out.println("finished");
	}

}
