package com.framework.thread;

//: TIEJ:X1:MultiJabberServer1.java
//Has the same semantics as multi-threaded 
//MultiJabberServer
//{RunByHand}
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * The Server accepts connections in non-blocking fashion. A connection when
 * established, a socket is created, which is registered for read/write with the
 * selector. Reading/Writing is performed on this socket when the selector
 * unblocks. This program works exactly the same way as MultiJabberServer.
 */
@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
public class MultiJabberServer1 {
	public static final int PORT = 8080;
	public static ExecutorService _threadPool = Executors.newFixedThreadPool(10);

	public static void main(String[] args) throws Exception {
		 doStart();
	}
	private static void doStart() throws IOException, ClosedChannelException {
		SocketChannel ch = null;
		ServerSocketChannel ssc = ServerSocketChannel.open();
		Selector sel = Selector.open();
		try {
			ssc.configureBlocking(false);
			ssc.socket().bind(new InetSocketAddress(PORT));
			SelectionKey key = ssc.register(sel, SelectionKey.OP_ACCEPT);
			System.out.println("Server on port: " + PORT);
			while (true) {
				sel.select(30000);
				System.out.println("Done select()!");
				System.out.println(_threadPool.toString());
				Iterator it = sel.selectedKeys().iterator();
				while (it.hasNext()) {
					SelectionKey skey = (SelectionKey) it.next();
					it.remove();
					if (skey.isAcceptable()) {
						ch = ssc.accept();
						System.out.println("Accepted connection from:" + ch.socket());
						ch.configureBlocking(false);
//						_threadPool.execute(new Worker(ch, _threadPool));
					}
				}
			}
		} finally {
			if (ch != null)
				ch.close();
			ssc.close();
			sel.close();
		}
	}
} // /:~
