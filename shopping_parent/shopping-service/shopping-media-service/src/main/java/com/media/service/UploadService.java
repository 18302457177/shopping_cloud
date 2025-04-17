package com.media.service;

import api.common.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface UploadService {
    Result uploadImg(MultipartFile file, String fileMd5) throws Exception;

    String getImg(String md5) throws Exception;


}
