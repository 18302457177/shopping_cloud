package com.media.mapper;

import com.media.domain.MediaProcess;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
* @author 86183
* @description 针对表【media_process】的数据库操作Mapper
* @createDate 2025-03-16 17:38:23
* @Entity com.media.domain.MediaProcess
*/
@Mapper
public interface MediaProcessMapper extends BaseMapper<MediaProcess> {

    @Select("select * from media_process t where t.id % #{shardTotal} = #{shardIndex} and (t.status = '1' or t.status = '3') and t.fail_count < 3 limit #{count}")
    List<MediaProcess> selectListByShardIndex(@Param("shardTotal") int shardTotal, @Param("shardIndex") int shardIndex, @Param("count") int count);

    /**
     * 开启一个任务
     */
    @Update("update media_process m set m.status='4'where (m.status = '1'or m.status='3')and m.fail_count<3 and m.id=#{id}")
    int startTask(@Param("id")long id);

}




