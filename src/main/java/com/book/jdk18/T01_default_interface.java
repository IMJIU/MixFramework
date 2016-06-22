package com.book.jdk18;

interface T01_default_interface {

	double calculate(int a);

	default double sqrt(int a) {
		return Math.sqrt(a);
	}
}