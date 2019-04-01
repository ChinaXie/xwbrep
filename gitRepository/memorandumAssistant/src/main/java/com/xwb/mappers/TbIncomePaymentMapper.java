package com.xwb.mappers;

import java.util.List;


import org.apache.ibatis.annotations.Param;
import com.xwb.model.TbIncomePayment;
import com.xwb.model.TbIncomePaymentDto;

public interface TbIncomePaymentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbIncomePayment record);

    int insertSelective(TbIncomePayment record);

    TbIncomePayment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbIncomePayment record);

    int updateByPrimaryKey(TbIncomePayment record);
    
    List<TbIncomePayment> selectByUserId(@Param("userId")Integer userId,@Param("status")Integer status);
    
    public List<TbIncomePayment> findVisitorList(TbIncomePaymentDto dto);
	public int findVisitorCount(TbIncomePaymentDto dto);
	
	public Double selectIncome(TbIncomePaymentDto dto);
	public Double selectPayment(TbIncomePaymentDto dto);
}