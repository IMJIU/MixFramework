package com.framework.nio;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

class HandleMessage implements Runnable {

	private SelectionKey selectionKey;

	private ByteBuffer byteBuffer;
	private Selector selector;

	public HandleMessage(SelectionKey selectionKey, ByteBuffer byteBuffer,Selector selector) {
		this.selectionKey = selectionKey;
		this.byteBuffer = byteBuffer;
		this.selector = selector;
	}

	@Override
	public void run() {
		EchoClient echoClient = (EchoClient) selectionKey.attachment();
		echoClient.enqueue(byteBuffer);

		// We've enqueued data to written to the client, we must not set interest in OP_WRITE
		selectionKey.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		// 强迫 selector 立即返回
		selector.wakeup();
	}
}
