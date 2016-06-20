package com.jewel.beanclass;

import com.jewel.proxy.AbstractProxyFactory;

public class ProxyClassFactory {

	public ProxyClassFactory() {
		// TODO Auto-generated constructor stub
	}

	public ProxyClassFactory(String interfaceName, String version) {
	
	}

	public  Object create(String interfaceName,String version) {
		AbstractProxyFactory abstractProxyFactory = AbstractProxyFactory
				.create(AbstractProxyFactory.JDK_PROXY_NAME);
		return abstractProxyFactory.proxy(interfaceName, version);
	}
}
