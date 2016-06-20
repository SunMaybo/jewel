package com.jewel.registry;

import java.io.IOException;
import java.util.List;

import com.jewel.config.conf.AppBeanCnf;
import com.jewel.config.conf.ProviderBeanCnf;
import com.jewel.config.conf.ReferenceBeanCnf;
import com.jewel.config.conf.RegistryBeanCnf;
import com.jewel.config.conf.ServiceBeanCnf;
import com.jewel.protocol.JewelProtocolPool;
import com.jewel.registry.exception.RegistryException;

/**
 * 持久化配置数据服务
 * 
 * @author maybo
 *
 */
public interface PersistenceService {

	/**
	 * 
	 * @Description: 注册
	 * @param @param registryBeanCnf 设定文件
	 * @return void 返回类型
	 * @throws IOException
	 * @throws RegistryException 
	 */
	public void registry(RegistryBeanCnf registryBeanCnf) throws  IOException;
	
	public void addSyncDataListener(SyncEventListener listener) ;

	/**
	 * 保存开启协议信息
	 * 
	 * @param beanCnf
	 * @param app
	 *            ----提供服务节点配置信息－－协议名字，ip,port,id
	 */
	public void SaveServerNode(ProviderBeanCnf beanCnf, String app);

	/**
	 * 保存提供者
	 * 
	 * @param serviceBeanCnf
	 */
	public void saveProvider(ServiceBeanCnf serviceBeanCnf);

	/**
	 * 保存app
	 * 
	 * @param appBeanCnf
	 */
	public void saveApp(AppBeanCnf appBeanCnf);

	/**
	 * 保存消费者
	 * 
	 * @param referenceBeanCnf
	 * @param appId
	 */
	public void saveCustomer(ReferenceBeanCnf referenceBeanCnf, String appId);

	/**
	 * 
	 * @Description: 查询服务配置信息
	 * @param @param referenceBeanCnf
	 * @param @return 设定文件
	 * @return ServiceBeanCnf 返回类型
	 */
	public ServiceBeanCnf findService(ReferenceBeanCnf referenceBeanCnf);

	/**
	 * 
	 * @Description: 查询服务器结点信息
	 * @param @param app
	 * @param @return 设定文件
	 * @return List<JewelProtocolPool> 返回已将创建对象但是没有建立连接的协议池
	 */
	public List<JewelProtocolPool> findProtocolPools(String app);

}
