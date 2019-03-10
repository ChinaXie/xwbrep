package com.xwb.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EventObject;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.xwb.model.EventObjectMode;
import com.xwb.model.TbCity;
import com.xwb.model.TbCounty;
import com.xwb.model.TbForecast;
import com.xwb.model.TbMemorandum;
import com.xwb.model.TbProvince;
import com.xwb.model.TbUser;
import com.xwb.model.TbWeather;
import com.xwb.model.TbZhishu;
import com.xwb.service.RegionService;
import com.xwb.service.TbMemorandumService;
import com.xwb.service.WeatherService;
/**
 * 备忘录信息
 * @author xwb
 *
 */
@RequestMapping("/headpage")
@Controller
public class MemorandumController extends BasicController{
	
	@Autowired
	private TbMemorandumService tbMemorandumService;
	@Autowired
	private RegionService regionService;
	@Autowired
	private WeatherService weatherService;
	
	
	/**
	 * 跳转添加页面
	 * @return
	 */
	@RequestMapping("/toAdd")
	public String toAdd() {
		
		TbUser tbUser = getUserFromSession();
		if(tbUser != null) {
			request.setAttribute("user_key", tbUser.getId());
		}
		return "/toadd";				
	}
	
	/**
	 * 跳转修改页面
	 * @return
	 */
	@RequestMapping("/toEdit")
	public String toEdit() {
		String memorandumId = request.getParameter("memorandumId");
		TbMemorandum tbMemorandum = null;
		if(memorandumId != null && !"".equals(memorandumId)) {
			tbMemorandum = tbMemorandumService.findOneById(Integer.parseInt(memorandumId));
		}
		request.setAttribute("tbMemorandum", tbMemorandum);
		TbUser tbUser = getUserFromSession();
		if(tbUser != null) {
			request.setAttribute("user_key", tbUser.getId());
		}
		return "/toedit";
	}
	
	/**
	 * 备忘录信息修改
	 * @return
	 */
	@SuppressWarnings("unlikely-arg-type")
	@RequestMapping("/list")
	public String listData() {
		TbUser tbUser = getUserFromSession();
		request.setAttribute("tbUser", tbUser);
		List<TbMemorandum> lsit = null;
		if(tbUser != null) {
			request.setAttribute("user_key", tbUser.getId());
			lsit = tbMemorandumService.findListByUserId(tbUser);
			//查询城市地区
			if(tbUser.getCountyCoude() != null) {
				TbCounty county = regionService.findCountyByCode(tbUser.getProvinceCode(), tbUser.getCityCode(), tbUser.getCountyCoude());
				request.setAttribute("county", county);
				
				List<TbWeather> tbWeatherList = weatherService.getTbWeather(tbUser.getProvinceCode()+""+tbUser.getCityCode()+""+tbUser.getCountyCoude());
				if(tbWeatherList != null && tbWeatherList.size()>0) {
					TbWeather tbWeather = tbWeatherList.get(0);
					request.setAttribute("tbWeather", tbWeather);
					List<TbForecast> forecastList = weatherService.getTbForecasts(tbWeather.getId());
					request.setAttribute("forecastList", forecastList);
					List<TbZhishu> tbZhishuList = weatherService.getTbZhishu(tbWeather.getId());
					request.setAttribute("tbZhishuList", tbZhishuList);
				}
			}
		}
		
		//查询备忘录事件
		ArrayList<EventObjectMode> eventObjectModeList = new ArrayList<EventObjectMode>();
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(lsit != null) {
			for(TbMemorandum tbMemorandum:lsit) {
				EventObjectMode model = new EventObjectMode();
				model.setTitle(tbMemorandum.getTitleName());
				if(tbMemorandum.getStartTime() != null && !"".equals(tbMemorandum.getStartTime())) {
					model.setStart(sdf1.format(tbMemorandum.getStartTime()));
				}
				if(tbMemorandum.getEndTime() != null && !"".equals(tbMemorandum.getEndTime())) {
					model.setEnd(sdf1.format(tbMemorandum.getEndTime()));
				}
				model.setUrl("javaScript:layer_show('500','500','查看或修改备忘事件','"+basePath+"/headpage/toEdit.do?memorandumId="+tbMemorandum.getId()+"');");
				eventObjectModeList.add(model);
			}
		}
		request.setAttribute("eventObjectModeList",JSONArray.toJSONString(eventObjectModeList));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		request.setAttribute("defaultDate", sdf.format(new Date()));
		return "/firstview";				
	}
	
	
	@RequestMapping("/regionInfo")
	public String getRegionInfo(String provinceCode,String cityCode) {
		//查询城市地区
		List<TbProvince> provinceList = regionService.getAllProvince();
		List<TbCity> allCityList = new ArrayList<TbCity>();
		List<TbCounty> allCountyList = new ArrayList<TbCounty>();
		if(provinceCode ==null || "".equals(provinceCode)) {
			if(provinceList != null ) {
				provinceCode = provinceList.get(0).getProvinceCode();
			}
		}
		
		if(provinceCode != null && !"".equals(provinceCode)) {
			List<TbCity> cityList = regionService.getCityList(provinceCode);
			if(cityList != null) {
				allCityList.addAll(cityList);
			}
			List<TbCounty> countyList = null;
			if(cityCode != null && !"".equals(cityCode)) {
				countyList = regionService.getCountyList(provinceCode, cityCode);
			}
			if(countyList != null) {
				allCountyList.addAll(countyList);
			}else {
				countyList = regionService.getCountyList(provinceCode, null);
				allCountyList.addAll(countyList);
			}
		}
		request.setAttribute("provinceCode", provinceCode);
		request.setAttribute("cityCode", cityCode);
		request.setAttribute("provinceList", provinceList);
		request.setAttribute("allCityList", allCityList);
		request.setAttribute("allCountyList", allCountyList);
		return "/tochoseregion";
	}
	
	
	
	/**
	 * 保存
	 * @param tbMemorandum
	 * @return
	 */
	@RequestMapping("/saveMemorandum")
	@ResponseBody
	public String saveTbMemorandum(TbMemorandum tbMemorandum) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String result = "1";
		try {
			if(tbMemorandum != null && tbMemorandum.getStartTimeStr() !=null && !"".equals(tbMemorandum.getStartTimeStr())) {
				tbMemorandum.setStartTime(sdf.parse(tbMemorandum.getStartTimeStr()));
			}
			if(tbMemorandum != null && tbMemorandum.getEndTimeStr() !=null && !"".equals(tbMemorandum.getEndTimeStr())) {
				tbMemorandum.setEndTime(sdf.parse(tbMemorandum.getEndTimeStr()));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(tbMemorandum.getId() == null ) {
			try {
				tbMemorandumService.saveTbMemorandum(tbMemorandum);
			} catch (Exception e) {
				result = "0";
				e.printStackTrace();
			}
			return result;
		}
		try {
			tbMemorandumService.updateTbMemorandum(tbMemorandum);
		} catch (Exception e) {
			result = "0";
			e.printStackTrace();
		}
		return result;
	}
	
	
}
