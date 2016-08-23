package com.framework.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadEchoServer {

	static class HandleMsg implements Runnable {

		private Socket socket;

		public HandleMsg(Socket clientSocket) {
			this.socket = clientSocket;
		}

		@Override
		public void run() {

			BufferedReader reader = null;
			PrintWriter writer = null;
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new PrintWriter(socket.getOutputStream(), true);

				long s = System.currentTimeMillis();

				String line = null;
				while ((line = reader.readLine()) != null) {
					writer.println(line);
				}

				long e = System.currentTimeMillis();

				System.out.println("spend " + (e - s) + " ms !");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (writer != null) {
					writer.close();
				}
				if (writer != null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private static ExecutorService tp = Executors.newFixedThreadPool(2);

	private static ServerSocket serverSocket;

	private static Socket clientSocket;

	private static int port = 8080;

	public static void main(String[] args) {

		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			try {
				clientSocket = serverSocket.accept();
				System.out.println(clientSocket.getRemoteSocketAddress() + " connected !");

				tp.execute(new HandleMsg(clientSocket));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
