package com.test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

public class testjdbc {

	private testjdbc() {}

	private static DataSource dataSource;

	public void dbcp() {
		// private static DataSource dataSource;
		ResultSet rs = null;// 建立一个数据库表的对象
		Statement stmt = null;// 用于执行静态SQL的对象
		Connection conn = null;// 用于建立联系的对象
		try {
			Class.forName("com.mysql.jdbc.Driver");// 构造一个"com.mysql.jdbc.Driver"类
			// new mysql.jdbc.driver.mysqldriver();
			// conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/firstdatabase", "root","family");//与数据库建立联系
			Properties pro = new Properties();
			InputStream in = testjdbc.class.getClassLoader().getResourceAsStream("dbcp.properties");
			pro.load(in);
			dataSource = BasicDataSourceFactory.createDataSource(pro);// 注意这段代码！！！
			conn = dataSource.getConnection();
			stmt = conn.createStatement();// 创建一个 Statement 对象来将 SQL 语句发送到数据库
			rs = stmt.executeQuery("select * from tab");// 执行给定的 SQL 语句，该语句返回单个 ResultSet 对象
			while (rs.next()) {// 当有下一个节点时
				System.out.println(rs.getString("name"));// 输出对应的数据库的成员的name
				System.out.println(rs.getString("passwords"));// 输出对应的数据库成员的id
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// finally{
		// try{
		// if(rs!=null){//如果不等于空
		// rs.close();//关闭
		// }
		// if(stmt!=null){//如果不等于空
		// stmt.close();//关闭
		// }
		// if(conn!=null){//如果不等于空
		// conn.close();//关闭
		// }
		// }catch(SQLException e){
		// e.printStackTrace();
		// }
		// }
	}
	public static void main(String args[]) {
		testjdbc tj = new testjdbc();
		long t1 = System.currentTimeMillis();
		tj.dbcp();
		long t2 = System.currentTimeMillis();
		System.out.println(t2 - t1);
	}
}