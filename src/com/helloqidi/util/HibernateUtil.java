package com.helloqidi.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {

	private static SessionFactory sessionFactory;
	
	static{
		try{
			sessionFactory=new AnnotationConfiguration().configure().buildSessionFactory();
			System.out.println("OK");
		}catch(Exception e){
			System.err.println("≥ı ºªØSesssionFactory ß∞‹");
			e.printStackTrace();
		}
	}
	
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}
}