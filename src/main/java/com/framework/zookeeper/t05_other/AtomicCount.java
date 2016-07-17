package com.framework.zookeeper.t05_other;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;

public class AtomicCount {
	static String path = "/atomic_count";
	static CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181")
			.retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();

	public static void main(String[] args) throws Exception {
		client.start();
		DistributedAtomicInteger atomicInteger = new DistributedAtomicInteger(client, path, new RetryNTimes(3, 1000));
		AtomicValue<Integer> rc = atomicInteger.add(8);
		System.out.println("result:" + rc.succeeded());
	}
}
