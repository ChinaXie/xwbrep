package com.xwb.service.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.xwb.common.PageListDTO;
import com.xwb.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xwb.Controller.SysMnagerController;
import com.xwb.common.MMap;
import com.xwb.common.SmsUtil;
import com.xwb.mappers.TbCityMapper;
import com.xwb.mappers.TbCountyMapper;
import com.xwb.mappers.TbForecastMapper;
import com.xwb.mappers.TbMemorandumMapper;
import com.xwb.mappers.TbProvinceMapper;
import com.xwb.mappers.TbUserMapper;
import com.xwb.mappers.TbWeatherMapper;
import com.xwb.mappers.TbZhishuMapper;
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
	
	@Autowired
	private TbMemorandumMapper tbMemorandumMapper;
	@Autowired
	private TbProvinceMapper tbProvinceMapper;
	@Autowired
	private TbCityMapper tbCityMapper;
	@Autowired
	private TbCountyMapper tbCountyMapper;
	

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

	public List<TbUser> findUserList() {
		return tbUserMapper.selectAllUser();
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

	public void sendMsg2Users() {
		List<TbUser> allUser = tbUserMapper.selectAllUser();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdftwo = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		 String currentTime = sdf.format(new Date());
		if(allUser !=null && allUser.size()>0) {
			for(TbUser tbUser:allUser) {
				List<TbMemorandum> listByStartTime = tbMemorandumMapper.findListByStartTime(tbUser.getId().intValue(),currentTime);
				List<TbMemorandum> listByEndTime = tbMemorandumMapper.findListByEndTime(tbUser.getId().intValue(), currentTime);
				if(listByEndTime != null) {
					listByEndTime.addAll(listByStartTime);
					HashMap<Integer, MMap> allTbMemorandum = new HashMap<Integer, MMap>();
					for(TbMemorandum tbMemorandum: listByEndTime) {
						StringBuffer sbf = new StringBuffer();
						MMap map = new MMap();
						map.setObj1("252556");
						map.setObj2("admin");
						map.setObj3("wbxluna2046");
						sbf.append(tbUser.getLoginName());
						sbf.append("您好，您有");
						sbf.append(sdftwo.format(tbMemorandum.getStartTime()));
						sbf.append("记录的未办理事件：");
						sbf.append(tbMemorandum.getTitleName());
						sbf.append("需要办理。");
						map.setObj4(sbf.toString());
						map.setObj5(tbUser.getPhone());
						map.setObj6("");
						map.setObj7("");
						map.setObj8("1");
						map.setObj9("");
						map.setObj10("");
						allTbMemorandum.put(tbMemorandum.getId(), map);
					}
					
					if(allTbMemorandum.size()>0) {
						Set<Entry<Integer,MMap>> entrySet = allTbMemorandum.entrySet();
						for(Entry<Integer,MMap> entrymodel : entrySet) {
							MMap mMap = entrymodel.getValue();
							System.out.println(SmsUtil.sendSmsForHTTP(mMap));
						}
					}
				}
			}
		}
	}

	public void getCodeSave() {
		List<TbProvince> plist = SmsUtil.readTxtFileP("classpath:code.txt");
		List<TbCity> clist = SmsUtil.readTxtFileC("classpath:citycode.txt");
		List<TbCounty> list = SmsUtil.readTxtFileCount("classpath:citycode.txt");
		
		for(TbProvince pmodel: plist) {
			tbProvinceMapper.insert(pmodel);
		}
		
		for(TbCity cmodel: clist) {
			tbCityMapper.insert(cmodel);		
		}
		
		for(TbCounty countmodel: list) {
			tbCountyMapper.insert(countmodel);
		}
		
	}

	public PageListDTO<TbUser> findTbUserPageList(TbUserDto dto) {
		PageListDTO<TbUser> pageListDTO = new PageListDTO<TbUser>();
		if(dto != null){
			List<TbUser> list = tbUserMapper.findVisitorList(dto);
			int count = tbUserMapper.findVisitorCount(dto);
			pageListDTO.setResultList(list);
			pageListDTO.setListCount(count);
			pageListDTO.setPageDTO(dto.getPageDTO());
		}
		return pageListDTO;
	}

}
