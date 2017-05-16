package cn.yxh.bookstore.order.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.New;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.jdbc.TxQueryRunner;
import cn.itcast.utils.CommonUtils;
import cn.yxh.bookstore.book.domain.Book;
import cn.yxh.bookstore.order.domain.Order;
import cn.yxh.bookstore.order.domain.OrderItem;
import cn.yxh.bookstore.user.domain.User;

public class OrderDao {
	private QueryRunner qr=new TxQueryRunner();
	
	public void addOrder(Order order){
		String sql="INSERT INTO orders VALUES(?,?,?,?,?,?)";
		//将java.util的time转换为java.sql的时间
		Timestamp timestamp=new Timestamp(order.getOrdertime().getTime());
		try {
			qr.update(sql, order.getOid(),timestamp,order.getTotal(),order.getState(),
					order.getOwner().getUid(),order.getAddress());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	/**
	 * 由于一个订单中可能有多个订单项，因此需要使用批处理
	 * @param orderItem
	 */
	public void addOrderItemList(List<OrderItem> orderItemList){
		String sql="INSERT INTO orderitem VALUES(?,?,?,?,?)";
		Object[][] params=new Object[orderItemList.size()][];
		
		for(int i=0;i<orderItemList.size();i++){
			OrderItem orderItem=orderItemList.get(i);
			params[i]=new Object[]{orderItem.getIid(),orderItem.getCount(),
					orderItem.getSubtotal(),orderItem.getOrder().getOid(),orderItem.getBook().getBid()};
		}
		try {
			qr.batch(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Order> findByUid(String uid) {
		String sql="SELECT * FROM orders WHERE uid=?";
		try {
			List<Order> orderList=qr.query(sql, new BeanListHandler<Order>(Order.class), uid);
			//还需要将所有订单中的订单条目的相关内容加载到订单列表中
			for(Order order:orderList){
				loadOrderItems(order);
			}
			return orderList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	/**
	 * 加载订单的所有订单条目
	 * @param order
	 * @throws SQLException 
	 */
	private void loadOrderItems(Order order) throws SQLException {
		//此时不仅需要查询orderitem表还需要查询book表来将其中图书信息添加进入。
		String sql="SELECT * FROM orderitem o,book b WHERE o.bid=b.bid AND o.oid=?";
		//由于此时查询结果不再对应一个javaBean,因此使用MAP
		List<Map<String,Object>> mapList=qr.query(sql, new MapListHandler(), order.getOid());
		//将Map列表转换成两个javaBean
		
		List<OrderItem>orderItemList=toOrderItemList(mapList);
		order.setOrderItems(orderItemList);
	}
	
	/*
	 * 将MapList中的结果转换成两个Bean对象
	 */
	private List<OrderItem> toOrderItemList(List<Map<String, Object>> mapList) {
		List<OrderItem> orderItems=new ArrayList<OrderItem>();
		for(Map<String, Object> map:mapList){
			OrderItem orderItem=toOrderItem(map);
			orderItems.add(orderItem);
		}
		return orderItems;
	}
	
	private OrderItem toOrderItem(Map<String, Object> map) {
		OrderItem orderItem=CommonUtils.toBean(map, OrderItem.class);
		Book book=CommonUtils.toBean(map, Book.class);
		orderItem.setBook(book);
		return orderItem;
	}
	public Order findByOid(String oid) {
		String sql="SELECT * FROM orders WHERE oid=?";
		try {
			Order order=qr.query(sql, new BeanHandler<Order>(Order.class), oid);
			//还需要将所有订单中的订单条目的相关内容加载到订单列表中
			loadOrderItems(order);	
			return order;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	public int getStateByOid(String oid) {
		String sql="SELECT state FROM orders WHERE oid=?";
		Number number;
		try {
			number = (Number) qr.query(sql, new ScalarHandler(),oid);
			return number.intValue();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		
	}
	public void updateState(String oid, int i) {
		String sql="UPDATE orders SET state=? WHERE oid=?";
		try {
			qr.update(sql, i,oid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<Order> findAll() {
		String sql="SELECT * FROM orders ";
		try {
			List<Order> orderList=qr.query(sql, new BeanListHandler<Order>(Order.class));
			//还需要将所有订单中的订单条目的相关内容加载到订单列表中
			for(Order order:orderList){
				loadOrderItems(order);
			}
			return orderList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<Order> findByState(int i) {
		String sql="SELECT * FROM orders WHERE state=?";
		try {
			List<Order> orderList=qr.query(sql, new BeanListHandler<Order>(Order.class),i);
			//还需要将所有订单中的订单条目的相关内容加载到订单列表中
			for(Order order:orderList){
				loadOrderItems(order);
			}
			return orderList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
