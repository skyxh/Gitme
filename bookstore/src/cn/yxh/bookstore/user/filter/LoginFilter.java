package cn.yxh.bookstore.user.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import cn.yxh.bookstore.user.domain.User;

public class LoginFilter implements Filter{

	@Override
	public void destroy() {
	
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		//首先将request转换成HttpServletRequest
		HttpServletRequest request=(HttpServletRequest) arg0;
		User user=(User) request.getSession().getAttribute("user");
		if(user!=null){
			//确保操作订单及购物车之前已经登录
			arg2.doFilter(arg0, arg1);
		}else {
			request.setAttribute("msg", "您还没有登录!");
			request.getRequestDispatcher("/jsps/user/login.jsp").
			forward(request, arg1);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
		
	}

}
