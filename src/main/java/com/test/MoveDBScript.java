package com.test;

import com.framework.db.DBTool;

public class MoveDBScript {

	public static void main(String[] args) throws Exception {
		// TODO 自动生成的方法存根
		int[] list1 = { 1, 2, 3,4, 5};
		for (int i = 0, j = list1.length - 1; i < list1.length/2; i++, j--) {
			int temp = list1[i];
			list1[i] = list1[j];
			list1[j] = temp;
		}

		for (int i = 0; i < 5; i++) {
			System.out.print(list1[i]);
		}

		String db1 = "select * from information_schema.COLUMNS where table_schema='idongri_v3'";
		String db2 = "select * from information_schema.COLUMNS where table_schema='idongri_copy'";
		// 数据库对比
		// DBCompare.compare(db1, db2,"","idongri_v3","idongri_copy");
		// DBCompare.compare(db2, db1,"idongri_copy","idongri_v3");
		// Sort
		// SortFileContent.sort("E:\\冬日暖阳\\文档\\db_change_log\\v3.0\\export_tables.sql");
		// DBTool.alterChangeCharset();
		DBTool.compare(t1, t2, p);
	}

}
