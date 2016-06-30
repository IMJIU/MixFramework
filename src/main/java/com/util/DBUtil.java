package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

	public static Connection getConn() {
		// 1.注册驱动
		// DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		// System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 2.建立连接
			String url = "jdbc:mysql://localhost:3306/test";
			String user = "root";
			String password = "root";
			Connection conn = DriverManager.getConnection(url, user, password);
			return conn;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ResultSet query(String sql) {
		Connection conn = null;
		Statement st = null;
		try {
			conn = getConn();
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void execute(String sql) {
		Connection conn = null;
		Statement st = null;
		try {
			conn = getConn();
			st = conn.createStatement();
			st.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
