package com.jdk.reference;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wison
 */
public class TestWeakReference {
	public static void main(String[] args) {
		// t1();
		// t2();
//		t3();
		t4();
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

	private static void t3() {
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
//				for (WeakReference<Car> weakReference : list) {
//					System.out.println(weakReference);
//					System.out.println(weakReference.get());
//				}
			} else {
				System.out.println("collected.");
				break;
			}
		}
	}
	private static void t4() {
		List<SoftReference<Car>> list = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			Car car = new Car(i, "silver");
			SoftReference<Car> weakCar = new SoftReference<Car>(car);
			list.add(weakCar);
		}
		int i = 0;

		while (true) {
			if (list.size() > 0) {
				System.out.println(list.size()+"-"+list.get(0).get());
//				for (WeakReference<Car> weakReference : list) {
//					System.out.println(weakReference);
//					System.out.println(weakReference.get());
//				}
			} else {
				System.out.println("collected.");
				break;
			}
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