package cn.yxh.bookstore.book.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.New;
import javax.management.RuntimeErrorException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.jdbc.TxQueryRunner;
import cn.itcast.utils.CommonUtils;
import cn.yxh.bookstore.book.domain.Book;
import cn.yxh.bookstore.category.domain.Category;

public class BookDao {
	private QueryRunner qr=new TxQueryRunner();

	public List<Book> findAll() {
		String sql="SELECT * FROM book WHERE del=false";
		
		try {
			return qr.query(sql, new BeanListHandler<Book>(Book.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Book> findByCategory(String cid) {
		String sql="SELECT * FROM book WHERE cid=? AND del=false";
		
		try {
			return qr.query(sql, new BeanListHandler<Book>(Book.class), cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Book findByBid(String bid) {
		String sql="SELECT * FROM book WHERE bid=? AND del=false";
		
		try {
//			return qr.query(sql, new BeanHandler<Book>(Book.class), bid);
			//由于同时需要获取category信息，因此使用Map然后进行封装
			Map<String, Object> map=qr.query(sql, new MapHandler(), bid);
			Book book=CommonUtils.toBean(map, Book.class);
			Category category=CommonUtils.toBean(map, Category.class);
			book.setCategory(category);
			return book;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int getCountByCid(String cid) {
		String sql="SELECT count(*) FROM book WHERE cid=? AND del=false";
		
		try {
			Number count=(Number) qr.query(sql, new ScalarHandler(), cid);
			return count.intValue();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void add(Book book) {
		String sql="INSERT INTO book VALUES(?,?,?,?,?,?,false)";
		Object[] params={book.getBid(),book.getBname(),book.getPrice(),
				book.getAuthor(),book.getImage(),book.getCategory().getCid()};
		try {
			qr.update(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	public void delete(String bid) {
		//删除操作在逻辑上是数据库中del列的状态变化
		String sql="UPDATE book SET del=true WHERE bid=?";
		try {
			qr.update(sql,bid);
		} catch (SQLException e) {
			throw new  RuntimeException(e);
		}
		
	}

	public void mod(Book book) {
		String sql="UPDATE book SET bname=?,price=?,author=?,image=?,cid=?,del=? WHERE bid=?";
		Object[] params={book.getBname(),book.getPrice(),book.getAuthor(),
				book.getImage(),book.getCategory().getCid(),book.isDel(),book.getBid()};
		try {
			qr.update(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
