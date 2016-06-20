package com.jewel.spring.tag.parser;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import com.jewel.config.conf.AppBeanCnf;
import com.jewel.config.loader.ConfLoaderFactory;
import com.jewel.spring.holder.SpringContextHolder;

/**
 * 
 * @Description: 解析工程名
 * @author maybo
 * @date 2016年5月25日 上午10:14:58
 *
 */
public class AppBeanDefinitionParser implements BeanDefinitionParser {

	private Class<?> clssze;

	public AppBeanDefinitionParser(Class<?> cls) {
		this.clssze = cls;
	}

	public BeanDefinition parse(Element element, ParserContext parserContext) {
		parserContext.getRegistry().registerBeanDefinition(// 静态持有
				"springContextHolder", new RootBeanDefinition(SpringContextHolder.class));
		RootBeanDefinition beanDefinition = new RootBeanDefinition();
		beanDefinition.setBeanClass(clssze);

		String name = element.getAttribute("name");
		AppBeanCnf appBeanCnf = new AppBeanCnf();
		appBeanCnf.setName(name);
		ConfLoaderFactory.load(appBeanCnf);
		return null;
	}
}