package com.framework.httpclient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class TestDate {

	public static void main(String[] args) {
		  Random random = new Random();
		  for (int i = 0; i < 200; i++) {
			  System.out.println(random.nextInt(100));
	        
        }
//		int a = 2;
//		System.out.println(a<<1);
//		a &= 31;
//		System.out.println(a);
//		 System.out.println(System.currentTimeMillis());
//		 computeDateTest();
//		 Date d = new Date("Sun Jan 01 00:00:00 CST 1988");
//		 System.out.println(d.getTime());
//		 System.out.println(getAgeByBirthday(d));
//		 System.out.println(getAge(d));

	}

	private static void computeDateTest() {
		Date d = new Date("Sun Jan 01 00:00:00 CST 1995");
		System.out.println(d);
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 10000000; i++) {
			// getAgeByBirthday(d);
			long l = Calendar.getInstance().getTimeInMillis() - 788940000000l;
		}
		long end = System.currentTimeMillis();
		for (int i = 0; i < 10000000; i++) {
			long l = new Date().getTime() - 788940000000l;
			// getAge(d);
		}
		long end2 = System.currentTimeMillis();
		System.out.println(end - begin);
		System.out.println(end2 - end);
	}

	/**
	 * 根据用户生日计算年龄
	 */
	public static int getAgeByBirthday(Date birthday) {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthday)) {
			throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthday);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		}
		return age;
	}

	public static int getAge(Date birthDate) {

		if (birthDate == null)
			throw new RuntimeException("出生日期不能为null");

		int age = 0;

		Date now = new Date();

		SimpleDateFormat format_y = new SimpleDateFormat("yyyy");
		SimpleDateFormat format_M = new SimpleDateFormat("MM");

		String birth_year = format_y.format(birthDate);
		String this_year = format_y.format(now);

		String birth_month = format_M.format(birthDate);
		String this_month = format_M.format(now);

		// 初步，估算
		age = Integer.parseInt(this_year) - Integer.parseInt(birth_year);

		// 如果未到出生月份，则age - 1
		if (this_month.compareTo(birth_month) < 0)
			age -= 1;
		if (age < 0)
			age = 0;
		return age;
	}

}
