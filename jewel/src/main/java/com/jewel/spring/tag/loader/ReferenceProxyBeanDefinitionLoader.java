package com.jewel.spring.tag.loader;

import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;

import com.jewel.beanclass.ProxyClassFactory;

/**
 * 
 * @Description: 代理bean的加载
 * @author maybo
 * @date 2016年5月25日 上午10:26:57
 *
 */
public class ReferenceProxyBeanDefinitionLoader {

	public static void Load(String interfaceName, String version, String name, ParserContext parserContext) {

		ConstructorArgumentValues cargs = new ConstructorArgumentValues();
		cargs.addGenericArgumentValue(interfaceName);
		cargs.addGenericArgumentValue(version);
		RootBeanDefinition proxyFactoryBean = null;
		try {
			proxyFactoryBean = new RootBeanDefinition(Class.forName(interfaceName), cargs, null);
			if (parserContext.getRegistry().containsBeanDefinition("proxyClassFactory")) {

			} else {
				parserContext.getRegistry().registerBeanDefinition("proxyClassFactory", new RootBeanDefinition(ProxyClassFactory.class));
			}
			proxyFactoryBean.setFactoryBeanName("proxyClassFactory");
			proxyFactoryBean.setFactoryMethodName("create");
			proxyFactoryBean.setAutowireCandidate(true);
			proxyFactoryBean.setAutowireMode(AbstractBeanDefinition.DEPENDENCY_CHECK_ALL);
			proxyFactoryBean.setTargetType(Class.forName(interfaceName));// 自动注入实现
			parserContext.getRegistry().registerBeanDefinition(name, proxyFactoryBean);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
