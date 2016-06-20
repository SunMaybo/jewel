package com.jewel.rpc.decode;

import java.util.List;

import com.jewel.utils.SerializationUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 
 * @Description: 解码
 * @author maybo
 * @date 2016年5月13日 下午3:18:14
 *
 */
public class RPCDecoder extends ByteToMessageDecoder {
	private Class<?> clazz;

	public RPCDecoder(Class<?> clazz) {
		this.clazz = clazz;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (in.readableBytes() < 4) {
			return;
		}
		in.markReaderIndex();
		int dataLength = in.readInt();
		if (dataLength < 0) {
			ctx.close();
		}
		if (in.readableBytes() < dataLength) {
			in.resetReaderIndex();
		}
		byte[] data = new byte[dataLength];
		in.readBytes(data);
		Object object = null;
		//反序列化
		object = SerializationUtil.deserialize(data, clazz);
		out.add(object);
	}
}
