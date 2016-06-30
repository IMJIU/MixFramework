package com.framework.thread.web;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class NioEchoServer implements Runnable {
	
	protected NioWorker _worker = null;

	public void run() {
		try {
			ServerSocketChannel serv = ServerSocketChannel.open();
			try {
				System.out.println("Server:localhost port:8080");
				serv.socket().bind(new InetSocketAddress("localhost", 8080));
				serv.configureBlocking(false);
				_worker = new NioWorker(Selector.open());
				NioServerSession s = new NioServerSession(serv, _worker);
				_worker.add(s);
				_worker.run();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				_worker.closeAllChannels();
				synchronized (this) {
					notify();
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new Error(ex);
		}
	}

	public synchronized void shutdown() {
		_worker.stop();
		try {
			wait();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			throw new Error(ex);
		}
	}

}
