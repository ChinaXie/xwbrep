package com.xwb.service.impl;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xwb.mappers.TbForecastMapper;
import com.xwb.mappers.TbWeatherMapper;
import com.xwb.mappers.TbZhishuMapper;
import com.xwb.model.TbForecast;
import com.xwb.model.TbWeather;
import com.xwb.model.TbZhishu;
import com.xwb.service.WeatherService;

/**
 * 天气信息
 * @author xwb
 *
 */
@Service
public class WeatherServiceImpl implements WeatherService {
	
	@Autowired
	private TbForecastMapper tbForecastMapper;
	
	@Autowired
	private TbWeatherMapper tbWeatherMapper;
	
	@Autowired
	private TbZhishuMapper tbZhishuMapper;

	public  List<TbWeather> getTbWeather(String cityCode) {
		
		return tbWeatherMapper.selectByCityCode(cityCode);
	}

	public List<TbForecast> getTbForecasts(Integer tbWeatherId) {
		return tbForecastMapper.selectTbForecasts(tbWeatherId);
	}

	public List<TbZhishu> getTbZhishu(Integer tbWeatherId) {
		return tbZhishuMapper.selectTbZhishu(tbWeatherId);
	}

	
	
	
	

}
