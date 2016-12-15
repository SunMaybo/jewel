package com.jewel.task.master;

import com.jewel.task.RPCTaskResponse;

/**
 * Created by maybo on 2016/12/15.
 */
public class TaskServer {

    private AbstractJewelTaskHandler abstractJewelTaskHandler;
    public TaskServer(){}
    public TaskServer(AbstractJewelTaskHandler abstractJewelTaskHandler){
        this.abstractJewelTaskHandler=abstractJewelTaskHandler;
    }
    public void setAbstractJewelTaskHandler(AbstractJewelTaskHandler abstractJewelTaskHandler){
        this.abstractJewelTaskHandler=abstractJewelTaskHandler;
    }
    public RPCTaskResponse excute(String appName,String serviceBeanName,String version){
        return this.abstractJewelTaskHandler.execute(appName,serviceBeanName,version);
    }
    public RPCTaskResponse excute(String serviceBeanName,String version){
        return this.abstractJewelTaskHandler.execute(serviceBeanName,version);
    }
}
