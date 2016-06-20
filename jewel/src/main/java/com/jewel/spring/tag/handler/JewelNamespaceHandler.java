package com.jewel.spring.tag.handler;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import com.jewel.config.conf.AppBeanCnf;
import com.jewel.config.conf.ProviderBeanCnf;
import com.jewel.config.conf.ReferenceBeanCnf;
import com.jewel.config.conf.ServiceBeanCnf;
import com.jewel.spring.tag.parser.AppBeanDefinitionParser;
import com.jewel.spring.tag.parser.JewelScanBeanDefaultParser;
import com.jewel.spring.tag.parser.ProtocolBeanDefinitionParser;
import com.jewel.spring.tag.parser.ReferenceBeanDefinitionParser;
import com.jewel.spring.tag.parser.RegistryBeanDefinitionParser;
import com.jewel.spring.tag.parser.ServiceBeanDefinitionParser;

public class JewelNamespaceHandler extends NamespaceHandlerSupport {

	public void init() {
		registerBeanDefinitionParser("application", new AppBeanDefinitionParser(AppBeanCnf.class));

		registerBeanDefinitionParser("registry", new RegistryBeanDefinitionParser());

		registerBeanDefinitionParser("protocol", new ProtocolBeanDefinitionParser(ProviderBeanCnf.class));

		registerBeanDefinitionParser("jewel-scan", new JewelScanBeanDefaultParser());
		
		registerBeanDefinitionParser("service", new ServiceBeanDefinitionParser(ServiceBeanCnf.class));

		registerBeanDefinitionParser("reference", new ReferenceBeanDefinitionParser(ReferenceBeanCnf.class));
		

	}

}