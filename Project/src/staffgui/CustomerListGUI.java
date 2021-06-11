package staffgui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cafe.*;
import gui.*;

public class CustomerListGUI extends JPanel {	//고객 목록

	static Color backGroundColor = new Color(0xFFDAB9);
	static Color topBarColor = new Color(0xFFDA55);
	static Cafe cafe = new Cafe();

	int page = 0;

	public CustomerListGUI() {
		paintGUI();
	}

	public void paintGUI() {
		setLayout(null);
		setSize(500, 800);
		setLocation(0, 0);
		setBackground(BackGroundFrameGUI.backGroundColor);

		// 상단바
		JLabel topBar = new JLabel("고객 목록");
		topBar.setBounds(0, 0, 500, 50);
		topBar.setHorizontalAlignment(JLabel.CENTER);
		topBar.setBackground(topBarColor);
		topBar.setOpaque(true);
		topBar.setFont(new Font("고딕", Font.BOLD, 20));
		add(topBar);

		JButton moveMainButton = new JButton("메인 이동");
		moveMainButton.setBounds(20, 660, 90, 30);
		moveMainButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				revalidate();
				repaint();
				StaffMainGUI move = new StaffMainGUI();
				add(move);
			}
		});
		add(moveMainButton);

		// 이전 페이지 이동 버튼
		JButton PrevButton = new JButton("<<Prev");
		PrevButton.setBounds(140, 700, 100, 40);
		PrevButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (page > 0) {
					page -= 1;
					removeAll();
					revalidate();
					repaint();
					paintGUI();
				}
			}
		});
		add(PrevButton);

		// 다음 페이지 이동 버튼
		JButton NextButton = new JButton("Next>>");
		NextButton.setBounds(260, 700, 100, 40);
		NextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (StartGUI.cafe.menudrinkMgr.mList.size() / 6 > page) {
					page += 1;
					removeAll();
					revalidate();
					repaint();
					paintGUI();
				}
			}
		});
		add(NextButton);

		
		JButton[] showOrderBtn = new JButton[10];	//주문 내역 페이지로 이동하는 버튼
		int labelY = 70;
		int lineHeight = 60;

		JLabel numLabel = new JLabel("번호");
		numLabel.setBounds(30, labelY, 30, lineHeight);
		add(numLabel);

		JLabel idLabel = new JLabel("아이디");
		idLabel.setBounds(60, labelY, 100, lineHeight);
		idLabel.setHorizontalAlignment(JLabel.CENTER);
		add(idLabel);

		JLabel pwLabel = new JLabel("비밀번호");
		pwLabel.setBounds(150, labelY, 100, lineHeight);
		pwLabel.setHorizontalAlignment(JLabel.CENTER);
		add(pwLabel);

		JLabel nameLabel = new JLabel("이름");
		nameLabel.setBounds(250, labelY, 50, lineHeight);
		nameLabel.setHorizontalAlignment(JLabel.CENTER);
		add(nameLabel);

		JLabel allergyLabel = new JLabel("알레르기");
		allergyLabel.setBounds(300, labelY, 80, lineHeight);
		allergyLabel.setHorizontalAlignment(JLabel.CENTER);
		add(allergyLabel);
		
		JLabel orderLabel = new JLabel("주문내역");
		orderLabel.setBounds(380,labelY,70,lineHeight);
		orderLabel.setHorizontalAlignment(JLabel.CENTER);
		add(orderLabel);

		for (int i = 0; i < 10; i++) {
			int index = page * 10 + i;
			int cusY = 120 + i * 50;

			if (index < StartGUI.cafe.customerMgr.mList.size()) {
				Customer customer = (Customer) StartGUI.cafe.customerMgr.mList.get(page * 10 + i);

				JLabel cusNum = new JLabel((index + 1) + ""); // 번호
				cusNum.setBounds(40, cusY, 20, lineHeight);
				add(cusNum);

				JLabel cusId = new JLabel(customer.id); // 아이디
				cusId.setBounds(60, cusY, 100, lineHeight);
				cusId.setHorizontalAlignment(JLabel.CENTER);
				add(cusId);

				JLabel cusPw = new JLabel(customer.pw); // 비밀번호
				cusPw.setBounds(150, cusY, 100, lineHeight);
				cusPw.setHorizontalAlignment(JLabel.CENTER);
				// cusPw.setBackground(Color.pink);
				// cusPw.setOpaque(true);
				add(cusPw);

				JLabel cusName = new JLabel(customer.name); // 이름
				cusName.setBounds(250, cusY, 50, lineHeight);
				cusName.setHorizontalAlignment(JLabel.CENTER);
				add(cusName);

				JLabel cusAllergy = new JLabel(""); // 알레르기
				for (String a : customer.allergyList) {
					cusAllergy.setText(cusAllergy.getText() + " " + a);
				}
				if (cusAllergy.getText().equals("")) // 알레르기 없을 때
					cusAllergy.setText("-");
				cusAllergy.setBounds(300, cusY, 80, lineHeight);
				cusAllergy.setHorizontalAlignment(JLabel.CENTER);
				add(cusAllergy);
				
				
				showOrderBtn[i] = new JButton("확인");
				showOrderBtn[i].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						removeAll();
						revalidate();
						repaint();
						Customer customer = (Customer)StartGUI.cafe.customerMgr.mList.get(index);
						add(new CustomerOrderGUI(customer));
					}
				});
				showOrderBtn[i].setBounds(390,cusY+15,60,30);
				showOrderBtn[i].setHorizontalAlignment(JLabel.CENTER);
				add(showOrderBtn[i]);
			}
		}

		
		
		JLabel backgroundLabel = new JLabel();
		backgroundLabel.setBounds(20, 70, 440, 550);
		backgroundLabel.setBackground(Color.white);
		backgroundLabel.setOpaque(true);
		add(backgroundLabel);

		// 현재 페이지 번호
		JLabel pageLabel = new JLabel("page : " + (page + 1));
		pageLabel.setBounds(200, 660, 100, 40);
		pageLabel.setHorizontalAlignment(JLabel.CENTER);
		add(pageLabel);

		setVisible(true);
	}

}
