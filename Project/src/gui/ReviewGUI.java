package gui;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cafe.Review;

public class ReviewGUI extends JPanel {	//후기 목록에 나오는 각 요소들
   int reviewNum;
   Review review;
   int[] locationX = { 220, 220, 220, 220};
   int[] locationY = { 30, 60, 90, 120};
   int Imagewidth=150;
   int ImageHeight=160;
   JButton detailButton;
   JButton removeButton;

   public ReviewGUI(int num) {
      setLayout(null);
      reviewNum = num;
      review = (Review) StartGUI.cafe.reviewMgr.mList.get(reviewNum);
      setBackground(BackGroundFrameGUI.topBarColor);
      
      
      ImageIcon image=new ImageIcon(review.menuObject.filename);
      Image img = image.getImage();
      Image resizeImg=img.getScaledInstance(Imagewidth, ImageHeight, Image.SCALE_SMOOTH);
      ImageIcon resizingMenuImg=new ImageIcon(resizeImg);
      JLabel menuImage = new JLabel(resizingMenuImg);
    //이미지넣는부분
      menuImage.setBounds(10, 10, Imagewidth, ImageHeight);
      add(menuImage);
      

      JLabel[] labels = { new JLabel("메뉴이름 : "+review.menu), 
                     new JLabel("작성자 : "+review.id), 
                     new JLabel("별점 : "+review.star+" / 5"), 
                     new JLabel("내용 : "+review.content) };
      
      for (int i = 0; i < labels.length; i++) {
         labels[i].setBounds(locationX[i], locationY[i], 200, 20);
         add(labels[i]);
      }
      
      detailButton = new JButton("상세 정보");
      detailButton.setBounds(350, 160, 90, 20);
      add(detailButton);
      
      removeButton = new JButton("삭제");
      removeButton.setBounds(260, 160, 90, 20);
      //removeButton.setFont(new Font("고딕", Font.BOLD, 8));
      add(removeButton);
   }

}