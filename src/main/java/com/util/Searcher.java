package com.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.test.dealfile.DealObject;

public class Searcher {

	private Map<String, String> paramMap = new HashMap<String, String>();

	private BufferedReader r = null;

	private BufferedWriter w = null;

	/**
	 * main处理方法
	 * 
	 * @param f1
	 * @param op
	 * @param params
	 */
	public void dealFile(File f1, int op, String params) {
		if (paramMap.size() == 0) {
			/** 提取参数 & 分隔 =赋值 **/
			stringToMap(params);
		}
		if (f1.isDirectory()){
			for (File f2 : f1.listFiles()) {
				if (f2.isDirectory()) {
					dealFile(f2, op, params);
				} else {
					loop(op, f2);
				}
			}
		}else{
			loop(op, f1);
		}
	}

	private void loop(int op, File f2) {
		try {
			switch( op )
			{
			case Ps.rep:
				w = replace(paramMap, f2, r);
				break;
			case Ps.del:
				del(paramMap, f2);
				break;
			case Ps.rename:
				rename(paramMap, f2);
				break;
			case Ps.filter:
				w = filter(paramMap, f2, r);
				break;
			case Ps.extract:
				break;
//				extractObject(paramMap, f2, r);
			default:
				break;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (r != null)
					r.close();
				if (w != null) {
					w.flush();
					w.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void find(File f1, String regex) {
		for (File f2 : f1.listFiles()) {
			if (f2.getName().indexOf(regex) != -1){
				System.out.println(f2.getAbsolutePath());
			}
			if (f2.isDirectory()) {
				find(f2, regex);
			} 
		}
	}

	public void findStr(File f1, int tag, String... regexs) {
		for (File f2 : f1.listFiles()) {
			if (f2.isDirectory()) {
				findStr(f2, tag, regexs);
			} else {
				try {
					r = new BufferedReader(new InputStreamReader(new FileInputStream(f2)));
					String str = "";
					int flag = 0;
					int linenum = 0;
					LinkedList<String> list = new LinkedList<String>();
					while ((str = r.readLine()) != null) {
						boolean stop = false;
						linenum++;
						// 查找用indexOF
						if (tag == 1) {
							stop = doIndex(str, regexs);
							// 查找用正则表达式
						} else if (tag == 2) {
							stop = doRegex(str, regexs);
						}
						//查到了
						if (!stop) {
							if (flag == 0) {
								System.out.println("===========【" + f2.getCanonicalPath() + "】========");
								flag = 1;
							}
							for (int i = 0; i < list.size(); i++) {
	                            System.out.println(list.poll());
                            }
							System.out.println(linenum + "." + str);
						//未查找到
						}else{
							if(list.size()>1)
								list.removeFirst();
							list.addLast(str);
						}
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						r.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private boolean doRegex(String str, String... regexs) {
		for (String regex : regexs) {
			if (str.trim().matches(regex)) {
				return true;
			}
		}
		return false;
	}

	private boolean doIndex(String str, String... regexs) {
		for (String reg : regexs) {
			if (str.toLowerCase().indexOf(reg.toLowerCase()) == -1) {
				return true;
			}
		}
		return false;
	}

	private BufferedWriter replace(Map<String, String> pmap, File f2, BufferedReader r) throws FileNotFoundException, IOException {
		String filter = pmap.get("filter");
		if (f2.getName().matches(filter)) {
			BufferedWriter w;
			if (r == null) {
				r = new BufferedReader(new InputStreamReader(new FileInputStream(f2)));
			}
			String str;
			w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f2.getAbsolutePath() + ".bak")));
			String regex = pmap.get("reg");
			String s = pmap.get("rep");
			while ((str = r.readLine()) != null) {
				// System.out.println(str.replaceAll(regex, s));
				w.append(str.replaceAll(regex, s)).append('\n');
			}
			return w;
		}
		return null;
	}

	private BufferedWriter filter(Map<String, String> pmap, File f2, BufferedReader r) throws FileNotFoundException, IOException {
		BufferedWriter w;
		String str;
		r = new BufferedReader(new InputStreamReader(new FileInputStream(f2)));
		w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f2.getAbsolutePath() + ".bak")));
		String regex = pmap.get("reg");
		while ((str = r.readLine()) != null) {
			if (!str.trim().equals("")) {
				if (regex != null && str.matches(regex))
					w.append(str).append('\n');
				else
					w.append(str).append('\n');
			}
		}
		return w;
	}

	private void extractObject(Map<String, String> pmap, File f2, BufferedReader r) throws FileNotFoundException, IOException {
		String str;
		r = new BufferedReader(new InputStreamReader(new FileInputStream(f2)));
		String className = pmap.get("className");
		String regex = pmap.get("regex");
		DealObject deal = null;
		List<Map<String,String>>list = new ArrayList<Map<String,String>>();
		try {
			deal = (DealObject) Class.forName(className).newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		Map<String, String> map = new HashMap<String, String>();
		while ((str = r.readLine()) != null) {
			 groupRegex(str,regex,map);
		}
//		deal.deal(map);
	}

	/**
	 * 取数
	 */
	public static void groupRegex(String str, String regex,Map<String,String> map) {
		Pattern p = Pattern.compile(regex);
		Matcher matcher = p.matcher(str);
		while (matcher.find()) {
			map.put(matcher.group(1),matcher.group(2));
		}
	}

	private void del(Map<String, String> pmap, File f2) {
		String regex = pmap.get("reg");
		if (f2.getName().matches(regex)) {
			System.out.println("del " + f2.getAbsolutePath());
			f2.deleteOnExit();
		}
	}

	private void rename(Map<String, String> pmap, File f2) {
		String reg = pmap.get("reg");
		String rep = pmap.get("rep");
		String str = pmap.get("str");
		if (f2.getName().matches(reg)) {
			boolean ret = f2.renameTo(new File(f2.getAbsolutePath().replaceAll(rep, str)));
			System.out.println(ret + " rename to " + f2.getAbsolutePath().replaceAll(rep, str));
			// System.out.println(" rename to "+f2.getAbsolutePath().replaceAll(rep,
			// str));
		}
	}

	/**
	 * 提取参数 & 分隔 =赋值
	 * 
	 * @param str
	 */
	public void stringToMap(String str) {
		String[] ss = str.split("&");
		for (String s : ss) {
			String[] kv = s.split("=");
			paramMap.put(kv[0], kv.length == 1 ? "" : kv[1]);
		}
		for (Entry<String, String> en : paramMap.entrySet()) {
			System.out.println(en.getKey() + " : " + en.getValue());
		}
	}

	public void recallToDir(File f1) {
		float all = 0;
		for (File f2 : f1.listFiles()) {
			if (f2.isDirectory()) {
				String parent = f2.getAbsolutePath();
				for (File f3 : f2.listFiles()) {
					if (f3.isDirectory()) {
						for (File f4 : f3.listFiles()) {
							if (f4.length() / 1024 / 1024 > 10) {
								all += f4.length();
								System.out.println((float) f4.length() / 1024 / 1024 + "MB\t" + f4.getAbsolutePath());
								if (f4.getName().endsWith(".qdl2")) {
									f4.deleteOnExit();
								}
								System.out.println(parent + File.separator + f4.getName());
								f4.renameTo(new File(parent + File.separator + f4.getName()));
							}
						}
					}
				}
			}
		}
		System.out.println("all:" + all / 1024 / 1024 + "MB");
	}

	public class Ps {

		public static final int del = -1;

		public static final int add = 1;

		public static final int rep = 2;

		public static final int rename = 3;

		public static final int filter = 4;

		public static final int extract = 5;

	}

	enum Op {
		del, add, mv, rep
	}
}
