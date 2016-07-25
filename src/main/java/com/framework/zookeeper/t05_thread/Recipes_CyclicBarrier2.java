package com.framework.zookeeper.t05_thread;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;

public class Recipes_CyclicBarrier2 {
	static String path = "/barrier_path";
	static DistributedBarrier barrier;


	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 5; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						 CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181")
								.retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
						 client.start();
						 barrier = new DistributedBarrier(client, path);
						 System.out.println(Thread.currentThread().getName()+"-barrier设置");
						 barrier.setBarrier();
						 barrier.waitOnBarrier();
						 System.out.println("Starting....");
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}).start();
			Thread.sleep(2000);
			barrier.removeBarrier();
		}
		
	}
}
