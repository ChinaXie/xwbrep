package com.xwb.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xwb.Controller.SysMnagerController;
import com.xwb.mappers.TbUserMapper;
import com.xwb.model.TbUser;
import com.xwb.service.TbUserService;

@Service
public class TbUserServiceImpl implements TbUserService {
	
	@Autowired
	private TbUserMapper tbUserMapper;

	public void addTbUser(TbUser tbUser) {
		tbUser.setTime(new Date());
		tbUserMapper.insert(tbUser);
	}
	
	/**
	 * 检查重复
	 */
	public String checkRepeat(TbUser tbUser) {
		if(tbUser == null ) {
			return SysMnagerController.RESULT_STATUS_ERROR;
		}
		int byPhone = tbUserMapper.selectCountByPhone(tbUser);
		if(byPhone != 0) {
			return SysMnagerController.RESULT_STATUS_RPEATE;
		}
		int byPhoneLoginName = tbUserMapper.selectCountByPhoneLoginName(tbUser);
		if(byPhoneLoginName != 0) {
			return SysMnagerController.RESULT_STATUS_RPEATE;
		}
		
		return SysMnagerController.RESULT_STATUS_OK;
	}

	public TbUser findUser(String loginName, Integer password) {
		return tbUserMapper.selectUser(loginName, password);
	}

}
