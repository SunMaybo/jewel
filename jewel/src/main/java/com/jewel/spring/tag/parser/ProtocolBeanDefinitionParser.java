package com.jewel.spring.tag.parser;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import com.jewel.config.conf.ProviderBeanCnf;
import com.jewel.config.loader.ConfLoaderFactory;

public class ProtocolBeanDefinitionParser implements BeanDefinitionParser {

	private Class<?> clssze;

	public ProtocolBeanDefinitionParser(Class<?> cls) {
		this.clssze = cls;
	}

	public BeanDefinition parse(Element element, ParserContext parserContext) {
		RootBeanDefinition beanDefinition = new RootBeanDefinition();
		beanDefinition.setBeanClass(clssze);

		String name = element.getAttribute("name");

		String registry = element.getAttribute("registry");

		String port = element.getAttribute("port");
		
		ProviderBeanCnf providerBeanCnf=new ProviderBeanCnf();
		

		if (StringUtils.hasText(name)) {

			providerBeanCnf.setName(name);

		}

		if (StringUtils.hasText(registry)) {
			providerBeanCnf.setRegistry(registry);

		}

		if (StringUtils.hasText(port)) {

			providerBeanCnf.setPort(Integer.valueOf(port));

		}
	
		ConfLoaderFactory.load(providerBeanCnf);
		return null;
	}
}