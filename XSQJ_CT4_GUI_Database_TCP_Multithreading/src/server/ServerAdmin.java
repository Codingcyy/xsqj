package server;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import client.gui.StudentGUI;



public class ServerAdmin extends JFrame implements ActionListener {

	public static void main(String[] args) {
//		try {
//			UIManager
//					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");// Nimbus风格
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		StudentGUI.JFstyle();
		new ServerAdmin();
	}

	// 界面元素
	private JTextField fieldAcount = new JTextField(20);
	private JPasswordField fieldPwd = new JPasswordField(20);
	private JButton buttonEnable = new JButton("开启服务");
	private JButton buttonDisable = new JButton("关闭服务");

	// 网络服务信息
	private ServerSocket serverSocket = null;
	private int serverPort = 54321;
	// 服务开关
	private boolean enableService = false;

    private ExecutorService executorService;//线程池
    private final int POOL_SIZE=10;//单个CPU线程池大小
	public ServerAdmin() {
		this.setTitle("Server");
		this.setLocation(300, 200);
		this.setSize(300, 120);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				disableService();
				System.exit(0);
			}
		});

		// 设置页面布局
		this.setLayout(new GridLayout(3, 2, 0, 0));

		this.add(new JLabel("帐  号"));
		this.add(fieldAcount);
		this.add(new JLabel("密  码"));
		this.add(fieldPwd);

		buttonDisable.setEnabled(false);
		this.add(buttonEnable);
		this.add(buttonDisable);

		buttonEnable.addActionListener(this);
		buttonDisable.addActionListener(this);
		fieldPwd.addActionListener(this);
		this.setVisible(true);

		// 启动服务器TCP端口和服务
		enableService();

		// 用户几点关闭服务按钮，系统退出，关闭serverSocket
		disableService();
	}
 
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttonEnable || e.getSource() == fieldPwd) {
			String account = fieldAcount.getText();
			String password = new String(fieldPwd.getPassword());
			// 判断用户名和密码是否正确
			// 登陆成功的条件：用户帐号存在，密码正确, 管理员权限
			if (handleLogin(account, password)) {

				// 禁止登录按钮，使能退出按钮
				buttonEnable.setEnabled(false);
				buttonDisable.setEnabled(true);

				// 启动ServerSocket
				enableService = true;
			} else {
				JOptionPane.showMessageDialog(this, "Invalid User");
				fieldPwd.setText("");
			}
		} else if (e.getSource() == buttonDisable) {
			// 系统退出服务，系统退出
			enableService = false;
			disableService();
			System.exit(0);
		}
	}

	private boolean handleLogin(String account, String password) {
		boolean a = account.equals("admin") && password.equals("admin");
		return a ? true : false;
	}

	public void enableService() {
		// 等待启动服务
		while (!enableService) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
			}
		}
		// 开放服务端口
		try {
			serverSocket = new ServerSocket(serverPort);
			executorService=Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*POOL_SIZE);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		while (enableService) {
			// 监听端口请求，等待连接
			try {
				// 通过端口建立连接
				
				Socket socket = serverSocket.accept();
				
				// 收到客户端连接，处理用户请求
				System.out.println("Request received");	
				 executorService.execute(new MultiThreadConnection(socket));
				socket = null;
			} catch (SocketTimeoutException e) {
				// do nothing
			} catch (IOException e) {
				e.printStackTrace();
			} finally { // 停止服务
			}
		}
	}

	private void disableService() {
		if (serverSocket != null) {
			try {
				serverSocket.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			serverSocket = null;
		}
	}

}