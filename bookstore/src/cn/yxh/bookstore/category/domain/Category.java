package cn.yxh.bookstore.category.domain;

public class Category {
	private String cname;
	private String cid;
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	@Override
	public String toString() {
		return "Category [cname=" + cname + ", cid=" + cid + "]";
	}
	
}
