package com.media.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName media_file
 */
@TableName(value ="media_file")
@Data
public class MediaFile implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 店铺id
     */
    private Integer storeId;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件id
     */
    private String fileId;

    /**
     * 上传时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime modifyTime;

    /**
     * 状态
     */
    private String status;

    /**
     * 存储桶
     */
    private String bucket;

    /**
     * 存储目录
     */
    private String storageDirectory;

    /**
     * 上传人
     */
    private String username;

    /**
     * 文件大小
     */
    private Long fileSize;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}