package messages;

import java.io.Serializable;

public class MessageStudentFlushReq extends Message implements Serializable {

	private boolean flushreq=false;
	
	public boolean isFlushreq() {
		return flushreq;
	}

	public MessageStudentFlushReq(boolean flushreq) {
		super(MSG_TYPE.MSG_STUDENT_FLUSH_REQ);
		this.flushreq=flushreq;
	}

}
