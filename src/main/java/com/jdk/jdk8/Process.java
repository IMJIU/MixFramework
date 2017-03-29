package com.jdk.jdk8;

@FunctionalInterface
public interface Process<T> {
	void process(T obj);
}