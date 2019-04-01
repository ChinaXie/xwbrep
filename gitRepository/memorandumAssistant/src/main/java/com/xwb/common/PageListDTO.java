package com.xwb.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 分页列表
 * 
 * @author
 *
 */
public class PageListDTO<T> implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3674880567837847216L;

    // 结果列表
    private List<T> resultList = new ArrayList<T>();
    
    // 分页参数
    private PageDTO pageDTO = new PageDTO();
    
    // 记录总数
    private int listCount = 0;

    

    public List<T> getResultList() {
        return resultList;
    }


    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }


    public PageDTO getPageDTO() {
        return pageDTO;
    }


    public void setPageDTO(PageDTO pageDTO) {
        this.pageDTO = pageDTO;
    }


    public int getListCount() {
        return listCount;
    }


    public void setListCount(int listCount) {
        this.listCount = listCount;
    }
    
    

}
