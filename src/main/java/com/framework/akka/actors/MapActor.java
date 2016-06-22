package com.framework.akka.actors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

import com.framework.akka.messages.MapData;
import com.framework.akka.messages.WordCount;

public class MapActor extends UntypedActor {

	private ActorRef reduceActor = null;

	String[] STOP_WORDS = { "a", "is" };

	private List<String> STOP_WORDS_LIST = Arrays.asList(STOP_WORDS);

	public MapActor(ActorRef actor) {
		reduceActor = actor;
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		System.out.println("map receive:" + msg);
		if (msg instanceof String) {
			String work = (String) msg;
			MapData data = evaluateExpression(work);
			System.out.println("map send:" + data);
			reduceActor.tell(data);
		} else {
			unhandled(msg);
		}

	}

	private MapData evaluateExpression(String line) {
		List<WordCount> datalist = new ArrayList<>();
		StringTokenizer st = new StringTokenizer(line);
		while (st.hasMoreTokens()) {
			String word = st.nextToken().toLowerCase();
			if (!STOP_WORDS_LIST.contains(word)) {
				datalist.add(new WordCount(word, Integer.valueOf(1)));
			}
		}
		return new MapData(datalist);
	}

}
