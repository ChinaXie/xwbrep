package com.xwb.service;

import com.xwb.model.TbUser;

public interface TbUserService {
	
	void addTbUser(TbUser tbUser);
	
	String checkRepeat(TbUser tbUser);
	
	TbUser findUser(String loginName,Integer password);
	

}
