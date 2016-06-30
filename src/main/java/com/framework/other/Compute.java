package com.framework.other;

import java.util.HashMap;
import java.util.Map;

public class Compute {

	public static void main(String[] args) {
		System.out.println(2.00 - 1.10);
		Map<String,Object>map = new HashMap<String,Object>();
		map.put("a",2);
		map.put("b",2);
		map.put("c",2);
		System.out.println(map);
		System.out.println(map.values());
	}

}
