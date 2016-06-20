package com.jewel.test;

import org.apache.log4j.PropertyConfigurator;

public class LoadLog4j {

	public static void load(){
		 PropertyConfigurator.configure(System.getProperty("user.dir")+"/src/main/java"+"/log4j.properties");
	}
}
