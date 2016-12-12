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
//					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");// Nimbus���
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		StudentGUI.JFstyle();
		new ServerAdmin();
	}

	// ����Ԫ��
	private JTextField fieldAcount = new JTextField(20);
	private JPasswordField fieldPwd = new JPasswordField(20);
	private JButton buttonEnable = new JButton("��������");
	private JButton buttonDisable = new JButton("�رշ���");

	// ���������Ϣ
	private ServerSocket serverSocket = null;
	private int serverPort = 54321;
	// ���񿪹�
	private boolean enableService = false;

    private ExecutorService executorService;//�̳߳�
    private final int POOL_SIZE=10;//����CPU�̳߳ش�С
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

		// ����ҳ�沼��
		this.setLayout(new GridLayout(3, 2, 0, 0));

		this.add(new JLabel("��  ��"));
		this.add(fieldAcount);
		this.add(new JLabel("��  ��"));
		this.add(fieldPwd);

		buttonDisable.setEnabled(false);
		this.add(buttonEnable);
		this.add(buttonDisable);

		buttonEnable.addActionListener(this);
		buttonDisable.addActionListener(this);
		fieldPwd.addActionListener(this);
		this.setVisible(true);

		// ����������TCP�˿ںͷ���
		enableService();

		// �û�����رշ���ť��ϵͳ�˳����ر�serverSocket
		disableService();
	}
 
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttonEnable || e.getSource() == fieldPwd) {
			String account = fieldAcount.getText();
			String password = new String(fieldPwd.getPassword());
			// �ж��û����������Ƿ���ȷ
			// ��½�ɹ����������û��ʺŴ��ڣ�������ȷ, ����ԱȨ��
			if (handleLogin(account, password)) {

				// ��ֹ��¼��ť��ʹ���˳���ť
				buttonEnable.setEnabled(false);
				buttonDisable.setEnabled(true);

				// ����ServerSocket
				enableService = true;
			} else {
				JOptionPane.showMessageDialog(this, "Invalid User");
				fieldPwd.setText("");
			}
		} else if (e.getSource() == buttonDisable) {
			// ϵͳ�˳�����ϵͳ�˳�
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
		// �ȴ���������
		while (!enableService) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
			}
		}
		// ���ŷ���˿�
		try {
			serverSocket = new ServerSocket(serverPort);
			executorService=Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*POOL_SIZE);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		while (enableService) {
			// �����˿����󣬵ȴ�����
			try {
				// ͨ���˿ڽ�������
				
				Socket socket = serverSocket.accept();
				
				// �յ��ͻ������ӣ������û�����
				System.out.println("Request received");	
				 executorService.execute(new MultiThreadConnection(socket));
				socket = null;
			} catch (SocketTimeoutException e) {
				// do nothing
			} catch (IOException e) {
				e.printStackTrace();
			} finally { // ֹͣ����
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