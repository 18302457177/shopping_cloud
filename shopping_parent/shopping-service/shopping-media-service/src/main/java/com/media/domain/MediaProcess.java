package com.media.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName media_process
 */
@TableName(value ="media_process")
@Data
public class MediaProcess implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 存储桶
     */
    private String bucket;

    /**
     * 存储路径
     */
    private String storagePath;

    /**
     * 状态
     */
    private String status;

    /**
     * 上传时间
     */
    private Date uploadTime;

    /**
     * 完成时间
     */
    private Date finishTime;

    /**
     * 文件访问地址
     */
    private String previewPath;

    /**
     * 失败信息
     */
    private String failMessage;

    private Integer failCount;

    private String fileId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}