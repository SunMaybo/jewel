package com.jewel.spring.tag.loader;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;

import com.jewel.config.conf.ServiceBeanCnf;
import com.jewel.config.loader.ConfLoaderFactory;

/**
 * 
 * @Description: 加载服务配置信息
 * @author maybo
 * @date 2016年5月25日 上午10:25:55
 *
 */
public class ServiceConfBeanDefinitionLoader {

	public static BeanDefinition load(Class<?> clssze, String interfaceName, String ref, String version, String registry, ParserContext parserContext) {

		ServiceBeanCnf serviceBeanCnf = new ServiceBeanCnf();

		if (StringUtils.hasText(ref)) {
			serviceBeanCnf.setRef(ref);

		}

		if (StringUtils.hasText(registry)) {

			serviceBeanCnf.setRegistry(registry);

		}

		if (StringUtils.hasText(interfaceName)) {

			serviceBeanCnf.setInterfaceName(interfaceName);

		}

		if (StringUtils.hasText(version)) {

			serviceBeanCnf.setVersion(version);

		}
		ConfLoaderFactory.load(serviceBeanCnf);
		return null;
	}

}
