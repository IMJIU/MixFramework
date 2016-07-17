package com.framework.zookeeper.t03_distribute_lock.service;

import com.framework.zookeeper.t03_distribute_lock.pojo.Order;


public interface OrderService {
	boolean doOrder(Order o);
}
