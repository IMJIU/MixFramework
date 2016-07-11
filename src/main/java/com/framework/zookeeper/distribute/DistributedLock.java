package com.framework.zookeeper.distribute;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import org.apache.zookeeper.ClientCnxn;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
  	DistributedLock lock = null;
	try {
		lock = new DistributedLock("127.0.0.1:2182","test");
		lock.lock();
		//do something...
	} catch (Exception e) {
		e.printStackTrace();
	} 
	finally {
		if(lock != null)
			lock.unlock();
	}
 *
 */
public class DistributedLock implements Lock,Watcher{
	private ZooKeeper zk;
	private String base = "/tPay";//根目录
	private String root = "/tPay/noticeLock";//根目录
	private String lockName;//竞争资源的标志
	private String waitNode;//等待前一个锁
	private String myZnode;//当前锁
	private CountDownLatch latch;//计数器
	private int sessionTimeout = 30000;
	private String config="172.28.6.131:2181,172.28.6.132:2181,172.28.6.133:2181";
    private static final Logger LOG = LoggerFactory.getLogger(ClientCnxn.class);
	private List<Exception> exception = new ArrayList<Exception>();
	
	/**
	 * 创建分布式锁,使用前请确认config配置的zookeeper服务可用
	 * @param config 127.0.0.1:2181
	 * @param lockName 竞争资源标志
	 */
	public DistributedLock(String config, String lockName){
		if(config == null){
			config = this.config;
		}
		this.lockName = lockName;
		// 创建一个与服务器的连接
		 try {
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
		} catch (IOException e) {
			exception.add(e);
		} catch (KeeperException e) {
			exception.add(e);
		} catch (InterruptedException e) {
			exception.add(e);
		}
	}

	/**
	 * zookeeper节点的监视器
	 */
	@Override
	public void process(WatchedEvent event) {
		if(this.latch != null) {  
            this.latch.countDown();  
        }
	}
	@Override
	public void lock() {
		if(exception.size() > 0){
			throw new LockException(exception.get(0));
		}
		try {
			if(this.tryLock()){
				LOG.info("Thread " + Thread.currentThread().getId() + " " +myZnode + " get lock true");
				return;
			}
			else{
				waitForLock(waitNode, sessionTimeout);//等待锁
			}
		} catch (KeeperException e) {
			throw new LockException(e);
		} catch (InterruptedException e) {
			throw new LockException(e);
		} 
	}
	@Override
	public boolean tryLock() {
		try {
			
			Stat stat = zk.exists(root +"/"+lockName, false);  //判断临时节点是否存在
			//创建临时子节点
			if(stat == null){
				myZnode =   zk.create(root + "/" + lockName, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
				LOG.info(myZnode + " is created ");
			    return true;
			}else{
				waitNode = root + "/" + lockName;
				return false;
			}
		} catch (KeeperException e) {
			e.printStackTrace();
			return false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		    return false;
		}
		
	}
	@Override
	public boolean tryLock(long time, TimeUnit unit) {
		try {
			if(this.tryLock()){
				return true;
			}
	        return waitForLock(waitNode,time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean waitForLock(String lower, long waitTime) throws InterruptedException, KeeperException {
        Stat stat = zk.exists(root + "/" + lower,true);
        if(stat != null){
        	LOG.info("Thread " + Thread.currentThread().getId() + " waiting for " + root + "/" + lower);
        	this.latch = new CountDownLatch(1);
        	this.latch.await(waitTime, TimeUnit.MILLISECONDS);
        	this.latch = null;
        }
        return true;
    }

	public void unlock() {
		try {
			LOG.info("unlock " + myZnode);
			zk.delete(myZnode,-1);
			myZnode = null;
			zk.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (KeeperException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void lockInterruptibly() throws InterruptedException {
		this.lock();
	}

	public Condition newCondition() {
		return null;
	}
	
	public class LockException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		public LockException(String e){
			super(e);
		}
		public LockException(Exception e){
			super(e);
		}
	}

}