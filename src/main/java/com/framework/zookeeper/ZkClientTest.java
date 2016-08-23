package com.framework.zookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

public class ZkClientTest {
	private ZkClient zkclient = null;
	public ZkClientTest(){
		this.zkclient = new ZkClient("192.168.99.210:2181",5000);
	}
	
	public void createPersistentNode(String path,Object data){
		zkclient.create(path, data, CreateMode.PERSISTENT);
	}
	
	public void exists(String path){
		zkclient.exists(path);
	}
	
	public static void main(String[] args) {
		ZkClientTest t = new ZkClientTest();
		System.out.println(t.equals("/test"));
	}

}
