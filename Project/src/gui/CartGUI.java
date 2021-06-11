package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import cafe.CustomerLoginAccount;
import cafe.Order;
import cafe.OrderedMenu;

public class CartGUI extends JPanel {	//장바구니 목록
	int page = 0;
	Order cart;
	int[] locationX = { 20, 20, 20 };
	int[] locationY = { 70, 270, 470 };
	int Imagewidth = 150;
	int ImageHeight = 160;

	public CartGUI() {
		cart = CustomerLoginAccount.getAccount().cart;
		paintGUI();
	}

	public void paintGUI() {
		setLayout(null);
		setSize(500, 800);
		setLocation(0, 0);
		setBackground(BackGroundFrameGUI.backGroundColor);
		setVisible(true);

		JButton moveMainButton = new JButton("메인 이동");
		moveMainButton.setBounds(20, 660, 90, 30);
		moveMainButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				revalidate();
				repaint();
				UserMainGUI move = new UserMainGUI();
				add(move);
			}
		});
		add(moveMainButton);

		JLabel topBar = new JLabel("장바구니");
		topBar.setFont(new Font("고딕", Font.BOLD, 25));
		topBar.setBounds(0, 0, 500, 50);
		topBar.setHorizontalAlignment(JLabel.CENTER);
		topBar.setBackground(BackGroundFrameGUI.topBarColor);
		topBar.setOpaque(true);
		topBar.setFont(new Font("고딕", Font.BOLD, 20));
		add(topBar);

		JButton orderButton = new JButton("주문 페이지로 이동");
		orderButton.setBounds(0, 700, 500, 70);
		orderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cart.orderedMenuList.size() > 0) {// 장바구니에 담은 메뉴가 있을 때만 이동
					removeAll();
					revalidate();
					repaint();
					add(new OrderingGUI(cart));
				} else {	//장바구니가 비어있다는 메세지 출력
					JOptionPane.showMessageDialog(null, "장바구니가 비어있습니다!");
				}
			}
		});
		add(orderButton);

		// 이전 페이지 이동 버튼
		JButton PrevButton = new JButton("<<Prev");
		PrevButton.setBounds(140, 660, 80, 30);
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
		NextButton.setBounds(290, 660, 80, 30);
		NextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cart.orderedMenuList.size() / 3 > page) {
					page += 1;
					removeAll();
					revalidate();
					repaint();
					paintGUI();
				}
			}
		});
		add(NextButton);

		// 현재 페이지 번호
		JLabel pageLabel = new JLabel("page : " + (page + 1));
		pageLabel.setBounds(200, 660, 100, 40);
		pageLabel.setHorizontalAlignment(JLabel.CENTER);
		pageLabel.setForeground(Color.WHITE);
		add(pageLabel);

		CartItemGUI[] cartItems = new CartItemGUI[3]; // null로 초기화 후

		for (int i = 0; i < 3; i++) {
			int index = page * 3 + i;
			if (index < cart.orderedMenuList.size()) {
				OrderedMenu om = cart.orderedMenuList.get(page * 3 + i);
				cartItems[i] = new CartItemGUI(om, this); // 객체 생성
				cartItems[i].setSize(440, 180);
				cartItems[i].setLocation(locationX[i], locationY[i]);
				add(cartItems[i]);
			}
		}

		setVisible(true);
	}

	public class CartItemGUI extends JPanel {
		int locationX = 220;
		int[] locationY = { 30, 60, 90, 120 };

		public CartItemGUI(OrderedMenu om, CartGUI cartGUI) {
			setLayout(null);
			setBackground(Color.WHITE);
			setOpaque(true);

			//ImageIcon image = new ImageIcon("image/dessert/"+om.menu.filename);
			ImageIcon image = new ImageIcon(om.menu.filename);
			Image img = image.getImage();
			Image resizeImg = img.getScaledInstance(Imagewidth, ImageHeight, Image.SCALE_SMOOTH);
			ImageIcon resizingMenuImg = new ImageIcon(resizeImg);
			JLabel menuImage = new JLabel(resizingMenuImg);
			menuImage.setBounds(10, 10, Imagewidth, ImageHeight);
			add(menuImage);

			JLabel[] labels = { new JLabel("메뉴 이름 : " + om.menu.name), new JLabel("가격 : " + om.menu.price + "원"),
					new JLabel("수량 : "+om.num+"개"), new JLabel(om.priceSum + "원") };

			for (int i = 0; i < labels.length; i++) {
				labels[i].setBounds(locationX, locationY[i], 200, 20);
				add(labels[i]);
			}

			JButton removeButton = new JButton("X"); // 삭제 버튼
			removeButton.setBounds(400, 0, 40, 40);
			removeButton.setFont(new Font("고딕", Font.BOLD, 8));
			removeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int result = JOptionPane.showConfirmDialog(null, "장바구니에서 삭제하시겠습니까?", "장바구니 메뉴 삭제",
							JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						cart.orderedMenuList.remove(om);
						cart.calTotalSum();// 메뉴 삭제 후 다시 총합 계산
						cartGUI.removeAll();
						cartGUI.revalidate();
						cartGUI.repaint();
						cartGUI.paintGUI();
					}
				}
			});
			add(removeButton);
		}
	}

}
