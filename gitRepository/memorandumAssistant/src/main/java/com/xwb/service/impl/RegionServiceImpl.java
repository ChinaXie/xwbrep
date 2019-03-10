package com.xwb.service.impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xwb.mappers.TbCityMapper;
import com.xwb.mappers.TbCountyMapper;
import com.xwb.mappers.TbProvinceMapper;
import com.xwb.model.TbCity;
import com.xwb.model.TbCounty;
import com.xwb.model.TbProvince;
import com.xwb.service.RegionService;

/**
 * 城市信息
 * @author xwb
 *
 */
@Service
public class RegionServiceImpl implements RegionService {
	
	@Autowired
	private TbCityMapper tbCityMapper;
	
	@Autowired
	private TbCountyMapper tbCountyMapper;
	
	@Autowired
	private TbProvinceMapper tbProvinceMapper;

	public List<TbProvince> getAllProvince() {
		return tbProvinceMapper.selectAll();
	}

	public List<TbCity> getCityList(String provinceCode) {
		return tbCityMapper.selectByProCode(provinceCode);
	}

	public List<TbCity> findCityNoProvince() {
		return tbCityMapper.selectCityNoProvince();
	}

	public List<TbCounty> getCountyList(String provinceCode, String cityCode) {
		return tbCountyMapper.selectByProCityCode(provinceCode, cityCode);
	}

	public TbCounty findCountyByCode(String provinceCode, String cityCode, String countyCode) {
		return tbCountyMapper.selectByCode(provinceCode, cityCode, countyCode);
	}
	
	
	
	

}
