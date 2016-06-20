package com.jewel.rpc.server;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jewel.rpc.decode.RPCDecoder;
import com.jewel.rpc.encode.RPCEncoder;
import com.jewel.rpc.pojo.RPCRequest;
import com.jewel.rpc.pojo.RPCResponse;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * 
 * @Description: 服务端
 * @author maybo
 * @date 2016年5月13日 下午3:24:10
 *
 */
public class RPCServer {
	private static final Logger LOGGER = LoggerFactory.getLogger(RPCServer.class);

	public static  String IP = "127.0.0.1";

	public static  int PORT = 9998;

	public static int READ_IDLE_TIME = 60;// 读空闲时间

	public static int WRITE_IDLE_TIME = 30;// 写空闲时间

	public static int ALL_IDLE_TIME = 10;// 读写空闲时间

	public static int TIMEOUT_SECONDS = 300;//

	/** 用于分配处理业务线程的线程组个数 */
	protected static final int BIZGROUPSIZE = Runtime.getRuntime().availableProcessors() * 2; // 默认
	/** 业务出现线程大小 */
	protected static final int BIZTHREADSIZE = 4;

	/*
	 * NioEventLoopGroup实际上就是个线程池,
	 * NioEventLoopGroup在后台启动了n个NioEventLoop来处理Channel事件,
	 * 每一个NioEventLoop负责处理m个Channel,
	 * NioEventLoopGroup从NioEventLoop数组里挨个取出NioEventLoop来处理Channel
	 */
	private static final EventLoopGroup bossGroup = new NioEventLoopGroup(BIZGROUPSIZE);
	private static final EventLoopGroup workerGroup = new NioEventLoopGroup(BIZTHREADSIZE);

	public static void run() throws Exception {
		LOGGER.info("----------------------------------------开启服务端连接--IP:"+IP+PORT+"------------------------");
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup);
		b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000);
		b.channel(NioServerSocketChannel.class);
		b.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast(new IdleStateHandler(READ_IDLE_TIME, WRITE_IDLE_TIME, ALL_IDLE_TIME, TimeUnit.SECONDS));
				pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
				pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
				pipeline.addLast(new RPCDecoder(RPCRequest.class));
				pipeline.addLast(new RPCEncoder(RPCResponse.class));
				pipeline.addLast(new RPCServerHandler());

			}
		});
		b.bind(IP, PORT).sync();
		LOGGER.info("----------------------------------------开启服务端连接成功-------------------------");
	}

	protected static void shutdown() {
		workerGroup.shutdownGracefully();
		bossGroup.shutdownGracefully();
	}

	public static void main(String[] args) throws Exception {
		RPCServer.run();

	}
}
