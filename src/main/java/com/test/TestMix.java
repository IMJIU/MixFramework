package com.test;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

public class TestMix {
	public static void main(String[] args) {
		JSONArray arr = JSON.parseArray("[{groupId:1,optionId:123,serviceName:\"男性疾病\",serviceDesc:\"花柳、艾滋都可以看\"}]");
//		System.out.println(arr);
		List<Object> mrr = (List<Object>)arr;
		for (int i = 0; i < mrr.size(); i++) {
			System.out.println(mrr.get(i));
			System.out.println(((Map<String,Object>)mrr.get(i)).get("serviceDesc"));;
		}
	}

}
