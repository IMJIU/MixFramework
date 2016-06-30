package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {

	public static void copy(File ofile, File nfile) {
		OutputStream out = null;
		InputStream r = null;
		try {
			if (ofile.exists()) {
				if (!nfile.exists())
					nfile.createNewFile();
				out = new FileOutputStream(nfile);
				r = new FileInputStream(ofile);
				byte[] bs = new byte[1024 * 8];
				int len = r.read(bs);
				while (len != -1) {
					out.write(bs, 0, len);
					len = r.read(bs);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
	            out.close();
	            r.close();
            } catch (IOException e) {
	            e.printStackTrace();
            }
		}
	}

}
