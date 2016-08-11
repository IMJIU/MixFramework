package com.framework.jedis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Test {
	/**
		 * 单机版测试
		 * <p>Title: testSpringJedisSingle</p>
		 * <p>Description: </p>
		 */
		@org.junit.Test
		public void testSpringJedisSingle() {
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("jedis.xml");
			JedisPool pool = (JedisPool) applicationContext.getBean("redisClient");
			Jedis jedis = pool.getResource();
			String string = jedis.get("key1");
			System.out.println(string);
			System.out.println("END");
			jedis.close();
			pool.close();
		}
	
}

