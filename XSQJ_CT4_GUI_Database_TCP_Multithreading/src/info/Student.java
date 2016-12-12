package info;

import java.io.Serializable;

public class Student extends User implements Serializable{

	public boolean isId() {
		return id;
	}

	public String getStuNum() {
		return stuNum;
	}

	public void setStuNum(String stuNum) {
		this.stuNum = stuNum;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	private final boolean id = false;
	private String sex = new String();
	private String name = new String();
	private String stuNum = new String();
	private String major = new String();
	private String reason = new String();
	private String state = new String("未批准");

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Student() {
		// TODO 自动生成的构造函数存根
		super();
	}
	public void show() {
		show();
		System.out.println("*** Name: \t" + name);
		System.out.println("*** StuNum: \t" + pwd);
		System.out.println("*** Sex:\t"+sex);
		System.out.println("*** Major:\t"+major);
		System.out.println("*** Reason:\t"+reason);
		System.out.println("*** State:\t"+state);
		
	}


}
