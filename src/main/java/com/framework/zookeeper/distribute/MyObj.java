package com.framework.zookeeper.distribute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.KeeperException;

public class MyObj {

	List<Integer> l = new ArrayList<Integer>();
	
	Integer i = 0;
	
	public int getNext() throws IOException, KeeperException, InterruptedException{
//		 ZLock lock =  new ZLock();
//		   boolean flag  = lock.Lock("13520185953");
//		   
//		   for(int i = 0;i<3&&flag==false;i++){
//	 			 try {
//					Thread.sleep(10000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//	 			  if(lock.Lock("13520185953")){
//	 				  break;
//	 			  };
//	 		  }
		   
//		 try {
//			Thread.sleep(1000);
//		    } catch (InterruptedException e){
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		    }
			i = i +1;
			System.out.println(i+":locked");  
//			lock.unlock();
		return i;
	}
}
