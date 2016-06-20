package com.jewel.proxy;

import java.lang.reflect.Proxy;
import com.jewel.cluster.AbstractLoadCluster;
import com.jewel.protocol.JewelInvocationHandler;

public class JdkProxyFactory extends AbstractProxyFactory {
	public Object proxy(String interfaceName, String version,
			AbstractLoadCluster abstractLoadCluster) {
		try {
			return Proxy.newProxyInstance(Class.forName(interfaceName)
					.getClassLoader(), new Class<?>[] { Class
					.forName(interfaceName) }, new JewelInvocationHandler(
					version, interfaceName, abstractLoadCluster));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Object proxy(String interfaceName, String version) {
		try {
			return Proxy.newProxyInstance(Class.forName(interfaceName)
					.getClassLoader(), new Class<?>[] { Class
					.forName(interfaceName) }, new JewelInvocationHandler(
					version, interfaceName));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
