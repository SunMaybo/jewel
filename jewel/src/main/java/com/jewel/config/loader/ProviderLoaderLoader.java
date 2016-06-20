package com.jewel.config.loader;

import com.jewel.config.cache.CnfCache;
import com.jewel.config.conf.AppBeanCnf;
import com.jewel.config.conf.ProviderBeanCnf;
import com.jewel.registry.PersistenceService;
import com.jewel.rpc.server.RPCServer;
import com.jewel.utils.UUIDUtils;

/**
 * 
 * @Description: 服务器节点信息加载
 * @author maybo
 * @date 2016年5月25日 上午10:18:34
 *
 */
public class ProviderLoaderLoader extends ConfLoader {

	private static ConfLoader confLoader;
	static {
		confLoader = new ProviderLoaderLoader();
	}

	protected static ConfLoader obtain() {
		return confLoader;
	}

	public void load(Object object) {
		ProviderBeanCnf providerBeanCnf = (ProviderBeanCnf) object;
		AppBeanCnf appBeanCnf = CnfCache.findApp();// 获取工程的名字
		if (null == appBeanCnf) {
			try {
				throw new Exception("application 必须首先加载");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String ip = "127.0.0.1";
		// ip = InetAddress.getLocalHost().getHostAddress();
		providerBeanCnf.setHost(ip);
		providerBeanCnf.setId(UUIDUtils.findUUID());//设置编号
		
		PersistenceService persistenceService = CnfCache.findPersistenceService(providerBeanCnf.getRegistry());
		if (null != persistenceService) {
			persistenceService.SaveServerNode(providerBeanCnf, appBeanCnf.getName());// 提供者信息持久化用于消费者服务发现
			RPCServer.PORT = providerBeanCnf.getPort();
			RPCServer.IP = providerBeanCnf.getHost();
			try {
				RPCServer.run();//启动服务
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

		}
	}

}
