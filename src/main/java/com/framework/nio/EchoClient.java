package com.framework.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class EchoClient {

	private static Socket clientSocket;

	private static int port = 8080;

	public static void main(String[] args) {

		PrintWriter writer = null;
		BufferedReader reader = null;
		try {
			clientSocket = new Socket();
			clientSocket.connect(new InetSocketAddress("localhost", port));

			writer = new PrintWriter(clientSocket.getOutputStream(), true);
			writer.println("hello !");

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
