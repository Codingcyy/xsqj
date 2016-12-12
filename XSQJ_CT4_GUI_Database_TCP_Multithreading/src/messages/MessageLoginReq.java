package messages;

import java.io.Serializable;

public class MessageLoginReq extends Message implements Serializable {
	private String account = null;
	private String pwd = null;

	private String username=null;
	public String getUsername() {
		return username;
	}
	public MessageLoginReq(String account, String pwd,String username) {
		super(MSG_TYPE.MSG_LOGIN_REQ);
		this.account = account;
		this.pwd = pwd;
		this.username=username;
	}
	public String getAccount() {
		return account;
	}

	public boolean verify(String username, String pwd) {
		if (this.username.equals(username) && this.pwd.equals(pwd))
			return true;

		return false;
	}

	public boolean verify(String pwd) {
		if (this.pwd.equals(pwd))
			return true;

		return false;
	}
}
