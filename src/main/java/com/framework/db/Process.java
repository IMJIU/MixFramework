package com.framework.db;

import java.util.Map;

public interface Process {

	String process(Map<String, String> col);

	String process(String id, String colName, Map<String, String> col1);
}
