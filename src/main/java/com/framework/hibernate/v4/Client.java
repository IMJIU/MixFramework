package com.framework.hibernate.v4;

import org.hibernate.Session;  
import org.hibernate.SessionFactory;  
import org.hibernate.Transaction;  
import org.hibernate.cfg.Configuration;

import com.framework.hibernate.t02.User2;  
  
public class Client {  
    public static void main(String[] args) {  
         SessionFactory sf = new Configuration().configure().buildSessionFactory();  
         Session s = null;  
         Transaction t = null;  
   
         try{  
             //准备数据  
             User2 um = new User2();  
             um.setName("kdjfal");
             um.setPassword("pass");
             s = sf.openSession();  
             t = s.beginTransaction();  
             s.save(um);  
             t.commit();  
        }catch(Exception err){  
             t.rollback();  
             err.printStackTrace();  
       }finally{  
             s.close();  
       }  
     }  
 }  