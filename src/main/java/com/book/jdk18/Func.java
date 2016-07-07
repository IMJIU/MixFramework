package com.book.jdk18;

@FunctionalInterface
public interface Func<F, T> {
	T convert(F from);
}