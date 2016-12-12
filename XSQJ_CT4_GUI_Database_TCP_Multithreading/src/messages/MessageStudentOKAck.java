package messages;

import java.io.Serializable;

public class MessageStudentOKAck extends Message implements Serializable {
	private boolean ChangeOK=false;
	public boolean isChangeOK() {
		return ChangeOK;
	}
	public MessageStudentOKAck(boolean changeOK) {
		super(MSG_TYPE.MSG_STUDENT_OK_ACK);
		this.ChangeOK= changeOK;
	}

}
