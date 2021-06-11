package staffgui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import cafe.Dessert;
import cafe.Drink;
import gui.BackGroundFrameGUI;
import gui.StartGUI;

public class AddMenuGUI extends JPanel {	//메뉴 추가
	int[] LabellocationY = { 120, 200, 280, 360, 440, 520 };

	JTextField name;
	JTextField price;
	JTextField category;
	JTextField hashtag;
	JTextField material;
	JRadioButton isDrink;
	JRadioButton isDessert;
	JButton imageButton;
	BufferedImage image = null;
	int imageWidth;
	int imageHeight;
	String fileName;
	
	String filePath;

	public AddMenuGUI() {
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
				StaffMainGUI move = new StaffMainGUI(); // 직원 메인 페이지로 이동
				add(move);
			}
		});
		add(moveMainButton);

		JLabel topBar = new JLabel("음료/디저트 정보 추가");
		topBar.setBounds(0, 0, 500, 50);
		topBar.setHorizontalAlignment(JLabel.CENTER);
		topBar.setBackground(BackGroundFrameGUI.topBarColor);
		topBar.setOpaque(true);
		topBar.setFont(new Font("고딕", Font.BOLD, 20));
		add(topBar);

		JLabel[] labels = { new JLabel("메뉴이름 : "), new JLabel("가격 : "), new JLabel("종류 : "), new JLabel("해시태그 : "),
				new JLabel("재료 : "), new JLabel("메뉴 이미지 : ") };

		for (int i = 0; i < labels.length; i++) {
			labels[i].setBounds(50, LabellocationY[i], 500, 30);
			add(labels[i]);
		}

		name = new JTextField(); // 메뉴명
		name.setBounds(120, LabellocationY[0], 310, 30);
		add(name);

		price = new JTextField(); // 가격
		price.setBounds(120, LabellocationY[1], 310, 30);
		add(price);

		category = new JTextField(); // 종류
		category.setBounds(300, LabellocationY[2], 130, 30);
		add(category);

		hashtag = new JTextField(); // 해시태그
		hashtag.setBounds(120, LabellocationY[3], 310, 30);
		add(hashtag);

		isDrink = new JRadioButton("음료"); // 음료인지 체크
		isDrink.setBounds(100, LabellocationY[2], 60, 30);
		isDrink.setOpaque(false);
		add(isDrink);

		isDessert = new JRadioButton("디저트"); // 디저트인지 체크
		isDessert.setBounds(160, LabellocationY[2], 80, 30);
		isDessert.setOpaque(false);
		add(isDessert);
		
		material = new JTextField(); // 해시태그
		material.setBounds(120, LabellocationY[4], 310, 30);
		add(material);

		imageButton = new JButton("이미지 불러오기");
		imageButton.setBounds(150, LabellocationY[5], 150, 30);
		imageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG Images", "jpg");
				JFileChooser chooser = new JFileChooser();
				chooser.setFileFilter(filter);
				int ret = chooser.showOpenDialog(null);
				if(ret == JFileChooser.APPROVE_OPTION) {
					filePath=chooser.getSelectedFile().getAbsolutePath();
					fileName = chooser.getSelectedFile().getName();
				}
				try {
					image = ImageIO.read(new File(filePath));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		add(imageButton);

		JButton searchButton = new JButton("추가");
		searchButton.setBounds(150, 580, 200, 70);
		searchButton.addActionListener(new addMenuListener());
		add(searchButton);

		JLabel Line = new JLabel();
		Line.setBounds(30, 80, 425, 600);
		Line.setHorizontalAlignment(JLabel.CENTER);
		Line.setOpaque(true);
		Line.setBackground(BackGroundFrameGUI.topBarColor);
		add(Line);
	}

	public class addMenuListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (isDrink.isSelected()) {
				if (isDessert.isSelected()) // 음료, 디저트 둘 다 선택했을 경우
					JOptionPane.showMessageDialog(null, "음료와 디저트 중 하나만 선택해주세요!");
				else { // 음료 메뉴 추가
					Drink drink = new Drink();
					drink.name = name.getText();
					drink.price = Integer.parseInt(price.getText());
					drink.category = category.getText();
					
					StringTokenizer hst = new StringTokenizer(hashtag.getText());	//공백 단위로 분리
					ArrayList<String> hashtagList = new ArrayList<>();
					while (hst.hasMoreTokens()) {
						hashtagList.add(hst.nextToken());
					}
					drink.hashTags = hashtagList;
					
					StringTokenizer mst = new StringTokenizer(material.getText());	//공백 단위로 분리
					ArrayList<String> materialList = new ArrayList<>();
					while (mst.hasMoreTokens()) {
						materialList.add(mst.nextToken());
					}
					drink.materials = materialList;
					
					drink.filename = "image/drink/" + fileName;
					
					StartGUI.cafe.menudrinkMgr.addToList(drink);
					try {
						StartGUI.cafe.menudrinkMgr.rewriteFile(new FileWriter("menudrink.txt"));
					} catch (IOException e2) {
						e2.printStackTrace();
					}
					try {
						ImageIO.write(image, "jpg", new File(drink.filename));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "음료가 추가되었습니다!");
					removeAll();
					revalidate();
					repaint();
					StaffMainGUI move = new StaffMainGUI();
					add(move);
				}
			} else {
				if (isDessert.isSelected()) { // 디저트 메뉴 추가
					Dessert dessert = new Dessert();
					dessert.name = name.getText();
					dessert.price = Integer.parseInt(price.getText());
					dessert.category = category.getText();
					
					StringTokenizer st = new StringTokenizer(hashtag.getText());	//공백 단위로 분리
					ArrayList<String> hashtagList = new ArrayList<>();
					while (st.hasMoreTokens()) {
						hashtagList.add(st.nextToken());
					}
					dessert.hashTags = hashtagList;
					
					StringTokenizer mst = new StringTokenizer(material.getText());	//공백 단위로 분리
					ArrayList<String> materialList = new ArrayList<>();
					while (mst.hasMoreTokens()) {
						materialList.add(mst.nextToken());
					}
					dessert.materials = materialList;
					
					dessert.filename = "image/dessert/" + fileName;
					
					StartGUI.cafe.menudessertMgr.addToList(dessert);
					try {
						StartGUI.cafe.menudessertMgr.rewriteFile(new FileWriter("menudessert.txt"));
					} catch (IOException e2) {
						e2.printStackTrace();
					}
					try {
						ImageIO.write(image, "jpg", new File(dessert.filename));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "디저트가 추가되었습니다!");
					removeAll();
					revalidate();
					repaint();
					StaffMainGUI move = new StaffMainGUI();
					add(move);
				} else { // 둘 다 선택되지 않았을 경우
					JOptionPane.showMessageDialog(null, "음료와 디저트 중 하나를 선택해주세요!");
				}
			}
		}
	}
}
