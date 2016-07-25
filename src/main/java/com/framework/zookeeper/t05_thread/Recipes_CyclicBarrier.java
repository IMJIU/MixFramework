package com.framework.zookeeper.t05_thread;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;

public class Recipes_CyclicBarrier {
	static String path = "/barrier_path";


	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 5; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						 CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181")
								.retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
						 client.start();
						 DistributedDoubleBarrier barrier = new DistributedDoubleBarrier(client, path,5);
						 Thread.sleep(Math.round(Math.random()*3000));
						 
						 System.out.println(Thread.currentThread().getName()+"-barrier设置");
						 barrier.enter();
						 System.out.println("Starting....");
						 Thread.sleep(Math.round(Math.random()*3000));
						 barrier.leave();
						 System.out.println("ending ....");
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}).start();
		}
		
	}
}
