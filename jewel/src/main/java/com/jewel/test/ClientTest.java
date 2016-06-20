package com.jewel.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClientTest {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		IDemo demo = (IDemo) ctx.getBean("demo");
		System.out.println("feffeff----"+demo.hello());
	}

}
