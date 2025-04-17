package com.media.service;

import api.common.Result;
import com.media.domain.MediaFile;
import com.baomidou.mybatisplus.extension.service.IService;
import com.media.dto.UploadFileParamsDto;

import java.io.File;

/**
* @author 86183
* @description 针对表【media_file】的数据库操作Service
* @createDate 2025-03-16 17:47:37
*/
public interface MediaFileService extends IService<MediaFile> {

    /**
     * 检查文件是否存在
     * @param fileMd5
     * @return
     */
    public Result<Boolean> checkFile(String fileMd5);

    /**
     * 检查分块是否存在
     * @param fileMd5
     * @param chunkIndex
     * @return
     */
    public Result<Boolean> checkChunk(String fileMd5, int chunkIndex);

    /**
     * 上传分块
     * @param fileMd5
     * @param chunk
     * @param localChunkFilePath
     * @return
     */
    public Result uploadChunk(String fileMd5,int chunk,String localChunkFilePath);

    /**
     * 合并分块
     * @param fileMd5
     * @param chunkTotal
     * @param uploadFileParamsDto
     * @return
     */
    public Result mergechunks(Integer storeId,String fileMd5, int chunkTotal, UploadFileParamsDto uploadFileParamsDto);

    public MediaFile addMediaFileToDB(Integer storeId,String fileMd5,UploadFileParamsDto uploadFileParamsDto,String bucket,String objName);

    /**
     * 保存任务结果
     */



    public File downloadFileFromMinIO(String bucket, String objectName);

    public boolean addMediaFileToMinIO(String localFilePath,String mimeType,String bucket,String objName);

}
