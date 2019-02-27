package com.xwb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xwb.mappers.TbUserMapper;
import com.xwb.model.TbUser;
import com.xwb.service.TbUserService;

@Service
public class TbUserServiceImpl implements TbUserService {
	
	@Autowired
	private TbUserMapper tbUserMapper;

	public void addTbUser(TbUser tbUser) { 
		tbUserMapper.insert(tbUser);
	}

}
