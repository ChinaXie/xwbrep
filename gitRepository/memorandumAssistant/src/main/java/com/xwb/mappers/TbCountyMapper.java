package com.xwb.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xwb.model.TbCounty;

public interface TbCountyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbCounty record);

    int insertSelective(TbCounty record);

    TbCounty selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbCounty record);

    int updateByPrimaryKey(TbCounty record);
    
    List<TbCounty> selectByProCityCode(@Param("provinceCode")String provinceCode,@Param("cityCode")String cityCode);
    
    TbCounty selectByCode(@Param("provinceCode")String provinceCode,@Param("cityCode")String cityCode,@Param("countyCode")String countyCode);
    
}