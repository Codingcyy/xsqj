package messages;

import java.io.Serializable;

import info.Student;

public class MessageStudentQuery extends Message implements Serializable{
	private Student student;

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public MessageStudentQuery(Student student,MSG_TYPE mt) {
		super(mt);
		this.student = student;

	}
//	public MessageStudentQuery(Student student,MSG_TYPE mt) {
//		super(mt);
//		this.student = student;
//		
//	}

}
