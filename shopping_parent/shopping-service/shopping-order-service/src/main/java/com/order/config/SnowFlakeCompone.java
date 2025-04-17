package com.order.config;

import com.utils.SnowFlake;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SnowFlakeCompone {
    @Value("${server.workId}")
    private long workId;

    @Value("${server.datacenterId}")
    private long datacenterId;

    private static volatile SnowFlake instance;

    /**
     * 获取实例
     * @return
     */
    public SnowFlake getInstance(){
        if(instance == null){
            synchronized (SnowFlake.class){
                if(instance == null){
                    instance = new SnowFlake(workId, datacenterId);
                }
            }
        }
        return instance;
    }
}
