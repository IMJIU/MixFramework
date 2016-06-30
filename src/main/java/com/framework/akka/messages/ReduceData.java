package com.framework.akka.messages;

import java.util.HashMap;
import java.util.List;

public class ReduceData {

	private HashMap<String, Integer> dataMap;

	public HashMap<String, Integer> getDataList() {
		return dataMap;
	}
	public ReduceData(HashMap<String, Integer> datalist) {
		this.dataMap = datalist;
	}
	@Override
	public String toString() {
	    return dataMap.toString();
	}
}
