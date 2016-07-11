package com.framework.zookeeper.distribute;
import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

 
public class ZLock implements Watcher{
	private ZooKeeper zk;
	private String base = "/tPay";//根目录
	private String root = "/tPay/noticeLock";//根目录
	private String lockName;//竞争资源的标志
	private int sessionTimeout = 30000;
	private String config="10.253.68.98:2181,10.253.68.99:2181,10.253.68.118:2181,10.253.68.119:2181,10.253.68.120:2181";
    private static final Logger LOG = LoggerFactory.getLogger(ZLock.class);
	/**
	 * 创建分布式锁,使用前请确认config配置的zookeeper服务可用
	 * @param config 127.0.0.1:2181
	 * @param lockName 竞争资源标志
	 * @throws IOException 
	 */
	public void ZLock(String config, String lockName) throws IOException,KeeperException,InterruptedException{ 
		if(config == null){
			config = this.config;
		}
		this.lockName = lockName;
		// 创建一个与服务器的连接
			zk = new ZooKeeper(config, sessionTimeout, this); 
			Stat basestat = zk.exists(base, false);
			if(basestat == null){
				// 创建根节点
				zk.create(base, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT); 
			}
			Stat stat = zk.exists(root, false);
			if(stat == null){
				// 创建根节点
				zk.create(root, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT); 
			}
	}
	
	public boolean Lock(String lockName) {
			try {
	
				ZLock( config,  lockName);
			    zk.create(root + "/" + lockName, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL);
			  //  zk.get
				 return true;
			} catch (KeeperException e) {
				return false;
			} catch (InterruptedException e) {
				return false;
			} catch (IOException e) {
				return false;
			}
	}

	public void unlock() {
		try {
			LOG.info("unlock " + lockName);
			Stat stat = zk.exists(root +lockName, false); 
			if(stat!= null){
			 zk.delete(root+lockName,-1);
			}
			zk.close();
		} catch (InterruptedException e){
			e.printStackTrace();
		} catch (KeeperException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void process(WatchedEvent event) {
		//event.ge
		KeeperState ks = event.getState();
		EventType st = event.getType();
		//EventType.
		//KeeperState.
		
		//System.out.println("WatchedEvent:"+event.getPath()+"---"+event.getState()); 
	}
}