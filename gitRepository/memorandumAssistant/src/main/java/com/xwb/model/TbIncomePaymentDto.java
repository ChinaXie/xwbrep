package com.xwb.model;

import java.io.Serializable;

import com.xwb.common.PageDTO;


public class TbIncomePaymentDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6755335106102257189L;
	
	 // 分页属性值
    private PageDTO pageDTO = new PageDTO();
    
    private TbIncomePayment tbIncomePayment = new TbIncomePayment();
    
    private String beginDate;//开始时间
    private String endDate;//结束时间

	public PageDTO getPageDTO() {
		return pageDTO;
	}

	public void setPageDTO(PageDTO pageDTO) {
		this.pageDTO = pageDTO;
	}

	public TbIncomePayment getTbIncomePayment() {
		return tbIncomePayment;
	}

	public void setTbIncomePayment(TbIncomePayment tbIncomePayment) {
		this.tbIncomePayment = tbIncomePayment;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	

}
