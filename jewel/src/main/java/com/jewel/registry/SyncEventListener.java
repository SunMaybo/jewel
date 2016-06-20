package com.jewel.registry;

import java.util.EventListener;
import java.util.List;

import com.jewel.cluster.ClusterFactory;
import com.jewel.config.cache.CnfCache;
import com.jewel.config.conf.ServiceBeanCnf;
import com.jewel.protocol.JewelProtocolPool;

public class SyncEventListener implements EventListener {

	/**
	 * 
	 * @Description: 数据一致性处理
	 * @param @param event 设定文件
	 * @return void 返回类型
	 */
	@SuppressWarnings("unchecked")
	public void syncCacheEvent(SyncEvent event) {
		SyncData syncData = (SyncData) event.getSource();
		switch (syncData.state) {
		case SyncData.SYNC_ADD_SERVICE_CNF:// 添加服务配置
			
			ServiceBeanCnf serviceBeanCnf = (ServiceBeanCnf) syncData
					.getObject();
			
			CnfCache.pushService(serviceBeanCnf);
			
			if (null == ClusterFactory.clusters.get(serviceBeanCnf.getApp())) {
				ClusterFactory.load(serviceBeanCnf.getRequestStyle(), CnfCache
						.findPersistenceService(serviceBeanCnf.getRegistry())
						.findProtocolPools(serviceBeanCnf.getApp()),
						serviceBeanCnf.getApp());// 加载服务提供者结点
			}
			
			break;
		case SyncData.SYNC_ALTER_SERVICE_CNF:// 修改服务配置
			
			CnfCache.pushService((ServiceBeanCnf) syncData.getObject());
			break;
			
		case SyncData.SYNC_PROTOCOL_POOL:// 变更集群信息
			
			ClusterFactory.syncClusters(syncData.getApp(),
					(List<JewelProtocolPool>) syncData.getObject());
			break;
		}
	}
}
