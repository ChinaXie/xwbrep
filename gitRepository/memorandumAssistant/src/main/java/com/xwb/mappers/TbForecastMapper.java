package com.xwb.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xwb.model.TbForecast;

public interface TbForecastMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbForecast record);

    int insertSelective(TbForecast record);

    TbForecast selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbForecast record);

    int updateByPrimaryKey(TbForecast record);
    
    List<TbForecast> selectTbForecasts(@Param("tbWeatherId")Integer tbWeatherId);
}