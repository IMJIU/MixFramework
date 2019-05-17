package com.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.framework.db.Col;
import com.framework.db.DBTool;
import com.jdk.jdk8.Func;
import com.jdk.jdk8.Process;

public class T6_修改文件内容 {

	private static String str;

	public static void main(String[] args) throws Exception {
		loop(new File("D:\\workspace_mars\\storm_flume_kafka"), new Func<String, String>() {
			@Override
			public String convert(String line) {
				if (line.indexOf("王扬庭") != -1) {
					line = line.replaceAll("王扬庭", "IMJIU");
				}
				return line;
			}
		});
	}

	public static void loop(File dir, Func<String, String> func) throws IOException {
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {
				loop(f, func);
			} else if (f.getName().endsWith("java")) {
				System.out.println(f.getAbsolutePath());
				FileInputStream fi;
				try {
					fi = new FileInputStream(f.getAbsolutePath());
					byte[] bs = new byte[1024];
					int len = 0;
					StringBuilder sb = new StringBuilder();
					while ((len = fi.read(bs)) != -1) {
						sb.append(func.convert(new String(bs)));
					}
					fi.close();
					Files.write(Paths.get(f.getAbsolutePath()), sb.toString().getBytes(), StandardOpenOption.WRITE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
