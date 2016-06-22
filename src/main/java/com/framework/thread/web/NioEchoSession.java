package com.framework.thread.web;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class NioEchoSession implements NioSession {
	
	protected SocketChannel _channel = null;
	protected SelectionKey _key;
	protected ByteBuffer _buf = null;
	protected EchoState _state = null;

	public NioEchoSession(SocketChannel c) {
		_channel = c;
		_buf = ByteBuffer.allocate(128);
		_state = EchoState.readState();
	}

	public void registered(SelectionKey key) {
		_key = key;
	}

	public void process() {
		try {
			_state.process(this);
		} catch (IOException ex) {
			ex.printStackTrace();
			close();
			throw new Error(ex);
		}
	}

	public SelectableChannel channel() {
		return _channel;
	}

	public int interestOps() {
		return _state.interestOps();
	}

	public void setState(EchoState state) {
		_state = state;
		_key.interestOps(interestOps());
	}

	public void close() {
		try {
			_channel.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new Error(ex);
		}
	}

}
