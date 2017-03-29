package com.framework.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.dbcp.BasicDataSource;

import com.jdk.jdk8.Func;

public class DBTool {

	private static BasicDataSource datasource = new BasicDataSource();

	private static BasicDataSource datasource2 = new BasicDataSource();

	private DBTool() {}
	
	public static void main(String[] args) throws Exception {
		// 对比两个库的字段
		// showDBDiff();

		// DBTool.compare(db2, db1, "", "idongri_cn", "idongri_v3",null,"nodiff");//CN-V3

		// 字符集
		alterChangeCharset("idongri_cn", 1);

	}

	static {
		datasource.setDriverClassName("com.mysql.jdbc.Driver");
		datasource2.setDriverClassName("com.mysql.jdbc.Driver");

		// datasource.setUrl("jdbc:mysql://192.168.99.166:3306/idongri_v3");
		// datasource.setUsername("root");
		// datasource.setPassword("idongri");
		datasource.setUrl("jdbc:mysql://121.43.100.2:3306/idongri_cn");
		datasource.setUsername("root");
		datasource.setPassword("idongri");

		// .net
		// datasource.setDriverClassName("com.mysql.jdbc.Driver");
		// datasource.setUrl("jdbc:mysql://rdsc1bvnleypj44z77yfepublic.mysql.rds.aliyuncs.com:3306/idongri_v3");
		// datasource.setUsername("idongri_dev");
		// datasource.setPassword("zaq1xsw2");

		datasource.setInitialSize(20);
		datasource.setMaxActive(100);
		datasource.setMinIdle(20);
		datasource.setMaxWait(1000 * 3);

		datasource2.setUrl("jdbc:mysql://rdsc1bvnleypj44z77yfepublic.mysql.rds.aliyuncs.com:3306/idongri_cn");
		datasource2.setUsername("idongri_dev");
		datasource2.setPassword("zaq1xsw2");

		datasource2.setInitialSize(1);
		datasource2.setMaxActive(2);
		datasource2.setMinIdle(1);
		datasource2.setMaxWait(1000 * 3);
	}

