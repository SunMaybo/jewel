package com.jewel.protocol;

import com.jewel.rpc.client.ProtocolActiveListener;
import com.jewel.rpc.client.RPCClient;
import com.jewel.rpc.pojo.RPCRequest;

/**
 * 
 * @Description: Jewel协议底层使用netty框架
 * @author maybo
 * @date 2016年5月13日 下午3:01:49
 *
 */
public class JewelProtocol extends AbstractProtocol {

	private String host = "127.0.0.1";

	private int port = 9999;

	protected RPCClient client = null;

	private ProtocolActiveListener activeListener;

	public void addListener(ProtocolActiveListener activeListener) {// 添加一个状态监听器
		this.activeListener = activeListener;
	}

	// 注册
	public AbstractProtocol register(String host, int port, int priority) throws InterruptedException {// 连接netty客户端
		this.host = host;
		this.port = port;
		this.setPriority(priority);
		if (null == host) {
			throw new NullPointerException("host 不可以为空");
		}

		client = RPCClient.init(this.getId()).connection(this.host, this.port);
		client.addActiveListener(activeListener);

		return this;
	}

	// 处理
	public Object invoke(RPCRequest request) {// 处理调用netty
		if (null != client) {
			try {
				return client.sendMsg(request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return null;
	}

	public void shutdown() {// 关闭
		if (null != client) {
			try {
				client.shutdown();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Object invoke(String interfaceName, String version) {
		// TODO Auto-generated method stub
		return null;
	}

}
