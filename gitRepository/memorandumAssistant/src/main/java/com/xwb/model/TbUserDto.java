package com.xwb.model;

import com.xwb.common.PageDTO;

public class TbUserDto {
    // 分页属性值
    private PageDTO pageDTO = new PageDTO();

    private TbUser tbUser = new TbUser();

    public PageDTO getPageDTO() {
        return pageDTO;
    }

    public void setPageDTO(PageDTO pageDTO) {
        this.pageDTO = pageDTO;
    }

    public TbUser getTbUser() {
        return tbUser;
    }

    public void setTbUser(TbUser tbUser) {
        this.tbUser = tbUser;
    }
}
