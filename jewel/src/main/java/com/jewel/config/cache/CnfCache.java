package com.jewel.config.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jewel.config.conf.AppBeanCnf;
import com.jewel.config.conf.ServiceBeanCnf;
import com.jewel.registry.PersistenceService;

/**
 * 
 * @Description: 配置文件缓存类
 * @author maybo
 * @date 2016年5月17日 下午3:08:49
 *
 */
public class CnfCache {

	private static final Logger LOGGER = LoggerFactory.getLogger(CnfCache.class);

	private static Map<String, Object> cnfMap = new HashMap<String, Object>();// 用于数据的缓存

	// 来自数据库持久化服务发现
	public static void addPersistenceService(PersistenceService persistenceService,String name) {
		cnfMap.put(name, persistenceService);
	}

	public static PersistenceService findPersistenceService(String name) {
		return (PersistenceService) cnfMap.get(name);
	}

	public static synchronized void addApp(AppBeanCnf appBeanCnf) {
		cnfMap.put("app", appBeanCnf);
	}

	/**
	 * 
	 * @Description: 添加服务的配置信息----非线程安全方法
	 * @param @param serviceBeanCnf 服务配置类
	 * @return void 返回类型
	 */
	public static void addService(ServiceBeanCnf serviceBeanCnf) {
		LOGGER.info("加载服务：" + serviceBeanCnf.toString());
		if (null == serviceBeanCnf.getVersion() || serviceBeanCnf.getVersion().length() <= 0) {
			cnfMap.put(serviceBeanCnf.getInterfaceName(), serviceBeanCnf);
			LOGGER.info("加载服务：" + cnfMap.get(serviceBeanCnf.getInterfaceName()).toString());
		} else {
			cnfMap.put(serviceBeanCnf.getInterfaceName() + serviceBeanCnf.getVersion(), serviceBeanCnf);
		}

	}

	/**
	 * 
	 * @Description: 添加服务的配置信息----线程安全方法
	 * @param @param serviceBeanCnf 服务配置类
	 * @return void 返回类型
	 */
	public static synchronized void pushService(ServiceBeanCnf serviceBeanCnf) {
		LOGGER.info("加载服务：" + serviceBeanCnf.toString());
		if (null == serviceBeanCnf.getVersion() || serviceBeanCnf.getVersion().length() <= 0) {
			cnfMap.put(serviceBeanCnf.getInterfaceName(), serviceBeanCnf);
			LOGGER.info("加载服务：" + cnfMap.get(serviceBeanCnf.getInterfaceName()).toString());
		} else {
			cnfMap.put(serviceBeanCnf.getInterfaceName() + serviceBeanCnf.getVersion(), serviceBeanCnf);
		}

	}

	/**
	 * 
	 * @Description: 添加服务配置------非线程安全
	 * @param @param beanCnfs 配置类
	 * @return void 返回类型
	 */
	public static void addServiceALL(List<ServiceBeanCnf> beanCnfs) {
		for (ServiceBeanCnf beanCnf : beanCnfs) {
			cnfMap.put(beanCnf.getInterfaceName() + beanCnf.getVersion() == null ? "" : beanCnf.getVersion(), beanCnf);
		}
	}

	/**
	 * 
	 * @Description: 添加服务配置------线程安全
	 * @param @param beanCnfs 配置类
	 * @return void 返回类型
	 */
	public static synchronized void pushServiceALL(List<ServiceBeanCnf> beanCnfs) {
		for (ServiceBeanCnf beanCnf : beanCnfs) {
			cnfMap.put(beanCnf.getInterfaceName() + beanCnf.getVersion() == null ? "" : beanCnf.getVersion(), beanCnf);
		}
	}

	/**
	 * 
	 * @Description: 发现服务
	 * @param @param interfaceName-----接口名字
	 * @param @param version-----------版本号
	 * @param @return 设定文件
	 * @return ServiceBeanCnf ------------服务配置类
	 */
	public static ServiceBeanCnf findServiceCnf(String interfaceName, String version) {
		ServiceBeanCnf serviceBeanCnf = null;
		if (null != version) {
			serviceBeanCnf = (ServiceBeanCnf) cnfMap.get(interfaceName + version);
		} else {
			serviceBeanCnf = (ServiceBeanCnf) cnfMap.get(interfaceName);
		}
		return serviceBeanCnf;
	}

	/**
	 * 
	 * @Description: 查询服务所在的项目名字
	 * @param @param interfaceName-----------------服务的接口号
	 * @param @param version-----------------------服务的版本号
	 * @param @return 设定文件
	 * @return String appName-----------项目名字
	 */
	public static String findAppFromService(String interfaceName, String version) {
		ServiceBeanCnf beanCnf = null;
		if (null == version || version.length() <= 0) {
			beanCnf = (ServiceBeanCnf) cnfMap.get(interfaceName);
		} else {
			beanCnf = (ServiceBeanCnf) cnfMap.get(interfaceName + version);
		}

		if (null == beanCnf) {
			return null;
		} else {
			return beanCnf.getApp();
		}
	}

	/**
	 * 
	 * @Description: 获取本项目名字
	 * @param @return 设定文件
	 * @return AppBeanCnf ------------工程配置类
	 */
	public static synchronized AppBeanCnf findApp() {
		return (AppBeanCnf) cnfMap.get("app");
	}

}
