package com.test.dealfile;

import java.io.File;
import java.util.Map;

public abstract class DealObject {

	public abstract void task(File file, Map<String, String> param);
	

	public void doloop(File f1, Map<String, String> param) {
		if (f1.isDirectory()) {
			for (File f2 : f1.listFiles()) {
				doloop(f2,param);
			}
		} else {
			task(f1,param);
		}
	}
}
