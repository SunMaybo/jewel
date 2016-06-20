package com.jewel.cluster;

import java.util.List;
import java.util.Random;

import com.jewel.protocol.JewelProtocolPool;

/**
 * 
 * @Description: 随机数算法
 * @author maybo
 * @date 2016年5月17日 下午4:05:48
 *
 */
public class RandomStrategy extends AbstractLoadCluster {

	private Random random = new Random();

	public RandomStrategy(List<JewelProtocolPool> jewelProtocolPools) {
		super(jewelProtocolPools);
	}

	public JewelProtocolPool loadProtocolPool() {
		if (null == jewelProtocolPools) {
			throw new NullPointerException("的服务没有注册，未找到启动服务．");
		}
		int index = random.nextInt(jewelProtocolPools.size());
		return jewelProtocolPools.get(index);
	}

	public JewelProtocolPool loadProtocolPool(String key) {
		// TODO Auto-generated method stub
		return null;
	}

}
