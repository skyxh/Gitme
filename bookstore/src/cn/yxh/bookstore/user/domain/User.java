package cn.yxh.bookstore.user.domain;

public class User {
	private String username;
	private String password;
	private String email;
	private boolean state;
	private String uid;
	private String code;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password
				+ ", email=" + email + ", state=" + state + ", uid=" + uid
				+ ", code=" + code + "]";
	}
	
	public User() {
		super();
	}
	public User(String username, String password, String email, boolean state,
			String uid, String code) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.state = state;
		this.uid = uid;
		this.code = code;
	}
	
}
