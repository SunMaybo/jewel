package com.jewel.spring.tag.parser;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import com.jewel.spring.tag.loader.ServiceConfBeanDefinitionLoader;

public class ServiceBeanDefinitionParser implements BeanDefinitionParser {

	private Class<?> clssze;

	public ServiceBeanDefinitionParser(Class<?> cls) {
		this.clssze = cls;
	}

	public BeanDefinition parse(Element element, ParserContext parserContext) {
		
		String interfaceName = element.getAttribute("interface");

		String version = element.getAttribute("version");

		String ref = element.getAttribute("ref");

		String registry = element.getAttribute("registry");

		return ServiceConfBeanDefinitionLoader.load( clssze,interfaceName, ref, version, registry, parserContext);
	}
}