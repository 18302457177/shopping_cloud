package com.media.service;

import com.media.domain.MediaProcess;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86183
* @description 针对表【media_process】的数据库操作Service
* @createDate 2025-03-16 17:38:23
*/
public interface MediaProcessService extends IService<MediaProcess> {

    /**
     * 查询待处理任务
     * @param shardIndex
     * @param shardTotal
     * @param count
     * @return
     */
    public List<MediaProcess> getMediaProcessList(int shardIndex, int shardTotal, int count);

    /**
     * 开启一个任务
     */
    public boolean startTask(long id);

    void saveProcessFinishStatus(Long taskId,String status,String fileId,String url,String errorMsg);

}
