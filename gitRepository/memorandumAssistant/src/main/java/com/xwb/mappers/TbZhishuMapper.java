package com.xwb.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xwb.model.TbZhishu;

public interface TbZhishuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbZhishu record);

    int insertSelective(TbZhishu record);

    TbZhishu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbZhishu record);

    int updateByPrimaryKey(TbZhishu record);
    
    List<TbZhishu> selectTbZhishu(@Param("tbWeatherId")Integer tbWeatherId);
}