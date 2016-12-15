package com.jewel.task.master;

import com.jewel.cluster.AbstractLoadCluster;
import com.jewel.cluster.ClusterFactory;
import com.jewel.cluster.exception.NotFindClusterException;
import com.jewel.config.cache.CnfCache;
import com.jewel.protocol.JewelProtocolPool;
import com.jewel.rpc.pojo.RPCRequest;
import com.jewel.task.RPCTaskRequest;
import com.jewel.task.RPCTaskResponse;
import com.jewel.utils.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * Created by maybo on 2016/12/15.
 */
public abstract  class AbstractJewelTaskHandler implements MasterTaskExecute{

    private Logger logger= LoggerFactory.getLogger(this.getClass());
    private String appName;//application
    private String serviceBeanName;//服务名字
    private AbstractLoadCluster abstractLoadCluster;//获取集群列表
    private RPCRequest request;//请求体
    private RPCTaskResponse response;
    public AbstractJewelTaskHandler(){}
    public RPCTaskResponse startTask(String appName, String serviceBeanName, String version){
        if (null==appName||"".equals(appName)){
            throw new NullPointerException("The appName is null!");
        }
        if (null==serviceBeanName||"".equals(serviceBeanName)){
            throw new NullPointerException("The serviceBeanName is null!");
        }

        if (null==appName){//获取app
            appName=CnfCache.findAppFromService(serviceBeanName,version);
        }
        // 通过工程名获取工程下的集群
        this.abstractLoadCluster = ClusterFactory.obtainCluster(appName);

        if (null == this.abstractLoadCluster) {
            logger.error(new NotFindClusterException().getMessage());
        } else {
            request=new RPCRequest();
            request.setDate(new Date());
            request.setInterfaceName(serviceBeanName);
            request.setMethodName("execute");
            request.setParamTypes(new Class[]{RPCTaskRequest.class});
            request.setRequestId(UUIDUtils.findUUID());
            request.setVersion(version);
            return handle(new RPCTaskRequest(),request,abstractLoadCluster.getJewelProtocolPools());
        }
        return null;
    }

    protected abstract  RPCTaskResponse handle(RPCTaskRequest rpcTaskRequest,RPCRequest request,List<JewelProtocolPool> jewelProtocolPools);


     public RPCTaskResponse execute(String appName, String serviceBeanName, String version) {
        return startTask(appName,serviceBeanName,version);
    }
    public RPCTaskResponse execute(String serviceBeanName, String version) {
        return startTask(null,serviceBeanName,version);
    }

}


