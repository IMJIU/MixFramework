package com.book.zkeeper;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class T11_ZkEvent_Callback implements Watcher {
	private static CountDownLatch countDownLatch = new CountDownLatch(1);
	public ZooKeeper zooKeeper;

	public static void main(String[] args) throws Exception {
		T11_ZkEvent_Callback t = new T11_ZkEvent_Callback();
		ZooKeeper zooKeeper = new ZooKeeper("localhost:2181", 5000, t);
		zooKeeper = t.zooKeeper;
	}

	public void process(WatchedEvent event) {
		// TODO Auto-generated method stub
		if (event.getState() == KeeperState.SyncConnected) {
			System.out.println("watcher received event");
			countDownLatch.countDown();
		}
		System.out.println("回调watcher1实例： 路径" + event.getPath() + " 类型：" + event.getType());
		// 事件类型，状态，和检测的路径
		EventType eventType = event.getType();
		KeeperState state = event.getState();
		String watchPath = event.getPath();
		switch (eventType) {
		case NodeCreated:
			break;
		case NodeDataChanged:
			break;
		case NodeChildrenChanged:
			try {
				// 处理收到的消息
				handleMessage(watchPath);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (KeeperException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	public void handleMessage(String watchPath) throws KeeperException, InterruptedException, UnsupportedEncodingException {
		System.out.println("收到消息");
		// 再监听该子节点
		List<String> Children = this.getChildren(watchPath);
		for (String a : Children) {
			String childrenpath = watchPath + "/" + a;
			byte[] recivedata = this.getData(childrenpath);
			String recString = new String(recivedata, "UTF-8");
			System.out.println("receive the path:" + childrenpath + ":data:" + recString);
			// 做完了之后，删除该节点
			this.deletNode(childrenpath, -1);
		}
	}

	private void deletNode(String childrenpath, int i) {
		// TODO Auto-generated method stub

	}

	private byte[] getData(String childrenpath) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getChildren(String path) throws KeeperException, InterruptedException {
		// 监听该节点子节点的变化情况
		return this.zooKeeper.getChildren(path, this);
	}

	public Stat setData(String path, byte[] data, int version) throws KeeperException, InterruptedException {
		return this.zooKeeper.setData(path, data, version);
	}
}
