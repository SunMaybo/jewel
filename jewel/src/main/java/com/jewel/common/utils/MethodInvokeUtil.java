package com.jewel.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodInvokeUtil {
	/**
	 * 
	 * @Description: 反射调用方法
	 * @param @param bean
	 * @param @param params
	 * @param @param methodName
	 * @param @param paramTypes
	 * @param @return
	 * @param @throws IllegalAccessException
	 * @param @throws IllegalArgumentException
	 * @param @throws InvocationTargetException
	 * @param @throws NoSuchMethodException
	 * @param @throws SecurityException
	 * @param @throws ClassNotFoundException 设定文件
	 * @return Object 返回类型
	 */
	public static Object invoke(Object bean, Object[] params, String methodName, Class<?>[] paramTypes) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		Class<?> clazz = bean.getClass();

		Method method;
		method = clazz.getDeclaredMethod(methodName, paramTypes);
		if(null==method){
			return null;
		}
		return method.invoke(bean, params);
	}

}
