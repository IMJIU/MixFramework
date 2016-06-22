package com.performance;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;

public class TestIO {

	static CharBuffer ch = null;

	static int begin = 0;

	static int ratio = 1;

	static int baseSize = 1024;// 1KB

	static int maxSize = 1024 * 1024;// 1MB

	static FileChannel fc = null;

	static String path2 = "F:\\工作\\工作\\logs\\data_tmp.sql";
	static String path = "F:\\工作\\工作\\logs\\data_tmp2.sql";

	static int direct = 1;

	static FileOutputStream fo = null;

	static ByteBuffer b ;

	public static void main(String[] args) {
		try {
	        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path2)));
//	        BufferedOutputStream bo = new BufferedOutputStream(new FileOutputStream(path, true));
//	        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, true)));
	        String msg = null;
	        b = ByteBuffer.allocate(8179);
	        long begin = System.currentTimeMillis();
	        while ((msg = br.readLine())!=null) {
//	            System.out.println(msg);
//	            nio_w_buffer(msg+"\n");
//	            bo.write((msg+"/n").getBytes());
//	        	bw.append(msg).append("\n");
	        	nio_w_map(msg);
	        	//总结：bw最快
            }
	        System.out.println(System.currentTimeMillis()-begin+" 秒");
        } catch (FileNotFoundException e) {
	        e.printStackTrace();
        } catch (IOException e) {
	        e.printStackTrace();
        }finally{
//        	closeFC();
        }
	}
	public static void closeFC(){
		try {
            fc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	public static FileChannel getFC() {
		if (fc == null) {
			try {
				fo = new FileOutputStream(path,true);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			fc = fo.getChannel();
		}
		return fc;
	}
	public static void nio_w_buffer(String msg) {
		try {
			int len = b.capacity() - b.position();
			if (len < msg.getBytes().length) {
				b.flip();
				getFC().write(b);
				b.clear();
			}
			b.put(msg.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static void extend2(int ratio, int maxSize) throws IOException {
		if (b != null && (b.position() == b.capacity())) {
			if (ratio > 0 && ratio <= maxSize) {
				ratio <<= 1;
				baseSize *= ratio;
			} else if (ratio == 0) {
				baseSize += baseSize;
			} else {

			}
			b.flip();
			fc.write(b);
			b = allocate(baseSize, 1);
		}
	}
	public static FileChannel getFC2() {
		if (fc == null) {
			try {
	            fc = new RandomAccessFile(path, "rw").getChannel();
            } catch (FileNotFoundException e) {
	            e.printStackTrace();
            }
		}
		return fc;
	}
	public static void nio_w_map(String msg) {
		try {
			if (ch == null)
				ch = getFC2().map(FileChannel.MapMode.READ_WRITE, begin, baseSize).asCharBuffer();
			if (ch.position() + msg.length() > ch.capacity()) {
				int len = ch.capacity() - ch.position();
				ch.append(msg, 0, len);
				extend();
				if (len < msg.length())
					ch.append(msg, len, msg.length());
			} else {
				ch.append(msg);
			}
		} catch (Exception e) {
			System.out.print(ch.position() + " " + ch.limit() + " " + ch.capacity());
			e.printStackTrace();
		}
	}
	public static ByteBuffer allocate(int size, int direct) {
		if (direct == 0) {
			return ByteBuffer.allocate(size);
		} else {
			return ByteBuffer.allocateDirect(size);
		}
	}
	private static void extend() throws IOException {
		if (ch != null && (ch.position() == ch.capacity())) {
			if (ratio > 0) {
				begin += baseSize;
				if (baseSize <= maxSize)
					ratio <<= 1;
				baseSize *= ratio;
			} else {
				begin += baseSize;
			}
			System.out.println(begin);
			System.out.println(baseSize);
			ch = fc.map(FileChannel.MapMode.READ_WRITE, begin, baseSize).asCharBuffer();
		}
	}
}
