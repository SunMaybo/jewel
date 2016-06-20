package com.jewel.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class App {
	
	@Autowired
	private IDemo demo12;
	@Autowired
	private IDemoTest demoTest;
	
	public void test(){
		System.out.println(demo12.hello());
		System.out.println(demoTest.hello());
	}

}
