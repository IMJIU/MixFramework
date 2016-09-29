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

import org.apache.commons.dbcp.BasicDataSource;

public class PostgreSqlTest {
	private static BasicDataSource datasource = new BasicDataSource();
	static {
		datasource.setDriverClassName("org.postgresql.Driver");
		datasource.setUrl("jdbc:postgresql://localhost:5432/ott-ids");
		datasource.setUsername("ott");
		datasource.setPassword("ott");

		datasource.setInitialSize(20);
		datasource.setMaxActive(100);
		datasource.setMinIdle(20);
		datasource.setMaxWait(1000 * 3);
	}
	public static void main(String[] args) throws Exception {
		Connection conn = getConn();
		query("select * from param", null, 1);
	}
	public static Connection getConn() {
		return getConn(null);
	}
	public static Connection getConn(Integer name) {
		try {
			return datasource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String query(String sql, Integer num, Integer colCount) throws Exception {
		Connection conn = getConn(num);
		Statement s = null;
		try {
			s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				if (colCount != null) {
					for (int i = 1; i < colCount + 1; i++) {
						 System.out.println(rs.getString(i));
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
}
