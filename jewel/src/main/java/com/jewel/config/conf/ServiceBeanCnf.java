package com.jewel.config.conf;

import com.jewel.cluster.ClusterFactory;

/**
 * 
 * @Description: 服务配置信息
 * @author maybo
 * @date 2016年5月17日 下午3:34:59
 *
 */
public class ServiceBeanCnf extends BeanCnf {

	private String interfaceName;// 接口名字
	private String version="v_0.0.1";// 版本号
	private String ref;// 实现类的名字
	private String registry="registry";// 注册名字
	private String requestStyle=ClusterFactory.CONSISTENT_HASH_STRATEGY;// 服务负载方式

	public void setRequestStyle(String requestStyle) {
		this.requestStyle = requestStyle;
	}

	public String getRequestStyle() {
		return requestStyle;
	}

	private String app;

	public void setApp(String app) {
		this.app = app;
	}

	public String getApp() {
		return app;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public void setRegistry(String registry) {
		this.registry = registry;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public String getRef() {
		return ref;
	}

	public String getRegistry() {
		return registry;
	}

	public String getVersion() {
		return version;
	}

	@Override
	public String toString() {
		return "ServiceBeanCnf [interfaceName=" + interfaceName + ", version=" + version + ", ref=" + ref + ", registry=" + registry
				+ ", requestStyle=" + requestStyle + ", app=" + app + "]";
	}
}
