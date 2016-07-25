package com.framework.zookeeper.t06_tools;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.utils.ZKPaths;
import org.apache.curator.utils.ZKPaths.PathAndNode;
import org.apache.zookeeper.ZooKeeper;

public class T01_ZKPaths {
	static String path = "/zkpath";
	static CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181")
			.sessionTimeoutMs(5000)
			.retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();

	public static void main(String[] args) throws Exception {
		client.start();
		ZooKeeper zookeeper = client.getZookeeperClient().getZooKeeper();
		
		System.out.println(ZKPaths.fixForNamespace(path, "sub"));
		System.out.println(ZKPaths.makePath(path, "sub"));
		System.out.println(ZKPaths.getNodeFromPath("/zkpath/sub1"));
		PathAndNode pn = ZKPaths.getPathAndNode("/zkpath/sub1");
		System.out.println(pn.getPath());
		System.out.println(pn.getNode());
		
		String dir1 = path + "/child1";
		String dir2 = path + "/child2";
		
		ZKPaths.mkdirs(zookeeper, dir1);
		ZKPaths.mkdirs(zookeeper, dir2);
		
		System.out.println(ZKPaths.getSortedChildren(zookeeper, path));
		
		ZKPaths.deleteChildren(client.getZookeeperClient().getZooKeeper(), path, true);
	}

}
