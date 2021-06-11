package cafe;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import mgr.Manageable;

public class Order implements Manageable {
	public String id;
	Customer customer;
	public ArrayList<OrderedMenu> orderedMenuList = new ArrayList<>();
	public String date;
	public int totalSum; // 주문 시, 총 계산 금액

	@Override
	public void read(Scanner scan) {
		id = scan.next();
		customer = (Customer) Cafe.customerMgr.find(id);

		while (true) {
			String menuName = scan.next();
			if (menuName.equals("0")) { // 0이면 메뉴 입력 종료
				break;
			}

			Menu menu1 = (Menu) Cafe.menudrinkMgr.find(menuName); // Menu 객체 검색
			Menu menu2 = (Menu) Cafe.menudessertMgr.find(menuName);

			if (menu1 != null) { // 음료일 때
				orderedMenuList.add(new OrderedMenu(menu1, scan));
			} else { // 디저트일 때
				orderedMenuList.add(new OrderedMenu(menu2, scan));
			}
		}
		customer.addToOrderList(this);
		date = scan.next();
		
		calTotalSum();	//최종 결제 금액 계산
	}

	@Override
	public void print() {
		System.out.printf("<주문 아이디 : %s> 주문 날짜 : %s\n", id, date);
		System.out.println("\t주문 메뉴 :");
		for (OrderedMenu om : orderedMenuList)
			om.print();
		System.out.println("최종 결제 금액 : "+ totalSum + "원");
	}

	@Override
	public boolean matches(String kwd) {
		if ((id).contentEquals(kwd))
			return true;
		return false;
	}

	@Override
	public void writeToFile(BufferedWriter bw) throws IOException {
		bw.append(id + " ");
		for (OrderedMenu m : orderedMenuList) {
			bw.append(m.menu.name + " ");
			bw.append(m.num + " ");
		}
		bw.append("0 ");
		bw.append(date + "\n");
	}

	

	public void addExistCategory(ArrayList<String> existCategory) { // 주문했던 음료 종류 리턴
		for (OrderedMenu oml : orderedMenuList) {
			if (oml.isDrink() && !existCategory.contains(oml.menu.category)) { // 중복 없이 저장
				existCategory.add(oml.menu.category);
			}
		}
	}
	public void addExistHashTag(ArrayList<String> existHashTag) { // 주문했던 음료 해시태그 리턴
		for (OrderedMenu oml : orderedMenuList) {
			if (oml.isDrink()) {
				for (String ht : oml.menu.hashTags) {
					if (!existHashTag.contains(ht)) { // 중복 없이 저장
						existHashTag.add(ht);
					}
				}
			}
		}
	}
	public boolean compareOrderedMenuCategory(String category) {
		for (OrderedMenu oml : orderedMenuList) {
			if (oml.isDrink() && category.equals(oml.menu.category))
				return true;
		}
		return false;
	}
	public boolean compareOrderedMenuHashTag(String hashTag) {
		for (OrderedMenu oml : orderedMenuList) {
			if (oml.isDrink()) {
				for (String ht : oml.menu.hashTags) {
					if (hashTag.equals(ht))
						return true;
				}
			}
		}
		return false;
	}

	// orderedMenuList에 넣음.
	public void addOrderedMenuList(OrderedMenu om) {
		orderedMenuList.add(om);
	}

	// 총 합계 계산용
	public void calTotalSum() {
		totalSum = 0;
		for (OrderedMenu om : orderedMenuList) {
			totalSum += om.getSum();
		}
	}
	
	public void setOrderCustomer(Customer customer) {	//CustomerLoginAccount 에서 사용
		this.customer = customer;
		if(customer!=null)
			this.id = customer.id;
		else
			this.id = null;
	}
	
	public boolean addMenuToOrderedMenuList(Menu menu) {
		//장바구니에서 쓰이는 메소드. 장바구니에 이미 있으면 num만 증가시키고 true 리턴, 없으면 메뉴 자체를 추가하고 false 리턴
		for(OrderedMenu om: orderedMenuList) {
			if(om.matches(menu.name)) {	//이미 장바구니에 있는 메뉴인 경우
				om.num++;	//주문 개수만 증가
				om.calPriceSum();	//가격 합계 다시 계산
				calTotalSum();
				return true;
			}
		}
		//장바구니에 없는 경우
		OrderedMenu newMenu = new OrderedMenu(menu, 1);
		orderedMenuList.add(newMenu);
		calTotalSum();
		return false;
	}

}
