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
 * @TableName media_process_history
 */
@TableName(value ="media_process_history")
@Data
public class MediaProcessHistory implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private String fileName;

    /**
     * 
     */
    private String bucket;

    /**
     * 
     */
    private String storagePath;

    /**
     * 
     */
    private String status;

    /**
     * 
     */
    private Date uploadTime;

    /**
     * 
     */
    private Date finishTime;

    /**
     * 
     */
    private String previewPath;

    /**
     * 
     */
    private String failMessage;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}