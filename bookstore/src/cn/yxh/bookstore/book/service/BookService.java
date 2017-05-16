package cn.yxh.bookstore.book.service;

import java.util.List;

import cn.yxh.bookstore.book.dao.BookDao;
import cn.yxh.bookstore.book.domain.Book;

public class BookService {
	private BookDao bookDao=new BookDao();
	public List<Book> findAll() {
		return bookDao.findAll();
	}
	public List<Book> findByCategory(String cid) {
		
		return bookDao.findByCategory(cid);
	}
	public Book findByBid(String bid) {
		// TODO Auto-generated method stub
		return bookDao.findByBid(bid);
	}
	public void add(Book book) {
		bookDao.add(book);
		
	}
	public void delete(String bid) {
		bookDao.delete(bid);
		
	}
	public void mod(Book book) {
		bookDao.mod(book);
		
	}

}
