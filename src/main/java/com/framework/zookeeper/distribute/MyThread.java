package com.framework.zookeeper.distribute;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;

public class MyThread implements Runnable{
	private MyObj obj = null;
	private int i;
	public MyThread(MyObj obj,int i){
		this.obj = obj;
		this.i = i;
	}
	@Override
	public void run() {
//		System.out.println(Thread.currentThread().getName()+"   ===  start ");
		try {
//			 ZLock lock =  new ZLock();
//			   boolean flag  = lock.Lock("13520185953");
//			   
//			   for(int i = 0;i<3&&flag==false;i++){
//		 			 try {
//						Thread.sleep(10000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//		 			  if(lock.Lock("13520185953")){
//		 				  break;
//		 			  };
//		 		  }
			obj.getNext();
			//lock.unlock();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}