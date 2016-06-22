package com.framework.tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//insert into tb_phone(p_nm,c_nm,phone_code,mobile_num,p_id,c_id) values('海南','海口',0898,1361755,null,null);
public class OracleExport {

	static Connection conn = null;

	static BufferedWriter bw;

	static String path = "F:\\工作\\工作\\logs\\data.sql";

	public static Connection getConn() {
		try {
			if (conn == null)
				conn = DriverManager.getConnection("jdbc:oracle:thin:@112.5.162.220:152:ora", "ysx_wap", "hi_SuN_#812$!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void main(String[] args) {
		int next = 1000;
		for (int i = 5000; i < 170640; i += next) {
			int end = i + next;
			test(i, end);
		}
		try {
			getWrite().close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public static void test(int start, int end) {
		try {
			String sql = "select * from (select rownum as rn,t.* from tb_phone t where rownum<=" + end + ") where rn>" + start;
			PreparedStatement p = getConn().prepareStatement(sql);
			ResultSet rs = p.executeQuery();
			long begin = System.currentTimeMillis();
			while (rs.next()) {
				StringBuilder sb = new StringBuilder();
				String P_NM = rs.getString("P_NM");
				String C_NM = rs.getString("C_NM");
				String PHONE_CODE = rs.getString("PHONE_CODE");
				String MOBILE_NUM = rs.getString("MOBILE_NUM");
				String P_ID = rs.getString("P_ID");
				String C_ID = rs.getString("C_ID");
				sb.append("insert into tb_phone(p_nm,c_nm,phone_code,mobile_num,p_id,c_id) values('").append(P_NM).append("','").append(C_NM).append("',").append(PHONE_CODE).append(",")
				        .append(MOBILE_NUM).append(",").append(P_ID).append(",").append(C_ID).append(");\n");
				write(sb.toString());
			}
			System.out.println((System.currentTimeMillis() - begin) / 1000 + " 秒");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				getWrite().flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static BufferedWriter getWrite() {
		if (bw == null)
			try {
				bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path), true)));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		return bw;
	}
	public static void write(String msg) {
		try {
			getWrite().append(msg);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
