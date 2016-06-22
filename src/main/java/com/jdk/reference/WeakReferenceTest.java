package com.jdk.reference;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
class A{
	String str=null;
}
public class WeakReferenceTest {   
	
  
    /**  
     * @param args  
     */  
    public static void main(String[] args) {   
    	weak_test();   
    	System.out.println("=============================");
        solf_test();
    }

	private static void weak_test() {
	    A a = new A();   
        a.str = "Hello, reference";   
        WeakReference<A> weak = new WeakReference<A>(a);   
        a = null;   
        int i = 0;   
        while (weak.get() != null) {   
            System.out.println(String.format("Get str from object of WeakReference: %s, count: %d", weak.get().str, ++i));   
            if (i % 10 == 0) {   
                System.gc();   
                System.out.println("System.gc() was invoked!");   
            }   
            try {   
                Thread.sleep(500);   
            } catch (InterruptedException e) {   
  
            }   
        }   
        System.out.println("object a was cleared by JVM!");
    }   
	public static void solf_test() {   
        A a = new A();   
        a.str = "Hello, reference";   
        SoftReference<A> sr = new SoftReference<A>(a);   
        a = null;   
        int i = 0;   
        while (sr.get() != null) {   
            System.out.println(String.format("Get str from object of SoftReference: %s, count: %d", sr.get().str, ++i));   
            if (i % 10 == 0) {   
                System.gc();   
                System.out.println("System.gc() was invoked!");   
            }   
            try {   
                Thread.sleep(500);   
            } catch (InterruptedException e) {   
  
            }   
        }   
        System.out.println("object a was cleared by JVM!");   
    }   
  
}  