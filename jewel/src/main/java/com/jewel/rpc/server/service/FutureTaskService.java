package com.jewel.rpc.server.service;

import io.netty.channel.ChannelHandlerContext;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.jewel.rpc.pojo.RPCRequest;
import com.jewel.rpc.pojo.RPCResponse;
import com.jewel.rpc.server.method.RequestMethodInvoker;

/**
 * 
 * @Description: 服务端通过调用服务处理请求
 * @author maybo
 * @date 2016年5月13日 下午3:23:26
 *
 */
public class FutureTaskService {

	private static FutureTaskService futureTaskService;

	private static ExecutorService executorPool = null;

	private static RequestMethodInvoker invoker = null;// 处理类

	static {
		executorPool = Executors.newCachedThreadPool();
		invoker = new RequestMethodInvoker();
	}

	public void shutdown() {
		executorPool.shutdown();
	}

	public static FutureTaskService newInstance() {
		futureTaskService = new FutureTaskService();
		return futureTaskService;
	}

	public static FutureTaskService getService() {
		return futureTaskService;
	}

	public RPCResponse invoke(final RPCRequest request, String clientId) throws InterruptedException, ExecutionException {// 任务的处理
		FutureTask<RPCResponse> futureTask = new FutureTask<RPCResponse>(new Callable<RPCResponse>() {
			public RPCResponse call() throws Exception {
				return invoker.invoke(request);
			}
		});
		executorPool.execute(futureTask);
		RPCResponse response = futureTask.get();
		return response;
	}

	public RPCResponse invoke(final RPCRequest request, String clientId, long timeout) throws InterruptedException, ExecutionException,
			TimeoutException {// 任务的处理
		FutureTask<RPCResponse> futureTask = new FutureTask<RPCResponse>(new Callable<RPCResponse>() {
			public RPCResponse call() throws Exception {
				return invoker.invoke(request);
			}
		});
		executorPool.execute(futureTask);
		RPCResponse response = futureTask.get(timeout, TimeUnit.MILLISECONDS);
		return response;
	}

	/**
	 * 
	 * @Description: TODO(调用服务类处理请求并返回)
	 * @param @param request------请求内容
	 * @param @param clientId-----客户编号
	 * @param @param channelFuture
	 * @param @throws InterruptedException
	 * @param @throws ExecutionException 设定文件
	 * @return void 返回类型
	 */
	public void invoke(final RPCRequest request, String clientId, final ChannelHandlerContext channelFuture) throws InterruptedException,
			ExecutionException {// 任务的处理
		executorPool.execute(new Runnable() {
			public void run() {
				RPCResponse response = invoker.invoke(request);// 处理请求
				if (channelFuture.channel().isActive()) {
					channelFuture.channel().writeAndFlush(response);
				}
			}
		});
	}

}
