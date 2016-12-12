package messages;

import java.io.Serializable;

public abstract class Message implements Serializable {
   public enum MSG_TYPE {
    MSG_UNKOWN, MSG_EXAMPLE,
    MSG_LOGIN_REQ, MSG_LOGIN_ACK,  
	MSG_STUDENT_QUERY, MSG_STUDENT_QUERY_ACK, 
	MSG_STUDENT_SEARCH, MSG_STUDENT_SEARCH_ACK,
	MSG_STUDENT_DELETE,MSG_STUDENT_DELETE_ACK,
	MSG_STUDENT_FLUSH_REQ,MSG_STUDENT_FLUSH_ACK,
	MSG_STUDENT_OK_REQ,MSG_STUDENT_OK_ACK
   }
   
   public MSG_TYPE msgType = MSG_TYPE.MSG_UNKOWN;
   
   public Message(MSG_TYPE mt) {
      msgType = mt;
   }
}
