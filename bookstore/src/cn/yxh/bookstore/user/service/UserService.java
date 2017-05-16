package cn.yxh.bookstore.user.service;

import cn.yxh.bookstore.user.dao.UserDao;
import cn.yxh.bookstore.user.domain.User;

public class UserService {
	private UserDao userDao=new UserDao();
	
	public void regist(User form) throws UserException{
		//校验用户名
		User user=userDao.findByUsername(form.getUsername());
		if(user!=null) throw new UserException("用户名已注册!");
		
		user =userDao.findByEmail(form.getEmail());
		if(user!=null) throw new UserException("Email已被注册!");
		userDao.add(form);
	}
	
	public void active(String code) throws UserException{
		User user=userDao.findByCode(code);
		if(user==null) throw new UserException("激活码无效!");
		if(user.isState()) throw new UserException("您的激活码已经注册过了!");
		/*
		 * 修改用户状态
		 */
		userDao.updateState(user.getUid(), true);
	}
	
	public User login(User form) throws UserException{
		User user=userDao.findByUsername(form.getUsername());
		if(user==null) throw new UserException("用户不存在!");
		if(!form.getPassword().equals(user.getPassword()))
			throw new UserException("密码错误");
		/**
		 * 要检测用户是否激活
		 */
		if(!user.isState()) throw new UserException("用户尚未激活");
		return user;
	}
}
