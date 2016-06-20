package com.jewel.spring.tag.loader;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;

import com.jewel.config.conf.ReferenceBeanCnf;
import com.jewel.config.loader.ConfLoaderFactory;

/**
 * 
 * @Description: 架加载远程依赖信息
 * @author maybo
 * @date 2016年5月25日 上午10:26:19
 *
 */
public class ReferenceConfBeanDefinitionLoader {

	public static BeanDefinition load(Class<?> clssze, String interfaceName, String version, String registry, String name, ParserContext parserContext) {
		ReferenceBeanCnf referenceBeanCnf = new ReferenceBeanCnf();
		if (StringUtils.hasText(interfaceName)) {

			referenceBeanCnf.setInterfaceName(interfaceName);

		}

		if (StringUtils.hasText(version)) {

			referenceBeanCnf.setVersion(version);

		}

		if (StringUtils.hasText(registry)) {

			referenceBeanCnf.setRegistry(registry);

		}
		ConfLoaderFactory.load(referenceBeanCnf);
		return null;
	}

}
