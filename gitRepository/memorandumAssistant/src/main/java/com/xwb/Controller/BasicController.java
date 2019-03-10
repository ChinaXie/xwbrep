package com.xwb.Controller;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xwb.model.TbUser;
import com.xwb.service.TbUserService;

@RequestMapping("/basic")
@Controller
public class BasicController {
	
	@Autowired
	protected HttpServletRequest request;
	 
	@Autowired
	protected HttpServletResponse response;
	
	@Autowired
	public TbUserService tbUserService;
	
	/**
	 * 获取登陆用户
	 * @return
	 */
	public TbUser getUserFromSession() {
		TbUser tbUser = (TbUser) request.getSession().getAttribute("tbluser");
		return tbUser;
	}
	
}
