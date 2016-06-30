package com.framework.thread;

public class Test_Syn {

	private static boolean ready;

	private static int number;

	private static class ReaderThread extends Thread {

		public void run() {
			while (!ready)
				Thread.yield();
			System.out.println(number);
		}
	}

	public static void main(String[] args) {
		new ReaderThread().start();
		number = 42;
		ready = true;
	}
}
