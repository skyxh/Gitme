package cn.yxh.bookstore.book.servlet.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import cn.yxh.bookstore.book.domain.Book;
import cn.yxh.bookstore.book.service.BookService;
import cn.yxh.bookstore.category.domain.Category;
import cn.yxh.bookstore.category.service.CategoryService;

public class AdminBookServlet extends BaseServlet {
	private BookService bookService=new BookService();
	private CategoryService categoryService=new CategoryService();
	/**
	 * 查看所有图书
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setAttribute("bookList", bookService.findAll());
		return "f:/adminjsps/admin/book/list.jsp";
	}
	
	/**
	 * 查看图书细节
	 */
	public String load(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String bid=request.getParameter("bid");
		request.setAttribute("book", bookService.findByBid(bid));
		//desc.jsp下拉列表中还需要获取所有分类信息
		request.setAttribute("categoryList", categoryService.findAll());
		return "f:/adminjsps/admin/book/desc.jsp";
	}
	
	public String addPre(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Category> categoryList=categoryService.findAll();
//		for(Category c:categoryList){
//			System.out.println(c);
//		}
		request.setAttribute("categoryList",categoryList );
		return "f:/adminjsps/admin/book/add.jsp";
	}
	
	public String mod(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Book book =CommonUtils.toBean(request.getParameterMap(), Book.class);
		Category category=CommonUtils.toBean(request.getParameterMap(), Category.class);
		book.setDel(false);
		book.setCategory(category);
		bookService.mod(book);
		return findAll(request,response);
	}
	
	public String delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		bookService.delete(request.getParameter("bid"));
		
		return findAll(request,response);
	}
}
