package com.framework.json;

import java.util.HashMap;
import java.util.Map;

public class MyGroupMapUser {

	private Long groupmapid;

	private String groupmapname;

	private Map<String, MyUser> mapuser = new HashMap<String, MyUser>();

	public MyGroupMapUser() {}

	public Long getGroupmapid() {
		return groupmapid;
	}

	public void setGroupmapid(Long groupmapid) {
		this.groupmapid = groupmapid;
	}

	public String getGroupmapname() {
		return groupmapname;
	}

	public void setGroupmapname(String groupmapname) {
		this.groupmapname = groupmapname;
	}

	public Map<String, MyUser> getMapuser() {
		return mapuser;
	}

	public void setMapuser(Map<String, MyUser> mapuser) {
		this.mapuser = mapuser;
	}
}