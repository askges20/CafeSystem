package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cafe.Cafe;

public class AllDrinkMenusGUI extends JPanel {	//음료 목록
	static Cafe cafe = new Cafe();

	int page = 0;
	int[] locationX = { 20, 250, 20, 250, 20, 250 };
	int[] locationY = { 70, 70, 270, 270, 470, 470 };

	public AllDrinkMenusGUI() {
		paintGUI();
	}

	public void paintGUI() {
		setLayout(null);
		setSize(500, 800);
		setLocation(0, 0);
		setBackground(BackGroundFrameGUI.backGroundColor);

		// 상단바
		JLabel topBar = new JLabel("메뉴 목록 - 음료");
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

		String[] sort = new String[5];
		sort[0] = "정렬";
		sort[1] = "별점높은순";
		sort[2] = "이름오름차순";
		sort[3] = "이름내림차순";
		sort[4] = "판매량순";
		JComboBox sortList = new JComboBox(sort);
		sortList.setBounds(370, 660, 90, 30);
		sortList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String manner = sortList.getSelectedItem().toString();
				if (!manner.contentEquals("정렬")) {
					if (manner.contentEquals("별점높은순"))
						StartGUI.cafe.sortDrinkStar();
					else if (manner.contentEquals("이름오름차순"))
						StartGUI.cafe.sortDrinkNameAsc();
					else if (manner.contentEquals("이름내림차순"))
						StartGUI.cafe.sortDrinkNameDes();
					else if (manner.contentEquals("판매량순"))
						StartGUI.cafe.sortDrinkSale();
					removeAll();
					revalidate();
					repaint();
					AllDrinkMenusGUI move = new AllDrinkMenusGUI();
					add(move);
				}
			}
		});
		add(sortList);

		JButton dessertButton = new JButton("디저트");
		dessertButton.setBounds(370, 700, 90, 30);// 380, 660, 90, 30
		dessertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				revalidate();
				repaint();
				AllDessertMenusGUI move = new AllDessertMenusGUI();
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
				if ((StartGUI.cafe.menudrinkMgr.mList.size()-1) / 6 > page) {
					page += 1;
					removeAll();
					revalidate();
					repaint();
					paintGUI();
				}
			}
		});
		add(NextButton);

		DrinkMenuGUI[] menuItems = new DrinkMenuGUI[6]; // null로 초기화 후

		for (int i = 0; i < 6; i++) {
			int index = page * 6 + i;
			if (index < StartGUI.cafe.menudrinkMgr.mList.size()) {
				menuItems[i] = new DrinkMenuGUI(page * 6 + i); // 객체 생성
				menuItems[i].setSize(220, 180);
				menuItems[i].setLocation(locationX[i], locationY[i]);
				menuItems[i].detailButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						removeAll();
						revalidate();
						repaint();
						add(new DrinkMenuDetailGUI(cafe, index));
					}
				});
				add(menuItems[i]);
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
