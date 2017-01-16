package com.framework.zookeeper.t04_master;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;

import org.apache.curator.retry.ExponentialBackoffRetry;

public class Recipes_MastrSelect {
	static String master_path= "/master_path";
	static CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181")
			.retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
	static AtomicInteger num = new AtomicInteger(0);
	static SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
	public static void main(String[] args) throws InterruptedException {
		double n = Math.floor(Math.random()*100);
		client.start();
		LeaderSelector selector = new LeaderSelector(client, master_path, new LeaderSelectorListenerAdapter() {
			@Override
			public void takeLeadership(CuratorFramework client) throws Exception {
				System.out.println(" become master!num:"+n+" T:"+df.format(new Date()));
				Thread.sleep(3000);
				System.out.println("complete master operator! lease master");
			}
			
		});
		selector.autoRequeue();
		selector.start();
		Thread.sleep(Integer.MAX_VALUE);
	}
}
