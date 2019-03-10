package com.xwb.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xwb.model.TbUser;

public interface TbUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbUser record);

    int insertSelective(TbUser record);

    TbUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbUser record);

    int updateByPrimaryKey(TbUser record);
    
    Integer selectCountByPhone(TbUser record);
    
    Integer selectCountByPhoneLoginName(TbUser record);
    
    TbUser selectUser(@Param("loginName")String loginName,@Param("password")Integer password);
    
    List<TbUser> selectAllUser();
    
}