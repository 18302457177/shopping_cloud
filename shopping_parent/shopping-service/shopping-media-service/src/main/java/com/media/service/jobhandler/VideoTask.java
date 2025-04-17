package com.media.service.jobhandler;

import com.media.domain.MediaProcess;
import com.media.service.MediaFileService;
import com.media.service.MediaProcessService;
import com.utils.video.Mp4VideoUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class VideoTask {

    @Autowired
    MediaFileService mediaFileService;

    @Autowired
    MediaProcessService mediaProcessService;

    @Value("${videoprocess.ffmpegpath}")
    String ffmpegpath;



    @XxlJob("videoJobHandler")
    public void videoJobHandler() throws Exception{
        int shardIndex = XxlJobHelper.getShardIndex();
        int shardTotal = XxlJobHelper.getShardTotal();
        List<MediaProcess> mediaProcessList = null;
        int size = 0;
        try{
            int processors = Runtime.getRuntime().availableProcessors();
            mediaProcessList = mediaProcessService.getMediaProcessList(shardIndex,shardTotal,processors);
            size = mediaProcessList.size();
            if(size<=0){
                return;
            }
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
        ExecutorService threadPool = Executors.newFixedThreadPool(size);
        CountDownLatch countDownLatch = new CountDownLatch(size);
        mediaProcessList.forEach(mediaProcess -> {
            threadPool.execute(()->{
                try {
                    Long taskId = mediaProcess.getId();
                    boolean b = mediaProcessService.startTask(taskId);
                    if(!b){
                        return;
                    }
                    //桶
                    String bucket = mediaProcess.getBucket();
                    //存储路径
                    String filePath = mediaProcess.getStoragePath();
                    //原始视频的md5值
                    String fileId = mediaProcess.getFileId();
                    //原始文件名称
                    String filename = mediaProcess.getFileName();
                    File originalFile = mediaFileService.downloadFileFromMinIO(mediaProcess.getBucket(), filePath);
                    if(originalFile == null){
                        log.debug("下载待处理文件失败,originalFile:{}", mediaProcess.getBucket().concat(mediaProcess.getStoragePath()));
                        return;
                    }
                    File mp4File = null;
                    try{
                        mp4File = File.createTempFile("mp4",".mp4");
                    }catch (IOException e){
                        log.error("创建mp4临时文件失败");
                        return;
                    }
                    String result = "";
                    try {
                        Mp4VideoUtil videoUtil = new Mp4VideoUtil(ffmpegpath, originalFile.getAbsolutePath(), mp4File.getName(), mp4File.getAbsolutePath());
                        result = videoUtil.generateMp4();
                    }catch (Exception e){
                        e.printStackTrace();
                        log.error("处理视频文件:{},出错:{}", mediaProcess.getStoragePath(), e.getMessage());
                    }
                    if (!result.equals("success")) {
                        //记录错误信息
                        log.error("处理视频失败,视频地址:{},错误信息:{}", bucket + filePath, result);
                        mediaProcessService.saveProcessFinishStatus(mediaProcess.getId(), "3", fileId, null, result);
                        return;
                    }

                    String objName = getFilePath(fileId,".mp4");
                    String url = "/"+bucket+"/"+objName;
                    try {
                        mediaFileService.addMediaFileToMinIO(mp4File.getAbsolutePath(), "video/mp4",bucket,objName);
                        mediaProcessService.saveProcessFinishStatus(mediaProcess.getId(), "2",fileId,url,null);
                    }catch (Exception e){
                        log.error("上传视频失败或入库失败,视频地址:{},错误信息:{}", bucket + objName, e.getMessage());
                        mediaProcessService.saveProcessFinishStatus(mediaProcess.getId(), "3",fileId,null,"处理后视频上传或入库失败");
                    }

                }finally {
                    countDownLatch.countDown();
                }
            });
        });
        countDownLatch.await(30, TimeUnit.MINUTES);
    }
    private String getFilePath(String fileMd5,String fileExt){
        return   fileMd5.substring(0,1) + "/" + fileMd5.substring(1,2) + "/"+ fileMd5.substring(2,3) + "/" + fileMd5 + "/" +fileMd5 +fileExt;
    }

}
