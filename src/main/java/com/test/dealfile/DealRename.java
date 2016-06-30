package com.test.dealfile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class DealRename extends DealObject {

	public static void main(String[] args) throws Exception{
//		System.out.println("Oracle Pro/C 编程系列 - 1.zip".replace('/', '_'));
		DealRename d= new DealRename();
		Map<String,String>map = d.read("g:\\book_name.sql");
		System.out.println(map);
		d.rename(new File("g://book"), map);
	}

	public void task(File f1, Map<String, String> map) {
		String fname = f1.getName().replace(".zip", "");
		if (map.containsKey(fname)) {
			String val = map.get(fname).replaceAll("/", "_");
			String newName = f1.getAbsolutePath().replaceAll(fname, fname + " - " + val);
			//boolean ret = f1.renameTo(new File(newName));
			//System.out.print(ret + "\t");
			System.out.println(" rename to " + newName);
		}
	}
	public Map<String,String> read(String path) throws Exception{
		File f = new File(path);
		Map<String,String>map = new HashMap<String,String>();
		BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		String str=null;
		while ((str = r.readLine()) != null) {
			String[] arr = str.split("-",2);
			if(arr.length>1){
				if(arr[0].startsWith("[bbk"))
				map.put(arr[0].replaceAll("\\[","").replaceAll("\\]", "").trim(),arr[1].trim());
			}
		}
		return map;
	}
	public void rename(File f1, Map<String,String>map) {
			for (File f2 : f1.listFiles()) {
				if (f2.isDirectory()) {
				} else {
					String fname = f2.getName().replaceAll("\\.zip", "");
//					System.out.println(fname);
					if(map.containsKey(fname)){
//						System.out.println(f2.getName());
//						System.out.println(map.get(fname));
						String rname = f2.getParent()+"\\"+map.get(fname)+"-"+fname+".zip";
						System.out.println("rename:"+rname);
						System.out.println(f2.renameTo(new File(rname)));
					}
				}
			}
	}


	
	


}
