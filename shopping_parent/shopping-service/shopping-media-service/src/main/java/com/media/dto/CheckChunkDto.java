package com.media.dto;

import lombok.Data;

@Data
public class CheckChunkDto {

    private String fileMd5;

    private Integer chunk;

}
