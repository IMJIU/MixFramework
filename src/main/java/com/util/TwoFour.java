package com.util;
import java.awt.Panel;
import java.awt.Window;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TwoFour {
	public static void main(String[] args) throws Exception {

		String sql = "1 5 11 11";
		compute(sql);
//		md5("123", 0);
//		dateFormatTest();
	}

	private static void dateFormatTest() {
		String s ="2012-09-05 20:21:13.0";
		SimpleDateFormat f =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String fs = f.format(new Date());
		System.out.println(fs);
		//Date date = new Date(s);
		try {
			Date d = f.parse(s);
			System.out.println(f.format(d));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public static void compute(String sql) {
		String[] s = sql.split(" ");
		p1(Integer.parseInt(s[0]),
			Integer.parseInt(s[1]),
			Integer.parseInt(s[2]),
			Integer.parseInt(s[3]));
	}
	public static void p1(int a,int b,int c,int d){
		System.out.println("why");
		double[] array = new double[4];
		array[0]=a;
		array[1]=b;
		array[2]=c;
		array[3]=d;
		for (int i = 0; i < array.length; i++) 
		{
			for (int j = 0; j < array.length; j++) 
			{
				if(j==i) 
				{
					continue;
				}
				for (int k = 0; k < array.length; k++) 
				{
					if(k==i || k==j) 
					{
						continue;
					}
					for (int m = 0; m < array.length; m++) 
					{
						if(m==i || m==j || m==k) 
						{
							continue;
						}
						p2(array[i],array[j],array[k],array[m]);
					}
				}
			}
		}
	}
	public  static void p2(double a,double b,double c,double d){
		double p1 = 0;
		double p2 = 0;
		double p3 = 0;
		for (int i = 1; i < 5; i++) {
			for (int j = 1; j < 5; j++) {
				for (int k = 1; k < 5; k++) {
					 p1 = op(i,a,b);
					 p2 = op(j,p1,c);
					 p3 = op(k,p2,d);
					 if(p3==24){
						 System.out.println(a+convert(i)+b+" | "+convert(j)+c+" | "+convert(k)+d+" = "+p3);
					 }
//					 System.out.println(a+convert(i)+b+" | "+convert(j)+c+" | "+convert(k)+d+" = "+p3);
				}
			}
		}
	}
	public  static double op(int opt,double num1,double num2){
		switch (opt) {
		case 1:
			return num1+num2;
		case 2:
			if(num1<num2) return -9999;
			return num1-num2;
		case 3:
			return num1*num2;
		case 4:
			if(num2 == 0) return -1;
			//if(num1%num2>0) return -9999;
			return num1/num2;
		default:
			return num1+num2;
		}
	}
	public static String convert(int opt){
		switch (opt) {
		case 1:
			return "+";
		case 2:
			return "-";
		case 3:
			return "*";
		case 4:
			return "/";
		}
		return "";
	}

}
