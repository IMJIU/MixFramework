package com.book.zkeeper;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;

public class T03_CreateAPISync implements Watcher {

	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

	public static void main(String[] args) throws Exception {
		ZooKeeper zookeeper = new ZooKeeper("localhost:2181", 5000, new T03_CreateAPISync());
		System.out.println(zookeeper.getState());
		connectedSemaphore.await();
		System.out.println("zookeeper session established!");
		String path1 = zookeeper.create("/t01-ephemeral", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		System.out.println("success craete znode:"+path1);
		
		String path2 = zookeeper.create("/t01-ephemeral", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		System.out.println("success craete znode:"+path2);
		
	}

	@Override
	public void process(WatchedEvent event) {
		System.out.println("receive watche event:" + event);
		if (KeeperState.SyncConnected == event.getState()) {
			connectedSemaphore.countDown();
		}
	}
}
