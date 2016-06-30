package com.test.dealfile;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DealRename2 extends DealObject {

	@Override
	public void task(File f1, Map<String, String> param) {
		String fname = f1.getName().replace(".zip", "");
		String[] s = fname.split("-", 2);
		fname = s[1].trim() + "-" + s[0].trim();
		String newName = f1.getParent() + "/" + fname + ".zip";
		boolean ret = f1.renameTo(new File(newName));
		System.out.print(ret + "\t");
		System.out.println(" rename to " + newName);

	}

}
