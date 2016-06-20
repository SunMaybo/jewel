package com.jewel.config.loader;

import com.jewel.config.cache.CnfCache;
import com.jewel.config.conf.AppBeanCnf;
import com.jewel.config.conf.ServiceBeanCnf;

/**
 * 
 * @Description: 服务配置信息的加载
 * @author maybo
 * @date 2016年5月25日 上午10:17:18
 *
 */
public class ServiceConfLoader extends ConfLoader {

	private static ConfLoader confLoader;
	static {
		confLoader = new ServiceConfLoader();
	}

	protected static ConfLoader obtain() {
		return confLoader;
	}

	public void load(Object object) {
		ServiceBeanCnf serviceBeanCnf = (ServiceBeanCnf) object;
		AppBeanCnf appBeanCnf = CnfCache.findApp();
		if (null == appBeanCnf) {
			try {
				throw new Exception("application 必須在此之前配置．");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			serviceBeanCnf.setApp(appBeanCnf.getName());
			CnfCache.addService(serviceBeanCnf);
			CnfCache.findPersistenceService(serviceBeanCnf.getRegistry()).saveProvider(serviceBeanCnf);
		}
	}

}
