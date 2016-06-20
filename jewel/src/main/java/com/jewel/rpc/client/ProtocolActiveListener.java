package com.jewel.rpc.client;

/**
 * 
 * @Description: netty客户端连接状态监听器类
 * @author maybo
 * @date 2016年5月13日 下午3:12:24
 *
 */
public interface ProtocolActiveListener {
	/**
	 * 
	 * @Description: TODO(事件方法，客户端连接状态)
	 * @param @param stateEvent 状态类
	 * @return void 返回类型
	 */
	public void clientStateEvent(StateEvent stateEvent);

}
