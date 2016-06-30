package com.book.zkeeper;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class T08_getData_Async implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    private static ZooKeeper zookeeper = null;

    public static void main(String[] args) throws Exception {
        String path = "/zk-book4";
        zookeeper = new ZooKeeper("localhost:2181", 5000, new T08_getData_Async());
        System.out.println(zookeeper.getState());
        connectedSemaphore.await();

        System.out.println("zookeeper session established!");
        zookeeper.create(path, "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        zookeeper.getData(path, true, (rc, p, ctx, data, stat) -> {
            System.out.println(rc + "," + p + "," + new String(data));
            System.out.println(stat.getCzxid() + "," + stat.getMzxid() + "," + stat.getVersion());
        }, null);
        zookeeper.setData(path, "123".getBytes(), -1);

        Thread.sleep(Integer.MAX_VALUE);

    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("receive watche event:" + event);

        if (KeeperState.SyncConnected == event.getState()) {
            if (EventType.None == event.getType() && null == event.getPath()) {
                connectedSemaphore.countDown();
            } else if (event.getType() == EventType.NodeDataChanged) {
                try {
                    zookeeper.getData(event.getPath(), true, (rc, path, ctx, data, stat) -> {
                        System.out.println(rc + "," + path + "," + new String(data));
                        System.out.println(stat.getCzxid() + "," + stat.getMzxid() + "," + stat.getVersion());
                    }, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

