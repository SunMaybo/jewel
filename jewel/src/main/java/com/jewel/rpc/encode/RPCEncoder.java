package com.jewel.rpc.encode;

import com.jewel.utils.SerializationUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 
 * @Description: 解码
 * @author maybo
 * @date 2016年5月13日 下午3:18:59
 *
 */
@SuppressWarnings("rawtypes")
public class RPCEncoder extends MessageToByteEncoder {

	private Class<?> clazz;

	public RPCEncoder(Class<?> clazz) {
		this.clazz = clazz;
	}
	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		if (clazz.isInstance(msg)) {
			// 序列化
			byte[] buff = SerializationUtil.serialize(msg, clazz);
			out.writeInt(buff.length);
			out.writeBytes(buff);
		}

	}
}
