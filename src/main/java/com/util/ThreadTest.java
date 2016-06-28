package com.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ThreadTest {

	public static void main(String[] args) {
//		t1();
		t2();
	}
	public static void t1(){
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			new Thread(new ThreadClass(i, begin)).start();
        }
	}
	public static void t2(){
		ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2+500);
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			exec.execute(new ThreadClass(i,begin));
        }
	}
	
}
class ThreadClass implements Runnable{
	int i ;
	long begin ;
	public ThreadClass(int i,long begin){
		this.i = i;
		this.begin = begin;
	}
	@Override
	public void run() {
		try {
	        Thread.sleep(5);
        } catch (InterruptedException e) {
	        e.printStackTrace();
        }
		for (int j = 0; j < 10000; j++) {
            int m = j+100*1000/5/122%100;
            m = m*1000*34234/35234%34*1000;
        }
		System.out.println("i="+i+" "+(System.currentTimeMillis()-begin));
	}
}
