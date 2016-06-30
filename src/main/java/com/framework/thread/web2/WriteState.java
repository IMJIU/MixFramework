package com.framework.thread.web2;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
public class WriteState extends EchoState {
	public void process(NioEchoSession s)
	throws IOException
	{
		SocketChannel channel = s._channel;
		ByteBuffer buf = s._buf;
		buf.flip();
//		System.out.println(new String(buf.array(),0,buf.limit()));
//		channel.write(buf);
		channel.write(ByteBuffer.wrap("hello".getBytes()));
		if (buf.remaining() == 0) {
			buf.clear();
			s.setState(EchoState.readState());
		}
	}
	public int interestOps() {
		return SelectionKey.OP_WRITE;
	}
}
