package com.jewel.cluster;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jewel.protocol.JewelProtocolPool;

public class ClusterFactory {
	public static final String CONSISTENT_HASH_STRATEGY = "hash";
	public static final String POLLING_STRATEGY = "polling";
	public static final String RANDOM_STRATEGY = "random";
	public static final String WEIGHT_POLLING_STRATEGY = "weightPolling";

	public static final Logger LOGGER = LoggerFactory.getLogger(ClusterFactory.class);
	public static Map<String, AbstractLoadCluster> clusters = new HashMap<String, AbstractLoadCluster>();

	public static int Replicas = 100;
	
	public static void load(String order, List<JewelProtocolPool> jewelProtocolPools, String app) {
		if (null != clusters.get(app)) {
			return;
		}
		AbstractLoadCluster abstractLoadCluster = null;
		if (order.equals(CONSISTENT_HASH_STRATEGY)) {
			abstractLoadCluster = new ConsistentHashStrategy(Replicas, jewelProtocolPools);
		} else if (order.equals(POLLING_STRATEGY)) {
			abstractLoadCluster = new PollingStrategy(jewelProtocolPools);
		} else if (order.equals(RANDOM_STRATEGY)) {
			abstractLoadCluster = new RandomStrategy(jewelProtocolPools);
		} else if (order.equals(WEIGHT_POLLING_STRATEGY)) {
			abstractLoadCluster = new WeightPollingStrategy(jewelProtocolPools);
		}
		if (null != abstractLoadCluster) {
			clusters.put(app, abstractLoadCluster);
		}
	}

	public static AbstractLoadCluster obtainCluster(String app) {
		if (null == app) {
			return null;
		}
		return clusters.get(app);
	}

	/**
	 * 
	 * @Description: 同步服务结点
	 * @param @param app 设定文件
	 * @param @param node
	 * @return void 返回类型
	 */
	public static  void syncClusters(String app, List<JewelProtocolPool> node) {
		AbstractLoadCluster abstractLoadCluster = clusters.get(app);
		if (null != abstractLoadCluster) {
			List<JewelProtocolPool> protocolPools = abstractLoadCluster.jewelProtocolPools;
			if(protocolPools.size()<=0){
				abstractLoadCluster.addAllNode(node);
			}else if (node.size() > protocolPools.size()) {

				Iterator<JewelProtocolPool> newIterator = node.iterator();
				while (newIterator.hasNext()) {
					JewelProtocolPool newPool = newIterator.next();
					Iterator<JewelProtocolPool> oldIterator = protocolPools.iterator();
					while (oldIterator.hasNext()) {
						JewelProtocolPool oldPool = oldIterator.next();
						if (!newPool.getHost().equals(oldPool.getHost()) || newPool.getPort() != oldPool.getPort()) {
							abstractLoadCluster.addNode(newPool);
						}
					}

				}
			} else if (node.size() < protocolPools.size()) {
				Iterator<JewelProtocolPool> oldIterator = protocolPools.iterator();
				while (oldIterator.hasNext()) {
					JewelProtocolPool oldPool = oldIterator.next();
					Iterator<JewelProtocolPool> newIterator = node.iterator();
					while (newIterator.hasNext()) {
						JewelProtocolPool newPool = newIterator.next();
						if (!newPool.getHost().equals(oldPool.getHost()) || newPool.getPort() != oldPool.getPort()) {
							abstractLoadCluster.deleteNode(oldPool);
						}
					}

				}
			}

		}
	}
}
