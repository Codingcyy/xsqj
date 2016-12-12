package server.Database;

import info.Student;
import info.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;


public class XSQJDatabase {
	private static Connection conn = null;
	private static Statement stmt = null;
	public static Vector<Student> Vst = null;

	public XSQJDatabase() {
		// TODO Auto-generated constructor stub

	}

	private static Connection getConnection() {
		if (conn != null) {
			return conn;
		}

		String driver_MySQL = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/XSQJ?useUmicode=true&characterEncoding=utf8";
		String account_MySQL = "root";
		String password_MySQL = "102417";

		try {
			Class.forName(driver_MySQL);
			conn = DriverManager.getConnection(url, account_MySQL,
					password_MySQL);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("找不到驱动！");
		}
		return conn;
	}

	private static String toSqlString(String str) {
		return new String(" '" + str + "' ");
	}

	private static ResultSet init(String sql) throws SQLException {
		stmt = getConnection().createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		return rs;
	}
//
	public static User userQquery(String username, String ID) {
		User u = null;
		try {
			ResultSet rs = null;
			if (ID.equals("user"))
				rs = init("select * from studentlist where username="
						+ toSqlString(username));
			else if (ID.equals("admin"))
				rs = init("select * from adminlist where username=" + toSqlString(username));
			

			if (rs.next() == true) {
				u = new User();
				u.setUsername(rs.getString(1));
				u.setPwd(rs.getString(2));
			}

		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		
		return u;

	}

	public static Student seachRecord(String value, String columns) {
		Student student = null;

		String sql = "select * from studentlist where " + columns + "="
				+ toSqlString(value);
		try {
			ResultSet rs = init(sql);
			if (rs.next() == true) {
				student = new Student();
				student.setName(rs.getString(3));
				student.setStuNum(rs.getString(4));
				student.setSex(rs.getString(5));
				student.setMajor(rs.getString(6));
				student.setReason(rs.getString(7));
				student.setState(rs.getString(8));
			}
		} catch (SQLException sqle) {
			System.out.println("查询数据出现异常: " + sqle.getMessage());
		}

		return student;
	}

	public static void changeStatus(String stuNum_value, String status_value)  {
		String sql = "UPDATE  studentlist set state ="
				+ toSqlString(status_value) + " where stuNum= "
				+ toSqlString(stuNum_value);
	
			try {
				stmt = getConnection().createStatement();
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
	

	}

	public static void removeRecord(Student s) {
		String sql = "UPDATE studentlist set name=null,stuNum=null,sex=null,major=null,reason=null,state="
				+ toSqlString("未批准")
				+ " where stuNum="
				+ toSqlString(s.getStuNum());
		try {
			stmt = getConnection().createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	public static void addRecord(Student s) throws SQLException {

		String sql = "UPDATE studentlist set name=" + toSqlString(s.getName())
				+ ",stuNum=" + toSqlString(s.getStuNum()) + ",sex="
				+ toSqlString(s.getSex()) + ",major="
				+ toSqlString(s.getMajor()) + ",reason="
				+ toSqlString(s.getReason()) + ",state="
				+ toSqlString(s.getState()) + "where username="
				+ toSqlString(s.getUsername());
		stmt = getConnection().createStatement();
		stmt.executeUpdate(sql);

	}

	public static Vector<Student> Searchall() {
		Vst = new Vector<Student>();
		Student student = null;
		String sql_1 = "select count(*) cou from studentlist";
		ResultSet rc;
		try {
			rc = init(sql_1);
			int count = 0;
			if (rc.next() == true)
				count = rc.getInt(1);
			for (int i = 0; i < count; i++) {
				String sql_2 = "select * from studentlist  limit " + i + ",1";
				ResultSet rs = init(sql_2);
				if (rs.next() == true) {
					student = new Student();
					student.setName(rs.getString(3));
					student.setStuNum(rs.getString(4));
					student.setSex(rs.getString(5));
					student.setMajor(rs.getString(6));
					student.setReason(rs.getString(7));
					student.setState(rs.getString(8));
					Vst.add(student);
				}
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return Vst;
	}
}
