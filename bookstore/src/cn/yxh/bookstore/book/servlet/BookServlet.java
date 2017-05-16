package cn.yxh.bookstore.book.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;
import cn.yxh.bookstore.book.service.BookService;

public class BookServlet extends BaseServlet {
	private BookService bookServce=new BookService();
	

	public String  findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("bookList", bookServce.findAll());
		return "f:/jsps/book/list.jsp";
	}
	
	public String  findByCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cid=request.getParameter("cid");
		request.setAttribute("bookList", bookServce.findByCategory(cid));
		return "f:/jsps/book/list.jsp";
	}
	
	public String  findByBid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String bid=request.getParameter("bid");
		request.setAttribute("book", bookServce.findByBid(bid));
		return "f:/jsps/book/desc.jsp";
	}
}
