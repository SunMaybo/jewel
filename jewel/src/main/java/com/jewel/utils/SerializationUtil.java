package com.jewel.utils;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * 
 * @序列化对象采用protostuff
 * @author maybo
 * @date 2016年5月9日 上午10:31:48
 *
 * @param <T>
 */
public class SerializationUtil {

	@SuppressWarnings("unchecked")
	public static <T> byte[] serialize(Object obj, Class<T> clazz) {
		Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(clazz);
		LinkedBuffer buffer = LinkedBuffer.allocate(4096);
		byte[] protostuff = null;
		try {
			protostuff = ProtostuffIOUtil.toByteArray((T)obj, schema, buffer);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			buffer.clear();
		}
		return protostuff;
	}

	public static <T> T deserialize(byte[] buff, Class<T> clazz) {
		T object = null;
		try {
			object = (T) clazz.newInstance();
			Schema<T> schema = RuntimeSchema.getSchema(clazz);
			ProtostuffIOUtil.mergeFrom(buff, object, schema);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return object;
	}
}
