package com.xwb.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xwb.model.TbWeather;

public interface TbWeatherMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbWeather record);

    int insertSelective(TbWeather record);

    TbWeather selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbWeather record);

    int updateByPrimaryKey(TbWeather record);
    
    List<TbWeather> selectByCityCode(@Param("cityCode")String cityCode);
}