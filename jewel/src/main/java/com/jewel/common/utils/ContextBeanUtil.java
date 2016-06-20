package com.jewel.common.utils;

import com.jewel.config.cache.CnfCache;
import com.jewel.config.conf.ServiceBeanCnf;
import com.jewel.spring.holder.SpringContextHolder;

public class ContextBeanUtil {

	public static Object getBean(String interfaceName) {
		ServiceBeanCnf beanCnf = (ServiceBeanCnf) CnfCache.findServiceCnf(interfaceName, null);
		if (null == beanCnf) {
			return null;
		} else {
			String name = beanCnf.getRef();
			return SpringContextHolder.getBean(name);
		}
	}

	public static Object getBean(String interfaceName, String version) {
		ServiceBeanCnf beanCnf = (ServiceBeanCnf) CnfCache.findServiceCnf(interfaceName, version);
		if (null == beanCnf) {
			return null;
		} else {
			String name = beanCnf.getRef();
			return SpringContextHolder.getBean(name);
		}
	}

}
