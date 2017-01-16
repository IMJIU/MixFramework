package com.framework.zookeeper.t01_curator;

import java.util.List;
import java.util.prefs.NodeChangeEvent;
import java.util.prefs.NodeChangeListener;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

public class T01 {

	private CuratorFramework client;
	
	private static String ip = "localhost"; 

	public static void main(String[] args) {
		T01 t = null;
		try {
			t = new T01(null);
			// t.createNode("/curator/test/node1", "t1-data".getBytes());// /base/curator/test
			// t.addNodeDataWather("/curator/test/node1");
			t.deleteNode("/mytopic", 99);
			t.deleteNode("/admin", 99);
			t.deleteNode("/config", 99);
			t.deleteNode("/brokers", 99);
			t.deleteNode("/consumers", 99);
			t.deleteNode("/ensure_path", 99);
			t.deleteNode("/dubbo", 99);
			t.deleteNode("/t1", 99);
			t.deleteNode("/c1", 99);
			t.deleteNode("/controller_epoch", 99);
			t.deleteNode("/master_path", 99);
			// t.readNode("/curator/test");
			// t.updateNode("/curator/test/node1", "zzzz".getBytes(), -1);
			// t.addChildWather("/curator/test");
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			t.closeClient();
		}
	}

	public T01(String base) {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		if (base != null) {
			client = CuratorFrameworkFactory.builder().connectString(ip+":2181").sessionTimeoutMs(1000).retryPolicy(retryPolicy).namespace(base).build();
		} else {
			client = CuratorFrameworkFactory.builder().connectString(ip+":2181").sessionTimeoutMs(1000).retryPolicy(retryPolicy).build();
		}
		client.start();
	}

	public void closeClient() {
		if (client != null) {
			this.client.close();
		}
	}

	public void createNode(String path, byte[] data) throws Exception {
		client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).withACL(Ids.OPEN_ACL_UNSAFE).forPath(path, data);
	}
	public void readNode(String path) throws Exception {
		Stat stat = new Stat();
		byte[] data = client.getData().storingStatIn(stat).forPath(path);
		System.out.println("read:" + path + " data:" + new String(data));
		System.out.println(stat.toString());
	}
	public void addNodeDataWather(String path) throws Exception {
		final NodeCache node = new NodeCache(client, path);
		node.start(true);
		node.getListenable().addListener(new NodeCacheListener() {

			public void nodeChanged() throws Exception {
				String data = new String(node.getCurrentData().getData());
				System.out.println("path=" + node.getCurrentData().getPath() + " data:" + data);
			}
		});
	}
	public void addChildWather(String path) throws Exception {
		final PathChildrenCache node = new PathChildrenCache(client, path, true);
		node.start(StartMode.BUILD_INITIAL_CACHE);
		// node.start(StartMode.NORMAL);
		System.out.println(node.getCurrentData().size());
		node.getListenable().addListener(new PathChildrenCacheListener() {

			public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
				switch( event.getType() )
				{
					case INITIALIZED:
						System.out.println("init finish!");
						break;
					case CHILD_ADDED:
						System.out.println("add:" + event.getData().getPath());
						break;
					case CHILD_REMOVED:
						System.out.println("remove:" + event.getData().getPath());
						break;
					case CHILD_UPDATED:
						System.out.println("update:" + event.getData().getPath());
						System.out.println("update new data:" + new String(event.getData().getData()));
						break;
				}
			}
		});
	}
	public void updateNode(String path, byte[] data, int version) throws Exception {
		client.setData().withVersion(version).forPath(path, data);
	}
	public void getChildren(String path, byte[] data, int version) throws Exception {
		// List<String>children = client.getChildren().forPath("/curator");
		// 增加watch
		List<String> children = client.getChildren().usingWatcher(new CuratorWatcher() {

			public void process(WatchedEvent event) throws Exception {
				System.out.println("type:" + event.getType() + " path:" + event.getPath());
			}
		}).forPath("/curator");
		for (String string : children) {
			System.out.println("child:" + string);
		}
	}
	public void deleteNode(String path, int version) throws Exception {
		// 递归删除
		client.delete().guaranteed().deletingChildrenIfNeeded().inBackground(new DeleteCallBack()).forPath(path);
		// 不递归
		// client.delete().guaranteed().withVersion(version).inBackground(new DeleteCallBack()).forPath(path);
	}

}

class DeleteCallBack implements BackgroundCallback {

	@Override
	public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(event.getPath() + ",data=" + event.getData());
		System.out.println("type:" + event.getType());
		System.out.println("code:" + event.getResultCode());
		// org.apache.zookeeper.KeeperException.Code
	}

}
