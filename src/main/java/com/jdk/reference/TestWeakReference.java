package com.jdk.reference;
import java.lang.ref.WeakReference;

/**
 * @author wison
 */
public class TestWeakReference {
  public static void main(String[] args) {
//    t1();
    t2();
  }
private static void t1() {
	Car car = new Car(22000,"silver");
    WeakReference<Car> weakCar = new WeakReference<Car>(car);
    
    int i=0;
    
    while(true){
      if(weakCar.get()!=null){
        i++;
        System.out.println("Object is alive for "+i+" loops - "+weakCar);
      }else{
        System.out.println("Object has been collected.");
        break;
      }
    }
}
  public static void t2(){
	  	Car car = new Car(22000,"silver");
	    WeakReference<Car> weakCar = new WeakReference<Car>(car);
	    
	    int i=0;
	    
	    while(true){
	      System.out.println("here is the strong reference 'car' "+car);
	      if(weakCar.get()!=null){
	        i++;
	        System.out.println("Object is alive for "+i+" loops - "+weakCar);
	      }else{
	        System.out.println("Object has been collected.");
	        break;
	      }
	    }
  }

}
class Car{
	int _n = 0;
	String _str = "";
	public Car(int n , String str){
		_n = n;
		_str = str;
	}
	@Override
	public String toString() {
	    return "n:"+_n+"-s:"+_str;
	}
}