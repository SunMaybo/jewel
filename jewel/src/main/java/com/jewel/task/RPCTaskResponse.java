package com.jewel.task;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by maybo on 2016/12/15.
 */
public class RPCTaskResponse extends HashMap<String,Object>{
    private String taskId;

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
    }
}
