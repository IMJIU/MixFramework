package com.book.jdk18;

@FunctionalInterface
public interface Process<T> {
	void process(T obj);
}