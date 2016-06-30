package com.book.zkeeper;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;

public class T04_CreateAPIAsync implements Watcher {

	public static CountDownLatch connectedSemaphore = new CountDownLatch(2);

	public static void main(String[] args) throws Exception {
		ZooKeeper zookeeper = new ZooKeeper("localhost:2181", 5000, new T04_CreateAPIAsync());
		System.out.println(zookeeper.getState());
		System.out.println("zookeeper session established!");

		zookeeper.create("/t01-ephemeral", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, (rc, path, ctx, name) -> {
            System.out.println("create path result:["+rc+","+path+","+ctx+", real path name:"+name);
            connectedSemaphore.countDown();
        },"dfasfdas");

		connectedSemaphore.await();
	}

	@Override
	public void process(WatchedEvent event) {
		System.out.println("receive watche event:" + event);
		if (KeeperState.SyncConnected == event.getState()) {
			connectedSemaphore.countDown();
		}
	}
}

