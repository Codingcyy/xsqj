package info;

import java.io.Serializable;

public class Admin extends User implements Serializable{

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isId() {
		return id;
	}

	private final boolean id = true;
	private String name = new String();

	public Admin() {
		// TODO �Զ����ɵĹ��캯�����
		super();
	}



}
