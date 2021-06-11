package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cafe.Cafe;
import cafe.CustomerLoginAccount;
import cafe.Drink;
import cafe.Menu;
import cafe.Review;

public class UserMainGUI extends JPanel {	//사용자 메인 화면
	static Cafe cafe = new Cafe();

	int[] locationX = { 10, 10, 10 };
	int[] locationY = { 150, 350, 550 };

	public UserMainGUI() {
		setLayout(null);
		setSize(500, 800);
		setLocation(0, 0);
		setBackground(BackGroundFrameGUI.backGroundColor);
		setVisible(true);

		JLabel topBar = new JLabel("Cafe JAVA CHIP FRAPPUCCINO");
		topBar.setFont(new Font("고딕", Font.BOLD, 25));
		topBar.setBounds(0, 0, 500, 50);
		topBar.setHorizontalAlignment(JLabel.CENTER);
		topBar.setBackground(BackGroundFrameGUI.topBarColor);
		topBar.setOpaque(true);
		topBar.setFont(new Font("고딕", Font.BOLD, 20));
		add(topBar);

		JTextField searchInput = new JTextField("메뉴 이름을 입력해주세요");
		searchInput.setBounds(10, 60, 400, 30);
		add(searchInput);

		JButton searchButton = new JButton("검색");
		searchButton.setBounds(420, 60, 60, 30);
		add(searchButton);
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				add(new AllSearchListGUI(searchInput.getText()));
				revalidate();
				repaint();
			}
		});

		// #
		JButton RecommendButton = new JButton("더보기");
		RecommendButton.setBounds(400, 140, 70, 30);
		RecommendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				add(new AllRecommendsGUI());
				revalidate();
				repaint();
			}
		});
		add(RecommendButton);

		JButton MenuButton = new JButton("더보기");
		MenuButton.setBounds(400, 340, 70, 30);
		MenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				add(new AllDrinkMenusGUI());
				revalidate();
				repaint();
			}
		});
		add(MenuButton);

		JButton ReviewButton = new JButton("더보기");
		ReviewButton.setBounds(400, 540, 70, 30);
		ReviewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				add(new AllReviewGUI());
				revalidate();
				repaint();
			}
		});
		add(ReviewButton);

		JButton MyPageButton = new JButton("마이페이지");
		MyPageButton.setBounds(45, 100, 100, 30);
		MyPageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				add(new MyReviewGUI());
				revalidate();
				repaint();
			}
		});
		add(MyPageButton);

		JButton CartButton = new JButton("장바구니");
		CartButton.setBounds(145, 100, 100, 30);
		CartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				add(new CartGUI());
				revalidate();
				repaint();
			}
		});
		add(CartButton);

		JButton SearchButton = new JButton("상세검색");
		SearchButton.setBounds(245, 100, 100, 30);
		SearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				add(new SearchGUI());
				revalidate();
				repaint();
			}
		});
		add(SearchButton);

		JButton logoutButton = new JButton("로그아웃");
		logoutButton.setBounds(345, 100, 100, 30);
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logout(); // 로그인 계정 해제
				removeAll();
				add(new LoginGUI());
				revalidate();
				repaint();
			}
		});
		add(logoutButton);

		JLabel[] labels = { new JLabel(LoginGUI.user.name + "님을 위한 추천메뉴입니다!"), new JLabel("메뉴"), new JLabel("후기") };

		for (int i = 0; i < labels.length; i++) {
			labels[i].setBounds(locationX[i], locationY[i], 200, 20);
			labels[i].setForeground(Color.WHITE);
			add(labels[i]);
		}

		// #
		ArrayList<Integer> recomIdx = new ArrayList<>();
		for (int i = 0; i < StartGUI.cafe.bothSatisfiedDrink.size(); i++) {
			Drink main = (Drink) cafe.bothSatisfiedDrink.get(i);
			for (int j = 0; j < StartGUI.cafe.menudrinkMgr.mList.size(); j++) {
				Drink compare = (Drink) cafe.menudrinkMgr.mList.get(j);
				if (main.name.contentEquals(compare.name))
					recomIdx.add(j);
			}
		}

		for (int i = 0; i < recomIdx.size(); i++) {
			if (i > 3)
				break;
			int index = recomIdx.get(i);
			Drink menu = (Drink) cafe.menudrinkMgr.mList.get(index);
			ImageIcon image = new ImageIcon(menu.filename);
			Image img = image.getImage();
			Image resizeImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
			ImageIcon resizingMenuImg = new ImageIcon(resizeImg);
			JButton imageButton = new JButton(resizingMenuImg);
			imageButton.setBounds(40 + i * 150, 200, 100, 100);
			add(imageButton);

			imageButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					removeAll();
					revalidate();
					repaint();
					add(new DrinkMenuDetailGUI(cafe, index));
				}
			});

			JLabel namelabel = new JLabel(menu.name);
			namelabel.setBounds(40 + i * 150, 300, 150, 30);
			add(namelabel);
		}

		JLabel recomdpan = new JLabel();
		recomdpan.setBounds(20, 180, 450, 150);
		recomdpan.setBackground(BackGroundFrameGUI.topBarColor);
		recomdpan.setOpaque(true);
		add(recomdpan);

		for (int i = 0; i < 3; i++) {
			int index = i;
			Drink menu = (Drink) cafe.menudrinkMgr.mList.get(i);
			ImageIcon image = new ImageIcon(menu.filename);
			Image img = image.getImage();
			Image resizeImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
			ImageIcon resizingMenuImg = new ImageIcon(resizeImg);
			JButton imageButton = new JButton(resizingMenuImg);
			imageButton.setBounds(40 + i * 150, 400, 100, 100);
			add(imageButton);

			imageButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					removeAll();
					revalidate();
					repaint();
					add(new DrinkMenuDetailGUI(cafe, index));
				}
			});

			JLabel namelabel = new JLabel(menu.name);
			namelabel.setBounds(40 + i * 150, 500, 150, 30);
			add(namelabel);
		}
		JLabel menupan = new JLabel();
		menupan.setBounds(20, 380, 450, 150);
		menupan.setBackground(BackGroundFrameGUI.topBarColor);
		menupan.setOpaque(true);
		add(menupan);

		for (int i = 0; i < 2; i++) {
			int index = i;
			Review review = (Review) cafe.reviewMgr.mList.get(i);
			// ImageIcon image=new ImageIcon("image/1.jpg");
			ImageIcon image = new ImageIcon(review.menuObject.filename);
			Image img = image.getImage();
			Image resizeImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
			ImageIcon resizingMenuImg = new ImageIcon(resizeImg);
			JButton imageButton = new JButton(resizingMenuImg);
			imageButton.setBounds(30 + i * 230, 605, 100, 100);
			add(imageButton);
			imageButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					removeAll();
					revalidate();
					repaint();
					add(new ReviewDetailGUI(cafe, index));
				}
			});
			JLabel[] label = { new JLabel(review.menu), new JLabel(review.star + " / 5"), new JLabel(review.content) };
			for (int j = 0; j < 3; j++) {
				label[j].setBounds(140 + i * 230, 600 + 20 * j, 100, 30);
				add(label[j]);
			}
		}
		JLabel reviewpan = new JLabel();
		reviewpan.setBounds(20, 580, 450, 150);
		reviewpan.setBackground(BackGroundFrameGUI.topBarColor);
		reviewpan.setOpaque(true);
		add(reviewpan);

	}

	private void logout() {
		CustomerLoginAccount account = CustomerLoginAccount.getAccount();
		account.setCustomer(null);
		account.clearCart();
	}

}