package com.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.TreeSet;

public class SortFileContent {

	private static String path = "E:\\冬日暖阳\\文档\\db_change_log\\v3.0\\export_tables.sql";

	// private String path = "E:\\冬日暖阳\\文档\\db_change_log\\v3.0\\error_col.sql";

	public static void sort(String p1) {
		if (p1 != null) {
			path = p1;
		}
		try {
			TreeSet<String> tree = new TreeSet<String>();
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
			String str = null;
			while ((str = reader.readLine()) != null) {
				tree.add(str.trim());
			}
			reader.close();
			System.out.println(tree);

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)));
			for (String string : tree) {
				writer.append(string).append("\n");
			}
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
