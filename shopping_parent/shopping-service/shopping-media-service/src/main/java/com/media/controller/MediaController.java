package com.media.controller;

import api.common.Result;
import com.media.dto.CheckChunkDto;
import com.media.dto.MergeChunksDto;
import com.media.dto.UploadChunkDto;
import com.media.dto.UploadFileParamsDto;
import com.media.service.MediaFileService;
import com.media.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/media")
@Api(tags = "文件上传访问接口")
public class MediaController {

    @Autowired
    UploadService uploadService;

    @Autowired
    MediaFileService mediaFileService;

    @ApiOperation("上传图片")
    @PostMapping("/upload/img")
    public Result UploadImg(@RequestParam("file") MultipartFile file,@RequestParam("fileMd5") String fileMd5) throws Exception {
        return uploadService.uploadImg(file,fileMd5);
    }

    @ApiOperation("访问minio图片")
    @GetMapping("/getImg")
    public String getImg(String md5) throws Exception {
        return uploadService.getImg(md5);
    }

    @ApiOperation(value = "文件上传前检查文件")
    @PostMapping("/upload/checkFile/{fileMd5}")
    public Result<Boolean> checkFile(@PathVariable("fileMd5") String fileMd5) throws Exception {

        return mediaFileService.checkFile(fileMd5);
    }

    @ApiOperation(value = "分块文件上传前的检测")
    @PostMapping("/upload/checkChunk")
    public Result<Boolean> checkChunk(@RequestBody CheckChunkDto checkChunk) throws Exception {
        return mediaFileService.checkChunk(checkChunk.getFileMd5(), checkChunk.getChunk());
    }

    @ApiOperation(value = "上传分块文件")
    @PostMapping("/upload/uploadChunk")
    public Result uploadChunk(@RequestParam("file") MultipartFile file,
                              @RequestParam("fileMd5") String fileMd5,
                              @RequestParam("chunk")Integer chunk) throws Exception {

        File temp = File.createTempFile("minio","temp");
        file.transferTo(temp);
        return mediaFileService.uploadChunk(fileMd5,chunk,temp.getAbsolutePath());
    }

    @ApiOperation(value = "合并文件")
    @PostMapping("/upload/mergeChunks")
    public Result mergeChunks(@RequestBody MergeChunksDto mergeChunksDto) throws Exception {

        UploadFileParamsDto uploadFileParamsDto = new UploadFileParamsDto();
        uploadFileParamsDto.setFileType("视频");
        uploadFileParamsDto.setFilename(mergeChunksDto.getFileName());

        return mediaFileService.mergechunks(1, mergeChunksDto.getFileMd5(), mergeChunksDto.getChunkTotal(),uploadFileParamsDto);

    }



}
