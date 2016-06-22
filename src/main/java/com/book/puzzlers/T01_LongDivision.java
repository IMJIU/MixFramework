package com.book.puzzlers;

public class T01_LongDivision {

	public static void main(String args[]) {
		/**
		 * 错误
		 */
		long MICROS_PER_DAY = 24 * 60 * 60 * 1000 * 1000;
		long MILLIS_PER_DAY = 24 * 60 * 60 * 1000;
		System.out.println(MICROS_PER_DAY / MILLIS_PER_DAY);
		/**
		 * 错误
		 */
		MICROS_PER_DAY = 24l * 60l * 60l * 1000l * 1000l;
		MILLIS_PER_DAY = 24l * 60l * 60l * 1000l;
		System.out.println(MICROS_PER_DAY / MILLIS_PER_DAY);
	}
}