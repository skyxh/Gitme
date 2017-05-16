package cn.yxh.bookstore.cart.servlet;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;
import cn.yxh.bookstore.book.domain.Book;
import cn.yxh.bookstore.book.service.BookService;
import cn.yxh.bookstore.cart.domain.Cart;
import cn.yxh.bookstore.cart.domain.CartItem;

public class CartServlet extends BaseServlet {
	
	/**
	 * 添加购物条目
	 */
	
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cart cart=(Cart) request.getSession().getAttribute("cart");
		String bid=request.getParameter("bid");
		Book book=new BookService().findByBid(bid);
		//System.out.println(book);
		int count=Integer.parseInt(request.getParameter("count"));
		CartItem cartItem=new CartItem();
		cartItem.setBook(book);
		cartItem.setCount(count);
		cart.add(cartItem);
//		//测试
//		Collection<CartItem> cartItems=cart.getCartItems();
//		for(CartItem cartItem2:cartItems){
//			Book book2=cartItem.getBook();
//					System.out.println(book2);
//		}
		
		request.getSession().setAttribute("cart", cart);
		return "f:/jsps/cart/list.jsp";
	}
	
	public String delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cart cart=(Cart) request.getSession().getAttribute("cart");
		String bid=request.getParameter("bid");
		cart.delete(bid);
		return "f:/jsps/cart/list.jsp";
	}
	public String clear(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cart cart=(Cart) request.getSession().getAttribute("cart");
		
		cart.clear();
		return "f:/jsps/cart/list.jsp";
	}
}
