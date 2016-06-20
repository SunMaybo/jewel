package com.jewel.common.utils;

/**
 * 
 * @获取服务实例
 * @author maybo
 * @date 2016年5月10日 下午1:13:04
 *
 */
public interface ContextBeanService {

	public Object getBean(String interfaceName);

	public Object getBean(Class<?> clazz);

	public Object getBean(String interfaceName, String version);

}
