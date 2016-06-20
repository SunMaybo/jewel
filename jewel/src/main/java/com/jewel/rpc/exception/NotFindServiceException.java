package com.jewel.rpc.exception;

public class NotFindServiceException extends Exception {

	/**
	 * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	public NotFindServiceException() {
		super("没发现服务异常");
	}
	public NotFindServiceException(String msg) {
		super(msg);
	}
}
