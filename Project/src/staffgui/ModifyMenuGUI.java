package staffgui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cafe.Menu;
import gui.BackGroundFrameGUI;

public class ModifyMenuGUI extends JPanel {	//메뉴 수정
	int page = 0;
	int[] LabellocationY = { 120, 200, 280, 360, 440, 520 };
	Menu menu;

	public ModifyMenuGUI(Menu menu) {
		this.menu = menu;
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

		JLabel topBar = new JLabel("음료/디저트 수정");
		topBar.setFont(new Font("고딕", Font.BOLD, 25));
		topBar.setBounds(0, 0, 500, 50);
		topBar.setHorizontalAlignment(JLabel.CENTER);
		topBar.setBackground(BackGroundFrameGUI.topBarColor);
		topBar.setOpaque(true);
		topBar.setFont(new Font("고딕", Font.BOLD, 20));
		add(topBar);
		
		JLabel[] labels = { new JLabel("메뉴이름 : "), new JLabel("가격 : "), new JLabel("종류 : "), new JLabel("해시태그 : "),
				new JLabel("재료 : ")};

		for (int i = 0; i < labels.length; i++) {
			labels[i].setBounds(50, LabellocationY[i], 500, 30);
			add(labels[i]);
		}

		JTextField name = new JTextField(); // 메뉴명
		name.setText(menu.name);
		name.disable();
		name.setBounds(120, LabellocationY[0], 310, 30);
		add(name);

		JTextField price = new JTextField(); // 가격
		price.setText(menu.price+"");
		price.setBounds(120, LabellocationY[1], 310, 30);
		add(price);

		JTextField category = new JTextField(); // 종류
		category.setText(menu.category);
		category.setBounds(120, LabellocationY[2], 310, 30);
		add(category);

		JTextField hashtag = new JTextField(); // 해시태그
		String tmpHashtag = "";
		for(String h:menu.hashTags) {
			tmpHashtag = tmpHashtag + h +" ";
		}
		hashtag.setText(tmpHashtag);
		hashtag.setBounds(120, LabellocationY[3], 310, 30);
		add(hashtag);
		
		JTextField material = new JTextField(); // 해시태그
		String tmpMaterial = "";
		for(String m:menu.materials) {
			tmpMaterial = tmpMaterial + m +" ";
		}
		material.setText(tmpMaterial);
		material.setBounds(120, LabellocationY[4], 310, 30);
		add(material);

		JButton searchButton = new JButton("수정 완료");
		searchButton.setBounds(150, 580, 200, 70);
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StringTokenizer mst = new StringTokenizer(material.getText());	//공백 단위로 분리
				ArrayList<String> materialList = new ArrayList<>();
				while (mst.hasMoreTokens()) {
					materialList.add(mst.nextToken());
				}
				
				StringTokenizer hst = new StringTokenizer(hashtag.getText());	//공백 단위로 분리
				ArrayList<String> hashtagList = new ArrayList<>();
				while (hst.hasMoreTokens()) {
					hashtagList.add(hst.nextToken());
				}
				
				menu.modify(Integer.parseInt(price.getText()), category.getText(), materialList, hashtagList);
				try {
					menu.askToRewriteFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "메뉴 정보가 변경되었습니다!");
				removeAll();
				revalidate();
				repaint();
				StaffMainGUI move = new StaffMainGUI();
				add(move);
			}
		});
		add(searchButton);
		
		JLabel Line = new JLabel();
		Line.setBounds(30, 80, 425, 600);
		Line.setHorizontalAlignment(JLabel.CENTER);
		Line.setOpaque(true);
		Line.setBackground(BackGroundFrameGUI.topBarColor);
		add(Line);

	}
}
