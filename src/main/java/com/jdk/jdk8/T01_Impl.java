package com.jdk.jdk8;


public class T01_Impl implements T01_default_interface{

	@Override
    public double calculate(int a) {
	    // TODO Auto-generated method stub
	    return 0;
    }
	public static void main(String[] args) {
	    T01_Impl t = new T01_Impl();
	    System.out.println(t.sqrt(5));
    }

}
