package com.framework.zookeeper.distribute;

public class TestLock {
	public static void main(String args[]){
		DistributedLock lock = null;
		try {
			
			lock = new DistributedLock("172.28.6.131:2181,172.28.6.132:2181,172.28.6.133:2181","13520185953");
			lock.lock();
          
		} catch (Exception e) {
			e.printStackTrace();
		} 
		finally {
			if(lock != null)
				lock.unlock();
		}
		
		
	}

}
