package gui;

import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cafe.Order;

public class OrderGUI extends JPanel{	//MyOrderGUI(고객 자신의 주문내역 페이지)의 각 요소들
	int orderNum;
	Order order;
	int[] locationX = { 10, 10 };
	int[] locationY = { 30, 90 };
	int Imagewidth=150;
	int ImageHeight=160;
	JButton detailButton;

	public OrderGUI(int ordernum) {
		setLayout(null);
		orderNum = ordernum;
		order = (Order) StartGUI.cafe.orderMgr.mList.get(orderNum);
		setBackground(BackGroundFrameGUI.topBarColor);
		

		JLabel[] labels = { new JLabel("주문번호 : "+(orderNum+1)),
							new JLabel("주문날짜 : "+order.date) };
		//이미지넣는부분
		
		for (int i = 0; i < labels.length; i++) {
			labels[i].setBounds(locationX[i], locationY[i], 400, 20);
			labels[i].setFont(new Font("고딕", Font.BOLD, 20));
			add(labels[i]);
		}
		
		detailButton = new JButton("상세 정보");
		detailButton.setBounds(300, 0, 140, 180);
		add(detailButton);
	}
}
