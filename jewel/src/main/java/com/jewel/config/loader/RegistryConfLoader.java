package com.jewel.config.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jewel.config.cache.CnfCache;
import com.jewel.config.conf.RegistryBeanCnf;
import com.jewel.registry.PersistenceService;
import com.jewel.registry.PersistenceServiceFactory;

/**
 * 
 * @Description: 注册配置信息加载
 * @author maybo
 * @date 2016年5月25日 上午10:19:29
 *
 */
public class RegistryConfLoader extends ConfLoader {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static ConfLoader confLoader;
	static {
		confLoader = new RegistryConfLoader();
	}

	protected static ConfLoader obtain() {
		return confLoader;
	}

	public void load(Object object) {
		RegistryBeanCnf registryBeanCnf = (RegistryBeanCnf) object;
		PersistenceService persistenceService;
		persistenceService = CnfCache.findPersistenceService(registryBeanCnf.getName());
		if (null == persistenceService) {
			persistenceService = PersistenceServiceFactory.create(registryBeanCnf);
			if (null != persistenceService) {
				CnfCache.addPersistenceService(persistenceService, registryBeanCnf.getName());// 缓存
			}
		} else {
			logger.warn("已经注册过，不能重复注册" + this.toString());
		}
	}

}
