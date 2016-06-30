package com.util;

import java.io.File;
import java.util.Properties;

public class Test {

	public static void t1() {
		Properties props = System.getProperties(); // 系统属性

		System.out.println("Java的运行环境版本：" + props.getProperty("java.version"));
		System.out.println("Java的运行环境供应商：" + props.getProperty("java.vendor"));
		System.out.println("Java供应商的URL：" + props.getProperty("java.vendor.url"));
		System.out.println("Java的安装路径：" + props.getProperty("java.home"));
		System.out.println("Java的虚拟机规范版本：" + props.getProperty("java.vm.specification.version"));
		System.out.println("Java的虚拟机规范供应商：" + props.getProperty("java.vm.specification.vendor"));
		System.out.println("Java的虚拟机规范名称：" + props.getProperty("java.vm.specification.name"));
		System.out.println("Java的虚拟机实现版本：" + props.getProperty("java.vm.version"));
		System.out.println("Java的虚拟机实现供应商：" + props.getProperty("java.vm.vendor"));
		System.out.println("Java的虚拟机实现名称：" + props.getProperty("java.vm.name"));
		System.out.println("Java运行时环境规范版本：" + props.getProperty("java.specification.version"));
		System.out.println("Java运行时环境规范供应商：" + props.getProperty("java.specification.vender"));
		System.out.println("Java运行时环境规范名称：" + props.getProperty("java.specification.name"));
		System.out.println("Java的类格式版本号：" + props.getProperty("java.class.version"));
		System.out.println("Java的类路径：" + props.getProperty("java.class.path"));
		System.out.println("加载库时搜索的路径列表：" + props.getProperty("java.library.path"));
		System.out.println("默认的临时文件路径：" + props.getProperty("java.io.tmpdir"));
		System.out.println("一个或多个扩展目录的路径：" + props.getProperty("java.ext.dirs"));
		System.out.println("操作系统的名称：" + props.getProperty("os.name"));
		System.out.println("操作系统的构架：" + props.getProperty("os.arch"));
		System.out.println("操作系统的版本：" + props.getProperty("os.version"));
		System.out.println("文件分隔符：" + props.getProperty("file.separator")); // 在
		                                                                    // unix
		                                                                    // 系统中是”／”
		System.out.println("路径分隔符：" + props.getProperty("path.separator")); // 在
		                                                                    // unix
		                                                                    // 系统中是”:”
		System.out.println("行分隔符：" + props.getProperty("line.separator")); // 在
		                                                                   // unix
		                                                                   // 系统中是”/n”
		System.out.println("用户的账户名称：" + props.getProperty("user.name"));
		System.out.println("用户的主目录：" + props.getProperty("user.home"));
		System.out.println("用户的当前工作目录：" + props.getProperty("user.dir"));

	}
	public static void main(String[] args) {
		// 举例：
		String fName = " \\share\\123lkj\\ckljwler_2344\\lev1_arrow.gif ";

		// 方法一：
//		File tempFile = new File(fName.trim());
//		String fileName = tempFile.getName();
//		System.out.println("fileName = " + fileName);

		// 方法二：

		fName = fName.trim();
		int i1 = fName.indexOf(File.separator);
		int i2 = fName.indexOf(File.separator,i1+1);
		System.out.println(i1);
		System.out.println(i2);
		while (i2!=-1) {
			String parent = fName.substring(i1+1,i2);
			System.out.println(parent);
			i1=i2;
			i2 = fName.indexOf(File.separator,i1+1);
        }
//		 String fileName = fName.substring(fName.indexOf(File.separator,fName.lastIndexOf(File.separator)+1);
		 //或者
//		 String fileName = fName.substring(fName.lastIndexOf("\\")+1);
		
//		 System.out.println("fileName = " + fileName);

		// 方法三：

		// String fName = fName.trim();
		//
		// String temp[] = fName.split("\\\\");
		// /**split里面必须是正则表达式，"\\"的作用是对字符串转义*/
		//
		// String fileName = temp[temp.length-1];
		//
		// System.out.println("fileName = " + fileName);

	}

}