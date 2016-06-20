package com.jewel.test.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


public class MyNamespaceHandler extends NamespaceHandlerSupport {  

    public void init() {  

        registerBeanDefinitionParser("people", new PeopleBeanDefinitionParser());  

    }  

}  