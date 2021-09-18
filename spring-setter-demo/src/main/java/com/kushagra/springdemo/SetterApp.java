package com.kushagra.springdemo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SetterApp {
	public static void main(String args[]) {
		//Create the application context
		ApplicationContext context=new ClassPathXmlApplicationContext("beans-cp.xml");
		//beans-cp.xml moved to resources as it will be available in classpath
		
		//create the bean
		Organization org=(Organization) context.getBean("myorg");
		
		//Invoke the corporate slogan
		org.coroprateSlogan();
		
		//Print Organization Details
		System.out.println(org);
		
		
		//Close the application context
		((ClassPathXmlApplicationContext)context).close();
	}
}
