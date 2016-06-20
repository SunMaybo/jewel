package com.jewel.registry.zookeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jewel.config.conf.AppBeanCnf;
import com.jewel.config.conf.ProviderBeanCnf;
import com.jewel.config.conf.ReferenceBeanCnf;
import com.jewel.config.conf.RegistryBeanCnf;
import com.jewel.config.conf.ServiceBeanCnf;
import com.jewel.protocol.JewelProtocolPool;
import com.jewel.registry.PersistenceService;
import com.jewel.registry.SyncData;
import com.jewel.registry.SyncEvent;
import com.jewel.registry.SyncEventListener;
import com.jewel.utils.SerializationUtil;

public class ZooKeeperPersistenceServiceImpl implements PersistenceService {

	private ZooKeeper zk = null;

	private SyncEventListener eventListener;

	public static int TimeOut = 5000;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private String id;
	private int port;

	public void setId(String id) {
		this.id = id;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getId() {
		return id;
	}

	public int getPort() {
		return port;
	}

	public ZooKeeperPersistenceServiceImpl() {

	}

	public void addSyncDataListener(SyncEventListener listener) {
		this.eventListener = listener;
	}

	public void SaveServerNode(ProviderBeanCnf beanCnf, String app) {
		try {
			Stat statApp = zk.exists("/" + app, true);
			if (null == statApp) {
				zk.create("/" + app, "创建一个工程".getBytes(),
						ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}
			Stat statHost = zk.exists("/" + app + "/" + beanCnf.getHost() + ":"
					+ beanCnf.getPort(), true);
			if (null == statHost) {
				zk.create(
						"/" + app + "/" + beanCnf.getHost() + ":"
								+ beanCnf.getPort(), SerializationUtil
								.serialize(beanCnf, ProviderBeanCnf.class),
						ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
			}else{
				logger.error("该服务结点已经启动－－－"+beanCnf.toString());
			}

		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveProvider(ServiceBeanCnf serviceBeanCnf) {
		try {
			Stat providerStat = zk.exists("/provider", false);
			if (null == providerStat) {
				zk.create("/provider", "提供者配置".getBytes(),
						ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}
			Stat interfaceStat = zk.exists(
					"/provider" + "/" + serviceBeanCnf.getInterfaceName(),
					false);
			if (null == interfaceStat) {
				zk.create(
						"/provider" + "/" + serviceBeanCnf.getInterfaceName(),
						"服务接口名".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
						CreateMode.PERSISTENT);
			}
			Stat versionStat = zk.exists(
					"/provider" + "/" + serviceBeanCnf.getInterfaceName() + "/"
							+ serviceBeanCnf.getVersion(), false);
			if (null == versionStat) {
				zk.create("/provider" + "/" + serviceBeanCnf.getInterfaceName()
						+ "/" + serviceBeanCnf.getVersion(), SerializationUtil
						.serialize(serviceBeanCnf, ServiceBeanCnf.class),
						ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}else{
				zk.setData("/provider" + "/" + serviceBeanCnf.getInterfaceName()
						+ "/" + serviceBeanCnf.getVersion(), SerializationUtil
						.serialize(serviceBeanCnf, ServiceBeanCnf.class),-1);
				
				
			}

		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveApp(AppBeanCnf appBeanCnf) {
		Stat statApp;
		try {
			statApp = zk.exists("/" + appBeanCnf.getName(), true);
			if (null == statApp) {
				zk.create("/" + appBeanCnf.getName(), "创建一个工程".getBytes(),
						ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void saveCustomer(ReferenceBeanCnf referenceBeanCnf, String appId) {
		try {
			Stat providerStat = zk.exists("/customer", false);
			if (null == providerStat) {
				zk.create("/customer", "消费者配置".getBytes(),
						ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}
			Stat interfaceStat = zk.exists(
					"/customer" + "/" + referenceBeanCnf.getInterfaceName(),
					false);
			if (null == interfaceStat) {
				zk.create(
						"/provider" + "/" + referenceBeanCnf.getInterfaceName(),
						"服务接口名".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
						CreateMode.PERSISTENT);
			}
			Stat versionStat = zk.exists(
					"/customer" + "/" + referenceBeanCnf.getInterfaceName()
							+ "/" + referenceBeanCnf.getVersion(), false);
			if (null == versionStat) {
				zk.create(
						"/customer" + "/" + referenceBeanCnf.getInterfaceName()
								+ "/" + referenceBeanCnf.getVersion(),
						SerializationUtil.serialize(referenceBeanCnf,
								ReferenceBeanCnf.class),
						ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}

		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<JewelProtocolPool> findProtocolPools(String app) {
		List<JewelProtocolPool> protocolPools = null;
		try {
			protocolPools = new ArrayList<JewelProtocolPool>();
			List<String> paths = zk.getChildren("/" + app, new Watcher() {

				public void process(WatchedEvent arg0) {
					SyncData syncData = new SyncData();
					if (arg0.getType() == EventType.NodeChildrenChanged) {
						syncData.state = SyncData.SYNC_PROTOCOL_POOL;
						syncData.setObject(findProtocolPools(arg0.getPath()
								.substring(1)));
						syncData.setApp(arg0.getPath().substring(1));
						SyncEvent event = new SyncEvent(syncData);
						eventListener.syncCacheEvent(event);
					}

				}
			});
			for (int i = 0; null != paths && i < paths.size(); i++) {
				Stat providerStat = zk.exists("/" + app + "/" + paths.get(i),
						false);
				if (null != providerStat) {
					byte[] providerBuff = zk.getData(
							"/" + app + "/" + paths.get(i), false, new Stat());
					ProviderBeanCnf providerBeanCnf = (ProviderBeanCnf) SerializationUtil
							.deserialize(providerBuff, ProviderBeanCnf.class);
					JewelProtocolPool jewelProtocolPool = new JewelProtocolPool(
							JewelProtocolPool.maxSize,
							providerBeanCnf.getHost(),
							providerBeanCnf.getPort(),
							providerBeanCnf.getName());
					jewelProtocolPool.setId(providerBeanCnf.getId());
					protocolPools.add(jewelProtocolPool);
				}
			}
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return protocolPools;
	}

	public ServiceBeanCnf findService(ReferenceBeanCnf referenceBeanCnf) {
		try {
			Stat stat = zk.exists(
					"/provider" + "/" + referenceBeanCnf.getInterfaceName()
							+ "/" + referenceBeanCnf.getVersion(),
					new Watcher() {

						public void process(WatchedEvent arg0) {
							// 监听服务添加
							try {
								byte[] serviceBuff = zk.getData(arg0.getPath(),
										false, new Stat());
								syncServiceCnf(serviceBuff);
							} catch (KeeperException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					});
			if (null != stat) {
				byte[] serviceBuff = zk.getData("/provider" + "/"
						+ referenceBeanCnf.getInterfaceName() + "/"
						+ referenceBeanCnf.getVersion(), new Watcher() {

					public void process(WatchedEvent arg0) {
						// 监听数据修改
						try {
							byte[] serviceBuff = zk.getData(arg0.getPath(),
									false, new Stat());
							syncServiceCnf(serviceBuff);
						} catch (KeeperException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}, new Stat());
				if (null != serviceBuff) {
					ServiceBeanCnf serviceBeanCnf = (ServiceBeanCnf) SerializationUtil
							.deserialize(serviceBuff, ServiceBeanCnf.class);
					return serviceBeanCnf;
				}
			}
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void syncServiceCnf(byte[] serviceBuff) {
		if (null != serviceBuff) {
			ServiceBeanCnf serviceBeanCnf = (ServiceBeanCnf) SerializationUtil
					.deserialize(serviceBuff, ServiceBeanCnf.class);
			SyncData syncData = new SyncData();
			syncData.state = SyncData.SYNC_ADD_SERVICE_CNF;
			syncData.setObject(serviceBeanCnf);
			SyncEvent event = new SyncEvent(syncData);
			eventListener.syncCacheEvent(event);
		}

	}

	public void registry(RegistryBeanCnf registryBeanCnf) throws IOException {
		zk = new ZooKeeper(registryBeanCnf.getAddress(), TimeOut,
				new Watcher() {
					public void process(WatchedEvent arg0) {
						if (arg0.getState() == KeeperState.SyncConnected) {
							logger.info("注册zookeeper---数据库---"
									+ arg0.getState().toString());
						}
					}
				});
	}
}
