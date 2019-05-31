package com.jdk.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import expect4j.ExpectEmulation.SleepCommand;
import org.junit.Test;

/**
 * @author wison
 */
public class TestWeakReference {
    public static ReferenceQueue<VeryBig> rq = new ReferenceQueue<VeryBig>();
    public static boolean release = false;

    public static void main(String[] args) throws Exception {
        // t1();
        // t2();
//		t3_WeakReference();
//		t4_SoftReference();
//        t5_PhantomReference();
    }

    /**
     * 启动的时候，最好加下jvm参数
     */
    @Test
    public void t1() throws Exception {
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
//            Thread.sleep(10);
        }
//        System.out.println("why car:"+car); //加了这段，对象就不会回收了
    }

    @Test
    public void t2() throws Exception {
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

    /**
     * 队列中的对象会为null,size没有变化，只是get()出来为null
     * 可用于缓存
     * 弱引用，在yonggc就回收了
     */
    @Test
    public void t3_WeakReference() throws Exception {
        List<WeakReference<Car>> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Car car = new Car(i, "silver");
            WeakReference<Car> weakCar = new WeakReference<Car>(car);
            list.add(weakCar);
        }
        int i = 0;

        while (true) {
            if (list.size() > 0) {
                if (list.get(0).get() == null) {
                    System.out.println("collected");
                    break;
                }
                System.out.println(list.size() + "-" + list.get(0).get());
            } else {
                System.out.println("collected.");
                break;
            }
        }
        Thread.sleep(1000);//等待1秒为了后面看到更多的回收信息
    }

    /**
     * 这个比较难测试，要设置比较小的jvm
     * 队列中的对象会为null,size没有变化，只是get()出来为null
     * 可用于缓存
     * 软引用，在fullgc回收
     */
    @Test
    public void t4_SoftReference() {
        List<SoftReference<Car>> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Car car = new Car(i, "silver");
            SoftReference<Car> weakCar = new SoftReference<Car>(car);
            list.add(weakCar);
        }
        int i = 0;

        while (true) {
            if (list.size() > 0) {
                System.out.println(list.size() + "\t" + list.get(list.size() - 1).get());
            } else {
                System.out.println("collected.");
                break;
            }
        }
    }


    /**
     * 虚引用纯粹是为了调用finalize
     * @throws Exception
     */
    @Test
    public void t5_PhantomReference2() throws Exception {
        Car car = new Car(22000, "silver");
        PhantomReference<Car> weakCar = new PhantomReference(car,null);//虚引用加入队列没有意义
        int i = 0;
        while(true){
            if(!TestWeakReference.release){
                new Car(i++,"ddd");
//                System.out.println();
            }else{
                System.out.println("release");
                break;
            }
        }
        Thread.sleep(1000);
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
        TestWeakReference.release=true;
    }
}