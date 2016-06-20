package com.jewel.rpc.client;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jewel.rpc.decode.RPCDecoder;
import com.jewel.rpc.encode.RPCEncoder;
import com.jewel.rpc.exception.RequestTimeOutException;
import com.jewel.rpc.pojo.RPCRequest;
import com.jewel.rpc.pojo.RPCResponse;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * 
 * @Description: 长连接，提供远程服务调用
 * @author maybo
 * @date 2016年5月10日 下午1:07:48
 *
 */
public class RPCClient {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private String id;// 编号

	public static String HOST = "127.0.0.1";

	public static int PORT = 9999;

	public static int TIMEOUT__SECONDS = 120;// 超时时间

	public static int CONNECT_TIMEOUT_SECONDS = 3;// 连接超时时间

	public static int RECONN_TIME_SECONDS = 3;// 重新建立连接时间

	private ChannelFuture channelFuture;

	private int connectAmount = 0;

	private RPCClient client = null;

	private Bootstrap bootstrap = null;

	private EventLoopGroup group;

	public static Map<String, Object> responseMap = new ConcurrentHashMap<String, Object>();

	public ProtocolActiveListener activeListener = null;

	public ProtocolActiveListener getActiveListener() {
		return activeListener;
	}

	public RPCClient(String id) {
		this.id = id;
	}

	/**
	 * 初始化Bootstrap
	 * 
	 * @return
	 */
	private Bootstrap getBootstrap() {
		group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, CONNECT_TIMEOUT_SECONDS * 1000);
		b.group(group).channel(NioSocketChannel.class);
		b.handler(new ChannelInitializer<Channel>() {
			@Override
			protected void initChannel(Channel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));// 防止丢包
				pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
				pipeline.addLast(new RPCEncoder(RPCRequest.class));// 编码
				pipeline.addLast(new RPCDecoder(RPCResponse.class));// 转码
				pipeline.addLast(new RPCClientHandler(responseMap, client));// 处理
			}
		});
		b.option(ChannelOption.SO_KEEPALIVE, true);
		return b;
	}

	/**
	 * 
	 * @Description: TODO(初始化建立连接)
	 * @param @param id
	 * @param @return 设定文件
	 * @return RPCClient 返回类型
	 */
	public static RPCClient init(String id) {
		RPCClient client = new RPCClient(id);
		client.client = client;
		client.bootstrap = client.getBootstrap();
		return client;
	}

	public RPCClient getClient() {
		return client;
	}

	public boolean isActive() {
		return client.getChannelFuture().channel().isActive();
	}

	public RPCClient doConnection() throws InterruptedException {
		client.connection(HOST, PORT);
		return client;
	}

	/**
	 * 
	 * @Description: TODO(连接)
	 * @param @param host
	 * @param @param port
	 * @param @return 设定文件
	 * @return RPCClient 返回类型
	 * @throws InterruptedException 
	 */
	public RPCClient connection(String host, int port) throws InterruptedException {
		
			channelFuture = client.bootstrap.connect(host, port).sync();
			channelFuture.addListener(new ChannelFutureListener() {

				public void operationComplete(ChannelFuture future) throws Exception {// 重连机制
					if (future.isSuccess()) {
						// 连接成功连接指数为0
						connectAmount = 0;

						if (null != activeListener) {// 接听客户状态
							StateEvent stateEvent = new StateEvent();
							stateEvent.setActice(true);
							stateEvent.setClientId(id);
							stateEvent.setClose(false);
							stateEvent.setReConnect(true);
							activeListener.clientStateEvent(stateEvent);
						}
						logger.info("客户端连接成功。");

					} else {
						connectAmount++;
						if (connectAmount == 3) {// 连接数大于3次停止连接
							connectAmount = 0;
							shutdown();// 关闭连接
						} else {
							future.channel().eventLoop().schedule(new Runnable() {
								public void run() {
									try {
										doConnection();
									} catch (InterruptedException e) {
										e.printStackTrace();
										logger.error("------------重新连接服务器失败--------");
									}
								}
							}, RECONN_TIME_SECONDS, TimeUnit.SECONDS);

							if (null != activeListener) {// 接听客户状态
								StateEvent stateEvent = new StateEvent();
								stateEvent.setActice(false);
								stateEvent.setClientId(id);
								stateEvent.setClose(false);
								stateEvent.setReConnect(false);
								activeListener.clientStateEvent(stateEvent);
							}
						}
					}
				}
			});
		
		return client;
	}

	public ChannelFuture getChannelFuture() {
		return channelFuture;
	}

	/**
	 * 
	 * @Description: TODO(发送消息)
	 * @param @param request
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return RPCResponse 返回类型
	 */
	public RPCResponse sendMsg(RPCRequest request) throws Exception {
		if (null == request) {
			throw new NullPointerException();
		}
		try {
			RPCResponse response = new RPCResponse();
			responseMap.put(request.getRequestId(), response);

			if (channelFuture.channel().isOpen() && channelFuture.channel().isActive()) {
				if (null != this.activeListener) {// 发送监听通道
					StateEvent stateEvent = new StateEvent();
					stateEvent.setActice(true);
					stateEvent.setClientId(this.id);
					stateEvent.setClose(false);
					stateEvent.setReConnect(false);
					stateEvent.setValid(false);
					activeListener.clientStateEvent(stateEvent);
				}
				channelFuture.channel().writeAndFlush(request);
				synchronized (response) {
					if (null == response.getResponseId()) {
						response.wait(TIMEOUT__SECONDS * 1000);
					} else {
						response.notifyAll();
					}
					if (null == response.getResponseId()) {
						channelFuture.channel().close();
						response = new RPCResponse();
						response.setDate(new Date());
						response.setResponseId(request.getRequestId());
						response.setState("305");
						response.setError(new RequestTimeOutException("请求超时"));
						this.shutdown();// 关闭连接

					} else {
						if (null != this.activeListener) {// 发送监听通道
							StateEvent stateEvent = new StateEvent();
							stateEvent.setActice(true);
							stateEvent.setClientId(this.id);
							stateEvent.setClose(false);
							stateEvent.setReConnect(false);
							stateEvent.setValid(true);
							activeListener.clientStateEvent(stateEvent);
						}
						response = (RPCResponse) responseMap.get(request.getRequestId());
						System.out.println(response.toString());
						responseMap.remove(request.getRequestId());
					}
				}

			}

			return response;
		} finally {

		}
	}

	public void shutdown() throws InterruptedException {
		channelFuture.channel().close();
		group.shutdownGracefully();

		if (null != activeListener) {// 接听客户状态
			StateEvent stateEvent = new StateEvent();
			stateEvent.setActice(false);
			stateEvent.setClientId(this.id);
			stateEvent.setClose(true);
			activeListener.clientStateEvent(stateEvent);
		}
		logger.info("客户端关闭连接。");
	}

	public void addActiveListener(ProtocolActiveListener listener) {
		this.activeListener = listener;
	}

	public String id() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
