package com.jewel.config.conf;
/**
 * @Description: 工程信息的配置类
 * @author maybo
 * @date 2016年5月17日 下午3:17:43
 *
 */
public class AppBeanCnf extends BeanCnf {

	private String name;// 工程的名字


	public void setName(String name) {
		this.name = name;
	}


	public String getName() {
		return name;
	}


	@Override
	public String toString() {
		return "AppBeanCnf [name=" + name + "]";
	}

	

}
