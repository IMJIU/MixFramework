package com.framework.thread.web;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class ReadState extends EchoState {
	
	public void process(NioEchoSession s) throws IOException {
		SocketChannel channel = s._channel;
		ByteBuffer buf = s._buf;
		int count = channel.read(buf);
		if (count == 0) {
			return;
		}
		if (count == -1) {
			s.close();
			return;
		}
		buf.flip();
		System.out.println("len:"+count+" msg:"+new String(buf.array(),0,buf.limit()));
		s.setState(EchoState.writeState());
	}

	public int interestOps() {
		return SelectionKey.OP_READ;
	}
}
