package com.framework.zookeeper.t03_distribute_lock.service;

import com.framework.zookeeper.t03_distribute_lock.pojo.Order;


public interface OrderRepository {
	void saveOrder(Order order);
}
