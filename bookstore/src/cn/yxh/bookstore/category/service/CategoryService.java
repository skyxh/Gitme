package cn.yxh.bookstore.category.service;

import java.util.List;

import cn.yxh.bookstore.book.dao.BookDao;
import cn.yxh.bookstore.book.domain.Book;
import cn.yxh.bookstore.category.dao.CategoryDao;
import cn.yxh.bookstore.category.domain.Category;
import cn.yxh.bookstore.category.servlet.admin.CategoryException;

public class CategoryService {
	private CategoryDao categoryDao=new CategoryDao();
	private BookDao bookDao=new BookDao();
 	
	public List<Category> findAll(){
		return categoryDao.findAll();
	}

	public void add(Category category) throws CategoryException {
		//首先获取所有分类，然后看是否与添加分类重复，若重复则返回添加失败
		List<Category> categoryList=findAll();
		for(Category c:categoryList){
			if(c.getCname().equals(category.getCname())){
				throw new CategoryException("该分类已存在，请勿重复添加!");
			}
		}
		categoryDao.add(category);
	}

	public Category load(String cid) {
		
		return categoryDao.findByCid(cid);
	}

	public void edit(Category category) {
		categoryDao.update(category);
		
	}

	public void delete(String cid) throws CategoryException {
		//删除之前首先要校验，该分类下是否是否存在图书，此时需要用到bookDao
		//若存在，则抛出异常，删除失败
		int count =bookDao.getCountByCid(cid);
		if(count>0) throw new CategoryException("该分类下还有图书，删除失败");
		categoryDao.delete(cid);
		
	}
	
}
