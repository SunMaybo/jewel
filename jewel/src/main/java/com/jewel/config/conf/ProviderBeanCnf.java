package com.jewel.config.conf;

/**
 * 
 * @Description: 关于服务提供者的配置信息
 * @author maybo
 * @date 2016年5月17日 下午3:20:00
 *
 */
public class ProviderBeanCnf extends BeanCnf {

	private String name;// 提供者的名字

	private String host;// 提供者的ip

	private int port;// 提供者的端口号

	private int weight;// 提供者的权重

	private String registry = "registry";

	public void setRegistry(String registry) {
		this.registry = registry;
	}

	public String getRegistry() {
		return registry;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getWeight() {
		return weight;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getName() {
		return name;
	}

	public int getPort() {
		return port;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getHost() {
		return host;
	}

	@Override
	public String toString() {
		return "ProviderBeanCnf [name=" + name + ", host=" + host + ", port=" + port + ", weight=" + weight + ", registry=" + registry + "]";
	}

}
