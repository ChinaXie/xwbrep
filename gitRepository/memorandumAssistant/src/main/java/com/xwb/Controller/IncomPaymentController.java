package com.xwb.Controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.druid.util.StringUtils;
import com.mysql.cj.xdevapi.JsonArray;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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

	public static final String YEAR_TYPE = "1";
	public static final String MONTH_TYPE = "2";
	
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
	 * 跳转至图表分析
	 * @return
	 */
	@RequestMapping("/toanalysis")
	public String toanalysis() {
		TbUser tbUser = getUserFromSession();
		if(tbUser == null) {
			return null;
		}
		request.setAttribute("user_key", tbUser.getId());
		String stype = request.getParameter("stype");
		String ybeginDate = request.getParameter("ybeginDate");
		String yendDate = request.getParameter("yendDate");
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		Map<String, BigDecimal> incomedatamap = new HashMap<String,BigDecimal>();
		Map<String, BigDecimal> outcomedatamap = new HashMap<String,BigDecimal>();
		JSONArray xseris = new JSONArray();
		SimpleDateFormat sdfyear = new SimpleDateFormat("yyyy");
		SimpleDateFormat sdfmonth = new SimpleDateFormat("yyyy-MM");
		if(IncomPaymentController.YEAR_TYPE.equals(stype) ) {
			if(StringUtils.isEmpty(ybeginDate) || StringUtils.isEmpty(yendDate)){
				return null;
			}
			List<TbIncomePayment> tbIncomePayments = incomePaymentService.selectDatas(tbUser.getId(), IncomPaymentController.ACCOUNT_STATUS_INCOME, ybeginDate, beginDate);
			for (TbIncomePayment tbIncomePayment :tbIncomePayments){
				String yearstr = sdfyear.format(tbIncomePayment.getAddTime());
				BigDecimal b1 = new BigDecimal("0.00");
				if(incomedatamap.get(yearstr) != null){
					b1 = incomedatamap.get(yearstr);
				}
				Double costNum = tbIncomePayment.getCostNum();
				BigDecimal b2 = new BigDecimal(costNum.toString());
				BigDecimal sumCostNum = b1.add(b2);
				incomedatamap.put(yearstr,sumCostNum);
			}
			List<TbIncomePayment> tbPayments = incomePaymentService.selectDatas(tbUser.getId(), IncomPaymentController.ACCOUNT_STATUS_PAYMENT, ybeginDate, beginDate);
			for(TbIncomePayment model :tbPayments){
				String ystr = sdfyear.format(model.getAddTime());
				BigDecimal bd1 = new BigDecimal("0.00");
				if(outcomedatamap.get(ystr) != null){
					bd1 = outcomedatamap.get(ystr);
				}
				Double costNum = model.getCostNum();
				BigDecimal bd2 = new BigDecimal(costNum.toString());
				BigDecimal sumCostNum = bd1.add(bd2);
				outcomedatamap.put(ystr,sumCostNum);
			}
			int syear = Integer.parseInt(ybeginDate);
			int eyear = Integer.parseInt(yendDate);
			JSONArray inseris = new JSONArray();
			JSONArray outseris = new JSONArray();
			for(int i= syear;i<=eyear;i++){
				xseris.add(String.valueOf(i));
				BigDecimal indata = incomedatamap.get(String.valueOf(i));
				if(indata!=null){
					inseris.add(indata.doubleValue());
				}else{
					inseris.add(Double.parseDouble("0.00"));
				}
				BigDecimal outdata = outcomedatamap.get(String.valueOf(i));
				if(outdata!=null){
					outseris.add(outdata.doubleValue());
				}else{
					outseris.add(Double.parseDouble("0.00"));
				}
			}
			Map<String, Object> tempMap = new HashMap<String, Object>();
			tempMap.put("name","收入");
			tempMap.put("data",inseris);
			JSONObject injsonObject = JSONObject.fromObject(tempMap);
			Map<String, Object> tempMap2 = new HashMap<String, Object>();
			tempMap2.put("name","支出");
			tempMap2.put("data",outseris);
			JSONObject outjsonObject = JSONObject.fromObject(tempMap2);
			JSONArray series = new JSONArray();
			series.add(injsonObject);
			series.add(outjsonObject);
			request.setAttribute("series",series.toString());
			request.setAttribute("xseris",xseris.toString());
		}

		if(IncomPaymentController.MONTH_TYPE.equals(stype) ) {
			if(StringUtils.isEmpty(beginDate) || StringUtils.isEmpty(endDate)){
				return null;
			}
			List<TbIncomePayment> tbIncomePayments = incomePaymentService.selectDatas(tbUser.getId(), IncomPaymentController.ACCOUNT_STATUS_INCOME, beginDate, endDate);
			for (TbIncomePayment tbIncomePayment :tbIncomePayments){
				String monthstr = sdfmonth.format(tbIncomePayment.getAddTime());
				BigDecimal b1 = new BigDecimal("0.00");
				if(incomedatamap.get(monthstr) != null){
					b1 = incomedatamap.get(monthstr);
				}
				Double costNum = tbIncomePayment.getCostNum();
				BigDecimal b2 = new BigDecimal(costNum.toString());
				BigDecimal sumCostNum = b1.add(b2);
				incomedatamap.put(monthstr,sumCostNum);
			}
			List<TbIncomePayment> tbPayments = incomePaymentService.selectDatas(tbUser.getId(), IncomPaymentController.ACCOUNT_STATUS_PAYMENT, beginDate, endDate);
			for(TbIncomePayment model :tbPayments){
				String mstr = sdfmonth.format(model.getAddTime());
				BigDecimal bd1 = new BigDecimal("0.00");
				if(outcomedatamap.get(mstr) != null){
					bd1 = outcomedatamap.get(mstr);
				}
				Double costNum = model.getCostNum();
				BigDecimal bd2 = new BigDecimal(costNum.toString());
				BigDecimal sumCostNum = bd1.add(bd2);
				outcomedatamap.put(mstr,sumCostNum);
			}


			String[] beginDateStr = beginDate.split("-");
			String[] endDateStr = endDate.split("-");
			int staryear = Integer.parseInt(beginDateStr[0]);
			int endyear = Integer.parseInt(endDateStr[0]);
			JSONArray inseris = new JSONArray();
			JSONArray outseris = new JSONArray();
			int tempmonth=Integer.parseInt(beginDateStr[1]);
			int endtempmonth = Integer.parseInt(endDateStr[1]);

			for(int i=staryear;i<=endyear;i++) {
				boolean monthflag = true;
				while (monthflag) {
					xseris.add(i + "年" + tempmonth + "月");
					String tempmonthstr = "";
					if(tempmonth<10){
						tempmonthstr = "0"+tempmonth;
					}
					BigDecimal indata = incomedatamap.get(i+"-"+tempmonthstr);
					if(indata!=null){
						inseris.add(indata.doubleValue());
					}else{
						inseris.add(Double.parseDouble("0.00"));
					}
					BigDecimal outdata = outcomedatamap.get(i+"-"+tempmonthstr);
					if(outdata!=null){
						outseris.add(outdata.doubleValue());
					}else{
						outseris.add(Double.parseDouble("0.00"));
					}

					if (i == endyear && tempmonth == endtempmonth) {
						monthflag = false;
					} else if (tempmonth == 12) {
						monthflag = false;
						tempmonth = 0;
					}
					tempmonth++;
				}
			}

			Map<String, Object> tempMap = new HashMap<String, Object>();
			tempMap.put("name","收入");
			tempMap.put("data",inseris);
			JSONObject injsonObject = JSONObject.fromObject(tempMap);
			Map<String, Object> tempMap2 = new HashMap<String, Object>();
			tempMap2.put("name","支出");
			tempMap2.put("data",outseris);
			JSONObject outjsonObject = JSONObject.fromObject(tempMap2);
			JSONArray series = new JSONArray();
			series.add(injsonObject);
			series.add(outjsonObject);
			request.setAttribute("series",series.toString());
			request.setAttribute("xseris",xseris.toString());
		}

		request.setAttribute("stype",stype);
		request.setAttribute("ybeginDate",ybeginDate);
		request.setAttribute("yendDate",yendDate);
		request.setAttribute("beginDate",beginDate);
		request.setAttribute("endDate",endDate);

		return "/accountchart";
	}
	
	
	/**
	 * 保存
	 * @param tbIncomePayment
	 * @return
	 */
	@RequestMapping("/saveIncomeAndPayment")
	@ResponseBody
	public String saveIncomeAndPayment(TbIncomePayment tbIncomePayment) {
		//获取当前登陆用户
		TbUser tbUser = getUserFromSession();
		String result = IncomPaymentController.RESULT_STATUS_OK;
		tbIncomePayment.setAddTime(new Date());
		if(tbUser != null) {
			tbIncomePayment.setUserId(tbUser.getId());
		}
		try {
			//保存TbIncomePayment对象数据
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
