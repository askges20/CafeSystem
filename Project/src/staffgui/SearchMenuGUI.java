package staffgui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cafe.Drink;
import cafe.Menu;
import gui.BackGroundFrameGUI;
import gui.StartGUI;

public class SearchMenuGUI extends JPanel {	//메뉴 검색
	int page = 0;
	int[] locationX = { 20, 250, 20, 250 };
	int[] locationY = { 100, 100, 400, 400 };
	String name;

	public SearchMenuGUI(String name) {
		this.name = name;
		StartGUI.cafe.searchDrinkIdx.clear();
		StartGUI.cafe.searchDessertIdx.clear();
		StartGUI.cafe.basicSearchDrink(name);
		StartGUI.cafe.basicSearchDessert(name);

		paintGUI();
	}

	public void paintGUI() {
		setLayout(null);
		setSize(500, 800);
		setLocation(0, 0);
		setBackground(BackGroundFrameGUI.backGroundColor);
		setVisible(true);

		JButton moveMainButton = new JButton("메인 이동");
		moveMainButton.setBounds(20, 700, 90, 30);
		moveMainButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				revalidate();
				repaint();
				StaffMainGUI move = new StaffMainGUI(); // 직원 메인 페이지로 이동
				add(move);
			}
		});
		add(moveMainButton);

		JTextField searchInput = new JTextField(name);
		searchInput.setBounds(10, 60, 400, 30);
		add(searchInput);

		JButton searchButton = new JButton("검색");
		searchButton.setBounds(420, 60, 60, 30);
		add(searchButton);
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				add(new SearchMenuGUI(searchInput.getText()));
				revalidate();
				repaint();
			}
		});

		JLabel topBar = new JLabel("수정할 음료/디저트 검색");
		topBar.setFont(new Font("고딕", Font.BOLD, 25));
		topBar.setBounds(0, 0, 500, 50);
		topBar.setHorizontalAlignment(JLabel.CENTER);
		topBar.setBackground(BackGroundFrameGUI.topBarColor);
		topBar.setOpaque(true);
		topBar.setFont(new Font("고딕", Font.BOLD, 20));
		add(topBar);

		MenuGUI[] menuItems = new MenuGUI[4]; // null로 초기화 후
		for (int i = 0; i < 4; i++) {
			int indexPage = page * 4 + i;
			if (StartGUI.cafe.searchDrinkIdx.size() == 0 && StartGUI.cafe.searchDessertIdx.size() == 0)
				break;
			
			if (indexPage < StartGUI.cafe.searchDrinkIdx.size()) {	//음료 메뉴에 대해 출력
				if (!StartGUI.cafe.searchDrinkIdx.isEmpty()) {
					int index = StartGUI.cafe.searchDrinkIdx.get(indexPage);
					Menu menuObject = (Menu) StartGUI.cafe.menudrinkMgr.mList.get(index);
					menuItems[i] = new MenuGUI(menuObject); // 객체 생성
					menuItems[i].setSize(220, 280);
					menuItems[i].setLocation(locationX[i], locationY[i]);
					Menu menu = menuItems[i].menu;
					menuItems[i].modifyButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							removeAll();
							revalidate();
							repaint();
							add(new ModifyMenuGUI(menu));
						}
					});
					add(menuItems[i]);
				}
			} else {	//디저트 메뉴에 대해 출력
				if (indexPage - StartGUI.cafe.searchDrinkIdx.size()<StartGUI.cafe.searchDessertIdx.size()) {
					int index = StartGUI.cafe.searchDessertIdx.get(indexPage - StartGUI.cafe.searchDrinkIdx.size());
					Menu menuObject = (Menu) StartGUI.cafe.menudessertMgr.mList.get(index);
					menuItems[i] = new MenuGUI(menuObject); // 객체 생성
					menuItems[i].setSize(220, 280);
					menuItems[i].setLocation(locationX[i], locationY[i]);
					Menu menu = menuItems[i].menu;
					menuItems[i].modifyButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							removeAll();
							revalidate();
							repaint();
							add(new ModifyMenuGUI(menu));
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
					if ((StartGUI.cafe.searchDrinkIdx.size() + StartGUI.cafe.searchDessertIdx.size() - 1) / 4 > page) {
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
	}
}
