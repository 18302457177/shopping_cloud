package com.media.service.impl;

import api.common.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.media.domain.MediaFile;
import com.media.domain.MediaProcess;
import com.media.domain.MediaProcessHistory;
import com.media.dto.UploadFileParamsDto;
import com.media.mapper.MediaProcessHistoryMapper;
import com.media.mapper.MediaProcessMapper;
import com.media.service.MediaFileService;
import com.media.mapper.MediaFileMapper;
import io.minio.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
* @author 86183
* @description 针对表【media_file】的数据库操作Service实现
* @createDate 2025-03-16 17:47:37
*/
@Service
public class MediaFileServiceImpl extends ServiceImpl<MediaFileMapper, MediaFile>
    implements MediaFileService{

    @Autowired
    MediaFileMapper mediaFileMapper;

    @Autowired
    MinioClient minioClient;

    @Autowired
    MediaProcessMapper mediaProcessMapper;

    @Autowired
    MediaProcessHistoryMapper mediaProcessHistoryMapper;


    /**
     * 检查文件是否存在
     *
     * @param fileMd5
     * @return
     */
    @Override
    public Result<Boolean> checkFile(String fileMd5) {

        MediaFile mediaFile = mediaFileMapper.selectById(fileMd5);
        if (mediaFile != null){
            String bucket = mediaFile.getBucket();
            String directory = mediaFile.getStorageDirectory();

            InputStream inputStream = null;

            try {
                inputStream = minioClient.getObject(GetObjectArgs.builder()
                                .bucket(bucket)
                                .object(directory)
                        .build());
                if (inputStream != null){
                    return Result.success(true);
                }
            }catch (Exception e){

            }

        }

        return Result.fail(false);
    }

    /**
     * 检查分块是否存在
     *
     * @param fileMd5
     * @param chunkIndex
     * @return
     */
    @Override
    public Result<Boolean> checkChunk(String fileMd5, int chunkIndex) {

        String chunkFilePath = getChunkFilePath(fileMd5) + chunkIndex;

        InputStream inputStream = null;
        try {
            inputStream = minioClient.getObject(GetObjectArgs.builder()
                            .bucket("video")
                            .object(chunkFilePath)
                    .build());
            if (inputStream != null){
                return Result.success(true);
            }
        }catch (Exception e){

        }

        return Result.fail(false);
    }

    public String getChunkFilePath(String md5){
        String substring1 = md5.substring(0, 1);
        String substring2 = md5.substring(1, 2);
        String substring3 = md5.substring(2, 3);
        return substring1+"/"+substring2+"/"+substring3+"/"+md5+"/"+"chunk"+"/";
    }

    /**
     * 上传分块
     *
     * @param fileMd5
     * @param chunk
     * @param localChunkFilePath
     * @return
     */
    @Override
    public Result uploadChunk(String fileMd5, int chunk, String localChunkFilePath) {

        String chunkFilePath  = getChunkFilePath(fileMd5) + chunk;
        String mimeType = getMimeType(null);

        boolean b = addMediaFileToMinIO(localChunkFilePath, mimeType, "video", chunkFilePath);
        if (!b){
            return Result.fail("上传失败");
        }
        return Result.success(true);

    }

    public boolean addMediaFileToMinIO(String localFilePath,String mimeType,String bucket,String objName){
        try {
            UploadObjectArgs upload = UploadObjectArgs.builder()
                    .bucket(bucket)
                    .object(objName)
                    .filename(localFilePath)
                    .contentType(mimeType)
                    .build();
            minioClient.uploadObject(upload);
            return true;
        }catch (Exception e){
            log.debug("上传文件失败");
        }
        return false;
    }

    public String getMimeType(String extension){
        if (extension == null){
            extension = "";
        }
        ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(extension);
        String mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        if (extensionMatch != null){
            mimeType = extensionMatch.getMimeType();
        }
        return mimeType;
    }

    @Transactional
    public MediaFile addMediaFileToDB(Integer storeId,String fileMd5,UploadFileParamsDto uploadFileParamsDto,String bucket,String objName){
        MediaFile mediaFile = mediaFileMapper.selectById(fileMd5);
        if (mediaFile == null){
            mediaFile = new MediaFile();
            mediaFile.setId(0);
            BeanUtils.copyProperties(uploadFileParamsDto,mediaFile);
            mediaFile.setBucket(bucket);
            mediaFile.setFileId(fileMd5);
            mediaFile.setStoreId(storeId);
            mediaFile.setStorageDirectory("/"+bucket+"/"+objName);
            //mediaFile.setCreateTime(LocalDateTime.now());
            mediaFile.setStatus("1");
            mediaFile.setFileName(uploadFileParamsDto.getFilename());

            int insert = mediaFileMapper.insert(mediaFile);
            if (insert<0){
                log.error("保存文件到数据库失败");

            }
            addWaitingTask(mediaFile);
        }
        return mediaFile;
    }

    /**
     * 合并分块
     */
    private void addWaitingTask(MediaFile mediaFile){
        String filename = mediaFile.getFileName();
        String extension = filename.substring(filename.lastIndexOf("."));
        String mimeType = getMimeType(extension);
        if(mimeType.equals("video/x-msvideo")){
            MediaProcess mediaProcess = new MediaProcess();
            BeanUtils.copyProperties(mediaFile,mediaProcess);
            mediaProcess.setStatus("1");
            mediaProcess.setFailCount(0);
            mediaProcessMapper.insert(mediaProcess);
        }
    }



    /**
     * 合并分块
     *
     * @param fileMd5
     * @param chunkTotal
     * @param uploadFileParamsDto
     * @return
     */
    @Override
    @Transactional
    public Result mergechunks(Integer storeId,String fileMd5, int chunkTotal, UploadFileParamsDto uploadFileParamsDto) {

        String chunkFilePath = getChunkFilePath(fileMd5);

        List<ComposeSource> sources = Stream.iterate(0, i -> ++i).limit(chunkTotal)
                .map(i -> ComposeSource.builder()
                        .bucket("video")
                        .object(chunkFilePath.concat(Integer.toString(i)))
                        .build())
                .collect(Collectors.toList());

        String filename = uploadFileParamsDto.getFilename();
        String extName = filename.substring(filename.lastIndexOf("."));

        String mergeFilePath = getFilePathByMd5(fileMd5, extName);
        try {
            ObjectWriteResponse response = minioClient.composeObject(
                    ComposeObjectArgs.builder()
                            .bucket("video")
                            .object(mergeFilePath)
                            .sources(sources)
                            .build()
            );
            System.out.println("response = " + response);
            log.debug("合并成功");
        }catch (Exception e){
            log.error("合并失败");
            throw new RuntimeException(e);
            //return Result.fail("合并失败");
        }
        File minioFile = downloadFileFromMinIO("video", mergeFilePath);
        if (minioFile == null){
            log.debug("下载合并文件失败");
            return Result.fail("下载合并文件失败");
        }

        try (InputStream fileInputStream = new FileInputStream(minioFile)){

            String md5Hex = DigestUtils.md5Hex(fileInputStream);
            if (!fileMd5.equals(md5Hex)){
                return Result.fail("文件校验失败，上传失败");
            }
            uploadFileParamsDto.setFileSize(minioFile.length());

        } catch (Exception e) {
            log.debug("校验文件失败");

            return Result.fail("校验文件失败");
        } finally {
            if (minioFile != null){
                minioFile.delete();
            }
        }

        addMediaFileToDB(1,fileMd5,uploadFileParamsDto,"video",mergeFilePath);
        clearChunkFiles(chunkFilePath,chunkTotal);



        return Result.success(true);
    }

    /**
     * 清除分块
     * @param chunkFilePath
     * @param chunkTotal
     */
    private void clearChunkFiles(String chunkFilePath,int chunkTotal){
        try {
            List<DeleteObject> deleteObjects = Stream.iterate(0, i -> ++i)
                    .limit(chunkTotal)
                    .map(i -> new DeleteObject(chunkFilePath.concat(Integer.toString(i))))
                    .collect(Collectors.toList());
            RemoveObjectsArgs removeObjectsArgs = RemoveObjectsArgs.builder()
                    .bucket("video")
                    .objects(deleteObjects)
                    .build();
            Iterable<io.minio.Result<DeleteError>> results = minioClient.removeObjects(removeObjectsArgs);
            results.forEach(r->{
                DeleteError deleteError = null;
                try {
                    deleteError = r.get();
                }catch (Exception e){
                    e.printStackTrace();
                    log.error("清除分块文件失败");
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            log.error("清除分块文件失败");
        }
    }

    /**
     * 得到合并后的文件的地址
     * @param fileMd5
     * @param fileExt
     * @return
     */
    private String getFilePathByMd5(String fileMd5,String fileExt){
        return   fileMd5.substring(0,1) + "/" + fileMd5.substring(1,2) + "/" + fileMd5.substring(2,3) + "/" + fileMd5 + "/" +fileMd5 +fileExt;
    }

    public File downloadFileFromMinIO(String bucket,String objectName){
        File minioFile = null;
        FileOutputStream outputStream = null;
        try {
            InputStream inputStream = minioClient.getObject(GetObjectArgs.builder()
                            .bucket("video")
                            .object(objectName)
                    .build());
            minioFile = File.createTempFile("minio", ".merge");
            outputStream = new FileOutputStream(minioFile);
            IOUtils.copy(inputStream,outputStream);
            return minioFile;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (outputStream != null){
                try {
                    outputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


}




