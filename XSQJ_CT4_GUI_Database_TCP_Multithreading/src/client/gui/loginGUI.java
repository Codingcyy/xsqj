package client.gui;

import info.Admin;
import info.Student;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.SQLException;
import javax.swing.*;
import messages.Message;
import messages.MessageLoginAck;
import messages.MessageLoginReq;

public class loginGUI extends JFrame implements ActionListener {

	private JComboBox IDJComboBox;
	private JPanel userJPanel_1, userJPanel_2, userJPanel_3;
	private JLabel pictureJLabel;
	private JButton okJButton;
	private JLabel IDJLabel, passwordJLabel, note, nameJLabel;
	private JTextField username;
	private JPasswordField passwordJPasswordField;
	private JCheckBox jcb_1;
	private JCheckBox jcb_2;
	private String name, password, ID;
	private static final String[] JComboBoxccontent = { "admin", "user" };
	private String serverIP = "127.0.0.1";
	private int serverPort = 54321;
	private boolean isConnected = false;
	private Socket socket = null;
	private OutputStream os = null;
	private InputStream is = null;
	private static ObjectInputStream ois = null;
	private static ObjectOutputStream oos = null;
	public loginGUI() {
		createUserInterface(); // 调用创建用户界面方法
		connectToServer();
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				disconnectFromServer();
				System.exit(0);
			}
		});
	}

	private void createUserInterface() {
		Container contentPane = getContentPane();
		contentPane.setLayout(null);

		userJPanel_1 = new JPanel();
		userJPanel_1.setBounds(209, 0, 284, 118);
		userJPanel_1.setLayout(new BorderLayout());
		userJPanel_1.setBackground(null);
		userJPanel_1.setOpaque(false);
		contentPane.add(userJPanel_1);

		userJPanel_2 = new JPanel();
		userJPanel_2.setBounds(209, 118, 284, 178);
		userJPanel_2.setLayout(null);
		userJPanel_2.setBackground(null);
		userJPanel_2.setOpaque(false);
		contentPane.add(userJPanel_2);

		userJPanel_3 = new JPanel();
		userJPanel_3.setBounds(209, 296, 284, 100);
		userJPanel_3.setLayout(null);
		userJPanel_3.setBackground(null);
		userJPanel_3.setOpaque(false);
		contentPane.add(userJPanel_3);

		IDJLabel = new JLabel("用户身份  ", JLabel.CENTER);
		IDJLabel.setBounds(30, 12, 80, 25);
		IDJLabel.setForeground(Color.blue);
		IDJLabel.setFont(new Font("Dialog", 0, 15));
		userJPanel_2.add(IDJLabel);

		IDJComboBox = new JComboBox(JComboBoxccontent);
		IDJComboBox.setBounds(100, 12, 170, 25);
		userJPanel_2.add(IDJComboBox);

		pictureJLabel = new JLabel();
		pictureJLabel.setBounds(0, 0, 702, 386);
		set_Picture("./image/bg_1.jpg");
		contentPane.add(pictureJLabel);

		passwordJLabel = new JLabel(" 密   码  ", JLabel.CENTER);
		passwordJLabel.setBounds(30, 109, 80, 25);
		passwordJLabel.setForeground(Color.blue);
		passwordJLabel.setFont(new Font("Dialog", 0, 15));
		userJPanel_2.add(passwordJLabel);

		passwordJPasswordField = new JPasswordField();
		passwordJPasswordField.setBounds(100, 109, 170, 25);
		userJPanel_2.add(passwordJPasswordField);

		nameJLabel = new JLabel("用 户 名  ", JLabel.CENTER);
		nameJLabel.setBounds(30, 60, 80, 25);
		nameJLabel.setForeground(Color.blue);
		nameJLabel.setFont(new Font("Dialog", 0, 15));
		userJPanel_2.add(nameJLabel);

		username = new JTextField();
		username.setBounds(100, 60, 170, 25);
		userJPanel_2.add(username);

		jcb_1 = new JCheckBox("保存密码");
		jcb_1.setBounds(20, 155, 100, 20);
		jcb_1.setBackground(null);
		jcb_1.setOpaque(false);
		jcb_1.setForeground(Color.blue);
		jcb_1.setFont(new Font("Dialog", 0, 13));

		jcb_2 = new JCheckBox("自动登录");
		jcb_2.setBounds(180, 155, 100, 20);
		jcb_2.setBackground(null);
		jcb_2.setOpaque(false);
		jcb_2.setForeground(Color.blue);
		jcb_2.setFont(new Font("Dialog", 0, 13));

		userJPanel_2.add(jcb_1);
		userJPanel_2.add(jcb_2);

		note = new JLabel("登录系统", JLabel.CENTER);
		note.setSize(180, 25);
		note.setForeground(Color.BLUE);
		note.setFont(new Font(Font.DIALOG, Font.BOLD, 40));
		userJPanel_1.add(note, "Center");

		okJButton = new JButton(new ImageIcon("./image/ok.png"));
		okJButton.setBounds(60, 12, 195, 29);
		userJPanel_3.add(okJButton);
		okJButton.addActionListener(this);
		passwordJPasswordField.addActionListener(this);
		setTitle("学生请假管理系统");
		setSize(702, 386);
		setResizable(false); // 将最大化按钮设置为不可用
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	private boolean connectToServer() {
		// Socket尚未初始化，在端口打开连接，取出对象输入输出流
		try {
			socket = new Socket(serverIP, serverPort);
			socket.setSoTimeout(3000); // 如果服务器没有反映，尝试3000毫秒
			os = socket.getOutputStream();
			is = socket.getInputStream();
			oos = new ObjectOutputStream(os);
			ois = new ObjectInputStream(is);

			isConnected = true;
		} catch (IOException e) {
			isConnected = false;
		} finally {
			
		}

		return isConnected;
	}
	  private void disconnectFromServer() {
		if (socket != null) {
			try {
				socket.shutdownOutput();
				socket.shutdownInput();
				socket.close();
				socket = null;
				oos = null;
				ois = null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void set_Picture(String s) {

		ImageIcon imageIcon1 = new ImageIcon(s);
		Image image1 = imageIcon1.getImage();
		Image smallImage1 = image1
				.getScaledInstance(702, 386, Image.SCALE_FAST);
		pictureJLabel.setIcon(new ImageIcon(smallImage1));
	}
	static synchronized boolean sendMessage(Message msg) {
		try {
			
			oos.writeObject(msg);
	
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	static synchronized Message receiveMessage() {
		try {
			return (Message) ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Student verifyAccount_1(String account, String password, String ID)
			throws SQLException {
		if (CheckLogin(account, password, ID)) {
			
			Student student = new Student();
			student.setUsername(account);
			student.setPwd(password);
			return student;
		}
		return null;
	}

	private Admin verifyAccount_2(String account, String password, String ID)
			throws SQLException {
		
		if (CheckLogin(account, password, ID)) {
			Admin admin = new Admin();
			admin.setUsername(account);
			admin.setPwd(password);
			return admin;
		}
		return null;
	}
	
	private boolean CheckLogin(String username, String password, String ID){
		if (!isConnected) {
			int maxRetry = 10, j = 0;
			while (j++ < maxRetry) {
				if(connectToServer())
					break; // 连接成功
			}	
		}
		if (!isConnected){
			JOptionPane.showMessageDialog(this, "Failed to connect to server: " + serverIP);
			return false;
		}
		MessageLoginReq msgLoginReq = new MessageLoginReq(ID, password,username);
		MessageLoginAck msgLoginAck = null;
		try {
			oos.writeObject(msgLoginReq);			
			msgLoginAck = (MessageLoginAck) ois.readObject();;
		} catch ( ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException  e) {
			e.printStackTrace();
		}finally{	
		}	
		return msgLoginAck.getUser()!=null?true:false;
	}

	private void okJButtonActionPerformed()
			throws SQLException {
		// okJButton响应事件,检查用户名和密码的匹配
		name = username.getText().toString();
		password = passwordJPasswordField.getText();
		ID = IDJComboBox.getSelectedItem().toString();
		if (ID.equals("user")) {
			Student s = new Student();
			s = verifyAccount_1(name, password, ID);
			if (s != null) {

				JOptionPane.showMessageDialog(this, "登录成功，进入学生界面....");
				StudentGUI.JFstyle();
				new StudentGUI(s);
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(this, "密码或用户名错误，拒绝登陆", "输入错误 ！",
						JOptionPane.ERROR_MESSAGE);
				passwordJPasswordField.setText("");
			}
		} else if (ID.equals("admin")) {
			Admin s = new Admin();
			s = verifyAccount_2(name, password, ID);
			if (s != null) {

				JOptionPane.showMessageDialog(this, "登录成功，进入管理员界面....");
				StudentGUI.JFstyle();
				new AdminGUI(s);
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(this, "密码或用户名错误，拒绝登陆", "输入错误 ！",
						JOptionPane.ERROR_MESSAGE);
				passwordJPasswordField.setText("");
			}

		}
	}

	public static void JFstyle() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame.setDefaultLookAndFeelDecorated(true);
				JDialog.setDefaultLookAndFeelDecorated(true);
				try {
					UIManager
							.setLookAndFeel(new org.jvnet.substance.skin.SubstanceOfficeBlue2007LookAndFeel());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}  

	public static void main(String[] args) {
		JFstyle();
		new loginGUI();
		
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		try {
			if (e.getSource() == passwordJPasswordField||e.getSource() == okJButton) 
			okJButtonActionPerformed();	
		} catch (SQLException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
	}
}