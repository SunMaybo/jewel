package com.jewel.config.conf;
/**
 * 
 * @Description: 远程访问类接口配置信息
 * @author maybo
 * @date 2016年5月17日 下午3:26:00
 *
 */
public class ReferenceBeanCnf extends BeanCnf {

	private String interfaceName;// 接口名字
	private String version="v_0.0.1";// 版本号
	private String registry="registry";// 注册的名字----用于发现提供者服务配置信息

	public void setRegistry(String registry) {
		this.registry = registry;
	}

	public String getRegistry() {
		return registry;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public String getVersion() {
		return version;
	}

	@Override
	public String toString() {
		return "ReferenceBeanCnf [interfaceName=" + interfaceName + ", version=" + version + ", registry=" + registry + "]";
	}


}
