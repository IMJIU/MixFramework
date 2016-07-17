package com.framework.zookeeper.t03_distribute_lock.service;

import java.util.HashMap;

import com.framework.zookeeper.t03_distribute_lock.pojo.Product;


public interface ProductRepository {
	Product selectProductById(Long id);
	void reduceNum(HashMap<String,Integer> hm);
}
