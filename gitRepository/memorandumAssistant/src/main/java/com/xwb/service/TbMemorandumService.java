package com.xwb.service;

import java.util.List;

import com.xwb.model.TbMemorandum;
import com.xwb.model.TbUser;

public interface TbMemorandumService {
	
	void saveTbMemorandum(TbMemorandum tbMemorandum);
	void updateTbMemorandum(TbMemorandum tbMemorandum);
	List<TbMemorandum> findListByUserId(TbUser tbUser);
	TbMemorandum findOneById(Integer id);
}
