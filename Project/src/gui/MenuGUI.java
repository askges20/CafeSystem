package gui;
import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cafe.Menu;

public class MenuGUI extends JPanel {	//메뉴 검색 결과에 나오는 각 요소들

   Menu menu;
   JButton detailButton;
   int[] locationY = { 110, 130, 150};

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

   }
}