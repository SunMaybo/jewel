package com.jewel.spring.tag.parser;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import com.jewel.spring.tag.loader.ReferenceConfBeanDefinitionLoader;
import com.jewel.spring.tag.loader.ReferenceProxyBeanDefinitionLoader;

public class ReferenceBeanDefinitionParser implements BeanDefinitionParser {

	private Class<?> clssze;

	public ReferenceBeanDefinitionParser(Class<?> cls) {
		this.clssze = cls;
	}

	public BeanDefinition parse(Element element, ParserContext parserContext) {

		String interfaceName = element.getAttribute("interface");

		String version = element.getAttribute("version") == null || element.getAttribute("version").length() <= 0 ? "v_0.0.1" : element
				.getAttribute("version");

		String registry = element.getAttribute("registry");

		String name = element.getAttribute("name");
		
		BeanDefinition beanDefinition= ReferenceConfBeanDefinitionLoader.load(clssze, interfaceName, version, registry, name, parserContext);
		
		ReferenceProxyBeanDefinitionLoader.Load(interfaceName, version, name, parserContext);
		return beanDefinition;
	}
}