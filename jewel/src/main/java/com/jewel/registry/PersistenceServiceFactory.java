package com.jewel.registry;

import java.io.IOException;

import com.jewel.config.conf.RegistryBeanCnf;
import com.jewel.registry.zookeeper.ZooKeeperPersistenceServiceImpl;

/**
 * 
 * @Description: 注册工厂用于选择不同的数据库
 * @author maybo
 * @date 2016年5月17日 下午3:54:55
 *
 */
public class PersistenceServiceFactory {

	private static final String ZOOKEEPER = "zookeeper";

	public static PersistenceService create(RegistryBeanCnf registryBeanCnf) {

		PersistenceService persistenceService = null;
		if (null != registryBeanCnf.getAddress()) {
			String[] addressSplit = registryBeanCnf.getAddress().split("://");
			if (addressSplit[0].equals(ZOOKEEPER)) {
				persistenceService = new ZooKeeperPersistenceServiceImpl();
				registryBeanCnf.setAddress(addressSplit[1]);
				try {
					persistenceService.registry(registryBeanCnf);
					persistenceService
							.addSyncDataListener(new SyncEventListener());
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
		return persistenceService;
	}

}
