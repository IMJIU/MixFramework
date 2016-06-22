package com.test.dealfile;

import java.io.File;

public class DealFactory {

	public static void main(String[] args) {
		String className = "com.self.RenameDeal2";
		String pathname = "";
		DealObject dealObj = null;
		try {
			dealObj = (DealObject) (Class.forName(className).newInstance());
			dealObj.doloop(new File(pathname),null);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
