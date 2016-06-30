package com.framework.db;

public class Col {

	public String sc;

	public String tb;

	public String colName;

	public String colType;

	public String defaultVal;

	public String charset;

	public Col(String sc, String tb, String colName, String colType, String defaultVal, String charset) {
		super();
		this.sc = sc;
		this.tb = tb;
		this.colName = colName;
		this.colType = colType;
		this.defaultVal = defaultVal;
		this.charset = charset;
	}

}