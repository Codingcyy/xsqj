package messages;

import info.Student;

import java.io.Serializable;

public class MessageStudentQueryAck extends Message implements Serializable {
	public boolean isSturepeat() {
		return isSturepeat;
	}
	public void setSturepeat(boolean isSturepeat) {
		this.isSturepeat = isSturepeat;
	}
	public boolean isStatepass() {
		return isStatepass;
	}
	public void setStatepass(boolean isStatepass) {
		this.isStatepass = isStatepass;
	}
	private boolean isSturepeat=false;
	private boolean isStatepass=false;
	private Student student=null;
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public MessageStudentQueryAck(Student student) {
		super(MSG_TYPE.MSG_STUDENT_QUERY_ACK);
		this.student=student;
		// TODO 自动生成的构造函数存根
	}
	public MessageStudentQueryAck() {
		// TODO 自动生成的构造函数存根
		super(MSG_TYPE.MSG_STUDENT_QUERY_ACK);
	}

}
