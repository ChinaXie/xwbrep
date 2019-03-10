package com.xwb.mappers;


import java.util.List;

import com.xwb.model.TbProvince;

public interface TbProvinceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbProvince record);

    int insertSelective(TbProvince record);

    TbProvince selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbProvince record);

    int updateByPrimaryKey(TbProvince record);
    
    List<TbProvince> selectAll();
}