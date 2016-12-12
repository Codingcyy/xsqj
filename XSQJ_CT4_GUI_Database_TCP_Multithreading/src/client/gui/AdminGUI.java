package client.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import info.Admin;
import info.Student;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import messages.MessageStudentFlushAck;
import messages.MessageStudentFlushReq;
import messages.MessageStudentOKAck;
import messages.MessageStudentOKReq;
import messages.MessageStudentQuery;
import messages.MessageStudentSearch;

import org.jvnet.substance.skin.SubstanceCremeCoffeeLookAndFeel;

public class AdminGUI extends JFrame implements ActionListener {
	private Container contentPane;
	private Admin admin;
	private JPanel body = null;
	private JButton flush = null, ok = null, search = null;
	private DefaultTableModel tableModelDefault = null;
	private JTextField jt3, jt4;
	private JComboBox status;
	private JTable table = null;
	private static final String[] JComboBoxccontent = { "不批准", "批准" };

	private void createTop() {
		JPanel topJpanel = new JPanel();
		topJpanel.setBounds(0, 0, 1022, 83);
		topJpanel.setLayout(null);
		topJpanel.setBorder(BorderFactory.createEtchedBorder());
		JLabel search_name = new JLabel("请输入姓名");
		search_name.setBounds(258, 33, 68, 25);
		JLabel search_stuNum = new JLabel("请输入学号");
		search_stuNum.setBounds(481, 33, 68, 25);
		jt3 = new JTextField(10);
		jt3.setBounds(326, 33, 120, 25);
		jt4 = new JTextField(10);
		jt4.setBounds(549, 33, 120, 25);
		search = new JButton("搜索");
		search.setBounds(704, 33, 60, 25);
		topJpanel.add(search_name);
		topJpanel.add(jt3);
		topJpanel.add(search_stuNum);
		topJpanel.add(jt4);
		topJpanel.add(search);
		body.add(topJpanel);
	}

	private void createResultTable() {
		Object[][] data = {};
		String[] name = { "姓名", "学号", "专业", "性别", "请假原因", "是否批准" };
		tableModelDefault = new DefaultTableModel(data, name);
		table = new JTable(tableModelDefault);
		JPanel mainJpane = new JPanel(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(table);
		mainJpane.setBounds(0, 83, 1022, 340);
		mainJpane.add(scrollPane, BorderLayout.CENTER);
		body.add(mainJpane);
	}

	private void createButton() {
		JPanel tailJbut = new JPanel();
		tailJbut.setBounds(0, 423, 1022, 83);
		tailJbut.setLayout(null);
		tailJbut.setBorder(BorderFactory.createEtchedBorder());
		flush = new JButton("刷新");
		ok = new JButton("确定");
		status = new JComboBox(JComboBoxccontent);
		status.setBounds(704, 33, 70, 25);
		flush.setBounds(389, 33, 120, 25);
		ok.setBounds(519, 33, 120, 25);
		tailJbut.add(status);
		tailJbut.add(flush);
		tailJbut.add(ok);
		body.add(tailJbut);
	}

	public AdminGUI(Admin admin) {
		this.admin = admin;
		// TODO 自动生成的构造函数存根
		contentPane = getContentPane();
		body = new JPanel(null);
		createTop();
		createResultTable();
		createButton();
		contentPane.add(body);
		flush.addActionListener(this);
		ok.addActionListener(this);
		search.addActionListener(this);
		this.setTitle("管理员界面");
		this.setSize(1028, 535);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		if (e.getSource() == flush) {
			int j = 0;
			Vector<Student> vst = null;
			MessageStudentFlushReq msf = new MessageStudentFlushReq(true);
			if (loginGUI.sendMessage(msf)) {
				MessageStudentFlushAck msfa = (MessageStudentFlushAck) loginGUI
						.receiveMessage();
				j = msfa.getSize();
				vst = msfa.getVst();
				if(msfa.isOK()){
				tableModelDefault.setRowCount(0);
				Student s = null;
				for (int i = 0; i < j; i++) {
					s = vst.get(i);
					try {
						if (!s.getName().equals(null)) {
							Object[] data = { s.getName(), s.getStuNum(),
									s.getMajor(), s.getSex(), s.getReason(),
									s.getState() };
							tableModelDefault.addRow(data);
						}
					} catch (Exception e1) {}
					
				}
			}
			}
		}
		if (e.getSource() == search) {
			MessageStudentSearch msgsnts = new MessageStudentSearch(
					jt4.getText());
			if (loginGUI.sendMessage(msgsnts)) {
				MessageStudentQuery msgsAck = (MessageStudentQuery) loginGUI
						.receiveMessage();
				Student s = msgsAck.getStudent();
				if (s != null) {
					if (s.getName().equals(jt3.getText())) {
						Object[] data = { s.getName(), s.getStuNum(),
								s.getMajor(), s.getSex(), s.getReason(),
								s.getState() };
						tableModelDefault.setRowCount(0);
						tableModelDefault.addRow(data);
					} else
						JOptionPane.showMessageDialog(this, "没有符合的记录！");
				} else
					JOptionPane.showMessageDialog(this, "没有符合的记录！");
			}
		}
		if (e.getSource() == ok) {
			int selectedRow = table.getSelectedRow();
			if (selectedRow < 0) {
				JOptionPane.showMessageDialog(this, "请选择学生记录（单选）");
			} else {
				String stuNum_value = table.getValueAt(selectedRow, 1)
						.toString();
				String status_value = status.getSelectedItem().toString();
				MessageStudentOKReq msor=new MessageStudentOKReq(stuNum_value, status_value);
				if(loginGUI.sendMessage(msor)){
					MessageStudentOKAck msoa=(MessageStudentOKAck)loginGUI.receiveMessage();
					if(msoa.isChangeOK())
						JOptionPane.showMessageDialog(this, "修改成功！");
				}
			}

		}

	}

}
