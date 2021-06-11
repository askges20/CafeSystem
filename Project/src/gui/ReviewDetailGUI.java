package gui;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import cafe.Cafe;
import cafe.Dessert;
import cafe.Drink;
import cafe.Review;

public class ReviewDetailGUI extends JPanel{	//후기 상세 정보
   int Imagewidth = 180;
   int ImageHeight = 180;
   Cafe cafe;
   
   public ReviewDetailGUI(Cafe cafe, int index) {
      this.cafe = cafe;

      setLayout(null);
      setBackground(BackGroundFrameGUI.backGroundColor);
      setSize(500, 800);

      // 상단바
      JLabel topBar = new JLabel("후기 상세 정보");
      topBar.setBounds(0, 0, 500, 50);
      topBar.setHorizontalAlignment(JLabel.CENTER);
      topBar.setBackground(BackGroundFrameGUI.topBarColor);
      topBar.setOpaque(true);
      topBar.setFont(new Font("고딕", Font.BOLD, 20));
      add(topBar);
      
      Review review = (Review) cafe.reviewMgr.mList.get(index);
      // 이미지
      ImageIcon menuImageIcon = new ImageIcon(review.menuObject.filename);
      Image img = menuImageIcon.getImage();
      Image resizeImg = img.getScaledInstance(Imagewidth, ImageHeight, Image.SCALE_SMOOTH);
      ImageIcon resizingMenuImg = new ImageIcon(resizeImg);
      JLabel imageLabel = new JLabel(resizingMenuImg);
      imageLabel.setBounds(150, 150, Imagewidth, ImageHeight);
      add(imageLabel);
      
      JButton moveReviewButton = new JButton("후기 목록으로 이동");
      moveReviewButton.setBounds(150, 640, 180, 50);
      add(moveReviewButton);
      
      moveReviewButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            removeAll();
            revalidate();
            repaint();
            add(new AllReviewGUI());
         }
      });
      
      String[] reviewInfos = new String[7];
      reviewInfos[0] = "메뉴명 : " + review.menu;
      reviewInfos[1] = "별점 : " + review.star;
      reviewInfos[2] = "작성자 : " + review.id;
      reviewInfos[3] = "내용";
      JLabel[] infoLabels = new JLabel[reviewInfos.length];
      for (int i = 0; i < infoLabels.length; i++) {
         infoLabels[i] = new JLabel(reviewInfos[i]);
         infoLabels[i].setFont(new Font("고딕", Font.BOLD, 15));
         infoLabels[i].setBounds(70, 370 + 30 * i, 500, 100);
         add(infoLabels[i]);
      }
      JTextArea content = new JTextArea(review.content);
      content.setBounds(70, 530, 350, 100);
      content.setEditable(false);
      content.setLineWrap(true);
      content.setFont(new Font("고딕", Font.BOLD, 15));
      add(content);
      
      JButton moveMenuButton = new JButton("해당 메뉴로 이동");
      moveMenuButton.setBounds(150, 340, 180, 50);
      add(moveMenuButton);
      
      moveMenuButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            removeAll();
            revalidate();
            repaint();
            reviewToMenu(review,index);
         }
      });
      
      JLabel Line = new JLabel();
      Line.setBounds(30, 90, 425, 620);
      Line.setHorizontalAlignment(JLabel.CENTER);
      Line.setOpaque(true);
      Line.setBackground(BackGroundFrameGUI.topBarColor);
      add(Line);
      setVisible(true);
   }
   
   public int reviewToMenu(Review review, int index) {
      
      for(int i=0; i<Cafe.menudrinkMgr.mList.size();i++) {
         Drink compare = (Drink)Cafe.menudrinkMgr.mList.get(i);
         if(review.menu.contentEquals(compare.name)) {
            add(new DrinkMenuDetailGUI(cafe,i));
            return i;
         }
      }
      
      for(int i=0; i<Cafe.menudessertMgr.mList.size();i++) {
         Dessert compare = (Dessert)Cafe.menudessertMgr.mList.get(i);
         if(review.menu.contentEquals(compare.name)) {
            add(new DessertMenuDetailGUI(cafe,i));
            return i;
         }
      }
      
      return 0;
   }
}