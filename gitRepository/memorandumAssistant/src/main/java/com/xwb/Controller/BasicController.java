package com.xwb.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.xwb.service.TbUserService;

@RequestMapping("/basic")
@Controller
public class BasicController {
	
	@Autowired
	public TbUserService tbUserService; 
	
	@RequestMapping("/saveTbUser")
	public String toSavePerson(String id) {
		
		
		
		
		return "/firstview";				
	}
}
