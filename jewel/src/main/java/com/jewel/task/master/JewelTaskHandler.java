package com.jewel.task.master;

import com.jewel.protocol.JewelProtocolPool;
import com.jewel.rpc.pojo.RPCRequest;
import com.jewel.task.RPCTaskRequest;
import com.jewel.task.RPCTaskResponse;

import java.util.List;

/**
 * Created by maybo on 2016/12/15.
 */
public class JewelTaskHandler extends AbstractJewelTaskHandler {
    @Override
    protected RPCTaskResponse handle(RPCTaskRequest rpcTaskRequest, RPCRequest request, List<JewelProtocolPool> jewelProtocolPools) {
        return null;
    }


}
