package com.util.upload_to_linux.base;

public class DownloadOperator extends FindOperator {
	
	public static void download(String dir, String file, String to) throws Exception {
		loopServer((channelSftp) -> {
			try {
				_downloadFromLinux(dir, file, to + "\\" + channelSftp.getSession().getHost() + "-" + file, channelSftp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		System.out.println("finished");
	}

}
