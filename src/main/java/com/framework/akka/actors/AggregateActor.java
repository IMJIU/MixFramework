package com.framework.akka.actors;

import java.util.HashMap;
import java.util.Map;

import akka.actor.UntypedActor;

import com.framework.akka.message.Result;
import com.framework.akka.messages.ReduceData;

public class AggregateActor extends UntypedActor {

	private Map<String, Integer> finalReduceMap = new HashMap<>();

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof ReduceData) {
			ReduceData reduceData = (ReduceData) msg;
			aggregateInMemReduce(reduceData.getDataList());
		} else if (msg instanceof Result) {
			System.out.println(finalReduceMap.toString());
		} else {
			unhandled(msg);
		}

	}

	private void aggregateInMemReduce(Map<String, Integer> data) {
		Integer count = null;
		for (String key : data.keySet()) {
			if (finalReduceMap.containsKey(key)) {
				count = data.get(key) + finalReduceMap.get(key);
				finalReduceMap.put(key, count);
			} else {
				finalReduceMap.put(key, data.get(key));
			}
		}
	}

}
