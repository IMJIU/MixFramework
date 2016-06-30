package com.framework.thread.web;

public class ServerMain {
	
	public static void main(String[] args) {
		new NioEchoServer().run();
	}
}
