package com.xwb.model;

public class TbForecast {
    private Integer id;

    private String cityCode;

    private String foreCastDate;

    private String highTemp;

    private String lowTemp;

    private String dayType;

    private String dayFengXiang;

    private String datFengLi;

    private String nightType;

    private String nightFengXiang;

    private String nightFengLi;

    private Integer weatherId;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getForeCastDate() {
        return foreCastDate;
    }

    public void setForeCastDate(String foreCastDate) {
        this.foreCastDate = foreCastDate == null ? null : foreCastDate.trim();
    }

    public String getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(String highTemp) {
        this.highTemp = highTemp == null ? null : highTemp.trim();
    }

    public String getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(String lowTemp) {
        this.lowTemp = lowTemp == null ? null : lowTemp.trim();
    }

    public String getDayType() {
        return dayType;
    }

    public void setDayType(String dayType) {
        this.dayType = dayType == null ? null : dayType.trim();
    }

    public String getDayFengXiang() {
        return dayFengXiang;
    }

    public void setDayFengXiang(String dayFengXiang) {
        this.dayFengXiang = dayFengXiang == null ? null : dayFengXiang.trim();
    }

    public String getDatFengLi() {
        return datFengLi;
    }

    public void setDatFengLi(String datFengLi) {
        this.datFengLi = datFengLi == null ? null : datFengLi.trim();
    }

    public String getNightType() {
        return nightType;
    }

    public void setNightType(String nightType) {
        this.nightType = nightType == null ? null : nightType.trim();
    }

    public String getNightFengXiang() {
        return nightFengXiang;
    }

    public void setNightFengXiang(String nightFengXiang) {
        this.nightFengXiang = nightFengXiang == null ? null : nightFengXiang.trim();
    }

    public String getNightFengLi() {
        return nightFengLi;
    }

    public void setNightFengLi(String nightFengLi) {
        this.nightFengLi = nightFengLi == null ? null : nightFengLi.trim();
    }

    public Integer getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(Integer weatherId) {
        this.weatherId = weatherId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}