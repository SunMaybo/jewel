package com.jewel.rpc.pojo;

import java.util.Arrays;
import java.util.Date;

public class RPCRequest {
	private String requestId;// 请求编号
	private int type;// 1.ping
	private String host;
	private String interfaceName;// 接口名
	private String methodName;// 方法名
	private Date date;// 日期
	private Object[] params;// 参数

	private Class<?>[] paramTypes;// 参数类型

	private String version;// 版本

	public void setVersion(String version) {
		this.version = version;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public String getVersion() {
		return version;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

	public void setParamTypes(Class<?>[] paramTypes) {
		this.paramTypes = paramTypes;
	}

	public Class<?>[] getParamTypes() {
		return paramTypes;
	}

	@Override
	public String toString() {
		return "RPCRequest [requestId=" + requestId + ", host=" + host + ", interfaceName=" + interfaceName + ", methodName=" + methodName
				+ ", date=" + date + ", params=" + Arrays.toString(params) + ", paramTypes=" + Arrays.toString(paramTypes) + ", version=" + version
				+ "]";
	}

}
