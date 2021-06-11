package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;

import cafe.CustomerLoginAccount;
import cafe.Menu;
import cafe.Order;
import cafe.OrderedMenu;

public class OrderingGUI extends JPanel {	//주문하는 화면
	Order cart;
	int[] locationX = { 20, 20, 20 };
	int[] locationY = { 70, 270, 470 };
	int Imagewidth=150;
	int ImageHeight=160;

	public OrderingGUI(Order cart) {
		this.cart = cart;
		paintGUI();
	}

	public void paintGUI() {
		setLayout(null);
		setSize(500, 800);
		setLocation(0, 0);
		setBackground(BackGroundFrameGUI.backGroundColor);
		setVisible(true);

		JButton moveMainButton = new JButton("장바구니로 이동");
		moveMainButton.setBounds(20, 660, 140, 30);
		moveMainButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				revalidate();
				repaint();
				CartGUI cartGUI = new CartGUI();
				add(cartGUI);
			}
		});
		add(moveMainButton);

		JLabel topBar = new JLabel("주문 확인");
		topBar.setFont(new Font("고딕", Font.BOLD, 25));
		topBar.setBounds(0, 0, 500, 50);
		topBar.setHorizontalAlignment(JLabel.CENTER);
		topBar.setBackground(BackGroundFrameGUI.topBarColor);
		topBar.setOpaque(true);
		topBar.setFont(new Font("고딕", Font.BOLD, 20));
		add(topBar);

		JButton orderButton = new JButton("주문하기");
		orderButton.setBounds(0, 700, 500, 70);
		orderButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Date today = new Date();
				SimpleDateFormat dateForm = new SimpleDateFormat("yyyy/MM/dd");
				String todayDate = dateForm.format(today);
				cart.date = todayDate;	//오늘 날짜로 등록
				StartGUI.cafe.orderMgr.addToList(cart);
				try {	//데이터 파일도 업데이트
					StartGUI.cafe.orderMgr.rewriteFile(new FileWriter("order.txt"));
					JOptionPane.showMessageDialog(null, "주문이 완료되었습니다!");
					for(OrderedMenu om: cart.orderedMenuList) {
						om.updateAcc();	//주문 완료한 메뉴들도 매출량에 반영함
					}
					CustomerLoginAccount account = CustomerLoginAccount.getAccount();
					account.clearCart();//장바구니 비우기 (cart = new Order())
					account.setOrderCustomer();	//new Order()을 했기 떄문에 다시 cart의 customer을 설정해줌
				} catch (IOException e1) {
					e1.printStackTrace();
				}				
				removeAll();
				revalidate();
				repaint();
				UserMainGUI move = new UserMainGUI();	//메인 화면으로 이동
				add(move);
			}
		});
		add(orderButton);
		
		add(new Receipt(cart));
		
		JLabel backgroundLabel = new JLabel();
		backgroundLabel.setBounds(20, 70, 440, 550);
		backgroundLabel.setBackground(Color.white);
		backgroundLabel.setOpaque(true);
		add(backgroundLabel);
	}
	
	public class Receipt extends JPanel {

		public Receipt(Order cart) {
			ArrayList<OrderedMenu> om = cart.orderedMenuList;
			
			setLayout(new GridLayout(3 + om.size(), 3));
			setBounds(25, 150, 425, (1 + om.size()) * 80);
			JLabel menuLabel = new JLabel("메뉴");
			menuLabel.setHorizontalAlignment(JLabel.CENTER);
			add(menuLabel);

			JLabel numLabel = new JLabel("개수");
			numLabel.setHorizontalAlignment(JLabel.CENTER);
			add(numLabel);

			JLabel priceLabel = new JLabel("가격");
			priceLabel.setHorizontalAlignment(JLabel.CENTER);
			add(priceLabel);

			for (int i = 0; i < om.size(); i++) {
				Menu menu = om.get(i).menu;
				int num = om.get(i).num;
				JLabel menuNameLabel = new JLabel(menu.name);
				menuNameLabel.setHorizontalAlignment(JLabel.CENTER);
				add(menuNameLabel);

				JLabel menuNumLabel = new JLabel(num + "개");
				menuNumLabel.setHorizontalAlignment(JLabel.CENTER);
				add(menuNumLabel);

				JLabel menuPriceLabel = new JLabel(menu.price * num + "원");
				menuPriceLabel.setHorizontalAlignment(JLabel.CENTER);
				add(menuPriceLabel);
			}
			
			
			add(new JLabel(" ----------------------------------"));
			add(new JLabel("-----------------------------------"));
			add(new JLabel("---------------------------------- "));
			
			add(new JLabel(""));
			
			JLabel totalPriceLabel = new JLabel("최종 금액");
			totalPriceLabel.setHorizontalAlignment(JLabel.CENTER);
			add(totalPriceLabel);
			
			JLabel totalPrice = new JLabel(cart.totalSum+"원");	//합계 구한거 넣어야함
			totalPrice.setHorizontalAlignment(JLabel.CENTER);
			add(totalPrice);
		}
	}
}
