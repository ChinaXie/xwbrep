package com.xwb.model;

import java.util.Date;

public class TbWeather {
    private Integer id;

    private String cityName;

    private String cityCode;

    private String updateTime;

    private String wenDu;

    private String fengLi;

    private String shiDu;

    private String fengXiang;

    private String sunRise;

    private String sunSet;

    private Date addTime;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    public String getWenDu() {
        return wenDu;
    }

    public void setWenDu(String wenDu) {
        this.wenDu = wenDu == null ? null : wenDu.trim();
    }

    public String getFengLi() {
        return fengLi;
    }

    public void setFengLi(String fengLi) {
        this.fengLi = fengLi == null ? null : fengLi.trim();
    }

    public String getShiDu() {
        return shiDu;
    }

    public void setShiDu(String shiDu) {
        this.shiDu = shiDu == null ? null : shiDu.trim();
    }

    public String getFengXiang() {
        return fengXiang;
    }

    public void setFengXiang(String fengXiang) {
        this.fengXiang = fengXiang == null ? null : fengXiang.trim();
    }

    public String getSunRise() {
        return sunRise;
    }

    public void setSunRise(String sunRise) {
        this.sunRise = sunRise == null ? null : sunRise.trim();
    }

    public String getSunSet() {
        return sunSet;
    }

    public void setSunSet(String sunSet) {
        this.sunSet = sunSet == null ? null : sunSet.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}