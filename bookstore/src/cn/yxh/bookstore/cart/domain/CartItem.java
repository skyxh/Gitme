package cn.yxh.bookstore.cart.domain;

import java.math.BigDecimal;

import cn.yxh.bookstore.book.domain.Book;

public class CartItem {
	private Book book;//商品
	private int count;//数量
	
	public double getSubtotal(){
		//计算条目对应的价格
		BigDecimal d1=new BigDecimal(book.getPrice()+"");
		BigDecimal d2=new BigDecimal(count+"");
		return d1.multiply(d2).doubleValue();
	}
	
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
}
