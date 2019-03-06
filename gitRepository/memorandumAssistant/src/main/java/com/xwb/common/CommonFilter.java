package com.xwb.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.xwb.model.TbUser;

/**
 * Servlet Filter implementation class CommonFilter
 */
public class CommonFilter implements Filter {
	//需要定义系统页面访问中可放行的连接集合
	private List<String> list = new ArrayList<String>();

    /**
     * Default constructor. 
     */
    public CommonFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		//获取访问路径
		String path = request.getServletPath();
		//判断路径是否为空或是否放行的路径
		System.out.println("经过过滤器。。。。。。。。。。。");
        if(list!=null&&list.contains(path)){
        	chain.doFilter(request, response);
        	return;
        }
        //查看session中的用户是否为空
        TbUser user = (TbUser)request.getSession().getAttribute("tbluser");
        if(user != null){
        	try{
        		chain.doFilter(request, response);
        	}catch (Exception e) {
        		e.printStackTrace();
        	}
        	return;
        }
        //不满足上述条件，退出到登录页
      response.sendRedirect(request.getContextPath()+"/view/login.jsp");
		//chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		list.add("/sysManager/login.do");
		list.add("/view/login.jsp");
		list.add("/view/authCodeImage.jsp");
		list.add("/sysManager/toRegist.do");
		list.add("/404.jsp");
		list.add("/error.jsp");
		list.add("/view/images/icon_right_s.png");
		list.add("/view/css/validform.css");
		list.add("/view/css/regist.css");
		list.add("/view/img/bj.gif");
	}

}
