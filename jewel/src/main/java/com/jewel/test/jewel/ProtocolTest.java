package com.jewel.test.jewel;

import java.util.Date;

import org.apache.log4j.PropertyConfigurator;

import com.jewel.protocol.JewelProtocolPool;
import com.jewel.rpc.pojo.RPCRequest;
import com.jewel.utils.UUIDUtils;

public class ProtocolTest {

	public static void main(String[] args) {
		PropertyConfigurator.configure(System.getProperty("user.dir")+"/src/main/java"+"/log4j.properties");
		JewelProtocolPool jewelProtocolPool = new JewelProtocolPool(5, "127.0.0.1", 4321);
		RPCRequest request = new RPCRequest();
		request.setDate(new Date());
		request.setInterfaceName("com.jewel.test.IDemo");
		request.setMethodName("hello");
		request.setVersion("v_0.0.1");
		request.setRequestId(UUIDUtils.findUUID());
		Object object = jewelProtocolPool.obtainProtocol().invoke(request);
		System.out.println(object.toString());
	}
}
