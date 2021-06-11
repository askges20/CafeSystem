package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import cafe.Cafe;
import cafe.Review;

//reviewWriteGUI로 이동하기
public class AllReviewGUI extends JPanel {	//후기 목록
	static Cafe cafe = new Cafe();
	int[] locationX = { 20, 20, 20};
	int[] locationY = { 70, 270, 470};

	int page=0;
	
	public AllReviewGUI() {
		paintGUI();
	}
	
	public void paintGUI() {
		setLayout(null);
		setSize(500, 800);
		setLocation(0, 0);
		setBackground(BackGroundFrameGUI.backGroundColor);

		// 상단바
		JLabel topBar = new JLabel("후기 목록");
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
		
		String[] sort = new String[2];
		sort[0] = "정렬";
		sort[1] = "별점높은순";
		JComboBox sortList = new JComboBox(sort);
		sortList.setBounds(370, 660, 90, 30);
		sortList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String manner = sortList.getSelectedItem().toString();
				if(manner.contentEquals("별점높은순")) {
					StartGUI.cafe.sortReviewStar();
					removeAll();
					revalidate();
					repaint();
					AllReviewGUI move = new AllReviewGUI();
					add(move);
				}
			}
		});
		add(sortList);

		JButton writeButton = new JButton("후기 작성");
		writeButton.setBounds(370, 700, 90, 30);
		writeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				revalidate();
				repaint();
				ReviewWriteGUI move = new ReviewWriteGUI();
				add(move);
				//writer.setVisible(true);
			}
		});
		add(writeButton);
		
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
				if ((StartGUI.cafe.reviewMgr.mList.size()-1) / 3 > page) {
					page += 1;
					removeAll();
					revalidate();
					repaint();
					paintGUI();
				}
			}
		});
		add(NextButton);

		ReviewGUI[] reviewItems = new ReviewGUI[3]; // null로 초기화 후

		for (int i = 0; i < 3; i++) {
			int index = page * 3 + i;
			if (index < StartGUI.cafe.reviewMgr.mList.size()) {
				Review review = (Review)cafe.reviewMgr.mList.get(index);
				reviewItems[i] = new ReviewGUI(page * 3 + i); // 객체 생성
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
								AllReviewGUI move = new AllReviewGUI();
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
}
