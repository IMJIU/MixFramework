package com.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.atomic.AtomicInteger;

import com.book.jdk18.Process;
import com.util.upload_to_linux.base.FindOperator;

public class Test_File_Process {
	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 100; i++) {
			System.out.println(i+"-"+isPowerOfTwo(i));
		}

		// t1_process_file();
	}

	private static boolean isPowerOfTwo(int val) {
		return (val & -val) == val;
	}
	public static void t3_next(){
		AtomicInteger idx = new AtomicInteger();
		AtomicInteger idx2 = new AtomicInteger();
		AtomicInteger idx3 = new AtomicInteger();
		String[] executors = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8" };

		for (int i = 0; i < 100; i++) {
			// 第1个和最后1个
			System.out.print(executors[idx.getAndIncrement() & executors.length - 1] + "\t");
			// 都取
			System.out.print(executors[Math.abs(idx2.getAndIncrement() % executors.length)] + "\t");
			// 都取
			System.out.println(executors[idx3.getAndIncrement() % executors.length]);
		}
	}
	
	public static void t2_time() {
		AtomicInteger idx = new AtomicInteger();
		AtomicInteger idx2 = new AtomicInteger();
		AtomicInteger idx3 = new AtomicInteger();
		String[] executors = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8" };
		String temp;
		int count = 100000000;
		long begin = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			temp = executors[idx.getAndIncrement() & executors.length - 1];
		}
		long begin2 = System.currentTimeMillis();
		System.out.println(begin2 - begin);
		for (int i = 0; i < count; i++) {
			temp = executors[idx3.getAndIncrement() % executors.length];
		}
		System.out.println(System.currentTimeMillis() - begin2);
	}

	public static void t1_process_file() throws Exception {
		FindOperator find = new FindOperator();
		find.localProcessFile(new File("D:\\git\\bak3\\MixTest\\src\\main\\java\\com\\book\\netty5"), new Process<File>() {
			public void process(File f) {
				try {
					StringBuilder sb = new StringBuilder();
					Path path = Paths.get(f.getAbsolutePath());
					Files.lines(path).forEach((s) -> {
						// if(s.startsWith("package com.framework.netty5")){
						// sb.append(s.replace("package com.framework.netty5",
						// "package com.book.netty5")).append("\n");
						// }else{
						// sb.append(s).append("\n");
						// }
						sb.append(s.replaceAll("com\\.framework\\.netty5", "com\\.book\\.netty5")).append("\n");
					});
					// System.out.println(sb.toString());
					Files.write(path, sb.toString().getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

}
