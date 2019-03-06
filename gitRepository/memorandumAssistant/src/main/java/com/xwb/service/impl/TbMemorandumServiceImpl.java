package com.xwb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.xwb.mappers.TbMemorandumMapper;
import com.xwb.model.TbMemorandum;
import com.xwb.model.TbUser;
import com.xwb.service.TbMemorandumService;

@Service
public class TbMemorandumServiceImpl implements TbMemorandumService {
	
	@Autowired
	private TbMemorandumMapper tbMemorandumMapper;

	public void saveTbMemorandum(TbMemorandum tbMemorandum) {
		tbMemorandumMapper.insert(tbMemorandum);
	}

	public List<TbMemorandum> findListByUserId(TbUser tbUser) {
		return tbMemorandumMapper.findListByUserId(tbUser.getId().intValue());
	}

	public TbMemorandum findOneById(Integer id) {
		return tbMemorandumMapper.selectByPrimaryKey(id);
	}

	public void updateTbMemorandum(TbMemorandum tbMemorandum) {
		tbMemorandumMapper.updateByPrimaryKey(tbMemorandum);
	}


}
