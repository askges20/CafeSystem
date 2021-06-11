package gui;

import java.awt.Color;
import java.awt.Container;
import javax.swing.JFrame;

public class BackGroundFrameGUI extends JFrame{	//GUI 화면의 틀
	
	public static Color backGroundColor = new Color(0x3F2416);
	public static Color topBarColor = new Color(0xFFDA55);
	Container c = getContentPane();
	
	public BackGroundFrameGUI() {
		setTitle("Cafe App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		c.setLayout(null);
		setSize(500, 800);
		setVisible(true);
		
		c.setBackground(backGroundColor);
		
		GoToLoginGUI();
	}
	
	public void GoToLoginGUI() {
		c.removeAll();
		c.add(new LoginGUI());
		revalidate();
		repaint();
	}
}
