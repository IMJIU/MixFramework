package com.framework.thread;

import java.io.PrintStream;

public class AnotherTestMain {

	public static void main(String[] args) {

		long x = 58;

		writeResult(x, System.out);

	}

	/*
	 * 
	 * 因为一个数组可能无法存放全部的结果，这里将结果输出
	 */

	public static void writeResult(long num, PrintStream ps) {

		/*
		 * 
		 * java里没有无符号类型，这里不考虑负数
		 */

		if (num < 0 || ps == null) {

			return;

		}

		/*
		 * 
		 * 0的话就不费劲了
		 */

		if (num == 0) {

			ps.print(0);

		}

		/*
		 * 
		 * 计算传入的数字中，二进制表示串里有多少个1
		 * 
		 * 经典方法
		 */

		int numOfSetBits = 0;

		long v = num;

		while (v > 0) {

			v &= (v - 1);

			numOfSetBits++;

		}

		/*
		 * 
		 * 将各个1表示对应的二进制的值存下来
		 * 
		 * 保存到一个数组中
		 */

		long[] valueOfBits = new long[numOfSetBits];

		long pos = 1;

		int i = 0;

		while (i < numOfSetBits) {

			if ((pos & num) != 0) {

				valueOfBits[i++] = pos;

			}

			pos <<= 1;

		}

		/*
		 * 
		 * 其实结果的个数与传入参数的二进制串中的1的个数有关系
		 * 
		 * 结果的个数 = Math.power(2, numOfSetBits)
		 */

		long resultsNum = (1L << numOfSetBits);

		ps.print(0);

		for (long l = 1L; l < resultsNum; l++) {

			v = 0;

			for (i = 0; i < numOfSetBits && i < l; i++) {

				if (((1L << i) & l) != 0) {

					v |= valueOfBits[i];

				}

			}

			ps.print(", ");

			ps.print(v);

		}

	}

}
