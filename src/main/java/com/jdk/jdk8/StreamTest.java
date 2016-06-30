package com.jdk.jdk8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTest {

	public static void main(String[] args) throws Exception {
		
		// init_stream();

		// reduce();
		
		// t02_sort();
		
		t03_sort();
		
		findWord();

		test_match();

		create_random();

		suppplier();

		Stream.iterate(0, n -> n + 3).limit(10).forEach(x -> System.out.print(x + " "));
		
		groupBy();

		groupBy2();
	}
	
	private static void groupBy2() {
		// 清单 26. 按照未成年人和成年人归组
		Map<Boolean, List<Person>> children = Stream.generate(new PersonSupplier()).limit(100).collect(Collectors.partitioningBy(p -> p.getAge() < 18));
		System.out.println("Children number: " + children.get(true).size());
		System.out.println("Adult number: " + children.get(false).size());
	}
	
	private static void groupBy() {
		// 清单 25. 按照年龄归组
		Map<Integer, List<Person>> personGroups = Stream.generate(new PersonSupplier()).limit(100).collect(Collectors.groupingBy(Person::getAge));
		Iterator it = personGroups.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, List<Person>> persons = (Map.Entry) it.next();
			System.out.println("Age " + persons.getKey() + " = " + persons.getValue().size());
		}
	}
	
	private static void suppplier() {
		// 清单 23. 自实现 Supplier
		Stream.generate(new PersonSupplier()).limit(10).forEach(p -> System.out.println(p.getName() + ", " + p.getAge()));
	}

	private static void create_random() {
		// 清单 22. 生成 10 个随机整数
		Random seed = new Random();
		Supplier<Integer> random = seed::nextInt;
		Stream.generate(random).limit(10).forEach(System.out::println);
		// Another way
		IntStream.generate(() -> (int) (System.nanoTime() % 100)).limit(10).forEach(System.out::println);
	}

	private static void test_match() {
		// 清单 21. 使用 Match
		List<Person> persons = new ArrayList();
		persons.add(new Person(1, "name" + 1, 10));
		persons.add(new Person(2, "name" + 2, 21));
		persons.add(new Person(3, "name" + 3, 34));
		persons.add(new Person(4, "name" + 4, 6));
		persons.add(new Person(5, "name" + 5, 55));
		boolean isAllAdult = persons.stream().allMatch(p -> p.getAge() > 18);
		System.out.println("All are adult? " + isAllAdult);
		boolean isThereAnyChild = persons.stream().anyMatch(p -> p.getAge() < 12);
		System.out.println("Any child? " + isThereAnyChild);
	}

	private static void reduce() {
		// 字符串连接，concat = "ABCD"
		String concat = Stream.of("A", "B", "C", "D").reduce("", String::concat);
		// 求最小值，minValue = -3.0
		double minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
		// 求和，sumValue = 10, 有起始值
		int sumValue = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
		// 求和，sumValue = 10, 无起始值
		sumValue = Stream.of(1, 2, 3, 4).reduce(Integer::sum).get();
		// 过滤，字符串连接，concat = "ace"
		concat = Stream.of("a", "B", "c", "D", "e", "F").filter(x -> x.compareTo("Z") > 0).reduce("", String::concat);
	}
	public static void testLimitAndSkip() {
		List<Person> persons = new ArrayList();
		for (int i = 1; i <= 10000; i++) {
			Person person = new Person(i, "name" + i);
			persons.add(person);
		}
		List<String> personList2 = persons.stream().map(Person::getName).limit(10).skip(3).collect(Collectors.toList());
		System.out.println(personList2);
	}

	public static void t02_sort() {
		List<Person> persons = new ArrayList();
		for (int i = 1; i <= 5; i++) {
			Person person = new Person(i, "name" + i);
			persons.add(person);
		}
		List<Person> personList2 = persons.stream().sorted((p1, p2) -> p1.getName().compareTo(p2.getName())).limit(2).collect(Collectors.toList());
		System.out.println(personList2);
	}
	
	// 有效
	public static void t03_sort() {
		List<Person> persons = new ArrayList();
		for (int i = 1; i <= 5; i++) {
			Person person = new Person(i, "name" + i);
			persons.add(person);
		}
		List<Person> personList2 = persons.stream().limit(2).sorted((p1, p2) -> p1.getName().compareTo(p2.getName())).collect(Collectors.toList());
		System.out.println(personList2);
	}
	
	// 清单 19. 找出最长一行的长度
	public static void t04_maxline() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("c:\\SUService.log"));
		int longest = br.lines().mapToInt(String::length).max().getAsInt();
		br.close();
		System.out.println(longest);
	}
	
	private static void findWord() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("c:\\SUService.log"));
		// 清单 20. 找出全文的单词，转小写，并排序
		List<String> words = br.lines().flatMap(line -> Stream.of(line.split(" "))).filter(word -> word.length() > 0).map(String::toLowerCase).distinct().sorted().collect(Collectors.toList());
		br.close();
		System.out.println(words);
	}
	
	private static void init_stream() {
		// 1. Individual values
		Stream stream = Stream.of("a", "b", "c");
		// 2. Arrays
		String[] strArray = new String[] { "a", "b", "c" };
		stream = Stream.of(strArray);
		stream = Arrays.stream(strArray);
		// 3. Collections
		List<String> list = Arrays.asList(strArray);
		stream = list.stream();
	}
}

class Person {

	public int no;

	private String name;

	private int age;

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setName(String name) {
		this.name = name;
	}
	public Person(int no, String name) {
		this.no = no;
		this.name = name;
	}
	public Person(int i, String string, int j) {
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		System.out.println(name);
		return name;
	}
}

class PersonSupplier implements Supplier<Person> {

	private int index = 0;

	private Random random = new Random();

	@Override
	public Person get() {
		return new Person(index++, "StormTestUser" + index, random.nextInt(100));
	}
}