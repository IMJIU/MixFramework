package com.framework.thread.web;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NioServerSession implements NioSession {

	protected ServerSocketChannel _channel;
	
	protected NioWorker _worker;

	public NioServerSession(ServerSocketChannel channel, NioWorker worker) {
		_channel = channel;
		_worker = worker;
	}

	public void registered(SelectionKey key) {}

	public void process() {
		try {
			SocketChannel c = _channel.accept();
			if (c != null) {
				c.configureBlocking(false);
				NioEchoSession s = new NioEchoSession(c);
				_worker.add(s);
			}
		} catch (IOException ex) {
			throw new Error(ex);
		}
	}

	public SelectableChannel channel() {
		return _channel;
	}

	public int interestOps() {
		return SelectionKey.OP_ACCEPT;
	}

}
