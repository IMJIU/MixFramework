package com.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;


public class Utils {
	private static Map<String, Long> map = new HashMap<String, Long>();
	static TreeSet<Movie>tree = new TreeSet<Movie>();
	public static void main(String[] args)  throws Exception{
		/** 24 �c **/
//		TwoFour.compute("3 3 4 4");
		/** 用户表密码加密   MD5 **/
//		md5("123","加密");
		/** 数据库密码加密 **/
//		System.out.println(Aesc.Encrypt("sso"));
//		System.out.println(org.jasig.cas.client.util.Aesc.Encrypt("sso"));
		searchBySize("I:/C盘/a/个人集");
	}
	
	public static void md5(String pass,String decry) throws Exception{
		if(decry.equals("解密")){
			pass = Aesc.Decrypt(pass);
			System.out.println(pass);
		}else if(decry.equals("加密")){
			MD5 md5 = new MD5(pass);
			md5.processString();
			String newPasswordMd5 = md5.getStringDigest();
			System.out.println(newPasswordMd5);
		}
	}
	public static void searchBySize(String path){
		listFile(new File(path));
		sortMap();
	}
	public static void listFile(File f){
		if(f.isDirectory()){
			for (File file : f.listFiles()) {
				listFile(file);
			}
		}else{
//			map.put(f.getAbsolutePath()+"/"+f.getName(), f.length()/1024/1024);
			tree.add(new Movie(f.getAbsolutePath(),f.length()/1024/1024));
		}
	}
	static class Movie implements Comparable<Movie>{
		String name;Long size;
		public Movie(String name,Long size){this.name=name;this.size=size;}
		@Override
		public int compareTo(Movie o) {
			return o.size>this.size?-1:1;
		}
	}
	public static void sortMap(){
//		Set<String>keys = map.keySet();
//		for (String file : keys) {
//			System.out.println(file+"\tsize:"+map.get(file)+"MB");
//		}
		for (Movie m : tree) {
			System.out.println("size:"+m.size+"MB"+"\t"+m.name);
		}
	}
}
