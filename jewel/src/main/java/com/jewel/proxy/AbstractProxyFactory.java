package com.jewel.proxy;

import com.jewel.cluster.AbstractLoadCluster;

/**
 * 
 * @Description: 动态代理工厂用于选择不同的代理
 * @author maybo
 * @date 2016年5月17日 下午3:53:42
 *
 */
public abstract class AbstractProxyFactory {

//	public static final String JDK_PROXY_NAME = "jdkProxy";
	public static final String JDK_PROXY_NAME = "com.jewel.proxy.JdkProxyFactory";

	public static AbstractProxyFactory create(String name) {
		AbstractProxyFactory apf = null;
		try {
			apf = (AbstractProxyFactory) Class.forName(name).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		/*if (name.equals(JDK_PROXY_NAME)) {
			return new JdkProxyFactory();
		}*/
		return apf;
	}

	public abstract Object proxy(String interfaceName, String version, AbstractLoadCluster abstractLoadCluster);

	public abstract Object proxy(String interfaceName, String version);
}
