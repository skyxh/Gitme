package cn.yxh.bookstore.book.servlet.admin;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.itcast.utils.CommonUtils;
import cn.yxh.bookstore.book.domain.Book;
import cn.yxh.bookstore.book.service.BookService;
import cn.yxh.bookstore.category.domain.Category;
import cn.yxh.bookstore.category.service.CategoryService;

public class AddBookServlet extends HttpServlet {
	private BookService bookService=new BookService();
	CategoryService categoryService=new CategoryService();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		
		/*
		 * 获取表单内容
		 */
		DiskFileItemFactory dfif=new DiskFileItemFactory( 15*1024,new File("F:/temp"));
		//使用工厂创建解析器
		ServletFileUpload sfu=new ServletFileUpload(dfif);
		//设置单个文件大小为15KB
		sfu.setFileSizeMax(15*1024);
		//使用工厂解析器对象解析request，获取FileItem列表
		try {
			List<FileItem> list=sfu.parseRequest(req);
			//将表单内容中的普通表单字段封装成Book,此处以Map作为过渡
			Map<String, String>map=new HashMap<String, String>();
			for(FileItem f:list){
				if(f.isFormField()){
					map.put(f.getFieldName(),f.getString("utf-8"));
				}
			}
			Book book=CommonUtils.toBean(map, Book.class);
			book.setBid(CommonUtils.uuid());
			Category category=CommonUtils.toBean(map, Category.class);
			book.setCategory(category);
			/**
			 * 保存上传的文件
			 */
			String path=this.getServletContext().getRealPath("/book_img");
			//得到上传图片名称,并避免文件名冲突
			String filename=CommonUtils.uuid()+"_"+list.get(1).getName();

			//检验文件扩展名是否为JPG
			if(!filename.toLowerCase().endsWith("jpg")){
				req.setAttribute("msg", "您上传的图不是JPG扩展名");
				req.setAttribute("categoryList", categoryService.findAll());
				req.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(req, resp);
				return ;
			}

			File file=new File(path, filename);
			//将文件写到指定路径
			list.get(1).write(file);
			book.setImage("book_img/"+filename);
			bookService.add(book);
			
			/**
			 * 校验图片尺寸
			 */
			Image image=new ImageIcon(file.getAbsolutePath()).getImage();
			if(image.getWidth(null)>200|| image.getHeight(null)>200){
				file.delete();//删除该文件
				req.setAttribute("msg", "您上传的图片尺寸大于200*200");
				req.setAttribute("categoryList", categoryService.findAll());
				req.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(req, resp);
				return ;
			}
			req.getRequestDispatcher("/admin/AdminBookServlet?method=findAll").forward(req, resp);
			
		} catch (Exception e) {
			if(e instanceof FileUploadBase.FileSizeLimitExceededException){
				req.setAttribute("msg", "您上传的文件超过15KB");
				req.setAttribute("categoryList", categoryService.findAll());
				req.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(req, resp);
				return ;
			}
		}
	}
	
}
