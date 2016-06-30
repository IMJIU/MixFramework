package com.performance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

import sun.misc.VM;

public class NIOReadWrite {

	public static void main(String[] args) throws Exception {
		String path = "F:\\工作\\工作\\logs\\1.log";
		new File(path).createNewFile();
		System.out.println("start()...!");
		nio_w_map(path);
//		nio_r_map(path);
		
//		nio_w_buffer(path,1024*10,1);
//		nio_r_buffer(path,1024*10,1);
	}

	public static void nio_r_map(String path) throws Exception {
		FileChannel fc = null;
		int leftLen = 0;
		int begin = 0;
		int len = 1024;
		int ratio = 1;
		IntBuffer b = null;
		try {
			long start = System.currentTimeMillis();
			fc = new RandomAccessFile(path, "r").getChannel();
			leftLen = Long.valueOf(fc.size()).intValue();
			// System.out.println("total len:" + leftLen);
			while (leftLen > 0) {
				b = fc.map(FileChannel.MapMode.READ_ONLY, begin, len).asIntBuffer();
				// System.out.println(b.position() + " " + b.limit() + " " +
				// b.capacity());
				leftLen = leftLen - len;
				while (b.hasRemaining()) {
//					System.out.println(b.position() + " " + b.get());
					 b.get();
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

	public static void nio_w_map(String path) throws Exception {
		FileChannel fc = null;
		int begin = 0;
		int ratio = 1;
		int baseSize = 1024;// 1KB
		int maxSize = 1024 * 1024;// 1MB
		IntBuffer b = null;
		long start = System.currentTimeMillis();
		try {
			fc = new RandomAccessFile(path, "rw").getChannel();
			b = fc.map(FileChannel.MapMode.READ_WRITE, begin, baseSize).asIntBuffer();
			// System.out.println(b.position() + " " + b.limit() + " " +
			// b.capacity());
			for (int i = 0; i < 1000000; i++) {
				if (b != null && (b.position() == b.capacity())) {
					// System.out.println(b.position() + " " + b.limit() + " " +
					// b.capacity());
					if (ratio > 0) {
						begin += baseSize;
						if (baseSize <= maxSize)
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
	public static ByteBuffer allocate(int size,int direct){
		if(direct == 0){
			return  ByteBuffer.allocate(size);
		}else{
			return  ByteBuffer.allocateDirect(size);
		}
	}
	public static void nio_w_buffer(String path,int baseSize,int direct) throws Exception {
		FileOutputStream fo = null;
		FileChannel fc = null;
		int ratio = 1;
		int maxSize = 1024 * 1024;// 1MB
		try {
			long start = System.currentTimeMillis();
			fo = new FileOutputStream(path);
			fc = fo.getChannel();
			ByteBuffer b = allocate(baseSize,direct);
			for (int i = 0; i < 1000000; i++) {
				if (b != null && (b.position() == b.capacity())) {
					if (ratio > 0 && ratio <= maxSize) {
						ratio <<= 1;
						baseSize *= ratio;
					} else if(ratio ==0){
						baseSize += baseSize;
					}else{
						
					}
					b.flip();
					fc.write(b);
					b = allocate(baseSize,1);
				}
				b.put(int2byte(i));
			}
			 b.flip();
			 fc.write(b);
			System.out.println("耗时：" + (System.currentTimeMillis() - start) + "毫秒");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fc != null)
				fc.close();
		}
	}
	
	public static void nio_r_buffer(String path,int baseSize,int direct) throws Exception {
		FileInputStream fi = null;
		FileChannel fc = null;
		int leftLen = 0;
		int ratio = 1;
		try {
			long start = System.currentTimeMillis();
			fi = new FileInputStream(path);
			fc = fi.getChannel();
			leftLen = Long.valueOf(fc.size()).intValue();
			ByteBuffer b = allocate(baseSize,direct);
			b.clear();
			while (true) {
				b.clear();
				if (fc.read(b) == -1) {
					break;
				}
				b.flip();
				while (b.hasRemaining()) {
//					System.out.println(byte2int(b.get(), b.get(), b.get(), b.get()));
					b.get();
				}
			}
			System.out.println("耗时：" + (System.currentTimeMillis() - start) + "毫秒");
			fc.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fc != null)
				fc.close();
		}
	}

	public static byte[] int2byte(int i) {
		byte[] targets = new byte[4];
		targets[3] = (byte) (i & 0xff);
		targets[2] = (byte) ((i >> 8) & 0xff);
		targets[1] = (byte) ((i >> 16) & 0xff);
		targets[0] = (byte) (i >>> 24);
		return targets;
	}

	public static int byte2int(byte b1, byte b2, byte b3, byte b4) {
		return ((b1 & 0xff) << 24) | ((b2 & 0xff) << 16) | ((b3 & 0xff) << 8) | (b4 & 0xff);
	}
	public static void nio_w_buffer(String file, String msg, boolean append) throws Exception {
		FileOutputStream fo = null;
		FileChannel fc = null;
		int baseSize = 1024;
		int ratio = 1;
		int maxSize = 1024 * 1024;// 1MB
		long start = System.currentTimeMillis();
		try {
			fo = new FileOutputStream(file, append);
			fc = fo.getChannel();
			ByteBuffer b = ByteBuffer.allocate(baseSize);
			byte[] src = msg.getBytes();
			for (int i = 0; i < src.length; i++) {
				if (b != null && (b.position() == b.capacity())) {
					if (ratio > 0 && ratio <= maxSize) {
						ratio <<= 1;
						baseSize *= ratio;
					} else {
						baseSize += baseSize;
					}
					b.flip();
					fc.write(b);
					b = ByteBuffer.allocate(baseSize);
				}
				b.put(src[i]);
			}
			if(b.hasRemaining()){
				b.put("\n".getBytes());
			}else{
				b.flip();
				fc.write(b);
				b.clear();
				b.put("\n".getBytes());
			}
			b.flip();
			fc.write(b);
			System.out.println("耗时：" + (System.currentTimeMillis() - start) + "毫秒");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fc != null)
				fc.close();
			if (fo != null)
				fo.close();
		}
	}
}
