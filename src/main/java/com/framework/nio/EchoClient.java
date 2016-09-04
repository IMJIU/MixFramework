package com.framework.nio;

import java.nio.ByteBuffer;
import java.util.LinkedList;

class EchoClient {

	private LinkedList<ByteBuffer> outq;

	public EchoClient() {
		outq = new LinkedList<>();
	}

	@Override
	public String toString() {
		return "EchoClient [outq=" + outq + "]";
	}

	public void enqueue(ByteBuffer bb) {
		outq.addFirst(bb);
	}

	public LinkedList<ByteBuffer> getOutputQueue() {
		return outq;
	}
}
