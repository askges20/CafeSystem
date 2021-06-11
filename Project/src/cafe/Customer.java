package cafe;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import mgr.Manageable;

public class Customer extends User {
	public ArrayList<Order> orderList = new ArrayList<>();
	public ArrayList<String> allergyList = new ArrayList<>();

	@Override
	public void read(Scanner scan) {
		super.read(scan);
		while (true) {
			String allergy = scan.next();
			if (allergy.equals("X"))
				break;
			allergyList.add(allergy);
		}
	}

	public void read(String text, String text2, String text3, String string) {
		this.id = text;
		this.pw = text2;
		this.name = text3;
		StringTokenizer st = new StringTokenizer(string);
		ArrayList<String> splitallergyList = new ArrayList<>();
		while (st.hasMoreTokens()) {
			splitallergyList.add(st.nextToken());
		}
		int i = 0;
		while (true) {
			String allergy = splitallergyList.get(i);
			if (allergy.equals("X"))
				break;
			allergyList.add(allergy);
			i++;
		}
	}

	@Override
	public void print() {
		super.print();
		System.out.print("알레르기 : ");
		if (allergyList.size() == 0)
			System.out.print("없음");
		else {
			for (String a : allergyList) {
				System.out.printf("%s ", a);
			}
		}
		System.out.println();
		for (Order o : orderList) {
			o.print();
		}
		System.out.println();
	}

	public void addToOrderList(Order order) {
		orderList.add(order);
	}

	@Override
	public void writeToFile(BufferedWriter bw) throws IOException {
		bw.append(id + " ");
		bw.append(pw + " ");
		bw.append(name + " ");
		for (String h : allergyList) {
			bw.append(h + " ");
		}
		bw.append("X\n");
	}

	public ArrayList<String> analyzePreferCategory() { // 선호하는 메뉴
		System.out.println("*선호하는 음료 종류 분석 중...");
		ArrayList<String> preferCategory = new ArrayList<>(); // 선호하는 음료 종류 저장할 곳
		double totalOrderNum = orderList.size();
		ArrayList<String> existCategory = new ArrayList<>(); // 주문했던 음료 종류 저장
		for (Order ol : orderList) {
			ol.addExistCategory(existCategory);
		}

		for (String ec : existCategory) {
			double cnt = 0;
			for (Order ol : orderList)
				if (ol.compareOrderedMenuCategory(ec)) // 주문 내역에 있는 메뉴들 중 해당 종류의 음료가 있으면
					cnt++;
			if (cnt / totalOrderNum >= 0.5)
				System.out.println("-" + ec + " : 전체 " + (int) totalOrderNum + "개의 주문내역 중 " + (int) cnt + "번 등장");
			preferCategory.add(ec);
		}
		return preferCategory;
	}

	public ArrayList<String> analyzePreferHashTag() {
		System.out.println("*선호하는 음료 해시태그 분석 중...");
		ArrayList<String> preferHashTag = new ArrayList<>(); // 선호하는 해시태그를 저장할 곳
		double totalOrderNum = orderList.size();
		ArrayList<String> existHashTag = new ArrayList<>(); // 주문했던 음료 해시태그 저장
		for (Order ol : orderList) {
			ol.addExistHashTag(existHashTag);
		}

		for (String eh : existHashTag) {
			double cnt = 0;
			for (Order ol : orderList)
				if (ol.compareOrderedMenuHashTag(eh)) // 주문 내역에 있는 메뉴들 중 해당 종류의 음료가 있으면
					cnt++;
			if (cnt / totalOrderNum >= 0.5) {
				System.out.println("#" + eh + " : 전체 " + (int) totalOrderNum + "개의 주문내역 중 " + (int) cnt + "번 등장");
				preferHashTag.add(eh);
			}
		}
		return preferHashTag;
	}

}
