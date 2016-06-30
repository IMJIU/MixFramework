package com.jdk.reference;

//: containers/References.java
// Demonstrates Reference objects
import java.lang.ref.*;
import java.util.*;

class VeryBig {

	private static final int SIZE = 10000;

	private long[] la = new long[SIZE];

	private String ident;

	public VeryBig(String id) {
		ident = id;
	}
	public String toString() {
		return ident;
	}
	protected void finalize() {
		System.out.println("Finalizing " + ident);
	}
}

public class References {

	private static ReferenceQueue<VeryBig> rq = new ReferenceQueue<VeryBig>();

	public static void checkQueue() {
		Reference<? extends VeryBig> inq = rq.poll();
		if (inq != null){
			System.out.println("In queue: " + inq.get());
		}
		else{
			System.out.println(inq);
		}
	}
	public static void main(String[] args) {
		int size = 10;
		// Or, choose size via the command line:
		if (args.length > 0)
			size = new Integer(args[0]);
		LinkedList<SoftReference<VeryBig>> sa = new LinkedList<SoftReference<VeryBig>>();
		for (int i = 0; i < size; i++) {
			sa.add(new SoftReference<VeryBig>(new VeryBig("Soft " + i), rq));
			System.out.println("Just created: " + sa.getLast());
			checkQueue();
		}
		System.out.println("=======================================");
		LinkedList<WeakReference<VeryBig>> wa = new LinkedList<WeakReference<VeryBig>>();
		for (int i = 0; i < size; i++) {
			wa.add(new WeakReference<VeryBig>(new VeryBig("Weak " + i), rq));
			System.out.println("Just created: " + wa.getLast());
			checkQueue();
		}
		SoftReference<VeryBig> s = new SoftReference<VeryBig>(new VeryBig("Soft"));
		WeakReference<VeryBig> w = new WeakReference<VeryBig>(new VeryBig("Weak"));
		System.out.println("=============== gc() =====================");
		System.gc();
		System.out.println("============= after gc =================");
		System.out.println(s.get());
		System.out.println(w.get());
		LinkedList<PhantomReference<VeryBig>> pa = new LinkedList<PhantomReference<VeryBig>>();
		for (int i = 0; i < size; i++) {
			pa.add(new PhantomReference<VeryBig>(new VeryBig("Phantom " + i), rq));
			System.out.println("Just created: " + pa.getLast());
			checkQueue();
		}
	}
} /* (Execute to see output) */// :~
/**
 * Just created: java.lang.ref.SoftReference@659e0bfd
null
Just created: java.lang.ref.SoftReference@2a139a55
null
Just created: java.lang.ref.SoftReference@15db9742
null
Just created: java.lang.ref.SoftReference@6d06d69c
null
Just created: java.lang.ref.SoftReference@7852e922
null
Just created: java.lang.ref.SoftReference@4e25154f
null
Just created: java.lang.ref.SoftReference@70dea4e
null
Just created: java.lang.ref.SoftReference@5c647e05
null
Just created: java.lang.ref.SoftReference@33909752
null
Just created: java.lang.ref.SoftReference@55f96302
null
Just created: java.lang.ref.WeakReference@3d4eac69
null
Just created: java.lang.ref.WeakReference@42a57993
null
Just created: java.lang.ref.WeakReference@75b84c92
null
Just created: java.lang.ref.WeakReference@6bc7c054
null
Just created: java.lang.ref.WeakReference@232204a1
null
Just created: java.lang.ref.WeakReference@4aa298b7
null
Just created: java.lang.ref.WeakReference@7d4991ad
null
Just created: java.lang.ref.WeakReference@28d93b30
null
Just created: java.lang.ref.WeakReference@1b6d3586
null
Just created: java.lang.ref.WeakReference@4554617c
null
=============== gc() =====================
Finalizing Weak
Finalizing Weak 9
Finalizing Weak 8
Finalizing Weak 7
Finalizing Weak 6
Finalizing Weak 5
Finalizing Weak 4
Finalizing Weak 3
Finalizing Weak 2
Finalizing Weak 1
Finalizing Weak 0
Just created: java.lang.ref.PhantomReference@74a14482
In queue: null
Just created: java.lang.ref.PhantomReference@1540e19d
In queue: null
Just created: java.lang.ref.PhantomReference@677327b6
In queue: null
Just created: java.lang.ref.PhantomReference@14ae5a5
In queue: null
Just created: java.lang.ref.PhantomReference@7f31245a
In queue: null
Just created: java.lang.ref.PhantomReference@6d6f6e28
In queue: null
Just created: java.lang.ref.PhantomReference@135fbaa4
In queue: null
Just created: java.lang.ref.PhantomReference@45ee12a7
In queue: null
Just created: java.lang.ref.PhantomReference@330bedb4
In queue: null
Just created: java.lang.ref.PhantomReference@2503dbd3
In queue: null
*/
