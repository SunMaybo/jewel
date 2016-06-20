package com.jewel.test;

import com.jewel.annotation.JewelService;

@JewelService(version="v_1.1.0")
public class DemoTest implements IDemoTest {

	public String hello() {
		return "Hello World";
	}
}
