package com.jewel.spring.tag.loader;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;

/**
 * 
 * @Description: 服务bean加载类
 * @author maybo
 * @date 2016年5月24日 下午4:28:29
 *
 */
public class ServiceReactBeanDefinitionLoader {

	public static void load(String name, Class<?> clazz, ParserContext parserContext) {
		RootBeanDefinition beanDefinition = new RootBeanDefinition(clazz);
		beanDefinition.setAutowireCandidate(true);
		beanDefinition.setAutowireMode(AbstractBeanDefinition.DEPENDENCY_CHECK_ALL);
		beanDefinition.setTargetType(clazz);
		parserContext.getRegistry().registerBeanDefinition(name, beanDefinition);
	}

}
