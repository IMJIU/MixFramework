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

	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

	public static void main(String[] args) throws Exception {
		ZooKeeper zookeeper = new ZooKeeper("localhost:2181", 5000, new T04_CreateAPIAsync());
		System.out.println(zookeeper.getState());
		connectedSemaphore.await();
		System.out.println("zookeeper session established!");
		IStringCallback b = new IStringCallback();
		zookeeper.create("/t01-ephemeral", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, b ,"dfasfdas");
		
	}

	@Override
	public void process(WatchedEvent event) {
		System.out.println("receive watche event:" + event);
		if (KeeperState.SyncConnected == event.getState()) {
			connectedSemaphore.countDown();
		}
	}
	
}
class IStringCallback implements AsyncCallback.StringCallback{
	@Override
	public void processResult(int rc, String path, Object ctx, String name) {
		System.out.println("create path result:["+rc+","+path+","+ctx+", real path name:"+name);
	}
}
