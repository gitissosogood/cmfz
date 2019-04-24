package com.baizhi.dao;

import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface ChapterDao extends Mapper<Chapter> {
    void add(@Param("chapter") Chapter chapter, @Param("albumId") Integer albumId);
}
