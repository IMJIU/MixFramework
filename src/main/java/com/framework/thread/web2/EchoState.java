package com.framework.thread.web2;

import java.io.IOException;

public abstract class EchoState {

	protected static EchoState _read = new ReadState();
	
	protected static EchoState _write = new WriteState();
	
	public abstract void process(NioEchoSession s) throws IOException;

	public abstract int interestOps();

	public static EchoState readState() {
		return _read;
	}

	public static EchoState writeState() {
		return _write;
	}

}
