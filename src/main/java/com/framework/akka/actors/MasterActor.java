package com.framework.akka.actors;

import com.framework.akka.message.Result;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;

public class MasterActor extends UntypedActor {

	private ActorRef aggregateActor = getContext().actorOf(new Props(AggregateActor.class), "aggregate");

	private ActorRef reduceActor = getContext().actorOf(new Props(new UntypedActorFactory() {

		public UntypedActor create() {
			return new ReduceActor(aggregateActor);
		};
	}), "reduce");

	private ActorRef mapActor = getContext().actorOf(new Props(new UntypedActorFactory() {

		public UntypedActor create() {
			return new MapActor(reduceActor);
		};
	}), "map");

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof String) {
			mapActor.tell(msg);
		} else if (msg instanceof Result) {
			aggregateActor.tell(msg);
		} else
			unhandled(msg);
	}

}
