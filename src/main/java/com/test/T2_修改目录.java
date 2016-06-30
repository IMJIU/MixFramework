package com.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class T2_修改目录 {

	static Map<String, String> map = new HashMap<String, String>();

	static String base = "C:\\Users\\Administrator\\Desktop\\工作\\pic_tmp\\";

	public static void main(String[] args) {
		String path = "C:\\Users\\Administrator\\Desktop\\hdm\\ftl";
		dealFile(new File(path));
	}
	public static void dealFile(File f1) {
		if (f1.isDirectory()) {
			for (File f2 : f1.listFiles()) {
				if (f2.isDirectory()) {
					dealFile(f2);
				} else {
					dowork(f2);
				}
			}
		} else {
				dowork(f1);
		}
	}
	public  static void dowork(File f1){
		if(f1.getName().endsWith(".html")){
			String name = f1.getAbsolutePath().replaceAll("ftl", "ftl2");
			System.out.println(name);
			File f2 = new File(name);
			
			try {
				if(!f2.exists()){
					f2.createNewFile();
				}
		        BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(f1)));
		        BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f2)));
		        String msg = null;
				while ((msg=r.readLine()) != null) {
					msg = msg.replace("../", "../../");
					w.write(msg+"/n");
					System.out.println(msg);
				}
	        } catch (FileNotFoundException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
	        } catch (IOException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
	        }
		}
	}

	public static void read(String path, String path2) {
		try {
			File f1 = new File(path);
			File f2 = new File(path2);
			BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(f1)));
			BufferedReader r2 = new BufferedReader(new InputStreamReader(new FileInputStream(f2)));
			String msg = r.readLine();
			String url = r2.readLine();
			map.put(msg, url);
			while (msg != null) {
				msg = r.readLine();
				url = r2.readLine();
				map.put(msg, url);
			}
			createFile();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static void createFile() {
		File pic = null;

		for (String key : map.keySet()) {

			System.out.println(key + "=" + map.get(key));
			pic = new File(base + map.get(key));
			System.out.println(pic.getAbsolutePath());
			System.out.println(pic.exists());
			copy(pic, new File(base + key + ".jpg"));

		}
	}

	public static void copy(File ofile, File nfile) {
		try {
			if (ofile.exists()) {
				if (!nfile.exists())
					nfile.createNewFile();
				OutputStream out = new FileOutputStream(nfile);
				InputStream r = new FileInputStream(ofile);
				byte[] bs = new byte[1024 * 8];
				int len = r.read(bs);
				while (len != -1) {
					out.write(bs, 0, len);
					len = r.read(bs);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
