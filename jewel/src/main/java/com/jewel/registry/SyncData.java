package com.jewel.registry;

public class SyncData {

	public static final int SYNC_PROTOCOL_POOL=2;//删除
	public static final int SYNC_ADD_SERVICE_CNF = 3;// 添加服务配置
	public static final int SYNC_ALTER_SERVICE_CNF = 4;// 修改服务配置

	public String app;
	public int state;
	public Object object;

	public void setApp(String app) {
		this.app = app;
	}

	public String getApp() {
		return app;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Object getObject() {
		return object;
	}

}
