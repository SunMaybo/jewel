package com.jewel.test.jewel;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jewel.test.App;
import com.jewel.test.Demo;
import com.jewel.test.IDemo;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			PropertyConfigurator.configure(System.getProperty("user.dir") + "/src/main/java" + "/log4j.properties");
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:client.xml");
			App app = (App) applicationContext.getBean(App.class);
			while (true) {
				app.test();
				Thread.sleep(5000);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
