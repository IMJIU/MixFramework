package com.framework.zookeeper.t02_config.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;


public class ServiceConsumer {
	private String connectString ="localhost:2181,localhost:2182,localhost:2183";
	private CuratorFramework client = null;
	Map<String,Set<String>> services = new HashMap<String,Set<String>>();
	Map<String,Set<String>> servicesByP = new HashMap<String,Set<String>>();
	public ServiceConsumer(){
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		client = CuratorFrameworkFactory.builder()
				.connectString(connectString)
				.sessionTimeoutMs(10000).
				retryPolicy(retryPolicy)
				.namespace("webServiceCenter").build();
		client.start();		
//		try {
//			List<String> children = client.getChildren().forPath("/");
//			System.out.println("-----"+children.size());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}

	public void addChildWatcher(String path) throws Exception {
		final PathChildrenCache cache = new PathChildrenCache(this.client,
				path, true);
		cache.start(StartMode.POST_INITIALIZED_EVENT);//ppt中需要讲StartMode
		//System.out.println(cache.getCurrentData().size());
		cache.getListenable().addListener(new PathChildrenCacheListener() {
			public void childEvent(CuratorFramework client,
					PathChildrenCacheEvent event) throws Exception {
				if(event.getType().equals(PathChildrenCacheEvent.Type.INITIALIZED)){
					System.out.println("客户端子节点cache初始化数据完成");
					//System.out.println("size="+cache.getCurrentData().size());
				}else if(event.getType().equals(PathChildrenCacheEvent.Type.CHILD_ADDED)){
					//System.out.println("添加子节点:"+event.getData().getPath());
					//System.out.println("修改子节点数据:"+new String(event.getData().getData()));
					updateLocalService(event.getData().getPath(),0);
				}else if(event.getType().equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)){
					System.out.println("删除子节点:"+event.getData().getPath());
					//if(services.containsKey(event.getData().getPath()))
					//servicesByP.containsKey(event.getData().)
					updateLocalService(event.getData().getPath(),1);
				}else if(event.getType().equals(PathChildrenCacheEvent.Type.CHILD_UPDATED)){
					//System.out.println("修改子节点数据:"+event.getData().getPath());
					//System.out.println("修改子节点数据:"+new String(event.getData().getData()));
				}
			}
		});
	}	
	private void updateLocalService(String path,int dOrAdd){
		//userCenter/tcdd.test.do/ip
		if(path.endsWith("/"))
			path = path.substring(0, path.length()-2);
		String nodes[] = path.split("/");
		String serviceName = nodes[nodes.length-2];
		String providerAdd = nodes[nodes.length-1];
		if(serviceName.endsWith(".do")){
			serviceName = serviceName.replace(".", "/");
			serviceName = serviceName.replace("/do", ".do");
			if(!serviceName.startsWith("/"))
				serviceName="/"+serviceName;
		}else{
			serviceName = serviceName.replace(".", "/");
		}		
		if(dOrAdd==0){
			//创建节点
			if(!services.containsKey(serviceName)){
				services.put(serviceName, new HashSet<String>());
			}
			services.get(serviceName).add(providerAdd);
			
			if(!servicesByP.containsKey(providerAdd)){
				servicesByP.put(providerAdd, new HashSet<String>());
			}
			servicesByP.get(providerAdd).add(serviceName);
			
		}else if(dOrAdd==1){
			//删除节点
			services.get(serviceName).remove(providerAdd);
			servicesByP.remove(providerAdd);
		}

		printService();
		
	}
	
	private void printService(){
		Set<String> srs = services.keySet();
		for(String ss : srs){
			System.out.println("service list-------"+ss);
			Set<String> vals = services.get(ss);
			for(String val : vals){
				System.out.println("     --------"+val);
			}
		}
		Set<String> ps = servicesByP.keySet();
		for(String ss : ps){
			System.out.println("provider list-------"+ss);
			Set<String> vals = servicesByP.get(ss);
			for(String val : vals){
				System.out.println("     ----------"+val);
			}
		}		
	}
	public Map<String,Set<String>> getServices(String bizCode){
		try {
			List<String> children = client.getChildren().forPath("/"+bizCode);
			System.out.println("-----------services----------");
			for(String bizCh : children){
				String servicepath = bizCh;
				addChildWatcher("/"+bizCode+"/"+bizCh);
				//System.out.println("-------bizCh-----------"+bizCh);
				if(servicepath.endsWith(".do")){
					servicepath = servicepath.replace(".", "/");
					servicepath = servicepath.replace("/do", ".do");
					if(!servicepath.startsWith("/"))
						servicepath="/"+servicepath;
				}else{
					servicepath = servicepath.replace(".", "/");
				}
				if(!services.containsKey(servicepath))
					services.put(servicepath, new HashSet<String>());
				//System.out.println("------------------"+servicepath);

				List<String> providers = client.getChildren().forPath(bizCode+"/"+bizCh);
				for(String p : providers){
					//System.out.println("------------------------"+p);
					services.get(servicepath).add(p);
					if(!servicesByP.containsKey(p)){
						servicesByP.put(p, new HashSet<String>());
					}
					servicesByP.get(p).add(servicepath);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		printService();
		return services;
	}
	
	private void accessService(){
		String spath = null;
		Set<String> srs = services.keySet();
		List<String> srsadds =null;
		for(String sr :srs){
			if(services.get(sr)!=null && services.get(sr).size()>0){
				srsadds = new ArrayList<String>();
				srsadds.addAll(services.get(sr));
				Collections.shuffle(srsadds);
				spath ="http://"+srsadds.get(0)+":8081/configManagement"+sr;
				System.out.println("access path="+spath);
				String res ="";
				try {
					res = HttpClientUtil.getMethod(spath);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("res="+res);
			}else{
				System.out.println("access path "+sr+",没有服务提供者");
			}
		}
	}
	
	   	
	public static void main(String[] args) throws InterruptedException {
		ServiceConsumer sc = new ServiceConsumer();
		sc.getServices("userCenter");
		for(int i=0;i<100;i++){
			sc.accessService();
			Thread.sleep(10000);
		}	
		Thread.sleep(1000000);
	}

}
