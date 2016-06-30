package com.framework.akka.messages;

import java.util.List;

public class MapData {

	private List<WordCount> dataList;

	public List<WordCount> getDataList() {
		return dataList;
	}
	public MapData(List<WordCount> dataList) {
		this.dataList = dataList;
	}
	@Override
	public String toString() {
		String ret = "";
		for (WordCount w : dataList) {
	        ret+=w.word+",";
        }
		return ret;
	}
}
