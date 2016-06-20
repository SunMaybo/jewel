package com.jewel.registry;

import java.util.EventObject;

public class SyncEvent extends EventObject {

	/**
	 * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;

	public SyncEvent(Object source) {
		super(source);
	}

}
