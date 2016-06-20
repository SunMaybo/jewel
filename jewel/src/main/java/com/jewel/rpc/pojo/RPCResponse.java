package com.jewel.rpc.pojo;

import java.util.Date;

public class RPCResponse {
	private String responseId;// 回复编号
	private Date date;// 日期
	private Object result;// 结果
	private Throwable error;// 异常
	private int type;// 1.ping
	private String state;// 状态

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public String getResponseId() {
		return responseId;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public Throwable getError() {
		return error;
	}

	public void setError(Throwable error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "RPCResponse [responseId=" + responseId + ", date=" + date + ", result=" + result + ", error=" + error + ", type=" + type + ", state="
				+ state + "]";
	}

}
