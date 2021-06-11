package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

import javax.swing.JLabel;
import javax.swing.JTextField;

import cafe.Customer;

public class RegisterGUI extends JPanel {	//회원 가입 페이지
   int[] LabellocationX = { 90, 90, 90, 90};
   int[] LabellocationY = { 170, 270, 370, 470};
   int[] TextlocationX = { 200, 200, 200, 200};
   int[] TextlocationY = { 170, 270, 370, 470};

   public RegisterGUI() {
      setLayout(null);
      setSize(500, 800);
      setLocation(0, 0);
      setBackground(BackGroundFrameGUI.backGroundColor);
      
      JLabel topBar = new JLabel("회원가입");
      topBar.setBounds(0, 0, 500, 50);
      topBar.setHorizontalAlignment(JLabel.CENTER);
      topBar.setBackground(BackGroundFrameGUI.topBarColor);
      topBar.setOpaque(true);
      topBar.setFont(new Font("고딕", Font.BOLD, 20));
      add(topBar);
      
      JLabel[] labels = {new JLabel("아이디"), new JLabel("비밀번호"), new JLabel("이름"), new JLabel("알레르기정보")};
      JTextField[] texts = {new JTextField(), new JTextField(), new JTextField(), new JTextField()};
      for (int i = 0; i < labels.length; i++) {
         labels[i].setBounds(LabellocationX[i], LabellocationY[i],100, 50);
         labels[i].setForeground(Color.WHITE);
         add(labels[i]);
      }
      JLabel caution = new JLabel("< 알레르기가 2가지 이상일 경우 띄어쓰기로 분리해주세요. >");
      caution.setBounds(80,550,1000,30);
      caution.setForeground(Color.WHITE);
      add(caution);
      
      for (int i = 0; i < labels.length; i++) {
         texts[i].setBounds(TextlocationX[i], TextlocationY[i],200, 50);
         add(texts[i]);
      }
      
      JButton registerButton = new JButton("등록");
      JButton exitButton = new JButton("취소");
      
      add(registerButton);
      add(exitButton);

      exitButton.setBounds(90, 620, 150, 50);
      registerButton.setBounds(250, 620, 150, 50);

      exitButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            removeAll();
            revalidate();
            repaint();
            LoginGUI move = new LoginGUI();//로그인 창으로 이동
            add(move);
         }
      });
      
      registerButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            removeAll();
            revalidate();
            repaint();
            LoginGUI move = new LoginGUI();//로그인 창으로 이동
            
            try {
               Customer customer = new Customer();
               customer.read(texts[0].getText(), texts[1].getText(), texts[2].getText(),texts[3].getText()+" X");
               StartGUI.cafe.addCustomerData(customer);
            } catch (IOException e1) {
               e1.printStackTrace();
            }
            
            add(move);
         }
      });
      setVisible(true);
   }
}