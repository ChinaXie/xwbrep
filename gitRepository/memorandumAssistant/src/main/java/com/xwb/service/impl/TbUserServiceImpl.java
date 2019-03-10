package com.xwb.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xwb.Controller.SysMnagerController;
import com.xwb.common.SmsUtil;
import com.xwb.mappers.TbForecastMapper;
import com.xwb.mappers.TbUserMapper;
import com.xwb.mappers.TbWeatherMapper;
import com.xwb.mappers.TbZhishuMapper;
import com.xwb.model.TbForecast;
import com.xwb.model.TbUser;
import com.xwb.model.TbWeather;
import com.xwb.model.TbZhishu;
import com.xwb.service.TbUserService;

@Service
public class TbUserServiceImpl implements TbUserService {
	
	@Autowired
	private TbUserMapper tbUserMapper;
	
	@Autowired
	private TbForecastMapper tbForecastMapper;
	
	@Autowired
	private TbWeatherMapper tbWeatherMapper;
	
	@Autowired
	private TbZhishuMapper tbZhishuMapper;
	

	public void addTbUser(TbUser tbUser) {
		tbUser.setTime(new Date());
		tbUserMapper.insert(tbUser);
	}
	
	/**
	 * 检查重复
	 */
	public String checkRepeat(TbUser tbUser) {
		if(tbUser == null ) {
			return SysMnagerController.RESULT_STATUS_ERROR;
		}
		int byPhone = tbUserMapper.selectCountByPhone(tbUser);
		if(byPhone != 0) {
			return SysMnagerController.RESULT_STATUS_RPEATE;
		}
		int byPhoneLoginName = tbUserMapper.selectCountByPhoneLoginName(tbUser);
		if(byPhoneLoginName != 0) {
			return SysMnagerController.RESULT_STATUS_RPEATE;
		}
		
		return SysMnagerController.RESULT_STATUS_OK;
	}

	public TbUser findUser(String loginName, Integer password) {
		return tbUserMapper.selectUser(loginName, password);
	}

	public void updateTbUser(TbUser tbUser) {
		tbUserMapper.updateByPrimaryKeySelective(tbUser);
	}

	public void saveRegion2User(TbUser tbUser) throws Exception {
		//更新地区编码
		tbUserMapper.updateByPrimaryKeySelective(tbUser);
		saveWeather(tbUser);
	}
	
	
	public void saveWeather(TbUser tbUser) throws Exception {
		if(tbUser == null) {
			throw new Exception();
		}
		//获取更新城市的天气数据
		TbWeather tbWeather = null;
		List<TbForecast> forecastList = new ArrayList<TbForecast>();
		List<TbZhishu> zhishuList = new ArrayList<TbZhishu>();
		String weatherStrs = SmsUtil.getWeatherByHttpXML(tbUser.getProvinceCode()+""+tbUser.getCityCode()+""+tbUser.getCountyCoude());
		if(weatherStrs != null) {
			JSONObject weathers = (JSONObject) JSONObject.parse(weatherStrs);
			Object errormsg = weathers.get("error");
			if(errormsg == null ) {
				String city = weathers.get("city").toString();
				String updatetime = weathers.get("updatetime").toString();
				String wendu = weathers.get("wendu").toString();
				String fengli = weathers.get("fengli").toString();
				String shidu = weathers.get("shidu").toString();
				String fengxiang = weathers.get("fengxiang").toString();
				String sunrise_1 = weathers.get("sunrise_1").toString();
				String sunset_1 = weathers.get("sunset_1").toString();
				tbWeather = new TbWeather();
				tbWeather.setCityCode(tbUser.getProvinceCode()+""+tbUser.getCityCode()+""+tbUser.getCountyCoude());
				tbWeather.setCityName(city);
				tbWeather.setUpdateTime(updatetime);
				tbWeather.setWenDu(wendu);
				tbWeather.setFengLi(fengli);
				tbWeather.setShiDu(shidu);
				tbWeather.setFengXiang(fengxiang);
				tbWeather.setSunRise(sunrise_1);
				tbWeather.setSunSet(sunset_1);
				
				JSONArray forecastArr = (JSONArray) weathers.get("forecast");
				JSONArray zhishusArr = (JSONArray) weathers.get("zhishus");
				if(forecastArr != null) {
					for(int i=0;i<forecastArr.size();i++) {
						JSONObject object1 = (JSONObject) forecastArr.get(i);
						String date = object1.get("date").toString();
						String high = object1.get("high").toString();
						String low = object1.get("low").toString();
						JSONObject day = (JSONObject) object1.get("day");
						String type = day.get("type").toString();
						String fengxiang1 = day.get("fengxiang").toString();
						String fengli1 = day.get("fengli").toString();
						JSONObject night = (JSONObject) object1.get("night");
						String type2 = night.get("type").toString();
						String fengxiang2 = night.get("fengxiang").toString();
						String fengli2 = night.get("fengli").toString();
						TbForecast tbForecast = new TbForecast();
						tbForecast.setForeCastDate(date);
						tbForecast.setHighTemp(high);
						tbForecast.setLowTemp(low);
						tbForecast.setDayType(type);
						tbForecast.setDatFengLi(fengli1);
						tbForecast.setDayFengXiang(fengxiang1);
						tbForecast.setNightType(type2);
						tbForecast.setNightFengLi(fengli2);
						tbForecast.setNightFengXiang(fengxiang2);
						forecastList.add(tbForecast);
					}
				}
				
				if(zhishusArr != null) {
					for(int i=0;i<zhishusArr.size();i++) {
						JSONObject object1 = (JSONObject) zhishusArr.get(i);
						String name = object1.get("name").toString();
						String value = object1.get("value").toString();
						String detail = object1.get("detail").toString();
						TbZhishu tbZhishu = new TbZhishu();
						tbZhishu.setName(name);
						tbZhishu.setInfo(value);
						tbZhishu.setInfoDetail(detail);
						zhishuList.add(tbZhishu);
					}
				}
			}
		}
		
		
		if(tbWeather != null) {
			tbWeather.setAddTime(new Date());
			int tbWeatherid = tbWeatherMapper.insert(tbWeather);
			if(forecastList.size()>0) {
				for(TbForecast tbForecast:forecastList) {
					tbForecast.setWeatherId(tbWeather.getId());
					tbForecastMapper.insert(tbForecast);
				}
			}
			
			if(zhishuList.size()>0) {
				for(TbZhishu tbZhishu:zhishuList) {
					tbZhishu.setWeatherId(tbWeather.getId());
					tbZhishuMapper.insert(tbZhishu);
				}
			}
		}
	}

	public void saveWeatherByAllUser() throws Exception {
		List<TbUser> allUser = tbUserMapper.selectAllUser();
		if(allUser !=null && allUser.size()>0) {
			for(TbUser tbUser:allUser) {
				saveWeather(tbUser);
			}
		}
	}

}
