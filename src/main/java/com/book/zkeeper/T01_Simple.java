package com.book.zkeeper;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;


public class T01_Simple implements Watcher{
	private static CountDownLatch connectedSemaphore =  new CountDownLatch(1);
	
	public static void main(String[] args) throws Exception{
	    ZooKeeper zookeeper = new ZooKeeper("localhost:2181", 5000, new T01_Simple() );
	    System.out.println(zookeeper.getState());
	    connectedSemaphore.await();
	    System.out.println("zookeeper session established!");
    }

	@Override
    public void process(WatchedEvent event) {
	    System.out.println("receive watche event:"+event);
	    if(KeeperState.SyncConnected == event.getState()){
	    	connectedSemaphore.countDown();
	    }
	    
    }

}
