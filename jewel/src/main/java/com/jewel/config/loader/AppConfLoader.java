package com.jewel.config.loader;

import com.jewel.config.cache.CnfCache;
import com.jewel.config.conf.AppBeanCnf;

/**
 * 
 * @Description: 加载工程配置信息
 * @author maybo
 * @date 2016年5月25日 上午10:17:38
 *
 */
public class AppConfLoader extends ConfLoader {

	protected static ConfLoader confLoader;

	static {
		confLoader = new AppConfLoader();
	}

	protected static ConfLoader obtain() {
		return confLoader;
	}

	public void load(Object object) {
		CnfCache.addApp((AppBeanCnf) object);
	}

}
