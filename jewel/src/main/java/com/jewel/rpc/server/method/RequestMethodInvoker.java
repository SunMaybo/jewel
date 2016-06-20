package com.jewel.rpc.server.method;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import com.jewel.common.utils.ContextBeanUtil;
import com.jewel.common.utils.MethodInvokeUtil;
import com.jewel.rpc.exception.NotFindServiceException;
import com.jewel.rpc.pojo.RPCRequest;
import com.jewel.rpc.pojo.RPCResponse;

/**
 * 
 * @请求数据的处理 通过反射调用服务实现方法
 * @author maybo
 * @date 2016年5月10日 下午1:18:30
 *
 */
public class RequestMethodInvoker {

	public RPCResponse invoke(RPCRequest request) {
		if (null == request) {
			throw new NullPointerException();
		}

		RPCResponse response = new RPCResponse();
		response.setResponseId(request.getRequestId());
		response.setDate(new Date());

		String interfaceName = request.getInterfaceName();// 接口名字
		if (null == interfaceName || interfaceName.length() <= 0) {// 接口名为空
			response.setState("400");
			response.setError(new NullPointerException("接口名字不可以为空"));
		} else {
			Object object = null;
			if (null != request.getVersion() && request.getVersion().length() > 0) {// 存在版本号
				object = ContextBeanUtil.getBean(interfaceName, request.getVersion());// 获取服务对象
			} else {
				object = ContextBeanUtil.getBean(interfaceName);// 获取服务对象
			}
			Object result;
			try {
				if (null == object) {
					response.setError(new NotFindServiceException("没有找到服务实现类异常"));
					response.setState("405");
				} else {
					result = MethodInvokeUtil.invoke(object, request.getParams(), request.getMethodName(), request.getParamTypes());
					response.setResult(result);
					response.setState("200");
				}
			} catch (IllegalAccessException e) {
				response.setState("410");
				response.setError(e);
			} catch (IllegalArgumentException e) {
				response.setState("415");
				response.setError(e);
			} catch (InvocationTargetException e) {
				response.setState("420");
				response.setError(e);
			} catch (NoSuchMethodException e) {
				response.setState("425");
				response.setError(e);
			} catch (SecurityException e) {
				response.setState("430");
				response.setError(e);
			} catch (ClassNotFoundException e) {
				response.setState("435");
				response.setError(e);
			}

		}
		return response;
	}

}
