package cn.yxh.bookstore.user.dao;

import java.sql.SQLException;

import javax.enterprise.inject.New;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.omg.CORBA.UserException;

import cn.itcast.jdbc.TxQueryRunner;
import cn.yxh.bookstore.user.domain.User;

public class UserDao {
	private QueryRunner qr=new TxQueryRunner();
	
	/**
	 * 通过用户名查询
	 * @throws SQLException 
	 */
	public User findByUsername(String username) {
			try {
				String sql="SELECT * FROM tb_user WHERE username=?";
				return qr.query(sql, new BeanHandler<User>(User.class),username);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
	}
	
	/**
	 * 通过email查询
	 * @throws SQLException 
	 */
	public User findByEmail(String email) {
		try {
			String sql="SELECT * FROM tb_user WHERE email=?";
			return qr.query(sql, new BeanHandler<User>(User.class),email);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/*
	 * 添加用户信息到数据库
	 */
	public void add(User form) {
		try {
			String sql="INSERT INTO tb_user VALUES(?,?,?,?,?,?)";
			qr.update(sql, form.getUid(),form.getUsername(),form.getPassword()
					,form.getEmail(),form.getCode(),form.isState());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 按激活码进行查询
	 */
	public User findByCode(String code){
		try {
			String sql="SELECT * FROM tb_user WHERE code=?";
			return qr.query(sql, new BeanHandler<User>(User.class),code);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}	
	}
	/**
	 * 根据指定id修改state
	 */
	public void updateState(String uid,boolean state){
		try {
			String sql="UPDATE tb_user SET state=? WHERE uid=?";
			qr.update(sql, state,uid);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}	
	}
}
