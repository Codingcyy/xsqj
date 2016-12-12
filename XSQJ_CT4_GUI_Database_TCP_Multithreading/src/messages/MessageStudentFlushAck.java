package messages;

import info.Student;

import java.io.Serializable;
import java.util.Vector;

public class MessageStudentFlushAck extends Message implements Serializable {
		public int getSize() {
		return size;
	}
	public Vector<Student> getVst() {
		return vst;
	}
		private int size=0;
		private Vector<Student> vst=null;
		private boolean isOK=false;
	public boolean isOK() {
			return isOK;
		}
		public void setOK(boolean isOK) {
			this.isOK = isOK;
		}
	public MessageStudentFlushAck(int size,Vector<Student> vst) {
		super(MSG_TYPE.MSG_STUDENT_FLUSH_ACK);
		this.size=size;
		this.vst=vst;
	}

}