	public static BasicDataSource init(String url) {
		BasicDataSource datasource = new BasicDataSource();
		datasource.setDriverClassName("com.mysql.jdbc.Driver");
		datasource.setUrl(url);
		datasource.setUsername("idongri_dev");
		datasource.setPassword("zaq1xsw2");
		datasource.setInitialSize(2);
		datasource.setMaxActive(10);
		datasource.setMinIdle(2);
		datasource.setMaxWait(1000 * 3);
		return datasource;
	}
	public static Connection getConn(int name) {
		try {
			if (name == 1) {
				return datasource.getConnection();
			}
			return datasource2.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String query(String sql, Integer num, Integer colCount) throws Exception {
		if (num == null)
			num = 1;
		Connection conn = getConn(num);
		Statement s = null;
		try {
			s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				if (colCount != null) {
					for (int i = 1; i < colCount + 1; i++) {
						// System.out.println(rs.getString(i));
					}
				} else {
					// String tableName = rs.getString(1);
					String info = rs.getString(2);
					return info;
				}
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (s != null)
				s.close();
			if (conn != null)
				conn.close();
		}
		return "";
	}
	public static Map<String, List<String>> queryInfo(String sql, Integer num) throws Exception {
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		if (num == null)
			num = 1;
		Connection conn = getConn(num);
		Statement s = null;
		try {
			s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				List<String> row = new ArrayList<String>();
				ResultSetMetaData meta = rs.getMetaData();
				Integer count = meta.getColumnCount();
				String id = rs.getString("id");
				for (int i = 1; i < count + 1; i++) {
					row.add(rs.getString(i));
				}

				result.put(id, row);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (s != null)
				s.close();
			if (conn != null)
				conn.close();
		}
		return result;
	}
	public static Map<String, Map<String, String>> queryInfo2(String sql, Integer num) throws Exception {
		Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
		if (num == null)
			num = 1;
		Connection conn = getConn(num);
		Statement s = null;
		try {
			s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				Map<String, String> row = new HashMap<String, String>();
				ResultSetMetaData meta = rs.getMetaData();
				Integer count = meta.getColumnCount();
				String id = rs.getString("id");
				for (int i = 1; i < count + 1; i++) {
					row.put(meta.getColumnName(i), rs.getString(i));
				}
				result.put(id, row);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (s != null)
				s.close();
			if (conn != null)
				conn.close();
		}
		return result;
	}
	public static Map<String, Map<String, Col>> getTableColumnInfo(String sql, Integer num, Func func) throws Exception {
		if (num == null)
			num = 1;
		Connection conn = getConn(num);
		Statement s;
		Map<String, Map<String, Col>> database = new HashMap<String, Map<String, Col>>();
		try {
			s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				String sc = rs.getString("TABLE_SCHEMA");
				String tbName = rs.getString("TABLE_NAME");
				String colName = rs.getString("COLUMN_NAME");
				String type = rs.getString("COLUMN_TYPE");
				String defaulVal = rs.getString("COLUMN_DEFAULT");
				String charset = rs.getString("CHARACTER_SET_NAME");
				Col c = new Col(sc, tbName, colName, type, defaulVal, charset);
				if (func != null) {
					func.convert(c);
				}
				Map<String, Col> table = database.get(tbName);
				if (table == null) {
					table = new HashMap<String, Col>();
					table.put(colName, c);
					database.put(tbName, table);
				} else {
					table.put(colName, c);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return database;
	}
	public static void compare(Map<String, Map<String, Col>> db1, Map<String, Map<String, Col>> db2, String target, String s1, String s2, Integer conn) throws Exception {
		compare(db1, db2, target, s1, s2, conn, "diff");
	}
	public static void compare(Map<String, Map<String, Col>> db1, Map<String, Map<String, Col>> db2, String target, String s1, String s2, Integer conn, String noDiff) throws Exception {
		Set<String> noChangeTable = new TreeSet<String>();
		Set<String> changeTable = new TreeSet<String>();
		Set<String> result = new TreeSet<String>();
		Set<String> resolve = new TreeSet<String>();

		for (String tableName : db1.keySet()) {
			if (tableName.equals("tb_drug_buy_record")) {
				System.out.println("coming");
			}
			Map<String, Col> table1 = db1.get(tableName);
			Map<String, Col> table2 = db2.get(tableName);
			if (table2 == null) {
				result.add(target + "no table:" + tableName);
				if (conn != null) {
					String createInfo = DBTool.query("show create table " + tableName, conn, null);
					resolve.add("drop table if exists " + tableName + ";");
					resolve.add(createInfo + ";");
				}
				changeTable.add(tableName);
				continue;
			}
			for (String colName : table1.keySet()) {
				Col col1 = table1.get(colName);
				Col col2 = table2.get(colName);
				if (col2 == null) {
					result.add(target + tableName + " no column:" + colName);
					if (col1.defaultVal != null) {
						resolve.add(generatorAlterTableSql(tableName, colName, col1, "add", true));
					} else {
						resolve.add(generatorAlterTableSql(tableName, colName, col1, "add", false));
					}
					changeTable.add(tableName);
					continue;
				}
				// col1 type 不为空
				if (col1.colType != null && !"nodiff".equals(noDiff)) {
					// col1 type 与 col2不一致
					if (!col1.colType.equals(col2.colType)) {
						changeTable.add(tableName);
						// default 不一致
						if (col1.defaultVal != null) {
							resolve.add(generatorAlterTableSql(tableName, colName, col1, "modify", true));
							result.add(target + tableName + ":" + colName + " diff type:new-" + col1.colType + " old-" + col2.colType);
							result.add(target + tableName + ":" + colName + " diff default value:new-" + col1.defaultVal + " old-" + col2.defaultVal);
							// default 一致
						} else {
							resolve.add(generatorAlterTableSql(tableName, colName, col1, "modify", false));
							result.add(target + tableName + ":" + colName + " diff type:new-" + col1.colType + " old-" + col2.colType);
						}
						// col1 type 与 col2一致 但default不一致
					} else if (col1.defaultVal != null && !col1.defaultVal.equals(col2.defaultVal)) {
						changeTable.add(tableName);
						resolve.add(generatorAlterTableSql(tableName, colName, col1, "modify", true));
						result.add(target + tableName + ":" + colName + " diff default value:new-" + col1.defaultVal + " old-" + col2.defaultVal);
					}

				}
			}
		}
		for (String tableName : db1.keySet()) {
			if (!changeTable.contains(tableName)) {
				noChangeTable.add(tableName);
			}
		}
		// System.out.println("===============================");
		// for (String string : noChangeTable) {
		// System.out.println(string);
		// }
		System.out.println("======  problem  =========================");
		for (String string : result) {
			System.out.println(string);
		}
		System.out.println("======  resovle  =========================");
		for (String string : resolve) {
			System.out.println(string);
		}

	}
	public static void compare(Map<String, Map<String, String>> t1, Map<String, Map<String, String>> t2, Process p) throws Exception {
		System.out.println(t1);
		Set<String> result = new TreeSet<String>();
		Set<String> resolve = new TreeSet<String>();
		for (String id : t1.keySet()) {
			Map<String, String> row1 = t1.get(id);
			Map<String, String> row2 = t2.get(id);
			if (row1 == null) {
				System.out.println("why");
				continue;
			}
			if (row2 == null) {
				result.add("None of " + id);
				resolve.add(p.process(row1));
				continue;
			}
			System.out.println("name:" + row1.get(2));
			for (String colName : row1.keySet()) {
				String col1 = row1.get(colName);
				String col2 = row2.get(colName);
				if (col1 == null) {
					System.out.println("col1." + colName + " null");
					continue;
				}
				if (col2 == null) {
					System.out.println("col2." + colName + " null");
					resolve.add(p.process(id, colName, row1));
					continue;
				}
				if (col1 != null && col2 != null && !col1.equals(col2)) {
					result.add("diff!r1:" + col1 + "-" + col2);
					resolve.add(p.process(id, colName, row1));
				}
			}
		}
		for (String string : result) {
			System.out.println(string);
		}
		for (String string : resolve) {
			System.out.println(string);
		}
	}
	private static String generatorAlterTableSql(String tableName, String colName, Col col1, String add, boolean addDefault) {
		StringBuilder res = new StringBuilder();
		res.append("alter table ").append(tableName);
		if ("add".equals(add)) {
			res.append(" add ");
		} else {
			res.append(" modify ");
		}
		res.append(colName).append(" ").append(col1.colType);
		if (addDefault) {
			res.append(" default ").append(col1.defaultVal);
		}
		res.append(";");
		return res.toString();
	}

	public static void alterChangeCharset(String schema, int conn) throws Exception {
		Map<String, Map<String, Col>> database = DBTool.getTableColumnInfo("select * from information_schema.COLUMNS where table_schema='" + schema + "'", conn, null);
		Set<String> resolve = new TreeSet<String>();
		Set<String> print = new TreeSet<String>();
		for (String tableName : database.keySet()) {
			Map<String, Col> tableInfo = database.get(tableName);
			for (String colName : tableInfo.keySet()) {
				Col col = tableInfo.get(colName);
				if ("utf8mb4".equals(col.charset)) {
					StringBuilder sb = builderSQL(col);
					resolve.add(sb.toString());
				} else if ("utf8".equals(col.charset)) {
					StringBuilder sb = builderSQL(col);
					resolve.add(sb.toString());
				}
			}
		}
		for (String string : print) {
			System.out.println(string);
		}
		for (String string : resolve) {
			System.out.println(string);
		}
	}
	private static StringBuilder builderSQL(Col col) {
		StringBuilder sb = new StringBuilder();
		// System.out.println(col.tb + ":" + col.colName);
		sb.append("alter table ").append(col.tb).append(" modify column ").append(col.colName).append(" ").append(col.colType).append(" character set utf8mb4 collate utf8mb4_general_ci");
		// sb.append("alter table ").append(col.tb).append(" convert to character").append(col.colName).append(" set utf8mb4 collate utf8mb4_general_ci");
		// alter table 表名 convert to character set gbk collate gbk_chinese_ci
		// if (col.defaultVal != null) {
		// sb.append(" defult ").append(col.defaultVal);
		// }
		sb.append(";");
		return sb;
	}
	public static Map<String, List<String>> getTableColumnInfo() throws Exception {
		Map<String, List<String>> result = new HashMap<>();
		DBTool.getTableColumnInfo("select * from information_schema.COLUMNS where table_schema='idongri_cn'", 2, new Func<Col, Object>() {

			@Override
			public Object convert(Col c) {
				List<String> colList = result.getOrDefault(c.tb, new ArrayList<String>());
				colList.add(c.colName);
				result.put(c.tb, colList);
				return null;
			}
		});
		return result;
	}

	private static void showDBDiff() throws Exception {
		Map<String, Map<String, Col>> db2 = DBTool.getTableColumnInfo("select * from information_schema.COLUMNS where table_schema='idongri_v3'", 1, null);
		Map<String, Map<String, Col>> db1 = DBTool.getTableColumnInfo("select * from information_schema.COLUMNS where table_schema='idongri_cn'", 2, null);
		DBTool.compare(db2, db1, "", "idongri_v3", "idongri_cn", null);// V3-CN
	}

	private static void other() {
		// Map<String, Map<String, String>> t1 = DBTool.queryInfo2("select * from tb_menu", 1);
		// Map<String, Map<String, String>> t2 = DBTool.queryInfo2("select * from tb_menu", 2);
		// System.out.println(t1);
		// compare(t1, t2, new Process() {
		//
		// @Override
		// public String process(Map<String, String> col) {
		// StringBuilder sb = new StringBuilder();
		// sb.append("insert into tb_menu(name, url, parent_id, create_time, update_time, action_url, rank, is_deleted)values('")
		// .append(col.get("name")).append("','")
		// .append(col.get("url")).append("','")
		// .append(col.get("parent_id")).append("',")
		// .append("now(),")
		// .append("now(),")
		// .append(col.get("action_url")).append("','")
		// .append(col.get("rank")).append("','")
		// .append(col.get("is_deleted")).append("')");
		// return sb.toString();
		// }
		// public String process(String id, String colName, Map<String, String> col) {
		// StringBuilder sb = new StringBuilder();
		// sb.append("alter tb_menu set ").append(colName).append("='").append(col.get(colName)).append("'").append("where id=" +
		// col.get("id")).append(" and name = '").append(col.get("name")).append("';");
		// return sb.toString();
		// }
		// });
	}
}
