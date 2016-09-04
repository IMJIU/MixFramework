package com.framework.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 
 *
 * @author jiayeee
 *
 * 		上午11:13:43
 */
public class MultiThreadNioEchoServer {

	private static Map<Socket, Long> geym_time_stat = new HashMap<>(1024);

	private Selector selector;

	private Executor tp = Executors.newCachedThreadPool();

	
	private void startServer() throws IOException {
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.socket().bind(new InetSocketAddress(8080));

		selector = Selector.open();
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

		while (true) {
			selector.select();

			for (SelectionKey selectionKey : selector.selectedKeys()) {
				selector.selectedKeys().remove(selectionKey);
				if (selectionKey.isAcceptable()) {
					doAccept(selectionKey);
				} else if (selectionKey.isValid() && selectionKey.isReadable()) {
					Socket clientSocket = ((SocketChannel) selectionKey.channel()).socket();
					if (!geym_time_stat.containsKey(clientSocket)) {
						geym_time_stat.put(clientSocket, System.currentTimeMillis());
					}

					doRead(selectionKey);
				} else if (selectionKey.isValid() && selectionKey.isWritable()) {
					doWrite(selectionKey);

					Socket clientSocket = ((SocketChannel) selectionKey.channel()).socket();
					long s = geym_time_stat.remove(clientSocket);
					long e = System.currentTimeMillis();
					System.out.println("spend " + (e - s) + " ms !");
				}
			}
		}
	}

	/**
	 * Called when a SelectionKey is ready for writing
	 * 
	 * @param selectionKey
	 */
	private void doWrite(SelectionKey selectionKey) {
		SocketChannel channel = (SocketChannel) selectionKey.channel();
		EchoClient client = (EchoClient) selectionKey.attachment();
		LinkedList<ByteBuffer> outq = client.getOutputQueue();

		ByteBuffer bb = outq.getLast();
		try {
			int len = channel.write(bb);
			if (len == -1) {
				disconnect(selectionKey);
				return;
			}

			if (bb.remaining() == 0) {
				// The buffer was completely written, remove it
				outq.removeLast();
			}
		} catch (IOException e) {
			System.out.println("Failed to write to client !");
			e.printStackTrace();
			disconnect(selectionKey);
		}

		// If there is no more data to be written, remove interest in OP_WRITE
		if (outq.size() == 0) {
			selectionKey.interestOps(SelectionKey.OP_READ);
		}
	}

	/**
	 * Read from a client, Enqueue the data on the clients output queue and set the selector to notify on OP_WRITE
	 * 
	 * @param selectionKey
	 */
	private void doRead(SelectionKey selectionKey) {
		SocketChannel channel = (SocketChannel) selectionKey.channel();
		ByteBuffer byteBuffer = ByteBuffer.allocate(8192);
		int len = 0;

		try {
			len = channel.read(byteBuffer);
			if (len < 0) {
				disconnect(selectionKey);
				return;
			}
		} catch (IOException e) {
			System.out.println("Failed to read from client !");
			e.printStackTrace();
			disconnect(selectionKey);
			return;
		}

		byteBuffer.flip();
		tp.execute(new HandleMessage(selectionKey, byteBuffer,selector));
	}

	private void disconnect(SelectionKey selectionKey) {
		try {
			selectionKey.cancel();
			SocketChannel channel = (SocketChannel) selectionKey.channel();
			channel.finishConnect();
		} catch (IOException e) {
			System.out.println("Failed to close the channel !");
			e.printStackTrace();
		}
	}

	/**
	 * Accept a new client and set it up for reading.
	 * 
	 * @param selectionKey
	 */
	private void doAccept(SelectionKey selectionKey) {
		try {
			ServerSocketChannel serverChannel = (ServerSocketChannel) selectionKey.channel();
			SocketChannel clientChannel = serverChannel.accept();
			clientChannel.configureBlocking(false);

			SelectionKey clientKey = clientChannel.register(selector, SelectionKey.OP_READ);

			// 添加附件
			EchoClient echoClient = new EchoClient();
			clientKey.attach(echoClient);

			System.out.println(clientChannel.socket().getRemoteSocketAddress() + " connected !");
		} catch (Exception e) {
			System.out.println("Failed to accept new client !");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		MultiThreadNioEchoServer server = new MultiThreadNioEchoServer();
		server.startServer();
	}
}
