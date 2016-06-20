package com.jewel.rpc.client;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jewel.rpc.pojo.RPCRequest;
import com.jewel.rpc.pojo.RPCResponse;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RPCClientHandler extends SimpleChannelInboundHandler<RPCResponse> {

	private RPCClient client = null;

	private Logger logger = LoggerFactory.getLogger(RPCClientHandler.class);

	public void setClient(RPCClient client) {
		this.client = client;
	}

	private Map<String, Object> responseMap;

	public RPCClientHandler(Map<String, Object> responseMap) {
		this.responseMap = responseMap;
	}

	public RPCClientHandler(Map<String, Object> responseMap, RPCClient client) {
		this.responseMap = responseMap;
		this.client = client;
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, RPCResponse response) throws Exception {
		if (response.getType() == 1) {// 发送心跳回文

			if (null != this.client.getActiveListener()) {// 监听通道空闲
				StateEvent stateEvent = new StateEvent();
				stateEvent.setActice(true);
				stateEvent.setClientId(this.client.id());
				stateEvent.setClose(false);
				stateEvent.setReConnect(false);
				stateEvent.setValid(true);
				this.client.getActiveListener().clientStateEvent(stateEvent);
			}

			RPCRequest request = new RPCRequest();
			request.setType(1);
			ctx.channel().writeAndFlush(request);
		} else {// 接收消息
			RPCResponse rpcResponse = (RPCResponse) responseMap.get(response.getResponseId());
			synchronized (rpcResponse) {
				BeanUtils.copyProperties(rpcResponse, response);
				rpcResponse.notifyAll();
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
		e.printStackTrace();
		super.exceptionCaught(ctx, e);
		if (e instanceof IOException) {// 发生异常关闭通道
			ctx.channel().close();
			client.shutdown();
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		// 重新连接服务器
		ctx.channel().eventLoop().schedule(new Runnable() {
			public void run() {
				try {
					client.doConnection();
				} catch (InterruptedException e) {
					logger.error("断开连接后重新建立连接出现异常."+e.getMessage());
					e.printStackTrace();
				}
			}
		}, RPCClient.RECONN_TIME_SECONDS, TimeUnit.SECONDS);
	}

}
