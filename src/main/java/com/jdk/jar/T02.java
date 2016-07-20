package com.jdk.jar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class T02 {
	/**
	 * 获得Manifest对象
	 * 
	 * @param original
	 *            源文件名称
	 * @return Manifest对象
	 */
	private static Manifest getManifest(String original) {
		try {
			JarInputStream jis = new JarInputStream(new FileInputStream("./" + original + ".jar"));
			Manifest mf = jis.getManifest();
			return mf;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 修改jar文件参数
	 * 
	 * @param original
	 *            原文件名称
	 * @param agent
	 *            代理商代号
	 * @param version
	 *            版本号
	 */
	private static Manifest modifyManifest(Manifest mf, String agent, String version) {
		try {
			Attributes ab = mf.getMainAttributes();
			ab.putValue("agent-Name", agent);
			if (version != null) {
				ab.putValue("MIDlet-Version", version);
			}
			return mf;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 按照代理商参数数组和版本号输出指定的jar
	 * 
	 * @param original
	 *            原文件名称，不包含.jar
	 * @param agentId
	 *            代理商编号数组
	 * @param version
	 *            版本号码
	 */
	private static void outPutJar(String original, String[] agentId, String version) {
		if (agentId == null || agentId.length == 0) {
			return;
		}
		try {
			// 创建out文件夹
			File f = new File("./out");
			if (!f.exists()) {
				f.mkdir();
			}
			Manifest mf = getManifest(original);
			// 写入新文件
			for (int i = 0; i < agentId.length; i++) {
				mf = modifyManifest(mf, agentId[i], version);
				JarOutputStream jos = new JarOutputStream(new FileOutputStream("./out/" + original + agentId[i] + ".jar"), mf);
				JarInputStream jis = new JarInputStream(new FileInputStream("./" + original + ".jar"));
				byte[] b = new byte[1024];
				while (true) {
					JarEntry entry = jis.getNextJarEntry();
					if (entry == null) {
						break;
					}
					jos.putNextEntry(entry);
					// 写入数据
					int len = jis.read(b, 0, b.length);
					while (len != -1) {
						jos.write(b, 0, len);
						len = jis.read(b, 0, b.length);
					}
				}
				jos.close();
				jis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
