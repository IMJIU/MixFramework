package com.framework.thread.web;

import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;


public interface NioSession {
	
	public SelectableChannel channel();

	public int interestOps();

	public void registered(SelectionKey key);

	public void process();
}
