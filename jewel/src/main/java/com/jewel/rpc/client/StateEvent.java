package com.jewel.rpc.client;

public class StateEvent {

	public String clientId;// 客户端编号，和jewel协议层一致

	private boolean isActice = true;// 通道是可用

	private boolean isClose = false;// 连接关闭不可用

	private boolean isValid = false;// 通道是空闲的

	public boolean isClose() {
		return isClose;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public boolean isValid() {
		return this.isValid;
	}

	public void setClose(boolean isClose) {
		this.isClose = isClose;
	}

	private boolean reConnect;

	public void setReConnect(boolean reConnect) {
		this.reConnect = reConnect;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientId() {
		return clientId;
	}

	public boolean isReConnect() {
		return reConnect;
	}

	public boolean isActive() {
		return isActice;
	}

	public void setActice(boolean isActice) {
		this.isActice = isActice;
	}

	@Override
	public String toString() {
		return "StateEvent [clientId=" + clientId + ", isActice=" + isActice + ", isClose=" + isClose + ", isValid=" + isValid + ", reConnect="
				+ reConnect + "]";
	}

}
