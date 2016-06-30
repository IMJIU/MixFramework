package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

/**
 * @author root
 *
 */
public class Sample {

	public static void main(String[] args) throws Exception {
		Searcher searcher = new Searcher(); 
		String path = "E:\\高阳飞动\\和动漫\\开发版本UE";
		// path = "J:\\C盘\\a\\个人集";
		// path = "E:\\workspace_spring\\JettyTest\\src\\com\\acme";
//		path = "E:\\高阳飞动\\PLSQL\\Procedures";
		path = "G:\\book";
//		path = "C:\\Users\\Administrator\\Desktop\\bbk.sql";
//		path = "F:\\Temp\\PDF";
		// path = "E:\\workspace_luna\\jetty_9.2.6\\src";
		/** 文件名 【查找】 */
//		searcher.find(new File(path),"卡耐基");

		/** 关键字【查找 】文件内容 */
		//searcher.findStr(new File(path), 1, "/*");
		/** 【替换】文件内容 */
		// searcher.dealFile(new
		// File(path),Ps.rep,"filter=.*\\.java&reg=/\\*.{3,8}\\*/&rep=");

		/** 【删除】文件 */
		// searcher.dealFile(new File(path),Ps.del,"reg=.*\\.java");

		/** 【处理】文件 */
		// searcher.dealFile(new File(path),Ps.rename,"reg=.*\\.bak&rep=.bak&str=");

		/** 【重命名】文件 */
//		 searcher.recallToDir(new File("J:\\C盘\\a\\个人集"));
//		 searcher.dealFile(new File(path), Ps.rename, "regex=\\[(bbk\\d+)\\]&rep=&str");
		// System.out.println(AESUtils.encrypt("123333333333333333333333333", "123"));

		/** 【过滤】文件内容 */
		// searcher.dealFile(new File(path),Ps.filter,"reg=");
//		searcher.dealFile(new File(path),Ps.rename,"regex=\\[(bbk\\d+)\\] - (.+)&className=com.self.DealRename");
		// w_map

		// nio_w_map();
		// nio_r_map();
		// nio_w_buffer();
		// nio_r_buffer();
		
		/** CPU寄存器 */
//		l3Cache();
	}

	static int level = 0;

	public static void print(Object o) throws IllegalArgumentException, IllegalAccessException {
		if (o != null) {
			Field[] fs = o.getClass().getDeclaredFields();
			System.out.println("==== " + o.getClass().getName() + " ====");
			for (Field f : fs) {
				f.setAccessible(true);
				System.out.print(f.getName() + "\t");
				Object p = f.get(o);
				if (p != null) {
					// System.out.print("[class]:"+p.getClass().getName()+"\t");
					System.out.println(p);
					printObject(p);
				} else {
					System.out.println("null");
				}

			}
		}
	}

