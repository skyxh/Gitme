package cn.yxh.bookstore.cart.domain;


import java.math.BigDecimal;
import java.util.Collection;

import java.util.LinkedHashMap;
import java.util.Map;



/**
 * 购物车类
 * @author Administrator
 *
 */
public class Cart {
	private Map<String, CartItem>map=new LinkedHashMap<String, CartItem>();
	/**
	 * 计算合计
	 * @return
	 */
	public double getTotal(){
		BigDecimal total=new BigDecimal("0");
		for(CartItem cartItem:map.values()){
			BigDecimal  subtotal=new BigDecimal(""+cartItem.getSubtotal());
			total=total.add(subtotal);
		}
		return total.doubleValue();
	}
	/**
	 * 添加条目到购物车
	 */
	
	public void add(CartItem cartItem){
		//若添加重复商品，需要合并
		if(map.containsKey(cartItem.getBook().getBid())){
			CartItem _cartItem=map.get(cartItem.getBook().getBid());//返回原条目
			_cartItem.setCount(_cartItem.getCount()+cartItem.getCount());//合并数量
			map.put(cartItem.getBook().getBid(), _cartItem);
		} else {
			map.put(cartItem.getBook().getBid(), cartItem);
		}
		//不能掉了没有重复添加时的逻辑
	}
	/*
	 * 清空所有条目
	 */
	public void clear(){
		map.clear();
	}
	
	/**
	 * 删除指定条目
	 */
	public void delete(String bid){
		map.remove(bid);
	}
	/**
	 * 获取所有条目
	 */
	public Collection<CartItem> getCartItems(){
		return map.values();
	}
}
