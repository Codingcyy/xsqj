package info;

import java.io.Serializable;

public class User implements Serializable {
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	String username = new String();
	String pwd = new String();

	public User() {
		// TODO 自动生成的构造函数存根
	}

	public void show() {
		System.out.println("=== Use Information ===");
		System.out.println("*** Username: \t" + username);
		System.out.println("*** Name   : \t" + pwd);
	}

	public boolean verifyPwd(String pwd) {
		if (!this.pwd.equals(pwd)) {
			return false;
		}

		return true;
	}

}
