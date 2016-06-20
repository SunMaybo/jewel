package com.jewel.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jewel.spring.holder.SpringContextHolder;

public class Main {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		System.out.println(SpringContextHolder.getApplicationContext());
	}

}
