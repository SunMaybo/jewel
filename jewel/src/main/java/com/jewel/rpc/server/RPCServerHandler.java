package com.jewel.rpc.server;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jewel.rpc.pojo.RPCRequest;
import com.jewel.rpc.pojo.RPCResponse;
import com.jewel.rpc.server.service.FutureTaskService;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 
 * @Description: 服务端处理类
 * @author maybo
 * @date 2016年5月13日 下午3:25:36
 *
 */
public class RPCServerHandler extends SimpleChannelInboundHandler<RPCRequest> {

	private static FutureTaskService taskService = FutureTaskService.newInstance();

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, RPCRequest request) {
		if (request.getType() != 1) {// 调用服务
			try {
				logger.info("Channel"+ctx.channel().id().asShortText()+"-------------"+"From:"+"jewel:"+ctx.channel().localAddress()+"/"+request.getInterfaceName()+"/"+request.getMethodName());
				taskService.invoke(request, ctx.channel().id().asShortText(), ctx);
			} catch (InterruptedException e) {
				RPCResponse response = new RPCResponse();
				response.setDate(new Date());
				response.setError(new RuntimeException());
				response.setResponseId(request.getRequestId());
				response.setState("505");
				if (ctx.channel().isActive()) {
					ctx.channel().writeAndFlush(response);
				}
				e.printStackTrace();
			} catch (ExecutionException e) {
				RPCResponse response = new RPCResponse();
				response.setDate(new Date());
				response.setError(new RuntimeException());
				response.setResponseId(request.getRequestId());
				response.setState("510");
				if (ctx.channel().isActive()) {
					ctx.channel().writeAndFlush(response);
				}
				e.printStackTrace();
			}
		}
	}

	/**
	 * 一段时间未进行读写操作 回调
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		// TODO Auto-generated method stub
		super.userEventTriggered(ctx, evt);

		if (evt instanceof IdleStateEvent) {

			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state().equals(IdleState.READER_IDLE)) {// 超时关闭通道
				// 超时关闭channel
				ctx.close();

			} else if (event.state().equals(IdleState.WRITER_IDLE)) {
				// 写超时
			} else if (event.state().equals(IdleState.ALL_IDLE)) {// 心跳检测
				// 未进行读写
				RPCResponse response = new RPCResponse();
				response.setType(1);
				if (ctx.channel().isActive()) {
					ctx.channel().writeAndFlush(response);
				}

			}

		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
		super.exceptionCaught(ctx, e);
		if (e instanceof IOException) {
			ctx.channel().close();
		}
		e.printStackTrace();
	}

}
