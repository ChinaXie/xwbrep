package com.xwb.common;

import java.io.Serializable;

public class PageDTO implements Serializable {

   
    /**
     * 
     */
    private static final long serialVersionUID = 3064772718651571878L;

    
    // 每页的数目
    private int pageSize = 10;
    
   /*  // 页码数
    private int pageNum = 0;*/
    
    
    // 开始页面码数
    private int beginCount = 0;
    
    

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

   /* public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }*/

    
    public int getBeginCount() {       
        
        return beginCount;
    }

    
    public void setBeginCount(int beginCount) {
        this.beginCount = beginCount;
    }

    
    

}
