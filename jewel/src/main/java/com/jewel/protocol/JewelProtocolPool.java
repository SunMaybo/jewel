package com.jewel.protocol;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jewel.rpc.client.ProtocolActiveListener;
import com.jewel.rpc.client.StateEvent;
import com.jewel.utils.UUIDUtils;

/**
 * 
 * @Description: jewel协议连接池----可以放入任何其它协议
 * @author maybo
 * @date 2016年5月13日 下午2:54:52
 *
 */
public class JewelProtocolPool extends PriorityQueue<AbstractProtocol> implements ProtocolActiveListener {
	private static final long serialVersionUID = 1L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private int weight;// 权重

	private boolean isValid;

	private String name;

	private String id = UUIDUtils.findUUID();

	public void setId(String id) {
		this.id = id;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public boolean isValid() {
		return this.isValid;
	}

	public String getId() {
		return id;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getWeight() {
		return weight;
	}

	public String getHost() {
		return host;
	}

	public static int maxSize = 3;// 最大容量

	private String host;
	private int port;

	public int getPort() {
		return port;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPoirt(int port) {
		this.port = port;
	}

	public void setMaxSize(int maxSize) {
		JewelProtocolPool.maxSize = maxSize;
	}

	public JewelProtocolPool(int maxSize) {
		super(5, new Comparator<AbstractProtocol>() {

			public int compare(AbstractProtocol o1, AbstractProtocol o2) {
				int numbera = o1.getPriority();
				int numberb = o2.getPriority();
				if (numberb > numbera) {
					return 1;
				} else if (numberb < numbera) {
					return -1;
				} else {
					return 0;
				}
			}
		});
		JewelProtocolPool.maxSize = maxSize;
	}

	public JewelProtocolPool(int size, String host, int port) {
		super(size, new Comparator<AbstractProtocol>() {//

					public int compare(AbstractProtocol o1, AbstractProtocol o2) {
						int numbera = o1.getPriority();
						int numberb = o2.getPriority();
						if (numberb < numbera) {
							return 1;
						} else if (numberb > numbera) {
							return -1;
						} else {
							return 0;
						}
					}
				});
		JewelProtocolPool.maxSize = size;
		this.host = host;
		this.port = port;
	}

	public JewelProtocolPool(int size, String host, int port, String name) {
		super(size, new Comparator<AbstractProtocol>() {//

					public int compare(AbstractProtocol o1, AbstractProtocol o2) {
						int numbera = o1.getPriority();
						int numberb = o2.getPriority();
						if (numberb < numbera) {
							return 1;
						} else if (numberb > numbera) {
							return -1;
						} else {
							return 0;
						}
					}
				});
		JewelProtocolPool.maxSize = size;
		this.host = host;
		this.port = port;
		this.name = name;
	}

	public JewelProtocolPool(int size, String host, int port, int weight) {
		super(size, new Comparator<AbstractProtocol>() {//

					public int compare(AbstractProtocol o1, AbstractProtocol o2) {
						int numbera = o1.getPriority();
						int numberb = o2.getPriority();
						if (numberb < numbera) {
							return 1;
						} else if (numberb > numbera) {
							return -1;
						} else {
							return 0;
						}
					}
				});
		JewelProtocolPool.maxSize = size;
		this.weight = weight;
		this.host = host;
		this.port = port;
	}

	// 通过编号删除连接协议
	public synchronized void deleteProtocol(String id) {
		Iterator<AbstractProtocol> iterator = this.iterator();
		while (iterator.hasNext()) {
			AbstractProtocol protocol = iterator.next();
			if (protocol.getId().equals(id)) {
				iterator.remove();
			}
		}
	}

	// 获取协议---选择最大权重的协议
	public synchronized AbstractProtocol obtainProtocol() {
		// 1.为空注册协议
		if (this.size() <= 0) {
			AbstractProtocol abstractProtocol = new JewelProtocol();
			abstractProtocol.addListener(this);
			try {
				abstractProtocol.register(this.host, this.port, 100);
				this.add(abstractProtocol);
			} catch (InterruptedException e) {
				logger.error(e.getLocalizedMessage());
				e.printStackTrace();
				return null;
			}

			return this.peek();
		}
		// 2.优先级大于等于100并且处于空闲状态的使用
		Iterator<AbstractProtocol> iterator = this.iterator();
		while (iterator.hasNext()) {
			AbstractProtocol protocol = iterator.next();
			if (protocol.isValid() && protocol.getPriority() >= 100) {
				return protocol;
			}
		}
		// 3.如果大小没有达到最大值，而且不满足条件2注册协议并使用
		if (this.size() < maxSize) {
			AbstractProtocol abstractProtocol = new JewelProtocol();
			abstractProtocol.addListener(this);
			try {
				abstractProtocol.register(this.host, this.port, 100);
				this.add(abstractProtocol);
			} catch (InterruptedException e) {
				logger.error(e.getLocalizedMessage());
				e.printStackTrace();
				return null;
			}

			return this.peek();
		}
		// 4.选举出优先级最大的协议
		return this.peek();
	}

	// 监听事件-----用于发现连接的状态选择最优的连接通道
	public void clientStateEvent(StateEvent stateEvent) {
		synchronized (this) {
			if (stateEvent.isClose()) {// 关闭删除协议
				this.deleteProtocol(stateEvent.getClientId());
			} else if (stateEvent.isValid()) {// 是可用也就是空闲
				Iterator<AbstractProtocol> iterator = this.iterator();
				while (iterator.hasNext()) {
					AbstractProtocol protocol = iterator.next();
					if (protocol.getId().equals(stateEvent.getClientId())) {
						protocol.setValid(true);
						iterator.remove();
						this.add(protocol);
						break;
					}
				}
			} else if (stateEvent.isReConnect() && stateEvent.isActive()) {// 重连接成功
				Iterator<AbstractProtocol> iterator = this.iterator();
				while (iterator.hasNext()) {
					AbstractProtocol protocol = iterator.next();
					if (protocol.getId().equals(stateEvent.getClientId())) {
						protocol.setPriority(protocol.getPriority() - 1);
						if (protocol.getPriority() <= 0) {
							iterator.remove();
						} else {
							iterator.remove();
							this.add(protocol);
						}
						break;
					}
				}
			} else if (stateEvent.isReConnect() && !stateEvent.isActive()) {// 重连接失败
				Iterator<AbstractProtocol> iterator = this.iterator();
				while (iterator.hasNext()) {
					AbstractProtocol protocol = iterator.next();
					if (protocol.getId().equals(stateEvent.getClientId())) {
						protocol.setPriority(protocol.getPriority() - 2);
						if (protocol.getPriority() <= 0) {
							iterator.remove();
						} else {
							iterator.remove();
							this.add(protocol);
						}
						break;
					}
				}
			}
		}
	}

}
