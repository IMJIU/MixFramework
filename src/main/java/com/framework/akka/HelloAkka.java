package com.framework.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import com.framework.akka.actors.MasterActor;
import com.framework.akka.message.Result;


public class HelloAkka {
	public static void main(String[] args) throws Exception{
	    ActorSystem _sys = ActorSystem.create("hello");
	    ActorRef master = _sys.actorOf(new Props(MasterActor.class),"master");
	    
	    master.tell("first ~ hi i'm rock ! how do you do ?");
	    master.tell("Second ~ today");
	    master.tell("third ~ do you like ?");
	    
	    Thread.sleep(500);
	    
	    master.tell(new Result());
	    
	    Thread.sleep(500);
	    _sys.shutdown();
    }

}
