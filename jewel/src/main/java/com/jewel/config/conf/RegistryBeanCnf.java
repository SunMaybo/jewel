package com.jewel.config.conf;

/**
 * 
 * @Description: 注册配置信息
 * @author maybo
 * @date 2016年5月17日 下午3:31:55
 *
 */
public class RegistryBeanCnf extends BeanCnf {

	private String address;// 地址---zookeeper://127.0.0.1:6757
	private String name = "registry";// 名字用于和ServiceBeanCnf或ReferenceBeanCnf配置进行关联
	private boolean check;
	public void setCheck(boolean check) {
		this.check = check;
	}

	public boolean getCheck() {
		return this.check;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	@Override
	public String toString() {
		return "RegistryBeanCnf [address=" + address + ", name=" + name + ", check=" + check + "]";
	}

}
