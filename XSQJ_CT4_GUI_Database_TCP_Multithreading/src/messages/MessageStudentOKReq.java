package messages;

import java.io.Serializable;

public class MessageStudentOKReq extends Message implements Serializable {
	public String getStuNum_value() {
		return stuNum_value;
	}
	public String getStatus_value() {
		return status_value;
	}
	private String stuNum_value;
	private String status_value;
	public MessageStudentOKReq(String stuNum_value,String status_value) {
		super(MSG_TYPE.MSG_STUDENT_OK_REQ);
		this.stuNum_value=stuNum_value;
		this.status_value=status_value;
	}

}
