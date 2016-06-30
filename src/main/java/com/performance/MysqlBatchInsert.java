package com.performance;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


//insert into tb_phone(p_nm,c_nm,phone_code,mobile_num,p_id,c_id) values('海南','海口',0898,1361755,null,null);
public class MysqlBatchInsert {

	static Connection conn = null;

	static BufferedWriter bw;

	static String path = "F:\\工作\\工作\\logs\\data3.sql";

	public static Connection getConn() {
		try {
			if (conn == null)
//				conn = DriverManager.getConnection("jdbc:oracle:thin:@112.5.162.220:152:ora", "ysx_wap", "hi_SuN_#812$!");
				conn = DriverManager.getConnection("jdbc:mysql://192.168.99.166:3306/idongri?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true", "root", "idongri");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	public static void main(String[] args) {
		test();
	}
	public static void test() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
			long begin = System.currentTimeMillis();
			int count = 0;
			String sql = null;
			getConn().setAutoCommit(false); 
			while ((sql = br.readLine()) != null) {
				System.out.println(sql);
				Statement p = getConn().createStatement();
				p.addBatch(new String(sql.getBytes(Charset.forName("utf-8"))));
				if (count++ > 100) {
					int[] a = p.executeBatch();
					System.out.println(a.length);
					return;
				}
			}
			System.out.println((System.currentTimeMillis() - begin) / 1000 + " 秒");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {}
	}

}
