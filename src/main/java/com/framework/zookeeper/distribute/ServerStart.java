package com.framework.zookeeper.distribute;


public class ServerStart {

	public static void main(String[] args) throws InterruptedException {
		MyObj obj = new MyObj();
		for(int i=0;i<10;i++){
			MyThread  m =  new MyThread(obj,i);
			Thread thread=new Thread(m);
			thread.start();
 
		}
	}

}
