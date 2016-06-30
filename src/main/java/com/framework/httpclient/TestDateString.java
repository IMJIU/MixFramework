package com.framework.httpclient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TestDateString {

	static List<String> datelist = new ArrayList(24 * 3);

	public static void main(String[] args) throws Exception {
		getDate();
	}

	private static void getDate() {
	    int d = Calendar.getInstance().get(Calendar.DATE);
		int m = Calendar.getInstance().get(Calendar.MONTH)+1;
		for (int i = 0; i < 24; i++) {
			for (int j = 1; j < 4; j++) {
				String hour = String.valueOf(i);
//				if (i < 10) {
//					hour = "0" + hour;
//				}
				System.out.println(m+"/"+d+" "+hour + ":" + j * 20);
				datelist.add(m+"/"+d+" "+hour + ":" + j * 20);
			}
		}
		System.out.println(datelist);
    }

}
