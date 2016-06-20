package com.jewel.cluster.exception;

public class NotFindClusterException extends Exception {

	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	private static final long serialVersionUID = 1L;

	public NotFindClusterException() {
		super("没发现集群提供异常");
	}
	public NotFindClusterException(String msg) {
		super(msg);
	}
}
