package com.jewel.cluster;

import java.util.List;

import com.jewel.protocol.JewelProtocolPool;

/**
 * 
 * @Description: 轮询算法
 * @author maybo
 * @date 2016年5月17日 下午4:04:57
 *
 */
public class PollingStrategy extends AbstractLoadCluster {

	private int flag = 0;// 标志

	public PollingStrategy(List<JewelProtocolPool> jewelProtocolPools) {
		super(jewelProtocolPools);
	}

	public synchronized JewelProtocolPool loadProtocolPool() {
		if (null == jewelProtocolPools) {
			throw new NullPointerException("服务没有注册，未找到启动服务．");
		}
		JewelProtocolPool jewelProtocolPool = jewelProtocolPools.get(flag);

		flag++;

		if (flag >= jewelProtocolPools.size()) {
			flag = 0;// 會到首部
		}

		return jewelProtocolPool;
	}

	public JewelProtocolPool loadProtocolPool(String key) {
		// TODO Auto-generated method stub
		return null;
	}

}
