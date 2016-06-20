package com.jewel.config.loader;

import com.jewel.config.conf.AppBeanCnf;
import com.jewel.config.conf.ProviderBeanCnf;
import com.jewel.config.conf.ReferenceBeanCnf;
import com.jewel.config.conf.RegistryBeanCnf;
import com.jewel.config.conf.ServiceBeanCnf;

/**
 * 
 * @Description: 配置信息加载工厂
 * @author maybo
 * @date 2016年5月25日 上午10:17:56
 *
 */
public class ConfLoaderFactory {
	public static final int CONF_APP = 1;
	public static final int CONF_REGISTRY = 2;
	public static final int CONF_PROVIDER = 3;
	public static final int CONF_SERVICE = 4;
	public static final int CONF_REFERENCE = 5;

	public static void load(Object object) {
		if (object instanceof AppBeanCnf) {
			AppConfLoader.obtain().load(object);
		}
		if (object instanceof RegistryBeanCnf) {
			RegistryConfLoader.obtain().load(object);
		}
		if (object instanceof ProviderBeanCnf) {
			ProviderLoaderLoader.obtain().load(object);
		}
		if (object instanceof ServiceBeanCnf) {
			ServiceConfLoader.obtain().load(object);
		}
		if (object instanceof ReferenceBeanCnf) {
			ReferenceConfLoader.obtain().load(object);
		}
	}

}
