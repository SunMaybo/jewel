package com.jewel.spring.tag.parser;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import com.jewel.config.conf.RegistryBeanCnf;
import com.jewel.config.loader.ConfLoaderFactory;

public class RegistryBeanDefinitionParser implements BeanDefinitionParser {

	public BeanDefinition parse(Element element, ParserContext parserContext) {

		String address = element.getAttribute("address");

		String name = element.getAttribute("name")==""?"registry":element.getAttribute("name");
		
		String check=element.getAttribute("check");
		RegistryBeanCnf registryBeanCnf=new RegistryBeanCnf();

		if (StringUtils.hasText(name)) {

			registryBeanCnf.setName(name);

		}
		if (StringUtils.hasText(address)) {

			registryBeanCnf.setAddress(address);
		}
		if(null==check||"true".equals(check)){
			registryBeanCnf.setCheck(true);
		}
		else{
			registryBeanCnf.setCheck(false);
		}
		ConfLoaderFactory.load(registryBeanCnf);
		return null;
	}
}