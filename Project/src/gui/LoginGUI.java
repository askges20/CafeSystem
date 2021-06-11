package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cafe.Customer;
import cafe.CustomerLoginAccount;
import cafe.Staff;
import cafe.User;
import staffgui.CustomerListGUI;
import staffgui.StaffMainGUI;

public class LoginGUI extends JPanel {	//로그인 화면
	public static User user;
	CustomerLoginAccount account;
	
	public LoginGUI() {
		
		setLayout(null);
		setSize(500, 800);
		setLocation(0, 0);
		setBackground(BackGroundFrameGUI.backGroundColor);
		setVisible(true);

		JLabel cafename = new JLabel("Cafe JAVA CHIP FRAPPUCCINO");
		cafename.setBounds(0, 100, 500, 50);
		cafename.setHorizontalAlignment(JLabel.CENTER);
		cafename.setFont(new Font("고딕", Font.BOLD, 25));
		cafename.setForeground(Color.WHITE);
		add(cafename);

		JLabel idLabel = new JLabel("ID");
		idLabel.setBounds(100, 270, 100, 50);
		idLabel.setFont(new Font("고딕", Font.PLAIN, 20));
		add(idLabel);

		JLabel passwdLabel = new JLabel("Password");
		passwdLabel.setBounds(100, 330, 100, 50);
		passwdLabel.setFont(new Font("고딕", Font.PLAIN, 20));
		add(passwdLabel);

		JTextField idArea = new JTextField();
		idArea.setBounds(200, 280, 200, 30);
		add(idArea);

		JTextField passwdArea = new JTextField();
		passwdArea.setBounds(200, 340, 200, 30);
		add(passwdArea);

		JButton loginButton = new JButton("로그인"); //User이면 UserMainGUI로 이동, 관리자면 ManagerMainGUI로 이동
		loginButton.setBounds(120, 430, 100, 40);
		loginButton.setFont(new Font("고딕", Font.BOLD, 15));
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(loginInfoCheckCustomer(idArea.getText(),passwdArea.getText())!=null) {	//고객 정보와 일치
					user = loginInfoCheckCustomer(idArea.getText(),passwdArea.getText());
					account = CustomerLoginAccount.getAccount();
					account.setCustomer((Customer) user);	//로그인 계정으로 설정
					StartGUI.cafe.bothSatisfiedDrink.clear();//기존 추천메뉴 삭제
					StartGUI.cafe.categorySatisfiedDrink.clear();
					StartGUI.cafe.hashTagSatisfiedDrink.clear();
					StartGUI.cafe.preferCategory.clear();
					StartGUI.cafe.preferHashTag.clear();
					StartGUI.cafe.recommendation(account.customer.id);
					removeAll();
					add(new UserMainGUI());	//고객 메인 화면으로 이동
					revalidate();
					repaint();
				}else if(loginInfoCheckStaff(idArea.getText(),passwdArea.getText())!=null) {	//직원 정보와 일치
					user = loginInfoCheckStaff(idArea.getText(),passwdArea.getText());
					removeAll();
					add(new StaffMainGUI());	//관리자 메인 화면으로 이동할 예정, 현재는 고객 목록 페이지로 이동함
					revalidate();
					repaint();
				}else {
					JOptionPane.showMessageDialog(null, "해당 가입정보가 존재하지 않습니다!");
				}
			}
		});
		add(loginButton);

		JButton registerButton = new JButton("회원가입");
		registerButton.setBounds(250, 430, 100, 40);
		registerButton.setFont(new Font("고딕", Font.BOLD, 15));
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				add(new RegisterGUI());
				revalidate();
				repaint();
			}
		});
		add(registerButton);
		
		ImageIcon menuImageIcon = new ImageIcon("image/logo.png");
		Image img = menuImageIcon.getImage();
		Image resizeImg = img.getScaledInstance(350, 250, Image.SCALE_SMOOTH);
		ImageIcon resizingMenuImg = new ImageIcon(resizeImg);
		JLabel imageLabel = new JLabel(resizingMenuImg);
		imageLabel.setBounds(70, 500, 350, 250);
		add(imageLabel);
		
		JLabel recomdpan = new JLabel();
		recomdpan.setBounds(20, 230, 450, 280);
		recomdpan.setBackground(BackGroundFrameGUI.topBarColor);
		recomdpan.setOpaque(true);
		add(recomdpan);
	}
	
	public User loginInfoCheckCustomer(String id, String pw) {
		for(int i=0 ; i<StartGUI.cafe.customerMgr.mList.size();i++) {
			Customer customer = (Customer)StartGUI.cafe.customerMgr.mList.get(i);
			if(customer.id.contentEquals(id)&&customer.pw.contentEquals(pw))
				return customer;
		}
		return null;
	}
	
	public User loginInfoCheckStaff(String id, String pw) {
		for(int i=0 ; i<StartGUI.cafe.staffMgr.mList.size();i++) {
			Staff staff = (Staff)StartGUI.cafe.staffMgr.mList.get(i);
			if(staff.id.contentEquals(id)&&staff.pw.contentEquals(pw))
				return staff;
		}
		return null;
	}
	
}