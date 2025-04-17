package com.media.service.jobhandler;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @description 测试执行器
 * @author Mr.M
 * @date 2022/9/13 20:32
 * @version 1.0
 */
@Component
@Slf4j
public class SampleJob {

    /**
     * 1、简单任务示例（Bean模式）
     */
    @XxlJob("test")
    public void testJob() throws Exception {
        System.out.println("开始执行.....");

    }

}
