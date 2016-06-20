package com.jewel.cluster;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import com.jewel.protocol.JewelProtocolPool;
import com.jewel.utils.UUIDUtils;

/**
 * 
 * @Description: hash一致算法
 * @author maybo
 * @date 2016年5月17日 下午4:05:25
 *
 */
public class ConsistentHashStrategy extends AbstractLoadCluster {
	/** Hash计算对象，用于自定义hash算法 */
	HashFunc hashFunc;
	/** 复制的节点个数 */
	private final int numberOfReplicas;
	/** 一致性Hash环 */
	private final SortedMap<Integer, JewelProtocolPool> circle = new TreeMap<Integer, JewelProtocolPool>();

	public static void main(String[] args) {
		List<JewelProtocolPool> jewelProtocolPools = new ArrayList<JewelProtocolPool>();
		JewelProtocolPool jewelProtocolPool = new JewelProtocolPool(3);
		jewelProtocolPool.setHost("111331");
		jewelProtocolPools.add(jewelProtocolPool);
		JewelProtocolPool jewelProtocolPool2 = new JewelProtocolPool(3);
		jewelProtocolPool2.setHost("2245522");
		jewelProtocolPools.add(jewelProtocolPool2);
		JewelProtocolPool jewelProtocolPool3 = new JewelProtocolPool(3);
		jewelProtocolPool3.setHost("33333");
		jewelProtocolPools.add(jewelProtocolPool3);
		AbstractLoadCluster hashStrategy = new ConsistentHashStrategy(100, jewelProtocolPools);
		System.out.println(hashStrategy.loadProtocolPool("dfdwqrtwttff").getHost());
		System.out.println(hashStrategy.loadProtocolPool("212452523562sfa125").getHost());
		hashStrategy.deleteNode(jewelProtocolPool2);
		System.out.println("-------------------------------------------------------------");
		System.out.println(hashStrategy.loadProtocolPool(UUIDUtils.findUUID()).getHost());
		System.out.println(hashStrategy.loadProtocolPool(UUIDUtils.findUUID()).getHost());
		System.out.println(hashStrategy.loadProtocolPool(UUIDUtils.findUUID()).getHost());
		System.out.println(hashStrategy.loadProtocolPool(UUIDUtils.findUUID()).getHost());
		System.out.println(hashStrategy.loadProtocolPool(UUIDUtils.findUUID()).getHost());
		System.out.println(hashStrategy.loadProtocolPool(UUIDUtils.findUUID()).getHost());
		System.out.println(hashStrategy.loadProtocolPool(UUIDUtils.findUUID()).getHost());
		System.out.println(hashStrategy.loadProtocolPool(UUIDUtils.findUUID()).getHost());
		System.out.println(hashStrategy.loadProtocolPool(UUIDUtils.findUUID()).getHost());
		System.out.println(hashStrategy.loadProtocolPool(UUIDUtils.findUUID()).getHost());
		System.out.println(hashStrategy.loadProtocolPool(UUIDUtils.findUUID()).getHost());
		System.out.println(hashStrategy.loadProtocolPool(UUIDUtils.findUUID()).getHost());
		System.out.println(hashStrategy.loadProtocolPool(UUIDUtils.findUUID()).getHost());
		System.out.println(hashStrategy.loadProtocolPool(UUIDUtils.findUUID()).getHost());

		System.out.println(hashStrategy.loadProtocolPool(UUIDUtils.findUUID()).getHost());
		System.out.println(hashStrategy.loadProtocolPool(UUIDUtils.findUUID()).getHost());
		System.out.println(hashStrategy.loadProtocolPool(UUIDUtils.findUUID()).getHost());
		System.out.println(hashStrategy.loadProtocolPool(UUIDUtils.findUUID()).getHost());

	}

	/**
	 * 构造，使用Java默认的Hash算法
	 * 
	 * @param numberOfReplicas
	 *            复制的节点个数，增加每个节点的复制节点有利于负载均衡
	 * @param nodes
	 *            节点对象
	 */
	public ConsistentHashStrategy(int numberOfReplicas, List<JewelProtocolPool> jewelProtocolPools) {
		super(jewelProtocolPools);
		this.numberOfReplicas = numberOfReplicas;
		this.hashFunc = new HashFunc() {

			public Integer hash(Object key) {
				String data = key.toString();
				// 默认使用FNV1hash算法
				final int p = 16777619;
				int hash = (int) 2166136261L;
				for (int i = 0; i < data.length(); i++)
					hash = (hash ^ data.charAt(i)) * p;
				hash += hash << 13;
				hash ^= hash >> 7;
				hash += hash << 3;
				hash ^= hash >> 17;
				hash += hash << 5;
				return hash;
			}
		};
		// 初始化节点
		for (JewelProtocolPool node : jewelProtocolPools) {
			addNode(node);
		}
	}
	@Override
	public synchronized void addNode(JewelProtocolPool node) {
		for (int i = 0; i < numberOfReplicas; i++) {
			circle.put(hashFunc.hash(node.getId() + i), node);
		}
	}

	@Override
	public synchronized void addAllNode(List<JewelProtocolPool> jewelProtocolPools) {
		for (int i = 0; null != jewelProtocolPools && i < jewelProtocolPools.size(); i++) {
			this.addNode(jewelProtocolPools.get(i));
		}
	}

	@Override
	public synchronized void deleteNode(JewelProtocolPool jewelProtocolPool) {
		for (int i = 0; i < numberOfReplicas; i++) {
			circle.remove(hashFunc.hash(jewelProtocolPool.getId() + i));
		}
	}

	/**
	 * 获得一个最近的顺时针节点
	 * 
	 * @param key
	 *            为给定键取Hash，取得顺时针方向上最近的一个虚拟节点对应的实际节点
	 * @return 节点对象
	 */
	public JewelProtocolPool get(Object key) {
		Random random=new Random();
		key = key.toString()+random.nextInt(numberOfReplicas);
		if (circle.isEmpty()) {
			return null;
		}
		int hash = hashFunc.hash(key);
		if (!circle.containsKey(hash)) {
			SortedMap<Integer, JewelProtocolPool> tailMap = circle.tailMap(hash); // 返回此映射的部分视图，其键大于等于
			// hash
			hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
		}
		// 正好命中
		return circle.get(hash);
	}

	/**
	 * Hash算法对象，用于自定义hash算法
	 * 
	 * @author maybo
	 *
	 */
	public interface HashFunc {
		public Integer hash(Object key);
	}

	@Override
	public JewelProtocolPool loadProtocolPool() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JewelProtocolPool loadProtocolPool(String key) {
		// TODO Auto-generated method stub
		return get(key);
	}
}
