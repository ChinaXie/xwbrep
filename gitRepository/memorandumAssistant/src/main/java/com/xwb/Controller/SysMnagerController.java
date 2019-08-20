package com.xwb.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xwb.model.TbUser;

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
		if(loginName != null && !"".equals(loginName) && password != null && !"".equals(password)) {
			findUser = tbUserService.findUser(loginName, Integer.parseInt(password));
		}
		
		if(findUser == null ) {
			return SysMnagerController.RESULT_USER_NOT_FIND;			
		}else {
			String AUTH_CODE = (String) request.getSession().getAttribute("AUTH_CODE");
			if(!AUTH_CODE.equals(authcode)){
				return SysMnagerController.RESULT_USER_AUTHCODE_ERROR;
			}else {
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
