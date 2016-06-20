package com.jewel.config.loader;

/**
 * 
 * @Description: 配置信息加载类
 * @author maybo
 * @date 2016年5月25日 上午10:20:16
 *
 */
public abstract class ConfLoader {

	/**
	 * 
	 * @Description: 数据加载主要是将配置信息加载到缓存 中
	 * @param @param object 设定文件
	 * @return void 返回类型
	 */
	public abstract void load(Object object);

}
