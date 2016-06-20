package com.jewel.spring.tag.parser;

import java.io.IOException;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import com.jewel.annotation.JewelRemote;
import com.jewel.annotation.JewelService;
import com.jewel.spring.tag.loader.AnnotationBeanDefinitionLoader;

public class JewelScanBeanDefaultParser implements BeanDefinitionParser {

	private static final String RESOURCE_PATTERN = "/**/*.class";

	private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

	public BeanDefinition parse(Element element, ParserContext parserContext) {
		String basePackage = element.getAttribute("base-package");
		if (StringUtils.hasText(basePackage)) {
			try {
				String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(basePackage)
						+ RESOURCE_PATTERN;
				Resource[] resources = resourcePatternResolver.getResources(pattern);
				MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);
				for (Resource resource : resources) {
					if (resource.isReadable()) {
						MetadataReader reader = readerFactory.getMetadataReader(resource);
						String className = reader.getClassMetadata().getClassName();
						Class<?> clazz = Class.forName(className);
						JewelService jewelService = clazz.getAnnotation(JewelService.class);
						if (null != jewelService) {
							AnnotationBeanDefinitionLoader.load(jewelService, clazz, parserContext);
						}
						JewelRemote jewelRemote = clazz.getAnnotation(JewelRemote.class);
						if (null != jewelRemote) {
							AnnotationBeanDefinitionLoader.load(jewelRemote, clazz, parserContext);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return null;
	}

}
