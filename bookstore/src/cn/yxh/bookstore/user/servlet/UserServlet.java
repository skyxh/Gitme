package cn.yxh.bookstore.user.servlet;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.Normalizer.Form;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.enterprise.inject.New;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import cn.itcast.servlet.BaseServlet;
import cn.itcast.utils.CommonUtils;
import cn.yxh.bookstore.cart.domain.Cart;
import cn.yxh.bookstore.user.domain.User;
import cn.yxh.bookstore.user.service.UserException;
import cn.yxh.bookstore.user.service.UserService;

public class UserServlet extends BaseServlet {
	private UserService userService=new UserService();

	public String regist(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		User user=CommonUtils.toBean(req.getParameterMap(), User.class);
		user.setUid(CommonUtils.uuid());
		user.setCode(CommonUtils.uuid()+CommonUtils.uuid());
		
		//用map存储错误信息，用来发送的regist界面
		Map< String, String> errors=new HashMap<String, String>();
		String username=user.getUsername();
		if(username==null || username.trim().isEmpty()){
			errors.put("username", "用户名不能为空!");
		} else if(username.length()<3||username.length()>12){
			errors.put("username", "用户名长度必须介于3~12!");
		}
		String password=user.getPassword();
		if(password==null || password.trim().isEmpty()){
			errors.put("password", "密码不能为空!");
		} else if(password.length()<3||password.length()>12){
			errors.put("password", "密码长度必须介于3~12!");
		}
		String email=user.getEmail();
		if(email==null || email.trim().isEmpty()){
			errors.put("email", "email不能为空!");
		} else if(!email.matches("\\w+@\\w+\\.\\w+")){
			errors.put("email", "email格式不正确");
		}
		
		if(errors.size()>0){
			req.setAttribute("errors",errors);
			req.setAttribute("user", user);
			return "f:/jsps/user/regist.jsp";
		}
		
		//完成注册后将成功信息返回到msg.jsp
		
		try {
			userService.regist(user);
			
		} catch (UserException e) {
			req.setAttribute("msg", e.getMessage());
			req.setAttribute("user", user);
			return "f:/jsps/user/regist.jsp";
		}
		/**
		 * 发邮件，准备配置文件
		 */
		//获取配置文件内容
		Properties props=new Properties();
		props.load(this.getClass().getClassLoader().
				getResourceAsStream("email_template.properties"));
		String host=props.getProperty("host");
		String uname=props.getProperty("uname");
		String pwd=props.getProperty("pwd");
		String from=props.getProperty("from");
		String to=user.getEmail();
		String subject=props.getProperty("subject");
		String content=props.getProperty("content");
		content=MessageFormat.format(content, user.getCode());//填充模板的占位符，{0}
		Session session=MailUtils.createSession(host, uname, pwd);
		Mail mail=new Mail(from, to, subject, content);	
		try{
			MailUtils.send(session, mail);
		} catch (Exception e){
			throw new RuntimeException(e);
		}
		//转发成功信息到msg.jsp
		req.setAttribute("msg", "恭喜，注册成功！请马上到邮箱激活");
		return "f:/jsps/msg.jsp";
	}
	
	public String active(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			String code=req.getParameter("code");
		    try {
				userService.active(code);
				req.setAttribute("msg", "恭喜，您已经注册成功!,请马上登录!");
			} catch (UserException e) {
				req.setAttribute("msg", e.getMessage());
//				return "f:/jsps/msg.jsp";
			}
		
		
		    return "f:/jsps/msg.jsp";
	}
	
	public String login(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		User form=CommonUtils.toBean(req.getParameterMap(), User.class);
		
		/**
		 * 校验用户信息
		 */
		Map< String, String> errors=new HashMap<String, String>();
		String username=form.getUsername();
		if(username==null||username.trim().isEmpty()){
			errors.put("username", "用户每户不能为空");
		} else if(username.length()<3||username.length()>12){
			errors.put("username", "用户名长度必须介于3~12之间");
		}
		String password=form.getPassword();
		if(password==null||password.trim().isEmpty()){
			errors.put("password", "用户每户不能为空");
		} else if(password.length()<3||password.length()>12){
			errors.put("password", "用户名长度必须介于3~12之间");
		}
		if(errors.size()>0){
			req.setAttribute("errors", errors);
			req.setAttribute("form", form);
			return "f:/jsps/user/login.jsp";
		}
		
		try {
			User user=userService.login(form);
			//确保整个会话，信息都存活
			req.getSession().setAttribute("user", user);
			//登录成功即分配购物车,保存在session域中
			req.getSession().setAttribute("cart", new Cart());
			
//			return "r:/jsps/top.jsp";
			return "r:/index.jsp";
		} catch (UserException e) {
			req.setAttribute("msg", e.getMessage());
			req.setAttribute("form", form);
			return "f:/jsps/user/login.jsp";
		}	
	}
	
	public String quit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//销毁session
		req.getSession().invalidate();
//		System.out.println("hahahha==============================");
		return "r:/index.jsp";
	}
}
