package com.jewel.cluster;

import java.util.List;
import com.jewel.protocol.JewelProtocolPool;

/**
 * 
 * @Description: 加权轮询算法
 * @author maybo
 * @date 2016年5月17日 下午4:06:04
 *
 */
public class WeightPollingStrategy extends AbstractLoadCluster {

	private int flag;// 標誌位
	private int frequency;// 使用次数

	public WeightPollingStrategy(List<JewelProtocolPool> jewelProtocolPools) {
		super(jewelProtocolPools);
	}

	public JewelProtocolPool loadProtocolPool() {
		if (null == jewelProtocolPools) {
			throw new NullPointerException("服务没有注册，未找到启动服务．");
		}
		JewelProtocolPool jewelProtocolPool = jewelProtocolPools.get(flag);
		if (jewelProtocolPool.getWeight() <= 0) {

			flag++;

			if (flag >= jewelProtocolPools.size()) {
				flag = 0;// 會到首部
			}
		} else {
			frequency++;
			if (frequency >= jewelProtocolPool.getWeight()) {
				frequency = 0;
				flag++;
				if (flag >= jewelProtocolPools.size()) {
					flag = 0;// 會到首部
				}
			}

		}

		return jewelProtocolPool;
	}

	@Override
	public JewelProtocolPool loadProtocolPool(String key) {
		// TODO Auto-generated method stub
		return null;
	}

}
