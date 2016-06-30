package com.data_structure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

/**
 * LockFreeList性能好像不好！！！
 * 
 * @author root
 *
 */
public class TestLockFreeList {

	static final int MAX_THREADS = 2000;

	static final int TASK_COUNT = 4000;

	List list;

	public class AccessListThread implements Runnable {

		protected String name;

		Random r = new Random();

		public AccessListThread() {
		}

		public AccessListThread(String name) {
			this.name = name;
		}

		@Override
		public void run() {
			try {
				for (int i = 0; i < 1000; i++) {
					handleList(r.nextInt(100));
				}
				Thread.sleep(r.nextInt(100));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public class CounterPoolExecutor extends ThreadPoolExecutor {

		private AtomicInteger count = new AtomicInteger(0);

		public long starttime = 0;

		public String funcname = "";

		public CounterPoolExecutor(int corepoolsize, int maxpoolsize, long keepalivetime, TimeUnit unit, BlockingQueue<Runnable> workQueue, long begin) {
			super(corepoolsize, maxpoolsize, keepalivetime, unit, workQueue);
			starttime = begin;
		}

		@Override
		protected void afterExecute(Runnable r, Throwable t) {
			int l = count.addAndGet(1);
			if (l == TASK_COUNT) {
				System.out.println(funcname + " time:" + (System.currentTimeMillis() - starttime));
			}
		}

	}

	public Object handleList(int index) {
		list.add(index);
		list.remove(index % list.size());
		return null;
	}

	public void initLinkedList() {
		List l = new ArrayList();
		for (int i = 0; i < 1000; i++) {
			l.add(i);
		}
		list = Collections.synchronizedList(new LinkedList(l));
	}

	public void initVector() {
		List l = new ArrayList();
		for (int i = 0; i < 100; i++) {
			l.add(i);
		}
		list = new Vector(l);
	}

	public void initFreeLockList() {
		List l = new ArrayList();
		for (int i = 0; i < 100; i++) {
			l.add(i);
		}
		list = new TestLockFreeList(l);
	}

	public void initFreeLockVector() {
		List l = new LockFreeVector();
		for (int i = 0; i < 100; i++) {
			l.add(i);
		}
		list = l;
	}

	@Test
	public void testFreeLockList() throws InterruptedException {
		initFreeLockList();
		doWork();
	}

	@Test
	public void testLinkedList() throws InterruptedException {
		initLinkedList();
		doWork();
	}

	@Test
	public void testVector() throws InterruptedException {
		initVector();
		doWork();
	}

	@Test
	public void testFreeLockVector() throws InterruptedException {
		initFreeLockVector();
		doWork();
	}

	private void doWork() throws InterruptedException {
		CounterPoolExecutor exe = new CounterPoolExecutor(MAX_THREADS, MAX_THREADS, 0l, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(),
				System.currentTimeMillis());
		exe.funcname = "testFreeLockList";
		Runnable t = new AccessListThread();
		for (int i = 0; i < TASK_COUNT; i++) {
			exe.submit(t);
		}
		Thread.sleep(10000);
	}

	public static void main(String[] args) {
		t1();
		t2();
		t3();
		t4();
	}

	public static void t1() {
		LockFreeList<Integer> list = new LockFreeList<Integer>();
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			list.add(i);
			list.get(i);
		}
		long end = System.currentTimeMillis();
		System.out.println(end - begin);
	}

	public static void t2() {
		Vector<Integer> list = new Vector<Integer>();
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			list.add(i);
			list.get(i);
		}
		long end = System.currentTimeMillis();
		System.out.println(end - begin);
	}

	public static void t3() {
		ArrayList<Integer> a = new ArrayList<Integer>();
		Collection<Integer> list = Collections.synchronizedCollection(a);
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			list.add(i);
			list.remove(i);
		}
		long end = System.currentTimeMillis();
		System.out.println(end - begin);
	}

	public static void t4() {
		LockFreeVector<Integer> list = new LockFreeVector<Integer>();
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			list.add(i);
			list.remove(i);
		}
		long end = System.currentTimeMillis();
		System.out.println(end - begin);
	}

}
