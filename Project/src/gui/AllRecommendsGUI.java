package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cafe.Cafe;
import cafe.Drink;

public class AllRecommendsGUI extends JPanel {	//추천 메뉴 목록
	static Cafe cafe = new Cafe();

	int page = 0;
	int[] locationX = { 20, 250, 20, 250 };
	int[] locationY = { 270, 270, 470, 470 };

	public AllRecommendsGUI() {
		paintGUI();
	}

	public void paintGUI() {
		setLayout(null);
		setSize(500, 800);
		setLocation(0, 0);
		setBackground(BackGroundFrameGUI.backGroundColor);

		// 상단바
		JLabel topBar = new JLabel(LoginGUI.user.name + "님을 위한 추천메뉴");
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

		// 추천메뉴설명부
		JLabel[] explain = { new JLabel(LoginGUI.user.name + "님의 주문내역을 바탕으로 음료메뉴를 추천드립니다!"),
				new JLabel("- " + LoginGUI.user.name + "님이 주문하신 음료의 종류는 아래와 같습니다!"),
				new JLabel("- " + LoginGUI.user.name + "님이 주문하신 음료의 해시태그는 아래와 같습니다!") };
		JLabel[] myCategory = new JLabel[cafe.preferCategory.size()];
		for (int i = 0; i < cafe.preferCategory.size(); i++) {
			myCategory[i] = new JLabel("[" + cafe.preferCategory.get(i) + "]");
		}
		JLabel[] myHashtag = new JLabel[cafe.preferHashTag.size()];
		for (int i = 0; i < cafe.preferHashTag.size(); i++) {
			myHashtag[i] = new JLabel("#" + cafe.preferHashTag.get(i));
		}

		for (int i = 0; i < 3; i++) {
			explain[i].setBounds(30, 70 + i * 55, 400, 30);
			add(explain[i]);
		}
		for (int i = 0; i < myCategory.length; i++) {
			myCategory[i].setBounds(35 + i * 50, 150, 100, 30);
			add(myCategory[i]);
		}
		for (int i = 0; i < myHashtag.length; i++) {
			myHashtag[i].setBounds(35 + i * 50, 205, 100, 30);
			add(myHashtag[i]);
		}

		JLabel explainpan = new JLabel();
		explainpan.setBounds(20, 70, 450, 180);
		explainpan.setBackground(Color.white);
		explainpan.setOpaque(true);
		add(explainpan);

		// 메뉴와 추천이 mapping되도록 인덱스 찾는 부분
		// 1순위
		ArrayList<Integer> recomIdx = new ArrayList<>();
		for (int i = 0; i < StartGUI.cafe.bothSatisfiedDrink.size(); i++) {
			Drink main = (Drink) cafe.bothSatisfiedDrink.get(i);
			for (int j = 0; j < StartGUI.cafe.menudrinkMgr.mList.size(); j++) {
				Drink compare = (Drink) cafe.menudrinkMgr.mList.get(j);
				if (main.name.contentEquals(compare.name))
					recomIdx.add(j);
			}
		}
		// 2순위
		if (recomIdx.isEmpty()) {
			for (int i = 0; i < StartGUI.cafe.categorySatisfiedDrink.size(); i++) {
				Drink main = (Drink) cafe.categorySatisfiedDrink.get(i);
				for (int j = 0; j < StartGUI.cafe.menudrinkMgr.mList.size(); j++) {
					Drink compare = (Drink) cafe.menudrinkMgr.mList.get(j);
					if (main.name.contentEquals(compare.name))
						recomIdx.add(j);
				}
			}
		}
		// 3순위
		if (recomIdx.isEmpty()) {
			for (int i = 0; i < StartGUI.cafe.hashTagSatisfiedDrink.size(); i++) {
				Drink main = (Drink) cafe.hashTagSatisfiedDrink.get(i);
				for (int j = 0; j < StartGUI.cafe.menudrinkMgr.mList.size(); j++) {// if(!menudessert.contains(menudessert_in.category))
					Drink compare = (Drink) cafe.menudrinkMgr.mList.get(j);
					if (main.name.contentEquals(compare.name))
						recomIdx.add(j);
				}
			}
		}
		// 추천출력
		DrinkMenuGUI[] menuItems = new DrinkMenuGUI[4]; // null로 초기화 후
		for (int i = 0; i < 4; i++) {
			int indexPage = page * 4 + i;
			// #해당 인덱스
			if (indexPage < recomIdx.size()) {// #
				int index = recomIdx.get(indexPage);
				menuItems[i] = new DrinkMenuGUI(index); // 객체 생성
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

		// 다음 페이지 이동 버튼
		JButton NextButton = new JButton("Next>>");
		NextButton.setBounds(260, 700, 100, 40);
		NextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((recomIdx.size() / 4)> page) {// #추천메뉴 계산
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

		setVisible(true);
	}

}
