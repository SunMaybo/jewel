package com.jewel.config.loader;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jewel.cluster.ClusterFactory;
import com.jewel.config.cache.CnfCache;
import com.jewel.config.conf.ReferenceBeanCnf;
import com.jewel.config.conf.ServiceBeanCnf;
import com.jewel.protocol.JewelProtocolPool;

/**
 * 
 * @Description: 依赖配置信息
 * @author maybo
 * @date 2016年5月25日 上午10:18:59
 *
 */
public class ReferenceConfLoader extends ConfLoader {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static ConfLoader confLoader;

	static {
		confLoader = new ReferenceConfLoader();
	}

	protected static ConfLoader obtain() {
		return confLoader;
	}

	public void load(Object object) {
		logger.info("加载远程服务配置--------" + object.toString());
		ReferenceBeanCnf referenceBeanCnf = (ReferenceBeanCnf) object;
		// 获取服务配置信息
		ServiceBeanCnf beanCnf = CnfCache.findPersistenceService(referenceBeanCnf.getRegistry()).findService(referenceBeanCnf);

		if (null == beanCnf) {
			logger.warn("服务没有提供----------" + object.toString());
		} else {

			ServiceBeanCnf serviceBeanCnf = (ServiceBeanCnf) CnfCache.findServiceCnf(referenceBeanCnf.getInterfaceName(),
					referenceBeanCnf.getVersion());
			if (null == serviceBeanCnf) {
				// 缓存服务配置信息
				CnfCache.addService(beanCnf);
				// 通过获取提供者，创建集群
				List<JewelProtocolPool> jPools = CnfCache.findPersistenceService(referenceBeanCnf.getRegistry()).findProtocolPools(beanCnf.getApp());
				ClusterFactory.load(beanCnf.getRequestStyle(), jPools, beanCnf.getApp());
			}
		}

	}

}
