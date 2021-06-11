
package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cafe.Cafe;
import cafe.Customer;
import cafe.Dessert;
import cafe.Drink;
import cafe.Menu;

public class AllDetailSearchGUI extends JPanel{	//상세 검색 결과
	static Cafe cafe = new Cafe();

	int page = 0;
	int[] locationX = { 20, 250, 20, 250, 20, 250 };
	int[] locationY = { 70, 70, 270, 270, 470, 470 };
	String name;
	boolean menudrink =true;

	public AllDetailSearchGUI(Customer user, int minprice, int maxprice, String category, String hashtag, boolean allergy) {
		this.name = name;
		StartGUI.cafe.searchDrink.clear();
		StartGUI.cafe.searchDessert.clear();
		StartGUI.cafe.detailSearchDrink(user, minprice, maxprice, category, hashtag, allergy);
		if (StartGUI.cafe.searchDrink.isEmpty()) {
			menudrink = false;
			StartGUI.cafe.detailSearchDessert(user, minprice, maxprice, category, hashtag, allergy);
		}
		paintGUI();
	}

	public void paintGUI() {
		setLayout(null);
		setSize(500, 800);
		setLocation(0, 0);
		setBackground(BackGroundFrameGUI.backGroundColor);
		
		// 상단바
		JLabel topBar = new JLabel("검색된 메뉴");
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
		
		MenuGUI[] menuItems = new MenuGUI[6]; // null로 초기화 후
		for (int i = 0; i < 6; i++) {
			int indexPage = page * 6 + i;
			if (StartGUI.cafe.searchDrink.size() == 0 && StartGUI.cafe.searchDessert.size() == 0)
				break;
			if (indexPage < StartGUI.cafe.searchDrink.size()) {	//음료 메뉴에 대해 출력
				if (!StartGUI.cafe.searchDrink.isEmpty()) {
					Menu menuObject = (Menu) StartGUI.cafe.searchDrink.get(indexPage);
					menuItems[i] = new MenuGUI(menuObject); // 객체 생성
					menuItems[i].setSize(220, 180);
					menuItems[i].setLocation(locationX[i], locationY[i]);
					menuItems[i].detailButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							int index=0;
							for(int i=0;i<StartGUI.cafe.menudrinkMgr.mList.size();i++) {
								Drink compare = (Drink)StartGUI.cafe.menudrinkMgr.mList.get(i);
								if(compare.equals((Drink)menuObject)) {
									index=i;
									break;
								}
							}
							removeAll();
							revalidate();
							repaint();
							add(new DrinkMenuDetailGUI(StartGUI.cafe, index));
						}
					});
					add(menuItems[i]);
				}
			} else {	//디저트 메뉴에 대해 출력
				if (indexPage - StartGUI.cafe.searchDrink.size()<StartGUI.cafe.searchDessert.size()) {
					Menu menuObject = (Menu) StartGUI.cafe.searchDessert.get(indexPage);
					menuItems[i] = new MenuGUI(menuObject); // 객체 생성
					menuItems[i].setSize(220, 180);
					menuItems[i].setLocation(locationX[i], locationY[i]);
					Menu menu = menuItems[i].menu;
					menuItems[i].detailButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							int index=0;
							for(int i=0;i<StartGUI.cafe.menudessertMgr.mList.size();i++) {
								Dessert compare = (Dessert)StartGUI.cafe.menudessertMgr.mList.get(i);
								if(compare.equals((Dessert)menuObject)) {
									index=i;
									break;
								}
							}
							removeAll();
							revalidate();
							repaint();
							add(new DessertMenuDetailGUI(StartGUI.cafe, index));
						}
					});
					add(menuItems[i]);
				}
			}
			
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
					if ((StartGUI.cafe.searchDrink.size() + StartGUI.cafe.searchDessert.size() -1) / 6 > page) {
						page += 1;
						removeAll();
						revalidate();
						repaint();
						paintGUI();
					}
				}
			});
			add(NextButton);
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