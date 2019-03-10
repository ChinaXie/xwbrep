package com.xwb.service;

import java.util.List;

import com.xwb.model.TbForecast;
import com.xwb.model.TbWeather;
import com.xwb.model.TbZhishu;

public interface WeatherService {
	
	 List<TbWeather> getTbWeather(String cityCode);
	 List<TbForecast> getTbForecasts(Integer tbWeatherId);
	 List<TbZhishu> getTbZhishu(Integer tbWeatherId);

}
