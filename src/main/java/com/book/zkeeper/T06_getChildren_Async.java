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
import org.apache.zookeeper.data.Stat;

public class T06_getChildren_Async implements Watcher {

	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

	public static void main(String[] args) throws Exception {
		String path = "/zk-book2";
		ZooKeeper zookeeper = new ZooKeeper("localhost:2181", 5000, new T06_getChildren_Async());
		System.out.println(zookeeper.getState());
		connectedSemaphore.await();
		
		System.out.println("zookeeper session established!"); 
		zookeeper.create(path, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		
		zookeeper.create(path+"/c1", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		zookeeper.create(path+"/c2", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		
		zookeeper.getChildren(path, true,new IChildren2Callback(),null);
		
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
class IChildren2Callback implements AsyncCallback.Children2Callback{

	@Override
    public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
		System.out.println("result[responseCode:"+rc+",ctx:"+ctx+",children list:"+children+",stat:"+stat);
    }
	
}
