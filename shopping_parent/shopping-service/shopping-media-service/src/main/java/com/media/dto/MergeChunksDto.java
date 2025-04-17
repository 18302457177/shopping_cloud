package com.media.dto;

import lombok.Data;

@Data
public class MergeChunksDto {

    private String fileMd5;
    private String fileName;
    private Integer chunkTotal;

}