	private static void printObject(Object p) {
		String cname = p.getClass().getName();
		if (cname.equals("java.lang.String") || cname.equals("java.lang.Boolean") || cname.equals("java.lang.Integer") || cname.equals("java.lang.Float")
		        || cname.equals("java.lang.Long") || cname.equals("java.lang.String")) {
			System.out.println(p);
		} else {
			try {
				if (level > 0) {
					print(p);
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public static void nio_r_map() throws Exception {
		FileChannel fc = null;
		int leftLen = 0;
		int begin = 0;
		int len = 1024;
		int ratio = 1;
		IntBuffer b = null;
		try {
			long start = System.currentTimeMillis();
			fc = new RandomAccessFile("C:\\Users\\Administrator\\Desktop\\工作\\logs\\soap_thirdAccount.log", "r").getChannel();
			leftLen = Long.valueOf(fc.size()).intValue();
			// System.out.println("total len:" + leftLen);
			while (leftLen > 0) {
				b = fc.map(FileChannel.MapMode.READ_ONLY, begin, len).asIntBuffer();
				// System.out.println(b.position() + " " + b.limit() + " " +
				// b.capacity());
				leftLen = leftLen - len;
				while (b.hasRemaining()) {
					System.out.println(b.position() + " " + b.get());
					// b.get();
				}
				begin += len;
				if (ratio > 0) {
					ratio <<= 1;
					len = len * ratio;
					if (leftLen <= len) {
						len = leftLen;
					}
				} else {
					if (leftLen <= len) {
						len = leftLen;
					} else {
						len = 1024;
					}
				}
			}
			System.out.println("耗时：" + (System.currentTimeMillis() - start) + "毫秒");
		} catch (Exception e) {
			System.out.println(b.position() + " " + b.limit() + " " + b.capacity());
			e.printStackTrace();
		} finally {
			if (fc != null)
				fc.close();
		}
	}

	public static void nio_w_map() throws Exception {
		FileChannel fc = null;
		int begin = 0;
		int ratio = 1;
		int baseSize = 1024;// 1KB
		IntBuffer b = null;
		long start = System.currentTimeMillis();
		try {
			fc = new RandomAccessFile("C:\\Users\\Administrator\\Desktop\\工作\\logs\\soap_thirdAccount.log", "rw").getChannel();
			b = fc.map(FileChannel.MapMode.READ_WRITE, begin, baseSize).asIntBuffer();
			// System.out.println(b.position() + " " + b.limit() + " " +
			// b.capacity());
			for (int i = 0; i < 1000000; i++) {
				if (b != null && (b.position() == b.capacity())) {
					// System.out.println(b.position() + " " + b.limit() + " " +
					// b.capacity());
					if (ratio > 0) {
						begin += baseSize;
						ratio <<= 1;
						baseSize *= ratio;
					} else {
						begin += baseSize;
					}
					b = fc.map(FileChannel.MapMode.READ_WRITE, begin, baseSize).asIntBuffer();
				}
				b.put(i);
			}
			System.out.println("耗时：" + (System.currentTimeMillis() - start) + "毫秒");
		} catch (Exception e) {
			System.out.print(b.position() + " " + b.limit() + " " + b.capacity());
			e.printStackTrace();
		} finally {
			if (fc != null)
				fc.close();
		}
	}

	public static void nio_w_buffer() throws Exception {
		FileOutputStream fo = null;
		FileChannel fc = null;
		int baseSize = 1024;
		int ratio = 1;
		try {
			fo = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\工作\\logs\\soap_thirdAccount.log");
			fc = fo.getChannel();
			ByteBuffer b = ByteBuffer.allocate(baseSize);
			for (int i = 0; i < 10000; i++) {
				if (b != null && (b.position() == b.capacity())) {
					if (ratio > 0) {
						ratio <<= 1;
						baseSize *= ratio;
					} else {
						baseSize += baseSize;
					}
					b.flip();
					fc.write(b);
					b = ByteBuffer.allocate(baseSize);
				}
				b.put(int2byte(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fc != null)
				fc.close();
		}
	}

	public static void nio_r_buffer() throws Exception {
		FileInputStream fi = null;
		FileChannel fc = null;
		int leftLen = 0;
		int baseSize = 1024;
		int ratio = 1;
		try {
			fi = new FileInputStream("C:\\Users\\Administrator\\Desktop\\工作\\logs\\soap_thirdAccount.log");
			fc = fi.getChannel();
			leftLen = Long.valueOf(fc.size()).intValue();
			ByteBuffer b = ByteBuffer.allocate(baseSize);
			b.clear();
			while (true) {
				b.clear();
				if (fc.read(b) == -1) {
					break;
				}
				b.flip();
				while (b.hasRemaining()) {
					System.out.println(byte2int(b.get(), b.get(), b.get(), b.get()));
					;
				}
			}
			fc.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fc != null)
				fc.close();
		}
	}

	public static byte[] int2byte(int res) {
		byte[] targets = new byte[4];
		targets[3] = (byte) (res & 0xff);
		targets[2] = (byte) ((res >> 8) & 0xff);
		targets[1] = (byte) ((res >> 16) & 0xff);
		targets[0] = (byte) (res >>> 24);
		return targets;
	}

	public static int byte2int(byte b1, byte b2, byte b3, byte b4) {
		return ((b1 & 0xff) << 24) | ((b2 & 0xff) << 16) | ((b3 & 0xff) << 8) | (b4 & 0xff);
	}

	private static final int RUNS = 10;

	private static final int DIMENSION_1 = 1024 * 1024;

	private static final int DIMENSION_2 = 6;

	private static long[][] longs;

	public static void l3Cache() throws Exception {
		Thread.sleep(10000);
		longs = new long[DIMENSION_1][];
		for (int i = 0; i < DIMENSION_1; i++) {
			longs[i] = new long[DIMENSION_2];
			for (int j = 0; j < DIMENSION_2; j++) {
				longs[i][j] = 0L;
			}
		}
		System.out.println("starting....");

		long sum = 0L;
		for (int r = 0; r < RUNS; r++) {

			final long start = System.nanoTime();

			// slow
			for (int j = 0; j < DIMENSION_2; j++) {
				for (int i = 0; i < DIMENSION_1; i++) {
					sum += longs[i][j];
				}
			}

			// fast
//			for (int i = 0; i < DIMENSION_1; i++) {
//				for (int j = 0; j < DIMENSION_2; j++) {
//					sum += longs[i][j];
//				}
//			}

			System.out.println((System.nanoTime() - start));
		}

	}
	public static void copy(File ofile, File nfile) {
		try {
			if (ofile.exists()) {
				if (!nfile.exists())
					nfile.createNewFile();
				OutputStream out = new FileOutputStream(nfile);
				InputStream r = new FileInputStream(ofile);
				byte[] bs = new byte[1024 * 8];
				int len = r.read(bs);
				while (len != -1) {
					out.write(bs, 0, len);
					len = r.read(bs);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
/**
13837256
8843528
10028507
14045278
18194285
14426193
13963175
13328843
9528780
11070199

starting....
47502583
45545118
47714948
45674589
47655738
48445987
45609854
43685545
47105090
43788965
*/