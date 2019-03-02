package com.xwb.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/headpage")
@Controller
public class MemorandumController extends BasicController{
	
	@RequestMapping("/toAdd")
	public String toAdd() {
		
		String user_key = request.getParameter("user_key");
		request.setAttribute("user_key", user_key);
		return "/toadd";				
	}
	
	@RequestMapping("/list")
	public String listData() {
		
		
		request.setAttribute("user_key", "1");
		return "/firstview";				
	}
}
