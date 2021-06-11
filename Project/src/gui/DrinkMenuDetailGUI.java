package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import cafe.Cafe;
import cafe.CustomerLoginAccount;
import cafe.Drink;
import cafe.Review;

public class DrinkMenuDetailGUI extends JPanel {	//음료 메뉴 상세 페이지

   int Imagewidth = 180;
   int ImageHeight = 180;
   int page = 0;
   int index;
   Cafe cafe;
   Drink menu;

   public DrinkMenuDetailGUI(Cafe cafe, int index) {
      this.index = index;
      this.cafe = cafe;
      paintGUI();
   }

   public void paintGUI() {
      setLayout(null);
      setBackground(BackGroundFrameGUI.backGroundColor);
      setSize(500, 800);
      setVisible(true);

      // 상단바
      JLabel topBar = new JLabel("메뉴 상세 정보");
      topBar.setBounds(0, 0, 500, 50);
      topBar.setHorizontalAlignment(JLabel.CENTER);
      topBar.setBackground(BackGroundFrameGUI.topBarColor);
      topBar.setOpaque(true);
      topBar.setFont(new Font("고딕", Font.BOLD, 20));
      add(topBar);

      menu = (Drink) cafe.menudrinkMgr.mList.get(index);
      // 이미지
      ImageIcon menuImageIcon = new ImageIcon(menu.filename);
      Image img = menuImageIcon.getImage();
      Image resizeImg = img.getScaledInstance(Imagewidth, ImageHeight, Image.SCALE_SMOOTH);
      ImageIcon resizingMenuImg = new ImageIcon(resizeImg);
      JLabel imageLabel = new JLabel(resizingMenuImg);
      imageLabel.setBounds(30, 150, 200, 200);
      add(imageLabel);

      JButton MenuButton = new JButton("메뉴 목록");
      MenuButton.setBounds(50, 430, 150, 50);
      add(MenuButton);
      MenuButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            removeAll();
            revalidate();
            repaint();
            add(new AllDrinkMenusGUI());
         }
      });
      
      String[] menuInfos = new String[6];
      menuInfos[0] = "메뉴명 : " + menu.name;
      menuInfos[1] = "종류 : " + menu.category;
      menuInfos[2] = "가격 : " + menu.price + "원";
      menuInfos[3] = "평점 : " + menu.star_avg;
      menuInfos[4] = "";
      for (String h : menu.hashTags) {
         menuInfos[4] += "#" + h + " ";
      }
      menuInfos[5] = "재료 : ";
      
      String tmp="";
      for(String h : menu.materials) {
         tmp = tmp+h+" ";
      }
      JTextArea materials = new JTextArea(tmp);
      materials.setFont(new Font("고딕", Font.BOLD, 15));
      materials.setBounds(250, 380, 200, 80);
      materials.setEditable(false);
      materials.setLineWrap(true);
      add(materials);

      JLabel[] infoLabels = new JLabel[menuInfos.length];
      for (int i = 0; i < infoLabels.length; i++) {
         infoLabels[i] = new JLabel(menuInfos[i]);
         infoLabels[i].setFont(new Font("고딕", Font.BOLD, 15));
         infoLabels[i].setBounds(250, 100 + 50 * i, 500, 20);
         add(infoLabels[i]);
      }

      Integer[] indexs = new Integer[cafe.reviewMgr.mList.size()];
      int j = 0;
      for (int i = 0; i < cafe.reviewMgr.mList.size(); i++) {
         Review review = (Review) cafe.reviewMgr.mList.get(i);
         if (menu.name.contentEquals(review.menu)) {
            indexs[j] = (Integer) cafe.reviewMgr.mList.indexOf(review);
            j++;
         }
      }
      
      JButton addCartButton = new JButton("장바구니 담기");
      addCartButton.setBounds(50, 380, 150, 50);
      addCartButton.addActionListener(new AddToCart());   //장바구니에 담는 이벤트 리스너
      add(addCartButton);

      // 이전 페이지 이동 버튼
      JButton PrevButton = new JButton("<<Prev");
      PrevButton.setBounds(220, 510, 80, 30);
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
      NextButton.setBounds(395, 510, 80, 30);
      NextButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            if (indexs.length > page && indexs[page + 1] != null) {
               page += 1;
               removeAll();
               revalidate();
               repaint();
               paintGUI();
            }
         }
      });
      add(NextButton);
      
      JLabel pageLabel = new JLabel("page : " + (page + 1));
      pageLabel.setBounds(300, 505, 100, 40);
      pageLabel.setHorizontalAlignment(JLabel.CENTER);
      pageLabel.setForeground(Color.WHITE);
      add(pageLabel);

      JLabel reviewexplain = new JLabel("관련 후기");
      reviewexplain.setBounds(10, 510, 200, 40);
      reviewexplain.setFont(new Font("고딕", Font.BOLD, 25));
      reviewexplain.setHorizontalAlignment(JLabel.CENTER);
      reviewexplain.setOpaque(true);
      reviewexplain.setBackground(BackGroundFrameGUI.topBarColor);
      add(reviewexplain);

      ReviewGUI[] reviewItems = new ReviewGUI[1]; // null로 초기화 후
      if (indexs[page] != null) {
         int index_review = indexs[page];
         if (index_review < cafe.reviewMgr.mList.size()) {
            reviewItems[0] = new ReviewGUI(index_review); // 객체 생성
            reviewItems[0].setSize(465, 200);
            reviewItems[0].setLocation(10, 550);
            reviewItems[0].detailButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                  removeAll();
                  revalidate();
                  repaint();
                  add(new ReviewDetailGUI(cafe, index_review));
               }
            });
            reviewItems[0].removeButton.setVisible(false);
            add(reviewItems[0]);
         }
      } else {
         JLabel reviewLabel = new JLabel("후기가 없습니다!");
         reviewLabel.setHorizontalAlignment(JLabel.CENTER);
         reviewLabel.setFont(new Font("고딕", Font.BOLD, 40));
         reviewLabel.setBackground(BackGroundFrameGUI.topBarColor);
         reviewLabel.setOpaque(true);
         reviewLabel.setBounds(10, 550, 465, 200);
         add(reviewLabel);
      }

      JLabel Line = new JLabel();
      Line.setBounds(10, 60, 465, 430);
      Line.setHorizontalAlignment(JLabel.CENTER);
      Line.setOpaque(true);
      Line.setBackground(Color.white);
      add(Line);
   }
   
   class AddToCart implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         CustomerLoginAccount account = CustomerLoginAccount.getAccount();
         account.addToCart(menu);
      }
   }

}