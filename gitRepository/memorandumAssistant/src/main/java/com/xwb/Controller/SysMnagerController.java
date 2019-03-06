package com.xwb.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xwb.model.TbUser;
import com.xwb.service.TbUserService;

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
	
	
	/**
	 * 注册用户
	 * @param tbUser
	 * @return
	 */
	@RequestMapping(value = "/resitUser",method=RequestMethod.POST)
	@ResponseBody
	public String registUser(TbUser tbUser) {
		//检查重复
		String checkRepeat = tbUserService.checkRepeat(tbUser);
		if(checkRepeat.equals(SysMnagerController.RESULT_STATUS_ERROR)) {
			return SysMnagerController.RESULT_STATUS_ERROR;
		}else if(checkRepeat.equals(SysMnagerController.RESULT_STATUS_RPEATE)) {
			return SysMnagerController.RESULT_STATUS_RPEATE;
		}
		
		try {
			tbUserService.addTbUser(tbUser);
		} catch (Exception e) {
			e.printStackTrace();
			return SysMnagerController.RESULT_STATUS_ERROR;
		}
		
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
			request.getSession().setAttribute("tbluser", findUser);
			String AUTH_CODE = (String) request.getSession().getAttribute("AUTH_CODE");
			if(!AUTH_CODE.equals(authcode)){
				return SysMnagerController.RESULT_USER_AUTHCODE_ERROR;
			}else {
				return SysMnagerController.RESULT_USER_LOGIN_SUCCESS;
			}
		}
	}
	
	

}
