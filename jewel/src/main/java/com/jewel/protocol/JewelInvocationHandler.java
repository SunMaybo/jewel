package com.jewel.protocol;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jewel.cluster.AbstractLoadCluster;
import com.jewel.cluster.ClusterFactory;
import com.jewel.cluster.exception.NotFindClusterException;
import com.jewel.config.cache.CnfCache;
import com.jewel.rpc.exception.NotFindServiceException;
import com.jewel.rpc.pojo.RPCRequest;
import com.jewel.rpc.pojo.RPCResponse;
import com.jewel.utils.UUIDUtils;

/**
 * 
 * @Description: jewel处理类来自动态代理的调用
 * @author maybo
 * @date 2016年5月13日 下午3:02:28
 *
 */
public class JewelInvocationHandler implements InvocationHandler {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private AbstractLoadCluster abstractLoadCluster;// 集群

	private String version="v_0.0.1";// 版本号

	private String interfaceName;// 接口名

	public void setJewelProtocolPool(AbstractLoadCluster abstractLoadCluster) {
		this.abstractLoadCluster = abstractLoadCluster;
	}

	public JewelInvocationHandler(String version, String interfaceName) {
		this.version = version;
		this.interfaceName = interfaceName;
	}

	public JewelInvocationHandler(String version, String interfaceName, AbstractLoadCluster abstractLoadCluster) {
		this.version = version;
		this.interfaceName = interfaceName;
		this.abstractLoadCluster = abstractLoadCluster;
	}

	public JewelInvocationHandler(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public JewelInvocationHandler(String interfaceName, AbstractLoadCluster abstractLoadCluster) {
		this.interfaceName = interfaceName;
		this.abstractLoadCluster = abstractLoadCluster;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{

		RPCRequest request = new RPCRequest();
		request.setRequestId(UUIDUtils.findUUID());// 设置唯一的编号
		request.setDate(new Date());
		request.setInterfaceName(interfaceName);
		request.setMethodName(method.getName());
		request.setParams(args);
		request.setParamTypes(method.getParameterTypes());
		request.setVersion(version);

		// 查询服务所在的工程名
		String app = CnfCache.findAppFromService(interfaceName, version);

		// 通过工程名获取工程下的集群
		this.abstractLoadCluster = ClusterFactory.obtainCluster(app);

		if (null == this.abstractLoadCluster) {
			logger.error(new NotFindClusterException().getMessage());
			return null;
		} else {
			JewelProtocolPool jewelProtocolPool = this.abstractLoadCluster.loadProtocolPool(UUIDUtils.findUUID());// 选择服务结点
			if (null == jewelProtocolPool) {
				logger.error(new NotFindServiceException().getMessage());
				return null;
			}
			try {
				RPCResponse response = (RPCResponse) jewelProtocolPool.obtainProtocol().invoke(request);
				if (null == response) {
					logger.error("消费者未建立连接");
				}
				if ("200".equals(response.getState())) {// 成功

					return response.getResult();
				} else if ("305".equals(response.getState())) {// 超时
					logger.warn("请求超时---------------");

					return reInvoke(request);// 选择其它提供者

				} else {
					logger.info(response.getError().getMessage());
					throw response.getError();// 调用异常
				}
			} catch (Exception e) {// 提供者不可用
				abstractLoadCluster.deleteNode(jewelProtocolPool);// 从集群中删除
				logger.warn("发送失败。。。。等待请求其它服务器");
				return reInvoke(request);// 交个其它提供者执行
			}
		}

	}

	private Object reInvoke(RPCRequest request) throws Throwable{
		JewelProtocolPool jewelProtocolPool = this.abstractLoadCluster.loadProtocolPool(interfaceName + version);// 选择服务结点
		if (null == jewelProtocolPool) {
			logger.error(new NotFindClusterException().getMessage());
			return null;
		}
		try {
			RPCResponse response = (RPCResponse) jewelProtocolPool.obtainProtocol().invoke(request);
			if (null == response) {
				logger.error("客戶端連接爲空");
			}
			if ("200".equals(response.getState())) {
				return response.getResult();
			} else if ("305".equals(response.getState())) {// 其它的

				logger.warn("请求超时---------------");

				return reInvoke(request);

			} else {
				logger.info(response.getError().getMessage());
				throw response.getError();
			}
		} catch (Exception e) {
			abstractLoadCluster.deleteNode(jewelProtocolPool);
			logger.warn("发送失败。。。。等待请求其它服务器");
			return reInvoke(request);
		}

	}

}
