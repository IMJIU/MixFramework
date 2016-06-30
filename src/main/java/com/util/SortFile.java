package com.util;

import java.io.File;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public class SortFile {

	TreeMap<Long, File> map = new TreeMap<Long, File>();

	public void dealFile(File f1) {
		if (f1.isDirectory()) {
			for (File f2 : f1.listFiles()) {
				if (f2.isDirectory()) {
					dealFile(f2);
				} else {
					if (f2.length() / 1024 / 1024 > 10)
						map.put(f2.lastModified(), f2);
				}
			}
		} else {
			if (f1.length() / 1024 / 1024 > 10)
				map.put(f1.lastModified(), f1);
		}
	}

	public void print() {
		Set<Long> keys = map.keySet();
		for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
			Long k = (Long) iterator.next();
			File f = map.get(k);
			// System.out.println(f.length() / 1024 / 1024 + "\t" +
			// f.getAbsolutePath());
			System.out.println(f.getAbsolutePath());
		}
	}

	public static void main(String[] args) {
		SortFile s = new SortFile();
		s.dealFile(new File("I:\\C盘\\a\\个人集"));
		s.print();
	}

}
