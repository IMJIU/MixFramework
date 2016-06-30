package com.book.zkeeper;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class T09_setData_Sync implements Watcher {

	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

	public static void main(String[] args) throws Exception {
		String path = "/zk-book111";
		ZooKeeper zookeeper = new ZooKeeper("localhost:2181", 5000, new T09_setData_Sync());
		System.out.println(zookeeper.getState());
		connectedSemaphore.await();

		System.out.println("zookeeper session established!");
		zookeeper.create(path, "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		zookeeper.getData(path, true, null);

		Stat stat = zookeeper.setData(path, "456".getBytes(), -1);
		System.out.println(stat.getCzxid() + "," + stat.getMzxid() + "," + stat.getVersion());
		
		Stat stat2 = zookeeper.setData(path, "456".getBytes(), stat.getVersion());
		System.out.println(stat2.getCzxid() + "," + stat2.getMzxid() + "," + stat2.getVersion());
		
		try {
	        zookeeper.setData(path, "456".getBytes(), stat.getVersion());
        } catch (Exception e) {
        	e.printStackTrace();
        }
		
		zookeeper.setData(path, "123".getBytes(), -1);

		Thread.sleep(Integer.MAX_VALUE);

	}

	@Override
	public void process(WatchedEvent event) {
		System.out.println("receive watche event:" + event);
		if (KeeperState.SyncConnected == event.getState()) {
			if (EventType.None == event.getType() && null == event.getPath()) {
				connectedSemaphore.countDown();
			}
		}
	}

}

class IDataCallback implements AsyncCallback.DataCallback {

	@Override
	public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
		System.out.println(rc + "," + path + "," + new String(data));
		System.out.println(stat.getCzxid() + "," + stat.getMzxid() + "," + stat.getVersion());
	}

}
