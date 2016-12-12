package client.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import info.Student;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import messages.Message.MSG_TYPE;
import messages.MessageStudentDeleteAck;
import messages.MessageStudentQuery;
import messages.MessageStudentQueryAck;
import messages.MessageStudentSearch;
import org.jvnet.substance.*;
import org.jvnet.substance.skin.*;
import org.jvnet.substance.watermark.*;

class MainPanel extends JPanel implements ActionListener{
	private JLabel name, stuNum, sex, result;
	private JLabel search_name, search_stuNum;
	private JTextField jt1, jt2, jt3, jt4;
	private JComboBox comboBox_1, comboBox_2;
	private ButtonGroup buttonGroup;
	private static final String[] JComboBoxccontent_Specialty_d = { "专业",
			"计算机", "电子", "通信", "电气", "汉语言", "新闻" };
	private static final String[] JComboBoxccontent_Department = { "系别", "电软",
			"文传" };
	private JRadioButton radioButton1;
	private JRadioButton radioButton2;
	private JTextArea ja;
	private JButton submit, search, cancel;
	private JPanel[] JpGroup_l = new JPanel[6], JpGroup_r = new JPanel[3];
	private JLabel[] search_l = new JLabel[12];
	private Student student;

	// private static Vector<Student> result_student=new Vector<Student>();
	public MainPanel(Student student) {
		this.student = student;
		JPanel left_J = new JPanel();
		left_J.setLayout(new GridLayout(6, 1, 0, 0));
		JPanel right_J = new JPanel();
		right_J.setLayout(null);

		name = new JLabel("姓名");
		name.setBounds(122, 32, 35, 25);
		stuNum = new JLabel("学号");
		stuNum.setBounds(122, 32, 35, 25);
		sex = new JLabel("性别");
		sex.setBounds(138, 32, 35, 25);
		result = new JLabel("请假原因");
		result.setBounds(167, 0, 65, 25);
		jt1 = new JTextField(10);
		jt1.setBounds(157, 32, 120, 25);
		jt2 = new JTextField(10);
		jt2.setBounds(157, 32, 120, 25);
		comboBox_1 = new JComboBox(JComboBoxccontent_Department);
		comboBox_1.setBounds(135, 32, 60, 25);
		comboBox_2 = new JComboBox(JComboBoxccontent_Specialty_d);
		comboBox_2.setBounds(195, 32, 70, 25);
		radioButton1 = new JRadioButton("男", false);
		radioButton1.setBounds(173, 32, 45, 25);
		radioButton2 = new JRadioButton("女", false);
		radioButton2.setBounds(218, 32, 45, 25);
		buttonGroup = new ButtonGroup();
		buttonGroup.add(radioButton1);
		buttonGroup.add(radioButton2);
		ja = new JTextArea();
		ja.setAutoscrolls(true);
		ja.setLineWrap(true);
		JScrollPane jsp = new JScrollPane(ja);
		jsp.setBounds(0, 25, 390, 65);
		submit = new JButton("提    交");
		submit.setBounds(135, 20, 120, 25);

		for (int i = 0; i < JpGroup_l.length; i++) {
			JpGroup_l[i] = new JPanel();
			JpGroup_l[i].setBorder(BorderFactory.createEtchedBorder());
			JpGroup_l[i].setLayout(null);
		}

		JpGroup_l[0].add(name);
		JpGroup_l[0].add(jt1);
		JpGroup_l[1].add(stuNum);
		JpGroup_l[1].add(jt2);
		JpGroup_l[2].add(comboBox_1);
		JpGroup_l[2].add(comboBox_2);
		JpGroup_l[3].add(sex);
		JpGroup_l[3].add(radioButton1);
		JpGroup_l[3].add(radioButton2);
		JpGroup_l[4].add(result);
		JpGroup_l[4].add(jsp);
		JpGroup_l[5].add(submit);

		for (int i = 0; i < JpGroup_l.length; i++)
			left_J.add(JpGroup_l[i]);

		search_name = new JLabel("请输入姓名");
		search_name.setBounds(104, 27, 68, 25);
		search_stuNum = new JLabel("请输入学号");
		search_stuNum.setBounds(104, 72, 68, 25);
		jt3 = new JTextField(10);
		jt3.setBounds(175, 27, 120, 25);
		jt4 = new JTextField(10);
		jt4.setBounds(175, 72, 120, 25);
		search = new JButton("查    询");
		search.setBounds(135, 127, 120, 25);
		cancel = new JButton("取 消 请 假");
		cancel.setBounds(135, 5, 120, 25);

		for (int i = 0; i < JpGroup_r.length; i++) {
			JpGroup_r[i] = new JPanel();
			JpGroup_r[i].setBorder(BorderFactory.createEtchedBorder());
		}
		JpGroup_r[0].setLayout(null);
		JpGroup_r[1].setLayout(new GridLayout(6, 2, 0, 0));
		JpGroup_r[2].setLayout(null);

		JpGroup_r[0].add(search_name);
		JpGroup_r[0].add(jt3);
		JpGroup_r[0].add(search_stuNum);
		JpGroup_r[0].add(jt4);
		JpGroup_r[0].add(search);

		for (int i = 0; i < search_l.length; i++)
			search_l[i] = new JLabel();

		search_l[0].setText("姓名");
		search_l[2].setText("学号");
		search_l[4].setText("专业");
		search_l[6].setText("性别");
		search_l[8].setText("请假原因");
		search_l[10].setText("批准状态");

		for (int i = 0; i < search_l.length; i++)
			JpGroup_r[1].add(search_l[i]);

		JpGroup_r[2].add(cancel);

		JpGroup_r[0].setBounds(0, 0, 399, 174);
		JpGroup_r[1].setBounds(0, 174, 399, 324);
		JpGroup_r[2].setBounds(0, 498, 399, 40);

		for (int i = 0; i < JpGroup_r.length; i++)
			right_J.add(JpGroup_r[i]);

		JTabbedPane tab = new JTabbedPane(JTabbedPane.TOP);
		tab.addTab("请假面板", left_J);
		tab.addTab("查询面板", right_J);
		tab.setPreferredSize(new Dimension(400, 600));
		add(tab);
		this.setEnabled(true);
		submit.addActionListener(this);
		search.addActionListener(this);
		cancel.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
			String[] content = { jt1.getText(), jt2.getText(), ja.getText(),
					comboBox_1.getSelectedItem().toString(),
					comboBox_2.getSelectedItem().toString() };
			if (content[0].equals(""))
				JOptionPane.showMessageDialog(this, "姓名输入为空！");
			else if (content[1].equals(""))
				JOptionPane.showMessageDialog(this, "学号输入为空！");
			else if ((content[3].equals("系别") || content[4].equals("专业")))
				JOptionPane.showMessageDialog(this, "请正确选择系别专业！");
			else if (!(radioButton1.isSelected() || radioButton2.isSelected()))
				JOptionPane.showMessageDialog(this, "请选择性别！");
			else if (content[2].equals(""))
				JOptionPane.showMessageDialog(this, "请输入请假原因！");
			else {
				student.setName(content[0]);
				student.setStuNum(content[1]);
				student.setMajor(comboBox_1.getSelectedItem().toString() + "/"
						+ comboBox_2.getSelectedItem().toString());
				if (radioButton1.isSelected())
					student.setSex(radioButton1.getText().toString());
				else if (radioButton2.isSelected())
					student.setSex(radioButton2.getText().toString());
				student.setReason(content[2]);
				
				MessageStudentQuery msgSnt = new MessageStudentQuery(student,
						MSG_TYPE.MSG_STUDENT_QUERY);
				
				if (loginGUI.sendMessage(msgSnt)) {
					MessageStudentQueryAck msgAck = (MessageStudentQueryAck) loginGUI
							.receiveMessage();
					if (msgAck.isStatepass())
						JOptionPane.showMessageDialog(this, "已经过审核！");
					else {
						if (!msgAck.isSturepeat())
							JOptionPane.showMessageDialog(this, "提交成功！");
						else
							JOptionPane.showMessageDialog(this, "提交失败,学号重复！");

					}
				}
			}
		}
		if (e.getSource() == search || e.getSource() == cancel) {
			MessageStudentSearch msgsnts = new MessageStudentSearch(
					jt4.getText());
			if (loginGUI.sendMessage(msgsnts)) {
				MessageStudentQuery msgsAck = (MessageStudentQuery) loginGUI
						.receiveMessage();
				Student s = msgsAck.getStudent();
				if (e.getSource() == search) {

					if (s != null) {
						if (s.getName().equals(jt3.getText())) {
							search_l[1].setText(s.getName());
							search_l[3].setText(s.getStuNum());
							search_l[5].setText(s.getMajor());
							search_l[7].setText(s.getSex());
							search_l[9].setText(s.getReason());
							search_l[11].setText(s.getState());
						} else
							JOptionPane.showMessageDialog(this, "姓名输入有误！");
					} else
						JOptionPane.showMessageDialog(this, "学号输入有误！");
				}
				if (e.getSource() == cancel) {
					if (s != null) {
						MessageStudentQuery msgSnt = new MessageStudentQuery(s, 
								MSG_TYPE.MSG_STUDENT_DELETE);
						// XSQJDatabase.removeRecord(s);
						if (loginGUI.sendMessage(msgSnt)) {
							MessageStudentDeleteAck msda = (MessageStudentDeleteAck) loginGUI
									.receiveMessage();
							if (msda.isSuccess()) {
								for (int i = 1; i < search_l.length; i += 2)
									search_l[i].setText(null);
								JOptionPane.showMessageDialog(this, "取消成功！");
							}
						}
					} else
						JOptionPane.showMessageDialog(this, "没有指定！");
				}
			}
		}
	}

}

public class StudentGUI extends JFrame {
	private Student student;
	private Container contentPane = getContentPane();

	public StudentGUI(Student student) {

		this.student = student;
		// TODO 自动生成的构造函数存根
		contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		MainPanel centerPanel = new MainPanel(student);
		contentPane.add(centerPanel, BorderLayout.CENTER);
		this.setTitle("请假界面");
		this.setBounds(100, 100, 400, 600);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
	}

	public static void JFstyle() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame.setDefaultLookAndFeelDecorated(true);
				JDialog.setDefaultLookAndFeelDecorated(true);
				try {
					UIManager
							.setLookAndFeel(new SubstanceMagmaLookAndFeel());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
			}
		});
	}

}
