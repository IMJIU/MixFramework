package com.util.upload_to_linux.base;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MoveOperate extends FindOperator{
	
	public static String doMoveClass(String uploadFile) {
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			String to = moveToDir + "\\" + uploadFile.substring(uploadFile.lastIndexOf("\\") + 1);
//			System.out.println("from:"+uploadFile+" to:"+to);
//			Files.copy(Paths.get(uploadFile), new FileOutputStream(to));
			 in = new FileInputStream(uploadFile);
			 out = new FileOutputStream(to);
			byte[] b = new byte[8096];
			int len = 0;
			while ((len = in.read(b))>0) {
	            out.write(b,0,len);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
	            in.close();
	            out.close();
            } catch (IOException e) {
	            e.printStackTrace();
            }
		}
		return "";
	}

}
