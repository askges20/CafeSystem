package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cafe.Review;

public class ReviewWriteGUI extends JPanel{	//리뷰 작성 페이지
	int[] LabellocationX = { 60, 60, 60};
	int[] LabellocationY = { 160, 200, 240};
	
	public ReviewWriteGUI() {
		setBackground(BackGroundFrameGUI.backGroundColor);

		// 상단바
		JLabel topBar = new JLabel("후기 작성");
		topBar.setBounds(0, 0, 500, 50);
		topBar.setHorizontalAlignment(JLabel.CENTER);
		topBar.setBackground(BackGroundFrameGUI.topBarColor);
		topBar.setOpaque(true);
		topBar.setFont(new Font("고딕", Font.BOLD, 20));
		add(topBar);
		
		setLayout(null);
		setLocation(0,0);
		setSize(500, 800);
		
		JLabel[] labels = { new JLabel("메뉴 : "), new JLabel("평점: "), new JLabel("내용")};
		
		for (int i = 0; i < labels.length; i++) {
			labels[i].setBounds(LabellocationX[i], LabellocationY[i], 50, 30);
			add(labels[i]);
		}
		
		JTextField menutext = new JTextField();
		menutext.setBounds(100, 160, 320, 30);
		add(menutext);
		
		JTextField scoretext = new JTextField();
		scoretext.setBounds(100, 200, 40, 30);
		add(scoretext);
		JLabel scoremaxlabel = new JLabel(" / 5 점");
		scoremaxlabel.setBounds(140, 200, 50, 30);
		add(scoremaxlabel);
		
		JTextArea reviewtext=new JTextArea();
		reviewtext.setBounds(60, 270, 370, 300);
		add(reviewtext);
		
		JButton exitButton = new JButton("취소");
		exitButton.setBounds(130, 590, 100, 50);
		add(exitButton);
		JButton writeButton = new JButton("저장");
		writeButton.setBounds(250, 590, 100, 50);
		add(writeButton);
		
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				revalidate();
				repaint();
				AllReviewGUI writer = new AllReviewGUI();//후기로 이동
				add(writer);
			}
		});
		
		writeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Review review = new Review();
					review.read(LoginGUI.user.id,menutext.getText(),scoretext.getText(),reviewtext.getText());
					StartGUI.cafe.addReviewData(review);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				removeAll();
				revalidate();
				repaint();
				AllReviewGUI move = new AllReviewGUI();//후기로 이동
				add(move);
			}
		});
		
		JLabel reviewpan = new JLabel();
		reviewpan.setBounds(20, 130, 450, 540);
		reviewpan.setBackground(BackGroundFrameGUI.topBarColor);
		reviewpan.setOpaque(true);
		add(reviewpan);
		setVisible(true);
	}

}
