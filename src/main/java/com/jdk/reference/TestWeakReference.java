package com.jdk.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import expect4j.ExpectEmulation.SleepCommand;

/**
 * @author wison
 */
public class TestWeakReference {
	private static ReferenceQueue<VeryBig> rq = new ReferenceQueue<VeryBig>();
	
	
	public static void main(String[] args) throws Exception{
		// t1();
		// t2();
//		t3_WeakReference();
//		t4_SoftReference();
		t5_PhantomReference();
	}

	private static void t1() {
		Car car = new Car(22000, "silver");
		WeakReference<Car> weakCar = new WeakReference<Car>(car);

		int i = 0;

		while (true) {
			if (weakCar.get() != null) {
				i++;
				System.out.println("alive for " + i + " loops - " + weakCar.get()._n);
			} else {
				System.out.println("collected.");
				break;
			}
		}
	}

	public static void t2() {
		Car car = new Car(22000, "silver");
		WeakReference<Car> weakCar = new WeakReference<Car>(car);

		int i = 0;

		while (true) {
			System.out.println("here is the strong reference 'car' " + car);
			if (weakCar.get() != null) {
				i++;
				System.out.println("alive for " + i + " loops - " + weakCar);
			} else {
				System.out.println("collected.");
				break;
			}
		}
	}

	private static void t3_WeakReference() {
		List<WeakReference<Car>> list = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			Car car = new Car(i, "silver");
			WeakReference<Car> weakCar = new WeakReference<Car>(car);
			list.add(weakCar);
		}
		int i = 0;

		while (true) {
			if (list.size() > 0) {
				System.out.println(list.size()+"-"+list.get(0).get());
			} else {
				System.out.println("collected.");
				break;
			}
		}
	}
	private static void t4_SoftReference() {
		List<SoftReference<Car>> list = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			Car car = new Car(i, "silver");
			SoftReference<Car> weakCar = new SoftReference<Car>(car);
			list.add(weakCar);
		}
		int i = 0;

		while (true) {
			if (list.size() > 0) {
				System.out.println(list.size()+"\t"+list.get(list.size()-1).get());
			} else {
				System.out.println("collected.");
				break;
			}
		}
	}
	private static void t5_PhantomReference() throws InterruptedException {
		List<PhantomReference<Car>> list = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			Car car = new Car(i, "silver");
			PhantomReference<Car> weakCar = new PhantomReference(car, rq);
			list.add(weakCar);
			System.out.println(list.get(i));;
			System.out.println(rq.poll());
			System.out.println("============");
		}
	}
}
class Car {
	int _n = 0;
	String _str = "";

	public Car(int n, String str) {
		_n = n;
		_str = str;
	}

	@Override
	public String toString() {
		return "n:" + _n + "-s:" + _str;
	}

	@Override
	protected void finalize() throws Throwable {
		System.out.println("finalize()" + _n);
		super.finalize();
	}
}