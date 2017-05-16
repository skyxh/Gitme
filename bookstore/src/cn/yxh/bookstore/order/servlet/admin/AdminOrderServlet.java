package cn.yxh.bookstore.order.servlet.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;
import cn.yxh.bookstore.order.service.OrderService;

public class AdminOrderServlet extends BaseServlet {
	private OrderService orderService=new OrderService();
	
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("orderList", orderService.findAll());		
		return "/adminjsps/admin/order/list.jsp";
	}
	public String findUnPay(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("orderList", orderService.findByState(1));		
		return "/adminjsps/admin/order/list.jsp";
	}
	public String findPayed(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("orderList", orderService.findByState(2));		
		return "/adminjsps/admin/order/list.jsp";
	}
	public String findUnReceive(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("orderList", orderService.findByState(3));		
		return "/adminjsps/admin/order/list.jsp";
	}
	public String findReceived(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("orderList", orderService.findByState(4));		
		return "/adminjsps/admin/order/list.jsp";
	}
}
