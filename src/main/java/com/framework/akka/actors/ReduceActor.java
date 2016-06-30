package com.framework.akka.actors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

import com.framework.akka.messages.MapData;
import com.framework.akka.messages.ReduceData;
import com.framework.akka.messages.WordCount;

public class ReduceActor extends UntypedActor {

	private ActorRef inAggregateActor = null;

	public ReduceActor(ActorRef inReduceActor) {
		inAggregateActor = inReduceActor;
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		System.out.println("reduce Receive:" + msg);
		if (msg instanceof MapData) {
			MapData mapdata = (MapData) msg;
			ReduceData reducedata = reduce(mapdata.getDataList());
			System.out.println("reduce send:" + reducedata.toString());
			inAggregateActor.tell(reducedata);
		} else {
			unhandled(msg);
		}

	}

	private ReduceData reduce(List<WordCount> datalist) {
		HashMap<String, Integer> reducemap = new HashMap<String, Integer>();
		for (WordCount wordCount : datalist) {
			if (reducemap.containsKey(wordCount.word)) {
				Integer value = reducemap.get(wordCount.word);
				value++;
				reducemap.put(wordCount.word, value);
			} else {
				reducemap.put(wordCount.word, 1);
			}
		}
		return new ReduceData(reducemap);
	}

}
