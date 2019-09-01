package com.xwb.service;

import java.util.List;

import com.xwb.common.PageListDTO;
import com.xwb.model.TbIncomePayment;
import com.xwb.model.TbIncomePaymentDto;

public interface IncomePaymentService {
	
	List<TbIncomePayment> findList(Integer userId,Integer status);
	
	void saveTbIncomePayment(TbIncomePayment tbIncomePayment);
	
	public PageListDTO<TbIncomePayment> findTbIncomePaymentPageList(TbIncomePaymentDto dto);
	
	Double selectIncome(TbIncomePaymentDto dto);
	
	Double selectPayment(TbIncomePaymentDto dto);
	
	void deleteTbIncomePayment(Integer id);

	List<TbIncomePayment> selectDatas(int userId,int status,String beginDate,String endDate);
}
