package com.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.framework.db.DBTool;

public class T4_解析binlog {

	private static String str;

	public static void main(String[] args) {
		// stringTobinary();
		// t1();
//		parser_bin_log();
		t3();
	}
	private static void stringTobinary() {
		String s1 = "11011";
		// 进制转换
		System.out.println(Integer.toBinaryString(255));// 11111111
		System.out.println(Integer.toHexString(255));// ff
		System.out.println(Integer.valueOf("11111111", 2));// 255
	}
	public static void t1() {
		File dir = new File("E:\\workspace_scala\\T01\\src\\com\\im\\code");
		Arrays.asList(dir.listFiles()).forEach(f -> {
			Arrays.asList(f.listFiles()).forEach(sfile -> {
				try {
					// List<String>list = Files.readAllLines(Paths.get(sfile.getPath()));
					// Files.write();

					BufferedReader read = null;
					read = new BufferedReader(new InputStreamReader(new FileInputStream(sfile)));
					String content = "";
					List<String> list = new LinkedList<>();
					while ((content = read.readLine()) != null) {
						if (content.matches("package.+")) {
							list.add(content.replace("com.dt.scala", "com.im"));
						} else {
							list.add(content);
						}
					}
					read.close();
					StringBuilder sb = new StringBuilder();
					for (String string : list) {
						if (string.matches("package.+")) {
							string = string.replace("com.dt.scala", "com.im");
						}
						sb.append(string).append("\n");
					}
					new FileOutputStream(sfile).write(sb.toString().getBytes());
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		});
	}
	public static void parser_bin_log() {
		// File dir = new File("E:\\冬日暖阳\\文档\\db_change_log\\476625_all.sql");
		File dir = new File("E:\\冬日暖阳\\文档\\db_change_log\\831_5646168_10870057.sql");
		BufferedWriter write;

		try {
			Map<String, List<String>> tabMap = DBTool.getTableColumnInfo();

			StringBuilder sb = null;
			write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("E:\\冬日暖阳\\文档\\db_change_log\\insert_update.sql")));
			List<String> content = Files.readAllLines(dir.toPath());
			int state = 0; // 0skip 1写
			int optype = 0; // 0insert 1update
			int update_order = 1; //0 set  1where
			Stack<StringBuilder>stack = new Stack<>();
			List<String> colList = null;
			for (String str : content) {
				if (state == 0 && str.startsWith("### INSERT INTO ")) {
					optype = 0;
					if (str.indexOf("tb_token") != -1) {
						continue;
					}
					sb = new StringBuilder();
					state = 1;
					sb.append(str.substring(str.indexOf("### ") + 4)).append("values(");
				} else if (state == 0 && str.startsWith("### UPDATE ")) {
					optype = 1;
					if (str.indexOf("tb_token") != -1) {
						continue;
					}
					sb = new StringBuilder();
					state = 1;
					String tableString = str.substring(str.indexOf("### ") + 4);
					sb.append(tableString);
					int startpos = tableString.indexOf(".") + 2;
					int endpos = tableString.indexOf("`", startpos);
					colList = tabMap.get(tableString.substring(startpos, endpos));
					if (colList == null) {
						state = 0;
					}
				} else if (optype == 0 && state == 1 && !str.startsWith("# at")) {
					if (str.indexOf("### SET") != -1) {
						continue;
					}
					str = str.replace("###", "");
					int len = 0;
					int len2 = 0;
					if (((len = str.indexOf("@")) != -1) && (len2 = str.indexOf("=")) != -1) {
						str = str.substring(len2 + 1);
					}
					sb.append(str).append(",");
				} else if (optype == 1 && state == 1 && !str.startsWith("# at")) {
					if (str.indexOf("### WHERE") != -1) {
						stack.push(sb);
						update_order = 1;
						sb = new StringBuilder();
						sb.append(" where ");
						continue;
					}
					if (str.indexOf("### SET") != -1) {
						StringBuilder tmp = sb;
						sb = stack.pop();
						stack.push(tmp);
						sb.append(" set ");
						update_order = 0;
						continue;
					}
					str = str.replace("###", "");
					int len = 0;
					int len2 = 0;
					if (((len = str.indexOf("@")) != -1) && (len2 = str.indexOf("=")) != -1) {
						String colname = colList.get(Integer.valueOf(str.substring(len + 1, len2))-1);
						sb.append(colname).append("=").append(str.substring(len2 + 1));
						if(update_order == 1){
							sb.append(" and ");
						}else{
							sb.append(",");
						}
					}
				} else if (optype == 0 && state == 1 && str.startsWith("# at")) {
					sb.deleteCharAt(sb.length() - 1).append(");\n");
					write.append(sb.toString());
					write.flush();
					sb = null;
					state = 0;
				} else if (optype == 1 && state == 1 && str.startsWith("# at")) {
					StringBuilder where = stack.pop();
					sb.deleteCharAt(sb.length()-1).append(where.delete(where.length()-5,where.length()-1)).append(";\n");
					write.append(sb.toString());
					write.flush();
					sb = null;
					state = 0;
				}
			}
			write.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void t3() {
		File dir = new File("E:\\冬日暖阳\\文档\\db_change_log\\tb_drug_buy_record_update.sql");
		BufferedWriter write;

		try {
			StringBuilder sb =null;
			write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("E:\\冬日暖阳\\文档\\db_change_log\\tb_drug_buy_record_update_2.sql")));
			List<String> content = Files.readAllLines(dir.toPath());
			
			for (String str : content) {
				sb = new StringBuilder();
				int pos1 = str.indexOf(" id=");
				int pos2 = str.indexOf(",",pos1+1);
				int pos3 = str.indexOf("address_id=");
				int pos4 = str.indexOf(",",pos3+1);
				int pos5 = str.indexOf(" id=",pos4);
				int pos6 = str.indexOf(" and",pos5+1);
				
				sb.append(str.substring(0,pos1+4))
				.append(Integer.valueOf(str.substring(pos1+4,pos2))+1000)
				.append(str.substring(pos2,pos3+11))
				.append(Integer.valueOf(str.substring(pos3+11,pos4))+1000)
				.append(str.substring(pos4,pos5+4))
				.append(Integer.valueOf(str.substring(pos5+4,pos6))+1000)
				.append(";\n");
				write.append(sb.toString());
				write.flush();
				sb = null;
			}
			write.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
