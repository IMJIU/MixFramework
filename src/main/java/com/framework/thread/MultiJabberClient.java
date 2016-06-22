package com.framework.thread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;
@SuppressWarnings({"unchecked","unused","rawtypes"})
public class MultiJabberClient {
	static final int MAX_THREADS = 40;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		InetAddress addr = InetAddress.getByName(null);
		for (int i = 0; i < 10; i++) {
//			if (JabberClientThread.threadCount() < MAX_THREADS)
				new JabberClientThread(addr);
//			Thread.currentThread().sleep(100);
		}
	}
} // /:~

class JabberClientThread extends Thread {
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private static int counter = 0;
	private int id = counter++;
	private static int threadcount = 0;

	public static int threadCount() {
		return threadcount;
	}

	public JabberClientThread(InetAddress addr) {
		System.out.println("Making【 client " + id+"】");
		threadcount++;
		try {
			socket = new Socket(addr, 8080);
		} catch (IOException e) {
			System.err.println("Socket failed");
			// If the creation of the socket fails,
			// nothing needs to be cleaned up.
		}
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// Enable auto-flush:
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
			start();
		} catch (IOException e) {
			e.printStackTrace();
			// The socket should be closed on any
			// failures other than the socket
			// constructor:
			try {
				socket.close();
			} catch (IOException e2) {
				System.err.println("Socket not closed");
			}
		}
		// Otherwise the socket will be closed by
		// the run() method of the thread.
	}

	public void run() {
		try {
			out.println("I'm Coming!");
//			out.flush();
//			String str = in.readLine();
//			System.out.println(str);
			Double t = Math.random()*3000;
			System.out.println("【 client " + id+"】doing heave thing!!!!!"+t.longValue());
			Thread.currentThread().sleep(t.longValue());
			out.println("End【 client " + id+"】");
			System.out.println("End【 client " + id+"】");
		}  catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				System.err.println("Socket not closed");
			}
			threadcount--; // Ending this thread
		}
	}
}