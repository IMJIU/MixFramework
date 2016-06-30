package com.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.UUID;


public class T3_修改图片名称 {
	static String path = "G:\\BaiduYunDownload\\周龙飞 林晔-9151-朱明生-10.15-526849180\\";
	static byte[]b = new byte[1024*1024];
	
	public static void main(String[] args) throws Exception {
		list(new File(path));
    }
	public static void list(File p) throws Exception {
		for (File f : p.listFiles()) {
	        if(!f.isDirectory()&&f.getName().endsWith("jpg")){
	        	copyFileTo(f, new File(path+UUID.randomUUID().toString()+".jpg"));
	        }else if(f.isDirectory()){
	        	list(f);
	        }
        }
	}
	public static void copyFileTo(File f ,File dest) throws Exception{
		FileInputStream fi = new FileInputStream(f);
		FileOutputStream fo = new FileOutputStream(dest);
		int len;
		while((len=fi.read(b))!=-1){
			fo.write(b,0,len);
		}
		fo.flush();
		fo.close();
		fi.close();
	}

}
