package com.jewel.test.jewel;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jewel.rpc.server.RPCServer;

public class Server {

	public static void main(String[] args) {
		try {
			 PropertyConfigurator.configure(System.getProperty("user.dir")+"/src/main/java"+"/log4j.properties");
			 ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:server.xml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
