package com.jewel.spring.tag.loader;

import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;

import com.jewel.annotation.JewelRemote;
import com.jewel.annotation.JewelService;
import com.jewel.config.conf.ReferenceBeanCnf;
import com.jewel.config.conf.ServiceBeanCnf;
import com.jewel.spring.tag.holder.BeanNameHolder;

public class AnnotationBeanDefinitionLoader {

	public static void load(Object obj, Class<?> clazz, ParserContext parserContext) {
		if (obj instanceof JewelRemote) {// 消费者加载类
			JewelRemote jewelRemote = (JewelRemote) obj;
			String name = jewelRemote.name();
			String registry = jewelRemote.registry();
			String interfaceName = clazz.getName();
			String version = jewelRemote.version();
			if (!StringUtils.hasText(name)) {
				name = BeanNameHolder.beanNameResolve(clazz.getName());
			}
			ReferenceConfBeanDefinitionLoader.load(ReferenceBeanCnf.class, interfaceName, version, registry, name, parserContext);
			ReferenceProxyBeanDefinitionLoader.Load(interfaceName, version, name, parserContext);
		}
		if (obj instanceof JewelService) {// 服务配置类
			JewelService jewelService = (JewelService) obj;
			String registry = jewelService.registry();
			String version = jewelService.version();
			String ref = jewelService.name();
			if (!StringUtils.hasText(ref)) {
				ref = BeanNameHolder.beanNameResolve(clazz.getName());
			}
			Class<?>[] interfaceClazzArray = clazz.getInterfaces();
			for (int i = 0; null != interfaceClazzArray && i < interfaceClazzArray.length; i++) {
				ServiceConfBeanDefinitionLoader.load(ServiceBeanCnf.class, interfaceClazzArray[i].getName(), ref, version, registry, parserContext);
			}
			ServiceConfBeanDefinitionLoader.load(ServiceBeanCnf.class, clazz.getName(), ref, version, registry, parserContext);
			ServiceReactBeanDefinitionLoader.load(ref, clazz, parserContext);

		}
	}
}
