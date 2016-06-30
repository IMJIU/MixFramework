package com.jdk.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.LinkedList;


public class T01 {
	class Person{String name;}
	public static void main(String[] args) {
		ReferenceQueue<WeakReference<Person>>queue = new ReferenceQueue<>();
//		LinkedList<SoftReference<Person>>
		System.out.println(0%7);
    }

}
