package messages;

import java.io.Serializable;

public class MessageStudentDeleteAck extends Message implements Serializable {
	private boolean isSuccess=false;
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public MessageStudentDeleteAck() {
		super(MSG_TYPE.MSG_STUDENT_DELETE_ACK);
		// TODO 自动生成的构造函数存根
	}

}
