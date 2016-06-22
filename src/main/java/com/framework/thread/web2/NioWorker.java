package com.framework.thread.web2;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class NioWorker implements Runnable {

	protected Selector _sel = null;
	protected Collection _added = null;
	protected volatile boolean _run = true;

	public NioWorker(Selector sel) {
		_sel = sel;
		_added = new HashSet();
	}

	public void run() {
		try {
			try {
				System.out.println("coming run!!!!");
				while (_run) {
					_sel.select(30000);
					System.out.print("Done!select()! ");
					Set selected = _sel.selectedKeys();
					System.out.println("selectedKeys:"+selected.size());
					for (Iterator itr = selected.iterator(); itr.hasNext();) {
						SelectionKey key = (SelectionKey) itr.next();
						NioSession s = (NioSession) key.attachment();
						System.out.println(s.getClass().getCanonicalName());
						s.process();
						itr.remove();
					}
					System.out.println("_addedSize:"+_added.size());
					synchronized (_added) {
						for (Iterator itr = _added.iterator(); itr.hasNext();) {
							NioSession s = (NioSession) itr.next();
							System.out.println(s.getClass().getCanonicalName());
							SelectionKey key = s.channel().register(_sel, s.interestOps(), s);
							s.registered(key);
							itr.remove();
						}
					}
				}
				System.out.println("end run!!!!");
			}catch (Exception e) {
				e.printStackTrace();
			} finally {
				_sel.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void add(NioSession s) {
		synchronized (_added) {
			_added.add(s);
		}
		_sel.wakeup();
	}

	public synchronized void stop() {
		_run = false;
		_sel.wakeup();
	}

	public void closeAllChannels() {
		for (Iterator itr = _sel.keys().iterator(); itr.hasNext();) {
			SelectionKey key = (SelectionKey) itr.next();
			try {
				key.channel().close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
