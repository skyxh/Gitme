package cn.yxh.bookstore.order.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import cn.itcast.jdbc.JdbcUtils;
import cn.yxh.bookstore.order.dao.OrderDao;
import cn.yxh.bookstore.order.dao.OrderException;
import cn.yxh.bookstore.order.domain.Order;

public class OrderService {
	private OrderDao orderDao=new OrderDao();
	
	public void add(Order order){
		//开启事务
		try {
			JdbcUtils.beginTransaction();
			orderDao.addOrder(order);
			orderDao.addOrderItemList(order.getOrderItems());
			JdbcUtils.commitTransaction();
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
				
			}
			throw new RuntimeException(e);
		}
	}


	public List<Order> myOrders(String uid) {
		// TODO Auto-generated method stub
		return orderDao.findByUid(uid);
	}


	public Order load(String oid) {
		// TODO Auto-generated method stub
		return orderDao.findByOid(oid);
	}


	public void confirm(String oid) throws OrderException {
		int  state=orderDao.getStateByOid(oid);
		if(state!=3) throw new OrderException("订单确认失败!");
		orderDao.updateState(oid,4);
	}

	/**
	 * 支付方法
	 * @param r6_Order
	 */
	public void zhiFu(String r6_Order) {
		/**
		 * 首先获取订单状态
		 */
		int state=orderDao.getStateByOid(r6_Order);
		if(state==1){
			//修改为已发货状态
			orderDao.updateState(r6_Order, 2);
		}
		
	}


	public List<Order> findAll() {
		
		return orderDao.findAll();
	}


	public List<Order> findByState(int i) {
		
		return orderDao.findByState(i);
	}
}
