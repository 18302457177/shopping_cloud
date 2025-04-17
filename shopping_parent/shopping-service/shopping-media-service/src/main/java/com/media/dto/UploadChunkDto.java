package com.media.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


import java.io.Serializable;

@Data
public class UploadChunkDto implements Serializable {


    private MultipartFile file;

    private String fileMd5;
    private Integer chunk;

}
