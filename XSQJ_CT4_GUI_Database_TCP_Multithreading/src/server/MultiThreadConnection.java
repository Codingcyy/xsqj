package server;

import info.Student;
import info.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

import server.Database.XSQJDatabase;
import messages.Message;
import messages.MessageLoginAck;
import messages.MessageLoginReq;
import messages.MessageStudentDeleteAck;
import messages.MessageStudentFlushAck;
import messages.MessageStudentFlushReq;
import messages.MessageStudentOKAck;
import messages.MessageStudentOKReq;
import messages.MessageStudentQuery;
import messages.MessageStudentQueryAck;
import messages.MessageStudentSearch;
import messages.Message.MSG_TYPE;

public class MultiThreadConnection extends Thread {
	private Socket socket;
	private ObjectOutputStream oos = null;
	private ObjectInputStream ois = null;

	public MultiThreadConnection(Socket socket) {
		this.socket = socket;
	
	}

	private ObjectOutputStream getWriter(Socket socket)  {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return oos;
	}

	private ObjectInputStream getReader(Socket socket) {
		ObjectInputStream ois=null;
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return ois;
	}


	private void handleClient(Socket socket) {
		oos=getWriter(socket);
		ois=getReader(socket);
		while (true) {
			
			// �Ӷ��������������ж�ȡ���ݶ���
			Message msgReceived = null;
			try {
				
				msgReceived = (Message) ois.readObject();
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				continue;
			} catch (IOException e) { // Socket ����ֹͣ�Դ�Socket�Ĵ���
				e.printStackTrace();
				System.out.println(e.getMessage());
				return;
			}
			switch (msgReceived.msgType) {
			case MSG_LOGIN_REQ:
				handleLoginRequest((MessageLoginReq) msgReceived, oos);
				break;
			case MSG_STUDENT_QUERY:
				handleStudentQuery((MessageStudentQuery) msgReceived, oos);
				break;
			case MSG_STUDENT_SEARCH:
				handleStudentSearch((MessageStudentSearch) msgReceived, oos);
				break;
			case MSG_STUDENT_DELETE:
				handleStudentDelete((MessageStudentQuery)msgReceived, oos);
				break;
			case MSG_STUDENT_FLUSH_REQ:
				handleStudentFlush((MessageStudentFlushReq)msgReceived, oos);
				break;
			case MSG_STUDENT_OK_REQ:
				handleStudentOK((MessageStudentOKReq)msgReceived, oos);
				break;
			default:
				System.out.println("Uknown message received: "
						+ msgReceived.msgType);
			}
			
		}
		
	}


	private void handleStudentOK(MessageStudentOKReq msgReceived,
			ObjectOutputStream oos) {
		// TODO �Զ����ɵķ������
		System.out.println(msgReceived.getStuNum_value());
		System.out.println(msgReceived.getStatus_value());
		XSQJDatabase.changeStatus(msgReceived.getStuNum_value(), msgReceived.getStatus_value());
		MessageStudentOKAck msoa=new MessageStudentOKAck(true);
		sendMessage(msoa, oos);
	}

	private void handleStudentFlush(MessageStudentFlushReq msgReceived,
			ObjectOutputStream oos) {
		// TODO �Զ����ɵķ������
		
		if(msgReceived.isFlushreq())
		{
			XSQJDatabase.Searchall();
			int s=XSQJDatabase.Vst.size();
			MessageStudentFlushAck msfa=new MessageStudentFlushAck(s,XSQJDatabase.Vst);
			msfa.setOK(true);
			sendMessage(msfa, oos);
		}
		
	}

	private void handleLoginRequest(MessageLoginReq msgLogin,
			ObjectOutputStream oos) {
		boolean verifyPassed = false;
		User user = XSQJDatabase.userQquery(msgLogin.getUsername(),
				msgLogin.getAccount());

		MessageLoginAck msgLoginAck = new MessageLoginAck(msgLogin.getAccount());
		if (user != null) {
			if (msgLogin.verify(user.getUsername(), user.getPwd())) {
				verifyPassed = true;
				msgLoginAck.setFailReason("�û���Ϣ��ȷ��");
				msgLoginAck.setUser(user);
			} else {
				msgLoginAck.setFailReason("�û���Ϣ����ȷ��");

			}
		}
		System.out.println("Account: " + msgLogin.getAccount()
				+ " Verification " + verifyPassed);

		msgLoginAck.setLoginResult(verifyPassed);

		sendMessage(msgLoginAck, oos);
	}
	private void handleStudentQuery(MessageStudentQuery msgStudentQuery,ObjectOutputStream oos)
	{
		
		Student s=msgStudentQuery.getStudent();
		Student s2 = XSQJDatabase.seachRecord(s.getUsername(), "username");
//		System.out.println(s.getStuNum());
		MessageStudentQueryAck msgAck=new MessageStudentQueryAck();
		msgAck.setStatepass(s2.getState().equals("δ��׼")?false:true);
		if(!msgAck.isStatepass())
		{
			try {
				
				XSQJDatabase.addRecord(s);
				msgAck.setStudent(s);
				msgAck.setSturepeat(false);
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				msgAck.setSturepeat(true);
				
			}
		}
		sendMessage(msgAck, oos);
	}
	private void handleStudentSearch(MessageStudentSearch msgStudentSearch,ObjectOutputStream oos)
	{
		Student s = XSQJDatabase.seachRecord(msgStudentSearch.getStu(), "stuNum");
		MessageStudentQuery msgsAck = new MessageStudentQuery(s,MSG_TYPE.MSG_STUDENT_SEARCH_ACK);
		sendMessage(msgsAck, oos);
	}
	private void handleStudentDelete(MessageStudentQuery msgReceived,
			ObjectOutputStream oos) {
		// TODO �Զ����ɵķ������
		XSQJDatabase.removeRecord(msgReceived.getStudent());
		MessageStudentDeleteAck msda=new MessageStudentDeleteAck();
		msda.setSuccess(true);
		sendMessage(msda, oos);
	}
	private boolean sendMessage(Message msg, ObjectOutputStream oos) {
		try {
			oos.writeObject(msg);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	private void closeClient(Socket socket) {
		if (socket != null) {
			try {
				socket.shutdownOutput();
				socket.shutdownInput();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void run() {
		// TODO �Զ����ɵķ������	
		handleClient(socket);
		closeClient(socket);	
	}

}
