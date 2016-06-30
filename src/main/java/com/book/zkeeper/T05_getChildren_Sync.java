package com.book.zkeeper;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class T05_getChildren_Sync implements Watcher {

	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

	public static void main(String[] args) throws Exception {
		String path = "/zk-book";
		ZooKeeper zookeeper = new ZooKeeper("localhost:2181", 5000, new T05_getChildren_Sync());
		System.out.println(zookeeper.getState());
		connectedSemaphore.await();
		
		System.out.println("zookeeper session established!");
		zookeeper.create(path, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		
		zookeeper.create(path+"/c1", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		zookeeper.create(path+"/c2", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		
		List<String>children = zookeeper.getChildren(path, true);
		System.out.println(children);
		
		Thread.sleep(Integer.MAX_VALUE);
		
	}

	@Override
	public void process(WatchedEvent event) {
		System.out.println("receive watche event:" + event);
		if (KeeperState.SyncConnected == event.getState()) {
			connectedSemaphore.countDown();
		}
	}
	
}

