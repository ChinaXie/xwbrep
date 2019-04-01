package com.xwb.Controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xwb.common.PageListDTO;
import com.xwb.model.TbCity;
import com.xwb.model.TbCounty;
import com.xwb.model.TbForecast;
import com.xwb.model.TbIncomePayment;
import com.xwb.model.TbIncomePaymentDto;
import com.xwb.model.TbProvince;
import com.xwb.model.TbUser;
import com.xwb.model.TbWeather;
import com.xwb.model.TbZhishu;
import com.xwb.service.IncomePaymentService;
import com.xwb.service.RegionService;
import com.xwb.service.WeatherService;
/**
 * 收入支出控制器
 * @author xwb
 *
 */
@RequestMapping("/accountinfo")
@Controller
public class IncomPaymentController extends BasicController{
	
	@Autowired
	private RegionService regionService;
	@Autowired
	private WeatherService weatherService;
	@Autowired
	private IncomePaymentService incomePaymentService;
	
	//收入标记
	public static final int ACCOUNT_STATUS_INCOME = 1;
	//支出标记
	public static final int ACCOUNT_STATUS_PAYMENT = 2;
	public static final String RESULT_STATUS_OK = "1";
	public static final String RESULT_STATUS_ERROR = "0";
	
	private TbIncomePaymentDto dto = new TbIncomePaymentDto();
	
	/**
	 * 备忘录信息修改
	 * @return
	 */
	@SuppressWarnings("unlikely-arg-type")
	@RequestMapping("/list")
	public String listData() {
		TbUser tbUser = getUserFromSession();
		request.setAttribute("tbUser", tbUser);
		if(tbUser != null) {
			request.setAttribute("user_key", tbUser.getId());
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
			dto.getTbIncomePayment().setUserId(tbUser.getId());
		}
		
		String pageNum = request.getParameter("pager.offset");
		String status = request.getParameter("status");
		String type = request.getParameter("type");
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		dto.setBeginDate(beginDate);
		dto.setEndDate(endDate);
		if (pageNum != null) {
			dto.getPageDTO().setBeginCount(Integer.parseInt(pageNum));
		}
		if(status != null &&!"".equals(status) ) {
			dto.getTbIncomePayment().setStatus(Integer.parseInt(status));
		}
		if(type != null &&!"".equals(type) ) {
			dto.getTbIncomePayment().setType(Integer.parseInt(type));
		}
		
		// 默认查询全部
		PageListDTO<TbIncomePayment> pageList = incomePaymentService.findTbIncomePaymentPageList(dto);
		Double incomeValue = incomePaymentService.selectIncome(dto);
		Double paymentValue = incomePaymentService.selectPayment(dto);
		request.setAttribute("pageList", pageList);
		request.setAttribute("dto",dto);
		if(incomeValue != null) {
			request.setAttribute("incomeValue", String.valueOf(incomeValue.doubleValue()));
		}
		if(paymentValue != null) {
			request.setAttribute("paymentValue", String.valueOf(paymentValue.doubleValue()));
		}
		
		return "/secondview";				
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
	 * 跳转至记账页面
	 * @return
	 */
	@RequestMapping("/tocharge")
	public String tocharge() {
		
		TbUser tbUser = getUserFromSession();
		if(tbUser != null) {
			request.setAttribute("user_key", tbUser.getId());
		}
		return "/chargetoaccount";				
	}
	
	
	/**
	 * 保存
	 * @param tbMemorandum
	 * @return
	 */
	@RequestMapping("/saveIncomeAndPayment")
	@ResponseBody
	public String saveIncomeAndPayment(TbIncomePayment tbIncomePayment) {
		TbUser tbUser = getUserFromSession();
		String result = IncomPaymentController.RESULT_STATUS_OK;
		tbIncomePayment.setAddTime(new Date());
		if(tbUser != null) {
			tbIncomePayment.setUserId(tbUser.getId());
		}
		try {
			incomePaymentService.saveTbIncomePayment(tbIncomePayment);
		} catch (Exception e) {
			e.printStackTrace();
			result = IncomPaymentController.RESULT_STATUS_ERROR;
		}
		
		return result;
	}
	
	/**
	 * 删除操作
	 * @param id
	 * @return
	 */
	@RequestMapping("/delIncomeAndPayment")
	@ResponseBody
	public String delIncomeAndPayment(Integer id) {
		String result = IncomPaymentController.RESULT_STATUS_OK;
		if(id == null || "".equals(id)) {
			return IncomPaymentController.RESULT_STATUS_ERROR;
		}
		try {
			incomePaymentService.deleteTbIncomePayment(id);
		} catch (Exception e) {
			e.printStackTrace();
			result = IncomPaymentController.RESULT_STATUS_ERROR;
		}
		return result;
	}
	
	
	
}
