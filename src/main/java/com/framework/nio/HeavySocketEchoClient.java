package com.framework.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 模拟非常延迟的一个客户端
 *
 * @author jiayeee
 *
 * 		上午11:06:40
 */
public class HeavySocketEchoClient {

	private static Socket clientSocket;

	private static int port = 8080;

	private static long nanos = TimeUnit.SECONDS.toNanos(1);

	public static void main(String[] args) {

		PrintWriter writer = null;
		BufferedReader reader = null;
		try {
			clientSocket = new Socket();
			clientSocket.connect(new InetSocketAddress("localhost", port));

			writer = new PrintWriter(clientSocket.getOutputStream(), true);

			writer.print("H");
			writer.flush();
			LockSupport.parkNanos(nanos);

			writer.print("e");
			writer.flush();
			LockSupport.parkNanos(nanos);

			writer.print("l");
			writer.flush();
			LockSupport.parkNanos(nanos);

			writer.print("l");
			writer.flush();
			LockSupport.parkNanos(nanos);

			writer.print("o");
			writer.flush();
			LockSupport.parkNanos(nanos);

			writer.print(" !!");
			writer.flush();
			LockSupport.parkNanos(nanos);

			writer.println();
			writer.flush();
			LockSupport.parkNanos(nanos);

			reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			System.out.println("from server : " + reader.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (writer != null) {
				writer.close();
			}
			if (clientSocket != null) {
				try {
					clientSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
