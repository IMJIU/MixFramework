package com.framework.json;

import java.util.ArrayList;
import java.util.List;

public class MyGroupUser {

private Long groupid;
private String groupname;
private List<MyUser> listuser = new ArrayList<MyUser>();

public MyGroupUser() {
}

public Long getGroupid() {
return groupid;
}

public void setGroupid(Long groupid) {
this.groupid = groupid;
}

public String getGroupname() {
return groupname;
}

public void setGroupname(String groupname) {
this.groupname = groupname;
}

public List<MyUser> getListuser() {
return listuser;
}

public void setListuser(List<MyUser> listuser) {
this.listuser = listuser;
}
}