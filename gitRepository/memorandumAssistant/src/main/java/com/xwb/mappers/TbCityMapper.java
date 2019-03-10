package com.xwb.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xwb.model.TbCity;

public interface TbCityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbCity record);

    int insertSelective(TbCity record);

    TbCity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbCity record);

    int updateByPrimaryKey(TbCity record);
    
    List<TbCity> selectByProCode(@Param("proviceCode") String provinceCode);
    
    List<TbCity> selectCityNoProvince();
}