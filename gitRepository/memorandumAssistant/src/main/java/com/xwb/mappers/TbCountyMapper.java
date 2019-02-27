package com.xwb.mappers;

import com.xwb.model.TbCounty;

public interface TbCountyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbCounty record);

    int insertSelective(TbCounty record);

    TbCounty selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbCounty record);

    int updateByPrimaryKey(TbCounty record);
}