package com.jewel.test;

import com.jewel.rpc.server.RPCServer;

public class ServerStart {

	public static void main(String[] args){
		try {
			RPCServer.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
