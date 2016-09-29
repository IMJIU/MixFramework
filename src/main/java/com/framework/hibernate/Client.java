package com.framework.hibernate;
 
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;  
  
public class Client {  
    public static void main(String[]args){  
//        t1_xml();  
        t2_anno();
    }

	private static void t1_xml() {
		//读取hibernate.cfg.xml文件  
        Configuration cfg = new Configuration().configure();  
        //建立SessionFactory  
        SessionFactory factory =cfg.buildSessionFactory();  
          
        //取得session  
        Session session = null;  
          
        try{  
            //开启session  
            session = factory.openSession();  
            //开启事务  
            session.beginTransaction();  
              
            User user = new User();  
            user.setName("jiuqiyuliang");  
            user.setPassword("123456");  
            user.setCreateTime(new Date());  
            user.setExpireTime(new Date());  
            //保存User对象  
            session.save(user);  
              
            //提交事务  
            session.getTransaction().commit();  
              
        }catch(Exception e){  
            e.printStackTrace();  
            //回滚事务  
            session.getTransaction().rollback();  
        }finally{  
            if(session != null){  
                if(session.isOpen()){  
                    //关闭session  
                    session.close();  
                }  
            }  
        }
	}  
    
    public static void t2_anno(){
    	   //读取hibernate.cfg.xml文件  
        Configuration cfg = new AnnotationConfiguration().configure();  
        //建立SessionFactory  
        SessionFactory factory =cfg.buildSessionFactory();  
          
        //取得session  
        Session session = null;  
          
        try{  
            //开启session  
            session = factory.openSession();  
            //开启事务  
            session.beginTransaction();  
              
            com.framework.hibernate.t02.User2 user = new com.framework.hibernate.t02.User2();  
            user.setName("jiuqiyuliang");  
            user.setPassword("123456");  
            user.setCreateTime(new Date());  
            user.setExpireTime(new Date());  
            //保存User对象  
            session.save(user);  
              
            //提交事务  
            session.getTransaction().commit();  
              
        }catch(Exception e){  
            e.printStackTrace();  
            //回滚事务  
            session.getTransaction().rollback();  
        }finally{  
            if(session != null){  
                if(session.isOpen()){  
                    //关闭session  
                    session.close();  
                }  
            }  
        }  
    }
}  