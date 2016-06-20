package com.jewel.protocol;

import com.jewel.rpc.client.ProtocolActiveListener;
import com.jewel.rpc.pojo.RPCRequest;
import com.jewel.utils.UUIDUtils;

/**
 * 
 * @Description: 远程调用协议接口
 * @author maybo
 * @date 2016年5月10日 下午2:36:45
 *
 */
public abstract class AbstractProtocol {

	private int priority;// 协议的优先级级别

	private boolean isValid = false;

	private String id = UUIDUtils.findUUID();// 协议的id

	public void init(int priority, String id) {
		this.priority = priority;
		this.id = id;
	}

	/**
	 * 
	 * @Description: 注册协议
	 * @param @param host
	 * @param @param port
	 * @param @param priority----设置优先级
	 * @param @return
	 * @param @throws InterruptedException 设定文件
	 * @return AbstractProtocol 返回类型
	 */
	public abstract AbstractProtocol register(String host, int port, int priority) throws InterruptedException;

	/**
	 * 
	 * @Description: 处理请求的数据
	 * @param @param request
	 * @param @return
	 * @return Object 回复数据--RPCRepose
	 */
	public abstract Object invoke(RPCRequest request);

	/**
	 * 
	 * @Description: 处理请求
	 * @param @param interfaceName---接口名字
	 * @param @param version---------版本号
	 * @param @return 设定文件
	 * @return Object 回复数据--RPCRepose
	 */
	public abstract Object invoke(String interfaceName, String version);

	//关闭连接协议
	public abstract void shutdown();

	//添加一个监听器用于监控连接状态
	public abstract void addListener(ProtocolActiveListener activeListener);

	public void setId(String id) {
		this.id = id;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public boolean isValid() {
		return this.isValid;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getId() {
		return id;
	}

	public int getPriority() {
		return priority;
	}
}
