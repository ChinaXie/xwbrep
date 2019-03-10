package com.xwb.service;

import java.util.List;

import com.xwb.model.TbCity;
import com.xwb.model.TbCounty;
import com.xwb.model.TbProvince;

public interface RegionService {
	
	public List<TbProvince> getAllProvince();
	
	public List<TbCity> getCityList(String provinceCode);
	
	public List<TbCity> findCityNoProvince();
	
	public List<TbCounty> getCountyList(String provinceCode,String cityCode);
	
	public TbCounty findCountyByCode(String provinceCode,String cityCode,String countyCode);
	

}
