package com.media.service.impl;

import api.common.MinioHost;
import api.common.Result;
import com.media.domain.MediaFile;
import com.media.mapper.MediaFileMapper;
import com.media.service.MediaFileService;
import com.media.service.UploadService;
import com.utils.jwt.SaltMd5Util;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    MinioClient minioClient;

    @Autowired
    MediaFileMapper mediaFileMapper;

    @Override
    public String getImg(String dir)throws Exception {
        String file = directory(dir,"jpg");
        return "http://192.168.226.138:9000/file/"+file;
    }

    @Override
    public Result uploadImg(MultipartFile file, String fileMd5) throws Exception {

        String[] split = file.getOriginalFilename().split("\\.");
        String directory = directory(fileMd5, split[1]);
        minioClient.putObject(PutObjectArgs.builder()
                        .bucket("file")
                        .object(directory)
                        .contentType("image/jpeg")
                        .stream(file.getInputStream(), file.getSize(), -1)
                .build());
        MediaFile mediaFile = new MediaFile();
        mediaFile.setFileId(fileMd5);
        mediaFile.setBucket("file");
        mediaFile.setFileSize(file.getSize());
        mediaFile.setFileType("图片");
        mediaFile.setStoreId(1);
        mediaFile.setStorageDirectory(MinioHost.MINIO_HOST+ MinioHost.FILE +directory);
        int insert = mediaFileMapper.insert(mediaFile);
        if(insert<=0){
            return Result.fail("插入失败");
        }
        return Result.success();
    }

    public String directory(String md5,String suffix){
        String substring1 = md5.substring(0, 1);
        String substring2 = md5.substring(1, 2);
        String substring3 = md5.substring(2, 3);
        return substring1+"/"+substring2+"/"+substring3+"/"+md5+"."+suffix;
    }


}
