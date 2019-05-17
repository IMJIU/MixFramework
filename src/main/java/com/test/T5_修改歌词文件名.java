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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.framework.db.Col;
import com.framework.db.DBTool;

public class T5_修改歌词文件名 {

	private static String str;

	public static void main(String[] args) throws Exception {
		change("l:\\");
	}
	public static void change(String path) throws IOException {
		Files.list(Paths.get(path)).filter((f)->f.getFileName().toString().endsWith("krc")).forEach((f) -> {
			System.out.println(f.getFileName());
			new File(path+f.getFileName()).renameTo(new File(path+f.getFileName().toString().replaceAll("krc", "lrc")));
		});
	}
	public static void change1(String path) throws IOException {
		Files.list(Paths.get(path)).filter((f)->f.getFileName().toString().endsWith("krc")).forEach((f) -> {
			System.out.println(f.getFileName());
			String[] s = f.getFileName().toString().split(" - ");
			String name = s[0]+" - "+s[1].split("-")[0];
//			System.out.println(path+name);
			new File(path+f.getFileName()).renameTo(new File(path+name));
		});
	}
	public static void change2(String path) throws IOException {
		Files.list(Paths.get(path)).filter((f)->!f.getFileName().toString().endsWith("mp3")).forEach((f) -> {
			System.out.println(f.getFileName());
			new File(path+f.getFileName()).renameTo(new File(path+f.getFileName()+".krc"));
		});
	}

}
