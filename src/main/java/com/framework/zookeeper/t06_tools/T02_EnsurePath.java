package com.framework.zookeeper.t06_tools;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.utils.EnsurePath;
import org.apache.curator.utils.ZKPaths;
import org.apache.curator.utils.ZKPaths.PathAndNode;
import org.apache.zookeeper.ZooKeeper;

public class T02_EnsurePath {
	static String path = "/ensure_path";
	static CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181")
			.sessionTimeoutMs(5000)
			.retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();

	public static void main(String[] args) throws Exception {
		client.start();
		client.usingNamespace("zk-book");
		
		EnsurePath en = new EnsurePath(path);
		en.ensure(client.getZookeeperClient());
		en.ensure(client.getZookeeperClient());
		
		EnsurePath en2 = client.newNamespaceAwareEnsurePath("/c1");
		en2.ensure(client.getZookeeperClient());
	}

}
