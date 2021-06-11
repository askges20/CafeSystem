package cafe;

import java.util.Scanner;


public class OrderedMenu {	//주문한 메뉴에 대한 클래스

	public Menu menu;	//메뉴 객체
	public int num;		//주문 개수
	public int priceSum;	//가격

	public OrderedMenu(Menu menu, Scanner scan) {
		this.menu = menu;
		this.num = scan.nextInt();
		updateAcc();
		calPriceSum();
	}
	
	public OrderedMenu(Menu menu, int num) {
		this.menu = menu;
		this.num = num;
		calPriceSum();
	}

	public void print() {
		System.out.printf("\t%s %d개 = %d원\n", menu.name, num, menu.price * num);
	}

	public boolean matches(String kwd) {
		if(menu.name.equals(kwd))
			return true;
		return false;
	}
	
	public int getSum() {
		return priceSum;
	}
	
	public boolean isDrink() {
		if(Cafe.menudrinkMgr.find(menu.name) == null)	//음료 목록에 없는 메뉴이면 디저트
			return false;
		return true;
	}
	
	public void calPriceSum() {
		priceSum = menu.price * num;
	}
	
	public void updateAcc() {
		menu.acc_cnt += this.num;
	}

}
