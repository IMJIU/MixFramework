package com.framework.hibernate;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;  
  
/** 
 * 将hbm生成ddl 
 * @author liang 
 * 
 */  
public class ExportDB{    
    public static void main(String[]args){  
//    	t1_xml();
    	  
        t02_ano();  
    }

	private static void t02_ano() {
		Configuration cfg = new AnnotationConfiguration().configure();  
        SchemaExport export = new SchemaExport(cfg);  
        export.create(true, true);
	}

	private static void t1_xml() {
		System.out.println("why");
        //默认读取hibernate.cfg.xml文件  
        Configuration cfg = new Configuration().configure();  
        ////生成并输出sql到文件（当前目录）和数据库  
        SchemaExport export = new SchemaExport(cfg);  
        export.create(true, true);
	}  
}  