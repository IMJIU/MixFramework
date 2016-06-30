package com.framework.json;

import java.util.Date;

public class MyUser {

private Long userid;
private String username;
private boolean sex;
private Date birthday;

public MyUser() {
}

public Long getUserid() {
return userid;
}

public void setUserid(Long userid) {
this.userid = userid;
}

public String getUsername() {
return username;
}

public void setUsername(String username) {
this.username = username;
}

public boolean isSex() {
return sex;
}

public void setSex(boolean sex) {
this.sex = sex;
}

public Date getBirthday() {
return birthday;
}

public void setBirthday(Date birthday) {
this.birthday = birthday;
}
}