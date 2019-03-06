package com.xwb.mappers;

import java.util.List;

import com.xwb.model.TbMemorandum;

public interface TbMemorandumMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbMemorandum record);

    int insertSelective(TbMemorandum record);

    TbMemorandum selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbMemorandum record);

    int updateByPrimaryKey(TbMemorandum record);
    
    List<TbMemorandum> findListByUserId(int userId);
}