package com.book.zkeeper;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;


public class T02_WithSidPasswd implements Watcher{
	private static CountDownLatch connectedSemaphore =  new CountDownLatch(2);
	
	public static void main(String[] args) throws Exception{
	    ZooKeeper zookeeper = new ZooKeeper("localhost:2181", 5000, new T02_WithSidPasswd() );
	    System.out.println(zookeeper.getState());
	    long sessiondId = zookeeper.getSessionId();
	    byte[]passwd = zookeeper.getSessionPasswd();
	    
	    //illegal
	    System.out.println("illegal");
	    zookeeper = new ZooKeeper("localhost:2181", 5000, new T02_WithSidPasswd(),1l,"test".getBytes() );
	    
	    //correct
	    System.out.println("correct");
	    zookeeper = new ZooKeeper("localhost:2181", 5000, new T02_WithSidPasswd(),sessiondId,passwd );
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
