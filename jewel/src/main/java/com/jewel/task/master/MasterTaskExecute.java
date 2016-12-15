package com.jewel.task.master;

import com.jewel.task.RPCTaskResponse;

/**
 * Created by maybo on 2016/12/15.
 */
public interface MasterTaskExecute {
    public RPCTaskResponse execute(String appName,String serviceBeanName,String version);
}
