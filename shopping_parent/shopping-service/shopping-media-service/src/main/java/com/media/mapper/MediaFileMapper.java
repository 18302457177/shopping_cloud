package com.media.mapper;

import com.media.domain.MediaFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86183
* @description 针对表【media_file】的数据库操作Mapper
* @createDate 2025-03-16 17:47:37
* @Entity com.media.domain.MediaFile
*/
@Mapper
public interface MediaFileMapper extends BaseMapper<MediaFile> {

}




