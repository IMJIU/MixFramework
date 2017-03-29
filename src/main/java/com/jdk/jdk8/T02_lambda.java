package com.jdk.jdk8;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class T02_lambda {
	public static void main(String[] args) {
		List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
		
//		Collections.sort(names, new Comparator<String>() {
//		    public int compare(String a, String b) {
//		        return b.compareTo(a);
//		    }
//		});
		
		//lambda
//		Collections.sort(names, (String a, String b) -> {
//		    return b.compareTo(a);
//		});
		
		//更短
		Collections.sort(names, (String a, String b) -> b.compareTo(a));
		Collections.sort(names, (a, b) -> b.compareTo(a));
		System.out.println(names);
    }

}
