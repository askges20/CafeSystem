package staffgui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import cafe.Cafe;
import cafe.CustomerLoginAccount;
import gui.BackGroundFrameGUI;
import gui.LoginGUI;

public class StaffMainGUI extends JPanel {	//관리자 메인 화면
	static Cafe cafe = new Cafe();

	int[] locationX = { 10, 10, 10 };
	int[] locationY = { 150, 350, 550 };

	public StaffMainGUI() {
		setLayout(null);
		setSize(500, 800);
		setLocation(0, 0);
		setBackground(BackGroundFrameGUI.backGroundColor);
		setVisible(true);

		JLabel topBar = new JLabel("Cafe JAVA CHIP FRAPPUCCINO");
		topBar.setFont(new Font("고딕", Font.BOLD, 25));
		topBar.setBounds(0, 0, 500, 50);
		topBar.setHorizontalAlignment(JLabel.CENTER);
		topBar.setBackground(BackGroundFrameGUI.topBarColor);
		topBar.setOpaque(true);
		topBar.setFont(new Font("고딕", Font.BOLD, 20));
		add(topBar);

		JLabel label = new JLabel("[Staff] " + LoginGUI.user.name + "님 안녕하세요");
		label.setBounds(50, 100, 200, 20);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("고딕", Font.BOLD, 15));
		add(label);

		JButton logoutButton = new JButton("로그아웃");
		logoutButton.setBounds(345, 100, 100, 30);
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logout(); // 로그인 계정 해제
				removeAll();
				add(new LoginGUI());
				revalidate();
				repaint();
			}
		});
		add(logoutButton);
		
		JTextArea staffFuncInfo = new JTextArea();
		staffFuncInfo.setFont(new Font("고딕", Font.BOLD, 15));
		staffFuncInfo.setBounds(50, 200, 300, 80);
		staffFuncInfo.setEditable(false);
		staffFuncInfo.setLineWrap(true);
		staffFuncInfo.setText("현재 제공되는 관리자 기능 : \n-카페 앱 이용 고객 정보 확인\n-음료/디저트 메뉴 정보 추가 및 수정");
		add(staffFuncInfo);
		
		JButton customerInfoButton = new JButton("고객 정보 확인");
		customerInfoButton.setBounds(100, 300, 280, 100);
		customerInfoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeAll();
				revalidate();
				repaint();
				add(new CustomerListGUI());
			}
		});
		add(customerInfoButton);
		
		JButton addMenuButton = new JButton("음료/디저트 정보 추가");
		addMenuButton.setBounds(100, 425, 280, 100);
		addMenuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeAll();
				revalidate();
				repaint();
				add(new AddMenuGUI());
			}
		});
		add(addMenuButton);
		
		JButton modifyMenuButton = new JButton("음료/디저트 정보 수정");
		modifyMenuButton.setBounds(100, 550, 280, 100);
		modifyMenuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeAll();
				revalidate();
				repaint();
				add(new SearchMenuGUI("메뉴 이름"));
			}
		});
		add(modifyMenuButton);

		JLabel backgroundLabel = new JLabel();
		backgroundLabel.setBounds(20, 170, 440, 550);
		backgroundLabel.setBackground(Color.white);
		backgroundLabel.setOpaque(true);
		add(backgroundLabel);

	}

	private void logout() {
		CustomerLoginAccount account = CustomerLoginAccount.getAccount();
		account.setCustomer(null);
		account.clearCart();
	}

}