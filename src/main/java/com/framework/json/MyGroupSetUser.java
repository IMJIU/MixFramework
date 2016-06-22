package com.framework.json;

import java.util.HashSet;
import java.util.Set;

public class MyGroupSetUser {

private Long groupsetid;
private String groupsetname;
private Set<MyUser> setuser = new HashSet<MyUser>();

public MyGroupSetUser() {
}

public Long getGroupsetid() {
return groupsetid;
}

public void setGroupsetid(Long groupsetid) {
this.groupsetid = groupsetid;
}

public String getGroupsetname() {
return groupsetname;
}

public void setGroupsetname(String groupsetname) {
this.groupsetname = groupsetname;
}

public Set<MyUser> getSetuser() {
return setuser;
}

public void setSetuser(Set<MyUser> setuser) {
this.setuser = setuser;
}
}