package staffgui;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cafe.Menu;

public class MenuGUI extends JPanel {	//SearchMenuGUI에 출력되는 메뉴 요소

	Menu menu;
	JButton detailButton;
	JButton modifyButton;
	int[] locationY = { 130, 150, 170 };

	public MenuGUI(Menu menu) {
		setLayout(null);
		this.menu = menu;
		setBackground(Color.white);

		ImageIcon image = new ImageIcon(menu.filename);
		Image img = image.getImage();
		Image resizeImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon resizingMenuImg = new ImageIcon(resizeImg);
		detailButton = new JButton(resizingMenuImg);
		detailButton.setBounds(60, 10, 100, 100);
		add(detailButton);

		JLabel[] labels = { new JLabel(menu.name), new JLabel(menu.price + "원"), new JLabel(menu.star_avg + "/5") };

		for (int i = 0; i < labels.length; i++) {
			labels[i].setHorizontalAlignment(JLabel.CENTER);
			labels[i].setBounds(0, locationY[i], 220, 20);
			add(labels[i]);
		}

		modifyButton = new JButton("수정하기");
		modifyButton.setBounds(60, 200, 100, 50);
		add(modifyButton);
	}
}