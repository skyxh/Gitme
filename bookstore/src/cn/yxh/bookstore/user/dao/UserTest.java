package cn.yxh.bookstore.user.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.MessageFormat;

import org.junit.Test;

import cn.yxh.bookstore.user.domain.User;

public class UserTest {
	@Test
	public void fun1() throws SQLException{
		User user=new User("zhangsan", "123", "9077@qq.com", true, "38u4923874928", "23984293874293742");
		UserDao userDao=new UserDao();
//		userDao.add(user);
//		User user1=userDao.findByUsername("zhangsan");
		User user2=userDao.findByEmail("9077@qq.com");
		System.out.println(user2);
	}
	/**
	 * MessageFormat 它使用占位符{0},{1}
	 */
	@Test
	public void fun2(){
		String s="{0}或{1}错误";
		String sf=MessageFormat.format(s, "用户名","密码");
		System.out.println(sf);
	}
	
	@Test
	public void fun3(){
		
//		double a1=2.0;
//		double a2=1.1;
		BigDecimal a1=new BigDecimal("2.0");
		BigDecimal a2=new BigDecimal("1.1");
		System.out.println(a1.multiply(a2).doubleValue());
	}
	
}
