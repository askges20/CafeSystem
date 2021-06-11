package gui;

import java.io.IOException;

import cafe.Cafe;

public class StartGUI {	//StartGUI를 실행시켜야 GUI 화면이 나옴
	public static Cafe cafe = new Cafe();
	public static BackGroundFrameGUI frame;
	
	public static void main(String[] args) {
		try {
			cafe.run();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		frame = new BackGroundFrameGUI();
	}
}
