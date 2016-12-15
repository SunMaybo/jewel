package com.jewel.cluster;

import java.util.Iterator;
import java.util.List;

import com.jewel.protocol.JewelProtocolPool;

public abstract class AbstractLoadCluster {

	protected List<JewelProtocolPool> jewelProtocolPools;

	public AbstractLoadCluster(List<JewelProtocolPool> jewelProtocolPools) {
		this.jewelProtocolPools = jewelProtocolPools;
	}

	public List<JewelProtocolPool> getJewelProtocolPools() {
		return jewelProtocolPools;
	}

	public abstract JewelProtocolPool loadProtocolPool();

	public abstract JewelProtocolPool loadProtocolPool(String key);

	public synchronized void addNode(JewelProtocolPool jewelProtocolPool) {
		this.jewelProtocolPools.add(jewelProtocolPool);

	}

	public synchronized void addAllNode(
			List<JewelProtocolPool> jewelProtocolPools) {
		this.jewelProtocolPools.addAll(jewelProtocolPools);
	}

	public synchronized void deleteNode(JewelProtocolPool jewelProtocolPool) {
		Iterator<JewelProtocolPool> iterator = this.jewelProtocolPools
				.iterator();
		while (iterator.hasNext()) {
			JewelProtocolPool protocolPool = iterator.next();
			if (protocolPool.getHost().equals(jewelProtocolPool.getHost())
					&& protocolPool.getPort() == jewelProtocolPool.getPort()) {
				iterator.remove();
			}
		}
	}

}
