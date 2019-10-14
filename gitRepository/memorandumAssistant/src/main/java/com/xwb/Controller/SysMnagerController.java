package com.xwb.Controller;

import com.xwb.common.PageListDTO;
import com.xwb.model.*;
import com.xwb.service.RegionService;
import com.xwb.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 项目或者用户相关信息
 * @author xwb
 *
 */
@RequestMapping("/sysManager")
@Controller
public class SysMnagerController extends BasicController{
	public static final String RESULT_STATUS_OK = "1";
	public static final String RESULT_STATUS_ERROR = "0";
	public static final String RESULT_STATUS_RPEATE = "2";
	public static final String RESULT_USER_NOT_FIND = "1";
	public static final String RESULT_USER_LOGIN_SUCCESS = "2";
	public static final String RESULT_USER_AUTHCODE_ERROR = "3";


	private TbUserDto dto = new TbUserDto();
	@Autowired
	private RegionService regionService;
	@Autowired
	private WeatherService weatherService;
	/**
	 * 跳转注册
	 * @return
	 */
	@RequestMapping("/toRegist")
	public String toRegist() {
		
		
		return "/regist";
	}
	
	@RequestMapping("/getCodeSave")
	@ResponseBody
	public String getCodeSave() {
		tbUserService.getCodeSave();
		return "OK";
	}


	/**
	 * 用户列表
	 * @return
	 */
	@RequestMapping("/userlist")
	public String listUserDatas() {
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
		}

		String pageNum = request.getParameter("pager.offset");
		String loginName = request.getParameter("loginName");
		if (pageNum != null) {
			dto.getPageDTO().setBeginCount(Integer.parseInt(pageNum));
		}
		dto.getTbUser().setLoginName(loginName);
		PageListDTO<TbUser> pageList = tbUserService.findTbUserPageList(dto);
		request.setAttribute("loginName",loginName);
		request.setAttribute("pageList", pageList);
		return "/userlist";
	}
	
	
	/**
	 * 注册用户
	 * @param tbUser
	 * @return
	 */
	@RequestMapping(value = "/resitUser",method=RequestMethod.POST)
	@ResponseBody
	public String registUser(TbUser tbUser) {
		//检查用户名和手机号重复
		String checkRepeat = tbUserService.checkRepeat(tbUser);
		if(checkRepeat.equals(SysMnagerController.RESULT_STATUS_ERROR)) {
			return SysMnagerController.RESULT_STATUS_ERROR;
		}else if(checkRepeat.equals(SysMnagerController.RESULT_STATUS_RPEATE)) {
			return SysMnagerController.RESULT_STATUS_RPEATE;
		}
		
		try {
			//持久化注册用户对象
			tbUserService.addTbUser(tbUser);
		} catch (Exception e) {
			e.printStackTrace();
			return SysMnagerController.RESULT_STATUS_ERROR;
		}
		//返回结果
		return SysMnagerController.RESULT_STATUS_OK;
	}
	
	/**
	 * 登陆
	 * @param loginName
	 * @param password
	 * @param authcode
	 * @return
	 */
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	@ResponseBody
	public String login(String loginName,String password,String authcode) {
		TbUser findUser = null;
		//根据用户名和密码查询tb_user表
		if(loginName != null && !"".equals(loginName) && password != null && !"".equals(password)) {
			findUser = tbUserService.findUser(loginName, Integer.parseInt(password));
		}
		//判断用户名或者和手机号是否重复
		if(findUser == null ) {
			return SysMnagerController.RESULT_USER_NOT_FIND;			
		}else {
			String AUTH_CODE = (String) request.getSession().getAttribute("AUTH_CODE");
			//判断验证码参数是否正确
			if(!AUTH_CODE.equals(authcode)){
				return SysMnagerController.RESULT_USER_AUTHCODE_ERROR;
			}else {
				//记录当前登陆用户，保存在会话Session
				request.getSession().setAttribute("tbluser", findUser);
				return SysMnagerController.RESULT_USER_LOGIN_SUCCESS;
			}
		}
	}
	
	
	/**
	 * 保存地区获取天气信息
	 * @param countyCode
	 * @return
	 */
	@RequestMapping(value = "/saveRegion",method = RequestMethod.POST)
	@ResponseBody
	public String saveRegion2User(String countyCode) {
		TbUser tbUser = getUserFromSession();
		if(countyCode != null && !"".equals(countyCode)) {
			String[] countyCodes = countyCode.split("_");
			if(tbUser != null) {
				tbUser.setProvinceCode(countyCodes[0]);
				tbUser.setCityCode(countyCodes[1]);
				tbUser.setCountyCoude(countyCodes[2]);
				try {
					tbUserService.saveRegion2User(tbUser);
				} catch (Exception e) {
					e.printStackTrace();
					return SysMnagerController.RESULT_STATUS_ERROR;
				}
			}
		}
		return SysMnagerController.RESULT_STATUS_OK;
	}
	
	/**
	 * 登陆退出
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/logout",method = RequestMethod.POST)
	@ResponseBody
	public String logout(String userId) {
		TbUser tbUser = getUserFromSession();
		if(tbUser == null) {
			return SysMnagerController.RESULT_STATUS_ERROR;
		}
		if(!userId.equals(String.valueOf(tbUser.getId()))) {
			return SysMnagerController.RESULT_STATUS_ERROR;
		}
		request.getSession().removeAttribute("tbluser");
		
		return SysMnagerController.RESULT_STATUS_OK;
	}
	

}
