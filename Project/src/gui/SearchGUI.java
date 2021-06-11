package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import cafe.Customer;
import cafe.Dessert;
import cafe.Drink;

public class SearchGUI extends JPanel{	//상세 검색 화면
	int[] LabellocationX = { 50, 50, 50, 50};
	int[] LabellocationY = { 120, 220, 320, 420};
	
	public SearchGUI() {
		setLayout(null);
		setSize(500, 800);
		setLocation(0, 0);
		setBackground(BackGroundFrameGUI.backGroundColor);
		
		JButton moveMainButton = new JButton("메인 이동");
		moveMainButton.setBounds(20, 700, 90, 30);
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
		
		JLabel topBar = new JLabel("검색");
		topBar.setBounds(0, 0, 500, 50);
		topBar.setHorizontalAlignment(JLabel.CENTER);
		topBar.setBackground(BackGroundFrameGUI.topBarColor);
		topBar.setOpaque(true);
		topBar.setFont(new Font("고딕", Font.BOLD, 20));
		add(topBar);
		
		JLabel[] labels = {new JLabel("가격 :                                                         ~"),
						   new JLabel("종류 : "), 
						   new JLabel("해시태그 : ")};
		
		for (int i = 0; i < labels.length; i++) {
			labels[i].setBounds(LabellocationX[i], LabellocationY[i], 500, 30);
			//labels[i].setForeground(Color.white);
			add(labels[i]);
		}
		JTextField price_min = new JTextField("0");
		price_min.setBounds(100, 120, 130, 30);
		add(price_min);
		JTextField price_max = new JTextField("0");
		price_max.setBounds(300, 120, 130, 30);
		add(price_max);
		
		ArrayList<String> menudrink = new ArrayList<>();
		for(int i=0; i<cafe.Cafe.menudrinkMgr.mList.size();i++) {
			Drink menudrink_in = (Drink)cafe.Cafe.menudrinkMgr.mList.get(i);
			if(!menudrink.contains(menudrink_in.category))
				menudrink.add(menudrink_in.category);
		}
		ArrayList<String> menudessert = new ArrayList<>();
		for(int i=0; i<cafe.Cafe.menudessertMgr.mList.size();i++) {
			Dessert menudessert_in = (Dessert)cafe.Cafe.menudessertMgr.mList.get(i);
			if(!menudessert.contains(menudessert_in.category))
				menudessert.add(menudessert_in.category);
		}
		
		String[] menus = new String[menudrink.size()+menudessert.size()];
		for(int i=0;i<menudessert.size();i++) {
			menus[i] = menudessert.get(i);
		}
		int j=0;
		for(int i=menudessert.size();i<menudrink.size()+menudessert.size();i++) {
			menus[i] = menudrink.get(j);
			j++;
		}
		
		JComboBox kindMenu = new JComboBox(menus);
		kindMenu.setBounds(100, 220, 310, 30);
		add(kindMenu);
		
		JTextField hashtag = new JTextField();
		hashtag.setBounds(120, 320, 310, 30);
		add(hashtag);
		
		JRadioButton allergy = new JRadioButton("알레르기 재료 제외");
		allergy.setBounds(50, 420, 150, 30);
		allergy.setBorderPainted(false);
		add(allergy);
		
		JButton searchButton = new JButton("검색");
		searchButton.setBounds(150, 580, 200, 70);
		searchButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 String manner = kindMenu.getSelectedItem().toString();
	             removeAll();
	             add(new AllDetailSearchGUI((Customer)LoginGUI.user, Integer.parseInt(price_min.getText()), Integer.parseInt(price_max.getText()), 
	            		 manner, hashtag.getText(), allergy.isSelected()));
	             revalidate();
	             repaint();
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