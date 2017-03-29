package com.jdk.jdk8;

@FunctionalInterface
public interface Func<F, T> {
	T convert(F from);
}