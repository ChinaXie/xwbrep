package com.xwb.service.impl;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xwb.common.PageListDTO;
import com.xwb.mappers.TbIncomePaymentMapper;
import com.xwb.model.TbIncomePayment;
import com.xwb.model.TbIncomePaymentDto;
import com.xwb.service.IncomePaymentService;
/**
 * 收入支出
 * @author xwb
 *
 */
@Service
public class IncomePaymentServiceImpl implements IncomePaymentService {
	
	@Autowired
	private TbIncomePaymentMapper tbIncomePaymentMapper;

	public List<TbIncomePayment> findList(Integer userId,Integer status) {
		return tbIncomePaymentMapper.selectByUserId(userId,status);
	}

	public void saveTbIncomePayment(TbIncomePayment tbIncomePayment) {
		tbIncomePaymentMapper.insert(tbIncomePayment);
	}

	public PageListDTO<TbIncomePayment> findTbIncomePaymentPageList(TbIncomePaymentDto dto) {
		 PageListDTO<TbIncomePayment> pageListDTO = new PageListDTO<TbIncomePayment>();
	        if(dto != null){
	        	List<TbIncomePayment> list = tbIncomePaymentMapper.findVisitorList(dto);
	        	int count = tbIncomePaymentMapper.findVisitorCount(dto);
	            pageListDTO.setResultList(list);
	            pageListDTO.setListCount(count);
	            pageListDTO.setPageDTO(dto.getPageDTO());
	        }    
	        return pageListDTO;
	}

	public Double selectIncome(TbIncomePaymentDto dto) {
		return tbIncomePaymentMapper.selectIncome(dto);
	}

	public Double selectPayment(TbIncomePaymentDto dto) {
		return tbIncomePaymentMapper.selectPayment(dto);
	}

	public void deleteTbIncomePayment(Integer id) {
		tbIncomePaymentMapper.deleteByPrimaryKey(id);
	}

	public List<TbIncomePayment> selectDatas(int userId, int status, String beginDate, String endDate) {
		return tbIncomePaymentMapper.selectTbIncomePayment(userId,status,beginDate,endDate);
	}


}
