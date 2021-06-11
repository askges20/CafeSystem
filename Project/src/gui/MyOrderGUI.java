package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cafe.Cafe;
import cafe.Order;
import cafe.OrderedMenu;
import cafe.Review;

public class MyOrderGUI extends JPanel{	//고객 자신의 주문내역
	static Cafe cafe = new Cafe();
	int[] locationX = { 20, 20, 20};
	int[] locationY = { 70, 270, 470};

	int page=0;
	
	public MyOrderGUI() {
		paintGUI();
	}
	
	public void paintGUI() {
		setLayout(null);
		setSize(500, 800);
		setLocation(0, 0);
		setBackground(BackGroundFrameGUI.backGroundColor);

		// 상단바
		JLabel topBar = new JLabel("MyPage - "+LoginGUI.user.name+"님의 주문목록");
		topBar.setBounds(0, 0, 500, 50);
		topBar.setHorizontalAlignment(JLabel.CENTER);
		topBar.setBackground(BackGroundFrameGUI.topBarColor);
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
				UserMainGUI move = new UserMainGUI();
				add(move);
			}
		});
		add(moveMainButton);
		
		JButton dessertButton = new JButton("후기내역");
		dessertButton.setBounds(380, 660, 90, 30);
		dessertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				revalidate();
				repaint();
				MyReviewGUI move = new MyReviewGUI();
				add(move);
			}
		});
		add(dessertButton);
		
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
				if (customerOrderCnt(LoginGUI.user.id) / 3 > page) {
					page += 1;
					removeAll();
					revalidate();
					repaint();
					paintGUI();
				}
			}
		});
		add(NextButton);

		//##문제
		ArrayList <Integer> myOrderIdx = new ArrayList<>();
		for(int i=0;i<cafe.orderMgr.mList.size();i++) {
			Order compare = (Order) cafe.orderMgr.mList.get(i);
			if(LoginGUI.user.id.contentEquals(compare.id)) {
				myOrderIdx.add(i);
			}
		}
		
		OrderGUI[] orderItems = new OrderGUI[3];
		for(int i=0;i<3;i++) {
			int indexPage = page*3+i;
			if(indexPage<myOrderIdx.size()) {
				int index = myOrderIdx.get(indexPage);
				Order order = (Order)cafe.orderMgr.mList.get(index);
				orderItems[i] = new OrderGUI(index);
				orderItems[i].setSize(440, 180);
				orderItems[i].setLocation(locationX[i], locationY[i]);
				orderItems[i].detailButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						removeAll();
						revalidate();
						repaint();
						add(new MyOrderListGUI(order, index));
					}
				});
				add(orderItems[i]);
			}
		}
		

		// 현재 페이지 번호
		JLabel pageLabel = new JLabel("page : " + (page + 1));
		pageLabel.setBounds(200, 660, 100, 40);
		pageLabel.setHorizontalAlignment(JLabel.CENTER);
		pageLabel.setForeground(Color.WHITE);
		add(pageLabel);

		setVisible(true);
	}
	
	public int customerOrderCnt(String id) {
		int cnt=0;
		for(int i=0;i<StartGUI.cafe.orderMgr.mList.size();i++) {
			Order order = (Order)StartGUI.cafe.orderMgr.mList.get(i);
			String compare = order.id;
			if(id.contentEquals(compare))
				cnt++;
		}
		return cnt;
	}
	
	public class MyOrderListGUI extends JPanel{
		int page = 0;
		int[] locationX = { 20, 250, 20, 250, 20, 250 };
		int[] locationY = { 70, 70, 270, 270, 470, 470 };
		Order order;

		public MyOrderListGUI(Order order, int index) {
			this.order = order;
			paintGUI();
		}

		public void paintGUI() {
			setLayout(null);
			setSize(500, 800);
			setLocation(0, 0);
			setBackground(BackGroundFrameGUI.backGroundColor);

			// 상단바
			JLabel topBar = new JLabel("MyPage - "+LoginGUI.user.name+"님의 주문목록");
			topBar.setBounds(0, 0, 500, 50);
			topBar.setHorizontalAlignment(JLabel.CENTER);
			topBar.setBackground(BackGroundFrameGUI.topBarColor);
			topBar.setOpaque(true);
			topBar.setFont(new Font("고딕", Font.BOLD, 20));
			add(topBar);
			
			JButton moveMainButton = new JButton("주문내역");
			moveMainButton.setBounds(20, 660, 90, 30);
			moveMainButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					removeAll();
					revalidate();
					repaint();
					MyOrderGUI move = new MyOrderGUI();
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
					if (order.orderedMenuList.size() / 6 > page) {
						page += 1;
						removeAll();
						revalidate();
						repaint();
						paintGUI();
					}
				}
			});
			add(NextButton);

			MyOrderDetailGUI[] orderItems = new MyOrderDetailGUI[6]; // null로 초기화 후

			for (int i = 0; i < 6; i++) {
				int index = page * 6 + i;
				if (index < order.orderedMenuList.size()) {//LoginGUI.cafe.menudrinkMgr.mList.size()
					orderItems[i] = new MyOrderDetailGUI(order, page * 6 + i); // 객체 생성
					orderItems[i].setSize(220, 180);
					orderItems[i].setLocation(locationX[i], locationY[i]);
					add(orderItems[i]);
				}
			}

			// 현재 페이지 번호
			JLabel pageLabel = new JLabel("page : " + (page + 1));
			pageLabel.setBounds(200, 660, 100, 40);
			pageLabel.setHorizontalAlignment(JLabel.CENTER);
			pageLabel.setForeground(Color.WHITE);
			add(pageLabel);

			setVisible(true);
		}
	}
	
	public class MyOrderDetailGUI extends JPanel{
		int menuNum;

		public MyOrderDetailGUI(Order order, int num) {
			menuNum = num;
			OrderedMenu ordermenu = order.orderedMenuList.get(menuNum);
			setBackground(BackGroundFrameGUI.topBarColor);

			setLayout(new GridLayout(3, 1));
			JLabel[] labels = { new JLabel( "메뉴이름 :"+ordermenu.menu.name), new JLabel("가격 : "+ordermenu.menu.price), new JLabel("개수 : "+ordermenu.num) };

			for (int i = 0; i < labels.length; i++) {
				labels[i].setHorizontalAlignment(JLabel.CENTER);
				add(labels[i]);
			}
		}
	}
}
