package com.media.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.media.domain.MediaFile;
import com.media.domain.MediaProcess;
import com.media.domain.MediaProcessHistory;
import com.media.mapper.MediaFileMapper;
import com.media.mapper.MediaProcessHistoryMapper;
import com.media.service.MediaProcessService;
import com.media.mapper.MediaProcessMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author 86183
* @description 针对表【media_process】的数据库操作Service实现
* @createDate 2025-03-16 17:38:23
*/
@Service
public class MediaProcessServiceImpl extends ServiceImpl<MediaProcessMapper, MediaProcess>
    implements MediaProcessService{

    @Autowired
    MediaFileMapper mediaFileMapper;

    @Autowired
    MediaProcessMapper mediaProcessMapper;

    @Autowired
    MediaProcessHistoryMapper mediaProcessHistoryMapper;


    /**
     * 查询待处理任务
     *
     * @param shardIndex
     * @param shardTotal
     * @param count
     * @return
     */
    @Override
    public List<MediaProcess> getMediaProcessList(int shardIndex, int shardTotal, int count) {

        List<MediaProcess> mediaProcesses = mediaProcessMapper.selectListByShardIndex(shardTotal, shardIndex, count);

        return mediaProcesses;
    }

    /**
     * 开启一个任务
     *
     * @param id
     */
    @Override
    public boolean startTask(long id) {

        int i = mediaProcessMapper.startTask(id);

        return i<=0?false:true;
    }


    /**
     * 保存任务结果
     *
     * @param taskId
     * @param status
     * @param fileId
     * @param url
     * @param errorMsg
     */
    @Override
    @Transactional
    public void saveProcessFinishStatus(Long taskId, String status, String fileId, String url, String errorMsg) {
        MediaProcess mediaProcess = mediaProcessMapper.selectById(taskId);
        if(mediaProcess == null){
            return;
        }
        LambdaQueryWrapper<MediaProcess> wrapper = new LambdaQueryWrapper<MediaProcess>().eq(MediaProcess::getId, taskId);
        if(status.equals("3")){
            MediaProcess mediaProcess1 = new MediaProcess();
            mediaProcess1.setStatus("3");
            mediaProcess1.setFailMessage(errorMsg);
            mediaProcess1.setFailCount(mediaProcess1.getFailCount()+1);
            mediaProcessMapper.update(mediaProcess1,wrapper);
            return;
        }
        MediaFile mediaFile = mediaFileMapper.selectOne(new LambdaQueryWrapper<MediaFile>().eq(MediaFile::getFileId, fileId));

        if(mediaFile!=null){
            mediaFile.setStorageDirectory(url);
            mediaFileMapper.updateById(mediaFile);
        }

        mediaProcess.setStoragePath(url);
        mediaProcess.setStatus("2");
        mediaProcessMapper.updateById(mediaProcess);

        MediaProcessHistory mediaProcessHistory = new MediaProcessHistory();
        BeanUtils.copyProperties(mediaProcess,mediaProcessHistory);
        mediaProcessHistoryMapper.insert(mediaProcessHistory);
        mediaProcessMapper.deleteById(mediaProcess.getId());

    }
}




