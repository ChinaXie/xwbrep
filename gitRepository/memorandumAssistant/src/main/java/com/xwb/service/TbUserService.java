package com.xwb.service;


import com.xwb.model.TbUser;

public interface TbUserService {
	
	void addTbUser(TbUser tbUser);
	
	String checkRepeat(TbUser tbUser);
	
	TbUser findUser(String loginName,Integer password);
	
	void updateTbUser(TbUser tbUser);
	
	void saveRegion2User(TbUser tbUser) throws Exception;
	
	void saveWeather(TbUser tbUser) throws Exception;
	
	void saveWeatherByAllUser() throws Exception;
	
	void sendMsg2Users();
	
	void getCodeSave();
	
}
