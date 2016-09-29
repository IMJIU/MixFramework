package com.framework.hibernate.t02;

import java.util.Date;  
  
import javax.persistence.Column;  
import javax.persistence.Entity;  
import javax.persistence.GeneratedValue;  
import javax.persistence.GenerationType;  
import javax.persistence.Id;  
import javax.persistence.Temporal;  
import javax.persistence.TemporalType;  
  
@Entity //不写Table默认为user，@Table(name="t_user")  
public class User2 {  
  
    @Id //主键  
    @GeneratedValue(strategy=GenerationType.AUTO)//采用数据库自增方式生成主键  
    //JPA提供的四种标准用法为TABLE,SEQUENCE,IDENTITY,AUTO.   
    //TABLE：使用一个特定的数据库表格来保存主键。   
    //SEQUENCE：根据底层数据库的序列来生成主键，条件是数据库支持序列。   
    //IDENTITY：主键由数据库自动生成（主要是自动增长型）   
    //AUTO：主键由程序控制。  
    private int id;  
      
    private String name;  
    private String password;  
      
    @Temporal(TemporalType.DATE)//生成yyyy-MM-dd类型的日期  
    private Date createTime;  
    @Temporal(TemporalType.TIMESTAMP)//生成yyyy-MM-dd类型的日期  
    private Date expireTime;  
      
      
  
    public int getId() {  
        return id;  
    }  
    public void setId(int id) {  
        this.id = id;  
    }  
    @Column(name="name",unique=true,nullable=false) //字段为name，不允许为空，用户名唯一  
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }  
    public String getPassword() {  
        return password;  
    }  
    public void setPassword(String password) {  
        this.password = password;  
    }  
    public Date getCreateTime() {  
        return createTime;  
    }  
    public void setCreateTime(Date createTime) {  
        this.createTime = createTime;  
    }  
    public Date getExpireTime() {  
        return expireTime;  
    }  
    public void setExpireTime(Date expireTime) {  
        this.expireTime = expireTime;  
    }  
  
}  