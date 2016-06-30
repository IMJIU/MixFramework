package com.framework.db;

import java.util.Map;


public class DBTest {
	
	public static void main(String[] args) throws Exception {

//		long a = System.currentTimeMillis();
//		for (int i = 0; i < 200000; i++) {
//	        query("select * from tb_customer where id = 22", 1, 5);
//        }
//		System.out.println(System.currentTimeMillis()-aXJ);
		Map<String, Map<String, Col>> db2 = DBTool.getTableColumnInfo("select * from information_schema.COLUMNS where table_schema='idongri_v3'", 1, null);
		Map<String, Map<String, Col>> db1 = DBTool.getTableColumnInfo("select * from information_schema.COLUMNS where table_schema='idongri_cn'", 2, null);
//		DBTool.compare(db2, db1, "", "idongri_v3", "idongri_cn", null);// V3-CN
		 
//		 DBTool.compare(db2, db1, "", "idongri_cn", "idongri_v3",null,"nodiff");//CN-V3
		 
		 
		alterChangeCharset("idongri_cn",1);
		
//		Map<String, Map<String, String>> t1 = DBTool.queryInfo2("select * from tb_menu", 1);	
//		Map<String, Map<String, String>> t2 = DBTool.queryInfo2("select * from tb_menu", 2);
		// System.out.println(t1);
//		compare(t1, t2, new Process() {
//
//			@Override
//			public String process(Map<String, String> col) {
//				StringBuilder sb = new StringBuilder();
//				sb.append("insert into tb_menu(name, url, parent_id, create_time, update_time, action_url, rank, is_deleted)values('")
//				.append(col.get("name")).append("','")
//				.append(col.get("url")).append("','")
//				.append(col.get("parent_id")).append("',")
//				.append("now(),")
//				.append("now(),")
//				.append(col.get("action_url")).append("','")
//				.append(col.get("rank")).append("','")
//				.append(col.get("is_deleted")).append("')");
//				return sb.toString();
//			}
//			public String process(String id, String colName, Map<String, String> col) {
//				StringBuilder sb = new StringBuilder();
//				sb.append("alter tb_menu set ").append(colName).append("='").append(col.get(colName)).append("'").append("where id=" + col.get("id")).append(" and name = '").append(col.get("name")).append("';");
//				return sb.toString();
//			}
//		});
	}

}
