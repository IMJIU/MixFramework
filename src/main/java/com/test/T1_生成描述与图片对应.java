package com.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class T1_生成描述与图片对应 {

	static Map<String, String> map = new HashMap<String, String>();

	static String base = "C:\\Users\\Administrator\\Desktop\\工作\\pic_tmp\\";

	public static void main(String[] args) {
		String path = "C:\\Users\\Administrator\\Desktop\\工作\\pic_tmp\\tmp.txt";
		path = "C:\\Users\\Administrator\\Desktop\\工作\\pic_tmp\\name2.txt";
		String path2 = "C:\\Users\\Administrator\\Desktop\\工作\\pic_tmp\\url.txt";
		read(path, path2);
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
