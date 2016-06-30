package com.book.jdk18;

public class T03_func {

	@FunctionalInterface
	interface Converter<F, T> {
		T convert(F from);
	}

	public static void main(String[] args) {
		Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
		Integer converted = converter.convert("123");
		System.out.println(converted); // 123

		// 另外一种方式
		converter = Integer::valueOf;
		converted = converter.convert("123");
		System.out.println(converted); // 123

		Converter<String, String> converter2 = ""::concat;
		String converted2 = converter2.convert("Java");
		System.out.println(converted2); // "J"

		final int num = 1;
		Converter<Integer, String> stringConverter = (from) -> String.valueOf(from + num);
		stringConverter.convert(2); // 3
	}

}
