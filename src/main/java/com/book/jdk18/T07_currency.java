package com.book.jdk18;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class T07_currency {

	public static void main(String[] args) {
		long b = System.currentTimeMillis();
		f1();
		System.out.println(System.currentTimeMillis() - b);
	}
	
	static void f1() {
		int max = 1000000;
		List<String> values = new ArrayList<>(max);
		for (int i = 0; i < max; i++) {
			UUID uuid = UUID.randomUUID();
			values.add(uuid.toString());
		}
		/**
		 * 串行
		 */
		currency(values);
		/**
		 * 并行
		 */
		concurrency(values);
	}
	
	private static void concurrency(List<String> values) {
		long t0 = System.nanoTime();
		long count = values.parallelStream().sorted().count();
		System.out.println(count);
		long t1 = System.nanoTime();
		long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
		System.out.println(String.format("parallel sort took: %d ms", millis));
	}
	
	private static void currency(List<String> values) {
		long t0 = System.nanoTime();
		long count = values.stream().sorted().count();
		System.out.println(count);
		long t1 = System.nanoTime();
		long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
		System.out.println(String.format("sequential sort took: %d ms", millis));
	}
}
