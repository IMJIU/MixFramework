package com.framework.zookeeper.t03_distribute_lock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class Recipes_Lock {
	static String lock_path = "/lock_path";
	static CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181")
			.retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();

	public static void main(String[] args) throws InterruptedException {
		client.start();
		final InterProcessMutex lock = new InterProcessMutex(client, lock_path);
		final CountDownLatch latch = new CountDownLatch(1);
		for (int i = 0; i < 30; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						System.out.println("lock！");
						latch.await();
						System.out.println("go");
						lock.acquire();
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.println("acquire!");
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
					String orderNo = sdf.format(new Date());
					System.out.println("orderNo:" + orderNo);
					try {
						lock.release();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}).start();
		}
		latch.countDown();
	}
}
