package messages;

import java.io.Serializable;

public class MessageStudentSearch extends Message implements Serializable {

	public String getStu() {
		return stu;
	}

	public void setStu(String stu) {
		this.stu = stu;
	}


	private String stu=null;
	
	public MessageStudentSearch(String stu) {
		super(MSG_TYPE.MSG_STUDENT_SEARCH);
	
		this.stu=stu;
		// TODO 自动生成的构造函数存根
	}

}
