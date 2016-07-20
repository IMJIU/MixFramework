package com.jdk.jar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import com.book.jdk18.Process;

import sun.tools.jar.resources.jar;

/**
 * Zip文件工具类
 * 
 * @author Luxh
 */
public class ZipFileUtil {

	/**
	 * 把文件压缩成zip格式
	 * 
	 * @param files
	 *            需要压缩的文件
	 * @param zipFilePath
	 *            压缩后的zip文件路径 ,如"D:/test/aa.zip";
	 */
	public static void compressFiles2Zip(File[] files, String zipFilePath) {
		if (files != null && files.length > 0) {
			if (isEndsWithZip(zipFilePath)) {
				ZipArchiveOutputStream zaos = null;
				try {
					File zipFile = new File(zipFilePath);
					zaos = new ZipArchiveOutputStream(zipFile);
					// Use Zip64 extensions for all entries where they are
					// required
					zaos.setUseZip64(Zip64Mode.AsNeeded);

					// 将每个文件用ZipArchiveEntry封装
					// 再用ZipArchiveOutputStream写到压缩文件中
					for (File file : files) {
						if (file != null) {
							ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(file, file.getName());
							zaos.putArchiveEntry(zipArchiveEntry);
							InputStream is = null;
							try {
								is = new FileInputStream(file);
								byte[] buffer = new byte[1024 * 5];
								int len = -1;
								while ((len = is.read(buffer)) != -1) {
									// 把缓冲区的字节写入到ZipArchiveEntry
									zaos.write(buffer, 0, len);
								}
								// Writes all necessary data for this entry.
								zaos.closeArchiveEntry();
							} catch (Exception e) {
								throw new RuntimeException(e);
							} finally {
								if (is != null)
									is.close();
							}

						}
					}
					zaos.finish();
				} catch (Exception e) {
					throw new RuntimeException(e);
				} finally {
					try {
						if (zaos != null) {
							zaos.close();
						}
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}

			}

		}

	}

	/**
	 * 把zip文件解压到指定的文件夹
	 * 
	 * @param zipFilePath
	 *            zip文件路径, 如 "D:/test/aa.zip"
	 * @param saveFileDir
	 *            解压后的文件存放路径, 如"D:/test/"
	 */
	public static void decompressZip(String zipFilePath, String saveFileDir) {
		if (isEndsWithZip(zipFilePath)) {
			File file = new File(zipFilePath);
			if (file.exists()) {
				InputStream is = null;
				// can read Zip archives
				ZipArchiveInputStream zais = null;
				try {
					is = new FileInputStream(file);
					zais = new ZipArchiveInputStream(is);
					ArchiveEntry archiveEntry = null;
					// 把zip包中的每个文件读取出来
					// 然后把文件写到指定的文件夹
					while ((archiveEntry = zais.getNextEntry()) != null) {
						// 获取文件名
						String entryFileName = archiveEntry.getName();
						// 构造解压出来的文件存放路径
						String entryFilePath = saveFileDir + entryFileName;
						byte[] content = new byte[(int) archiveEntry.getSize()];
						zais.read(content);
						OutputStream os = null;
						try {
							// 把解压出来的文件写到指定路径
							File entryFile = new File(entryFilePath);
							os = new FileOutputStream(entryFile);
							os.write(content);
						} catch (IOException e) {
							throw new IOException(e);
						} finally {
							if (os != null) {
								os.flush();
								os.close();
							}
						}

					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				} finally {
					try {
						if (zais != null) {
							zais.close();
						}
						if (is != null) {
							is.close();
						}
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
	}

	/**
	 * 判断文件名是否以.zip为后缀
	 * 
	 * @param fileName
	 *            需要判断的文件名
	 * @return 是zip文件返回true,否则返回false
	 */
	public static boolean isEndsWithZip(String fileName) {
		boolean flag = false;
		if (fileName != null && !"".equals(fileName.trim())) {
			if (fileName.endsWith(".ZIP") || fileName.endsWith(".zip") || fileName.endsWith(".jar")) {
				flag = true;
			}
		}
		return flag;
	}

	public static String path1 = "d://target//121.40.150.187-common-service-3.0-SNAPSHOT.jar";
	public static String path2 = "d://target//common-service-3.0-SNAPSHOT2.jar";

	/**
	 * 把zip文件解压到指定的文件夹
	 * 
	 * @param jarPath
	 *            zip文件路径, 如 "D:/test/aa.zip"
	 * @param saveFileDir
	 *            解压后的文件存放路径, 如"D:/test/"
	 */
	public static void decompressJarToJar(String jarPath, String toJarPath,Process<JarOutputStream>process) {
		if (isEndsWithZip(jarPath)) {
			File file = new File(jarPath);
			if (file.exists()) {
				ZipArchiveInputStream zinputStream = null;
				JarOutputStream jOutputStream = null;
				try {
					zinputStream = new ZipArchiveInputStream(new FileInputStream(file));
					jOutputStream = new JarOutputStream(new FileOutputStream(toJarPath));
					ArchiveEntry archiveEntry = null;
					while ((archiveEntry = zinputStream.getNextEntry()) != null) {
						byte[] content = new byte[(int) archiveEntry.getSize()];
						zinputStream.read(content);
						try {
							if (archiveEntry == null)
								break;
							jOutputStream.putNextEntry(new ZipEntry(archiveEntry.getName()));
							jOutputStream.write(content, 0, content.length);
						} catch (IOException e) {
							throw new IOException(e);
						}
					}
					if(process!=null) process.process(jOutputStream);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (zinputStream != null) {
							zinputStream.close();
						}
						jOutputStream.flush();
						jOutputStream.close();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
	}

}