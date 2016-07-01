package com.test.decimal;

import java.math.BigDecimal;

public class BigDecimalTest {

	public static void main(String[] args) {
		BigDecimal a = new BigDecimal(40.751);

		BigDecimal b = new BigDecimal(5.5);

		BigDecimal c = a.multiply(b);
		System.out.println(Math.round(mul(40.751d, 5.5d)));
	}

	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}
}
