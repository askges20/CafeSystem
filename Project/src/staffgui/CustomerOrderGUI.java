package staffgui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cafe.*;
import cafe.Menu;
import gui.*;

public class CustomerOrderGUI extends JPanel {	//고객의 주문 내역

	Color backGroundColor = new Color(0xFFDAB9);
	Color topBarColor = new Color(0xFFDA55);

	int Imagewidth = 180;
	int ImageHeight = 180;
	int page = 0;

	Customer customer;
	ArrayList<Order> orderList;

	public CustomerOrderGUI(Customer customer) {
		this.customer = customer;
		this.orderList = customer.orderList;
		paintGUI();
	}

	public void paintGUI() {
		setLayout(null);
		setBackground(BackGroundFrameGUI.backGroundColor);
		setSize(500, 800);
		setVisible(true);

		// 상단바
		JLabel topBar = new JLabel(customer.name + "(" + customer.id + ")" + " 고객님 주문 내역");
		topBar.setBounds(0, 0, 500, 50);
		topBar.setHorizontalAlignment(JLabel.CENTER);
		topBar.setBackground(topBarColor);
		topBar.setOpaque(true);
		topBar.setFont(new Font("고딕", Font.BOLD, 20));
		add(topBar);

		JButton MenuButton = new JButton("고객 목록"); // 고객 목록 화면으로 되돌아가는 버튼
		MenuButton.setBounds(20, 660, 90, 30);
		add(MenuButton);
		MenuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeAll();
				revalidate();
				repaint();
				add(new CustomerListGUI());
			}
		});

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
				if (customer.orderList.size() - 1 > page) {
					page += 1;
					removeAll();
					revalidate();
					repaint();
					paintGUI();
				}
			}
		});
		add(NextButton);

		if (orderList.size() == 0) {
			JLabel noOrderListLabel = new JLabel("해당 고객의 주문 내역이 없습니다.");
			noOrderListLabel.setBounds(0,300,500,50);
			noOrderListLabel.setHorizontalAlignment(JLabel.CENTER);
			add(noOrderListLabel);
		} else {
			Order order = customer.orderList.get(page);
			JLabel orderLabel = new JLabel("[" + (page + 1) + "]   " + order.date + " 일자 주문 내역");
			orderLabel.setBounds(50, 100, 300, 50);
			orderLabel.setFont(new Font("고딕", Font.BOLD, 18));
			add(orderLabel);
			
			add(new OrderDetailPanel(orderList.get(page)));
		}

		JLabel backgroundLabel = new JLabel();
		backgroundLabel.setBounds(20, 70, 440, 550);
		backgroundLabel.setBackground(Color.white);
		backgroundLabel.setOpaque(true);
		add(backgroundLabel);
	}

	public class OrderDetailPanel extends JPanel {

		public OrderDetailPanel(Order order) {
			ArrayList<OrderedMenu> om = order.orderedMenuList;
			
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
			
			JLabel totalPrice = new JLabel(order.totalSum+"원");	//합계 구한거 넣어야함
			totalPrice.setHorizontalAlignment(JLabel.CENTER);
			add(totalPrice);
		}
	}
}