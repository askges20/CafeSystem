package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cafe.Cafe;
import cafe.Review;

public class MyReviewGUI extends JPanel{	//고객 자신이 작성한 후기 목록
	static Cafe cafe = new Cafe();
	int[] locationX = { 20, 20, 20};
	int[] locationY = { 70, 270, 470};

	int page=0;
	
	public MyReviewGUI() {
		paintGUI();
	}
	
	public void paintGUI() {
		setLayout(null);
		setSize(500, 800);
		setLocation(0, 0);
		setBackground(BackGroundFrameGUI.backGroundColor);

		// 상단바
		JLabel topBar = new JLabel("MyPage - "+LoginGUI.user.name+"님의 후기목록");
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
		
		JButton dessertButton = new JButton("주문내역");
		dessertButton.setBounds(380, 660, 90, 30);
		dessertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				revalidate();
				repaint();
				MyOrderGUI move = new MyOrderGUI();
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
				if (customerReviewCnt(LoginGUI.user.id) / 3 > page) {
					page += 1;
					removeAll();
					revalidate();
					repaint();
					paintGUI();
				}
			}
		});
		add(NextButton);
		
		ArrayList<Integer> myReviewIdx = new ArrayList<>();
		for(int i=0;i<cafe.reviewMgr.mList.size();i++) {
			Review compare = (Review)cafe.reviewMgr.mList.get(i);
			if(LoginGUI.user.id.contentEquals(compare.id)) {
				myReviewIdx.add(i);
			}
		}
		
		ReviewGUI[] reviewItems = new ReviewGUI[3]; // null로 초기화 후
		
		for(int i=0; i<3; i++) {
			int indexPage = page * 3 + i;
			if (indexPage < myReviewIdx.size()) {
				int index = myReviewIdx.get(indexPage);
				Review review = (Review)cafe.reviewMgr.mList.get(index);
				reviewItems[i] = new ReviewGUI(index); // 객체 생성
				reviewItems[i].setSize(440, 180);
				reviewItems[i].setLocation(locationX[i], locationY[i]);
				reviewItems[i].detailButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						removeAll();
						revalidate();
						repaint();
						add(new ReviewDetailGUI(cafe, index));
					}
				});
				
				reviewItems[i].removeButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if(LoginGUI.user.id.contentEquals(review.id)) {
							try {
								cafe.deleteReviewData(index);
								JOptionPane.showMessageDialog(null, "삭제가 완료되었습니다!");
								removeAll();
								revalidate();
								repaint();
								MyReviewGUI move = new MyReviewGUI();
								add(move);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}else {
							JOptionPane.showMessageDialog(null, "권한이 없습니다!");
						}
					}
				});
				
				add(reviewItems[i]);
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
	
	public int customerReviewCnt(String id) {
		int cnt=0;
		for(int i=0;i<StartGUI.cafe.reviewMgr.mList.size();i++) {
			Review review = (Review)StartGUI.cafe.reviewMgr.mList.get(i);
			String compare = review.id;
			if(id.contentEquals(compare))
				cnt++;
		}
		return cnt;
	}
}