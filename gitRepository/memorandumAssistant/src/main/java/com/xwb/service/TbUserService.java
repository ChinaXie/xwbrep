package com.xwb.service;


import com.xwb.common.PageListDTO;
import com.xwb.model.TbUser;
import com.xwb.model.TbUserDto;

import java.util.List;

public interface TbUserService {
	
	void addTbUser(TbUser tbUser);
	
	String checkRepeat(TbUser tbUser);
	
	TbUser findUser(String loginName,Integer password);

	List<TbUser> findUserList();
	
	void updateTbUser(TbUser tbUser);
	
	void saveRegion2User(TbUser tbUser) throws Exception;
	
	void saveWeather(TbUser tbUser) throws Exception;
	
	void saveWeatherByAllUser() throws Exception;
	
	void sendMsg2Users();
	
	void getCodeSave();

	PageListDTO<TbUser> findTbUserPageList(TbUserDto dto);
	
}
