package cafe;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.StringTokenizer;

import mgr.*;

public class Cafe extends Manager {
	Scanner scan = new Scanner(System.in);
	public static Manager customerMgr = new Manager();
	public static Manager staffMgr = new Manager();
	public static Manager reviewMgr = new Manager();
	public static Manager orderMgr = new Manager();
	public static Manager menudessertMgr = new Manager();
	public static Manager menudrinkMgr = new Manager();

	// 메뉴 추천에 사용되는 어레이리스트
	public static ArrayList<Drink> bothSatisfiedDrink = new ArrayList<>(); // 종류, 해시태그 모두 만족하는 음료
	public static ArrayList<Drink> categorySatisfiedDrink = new ArrayList<>(); // 종류만 만족하는 음료
	public static ArrayList<Drink> hashTagSatisfiedDrink = new ArrayList<>(); // 해시태그만 만족하는 음료
	public static ArrayList<String> preferCategory = new ArrayList<>();
	public static ArrayList<String> preferHashTag = new ArrayList<>();

	// 검색에 사용되는 어레이리스트
	public static ArrayList<Integer> searchDrinkIdx = new ArrayList<>();
	public static ArrayList<Integer> searchDessertIdx = new ArrayList<>();
	public static ArrayList<Drink> searchDrink = new ArrayList<>();
	public static ArrayList<Dessert> searchDessert = new ArrayList<>();

	public void detailSearchDrink(Customer user, int minprice, int maxprice, String category, String hashtag,
			boolean allergy) { // 상세 검색에 사용
		StringTokenizer st = new StringTokenizer(hashtag);
		ArrayList<String> hashtagList = new ArrayList<>();
		while (st.hasMoreTokens()) {
			hashtagList.add(st.nextToken());
		}
		if (hashtag.contentEquals("")) {
			for (Manageable d : menudrinkMgr.mList) {
				searchDrink.add((Drink) d);
			}
		} else {
			for (Manageable d : menudrinkMgr.mList) {
				for (String s : ((Drink) d).hashTags) {
					if (hashtagList.contains(s)) {
						searchDrink.add((Drink) d);
						break;
					}
				}
			}
		}

		ArrayList<Drink> tmplist = new ArrayList<>();
		tmplist.clear();
		tmplist.addAll(searchDrink);
		// 가격
		for (Drink d : tmplist) {
			if (!d.matchesPrice(minprice, maxprice))
				searchDrink.remove(d);
		}

		tmplist.clear();
		tmplist.addAll(searchDrink);
		// 종류
		for (Drink d : tmplist) {
			if (!d.category.contentEquals(category))
				searchDrink.remove(d);
		}

		tmplist.clear();
		tmplist.addAll(searchDrink);
		// 알레르기유무
		if (allergy) {
			ArrayList<String> allergyList = user.allergyList;
			for (Drink d : tmplist) {
				if (d.matchesallergy(allergyList)) {
					searchDrink.remove(d);
					break;
				}

			}
		}

	}

	public void detailSearchDessert(Customer user, int minprice, int maxprice, String category, String hashtag,
			boolean allergy) { // 상세 검색에 사용
		StringTokenizer st = new StringTokenizer(hashtag);
		ArrayList<String> hashtagList = new ArrayList<>();
		while (st.hasMoreTokens()) {
			hashtagList.add(st.nextToken());
		}
		if (hashtag.contentEquals("")) {
			for (Manageable d : menudessertMgr.mList) {
				searchDessert.add((Dessert) d);
			}
		} else {
			for (Manageable d : menudessertMgr.mList) {
				for (String s : ((Dessert) d).hashTags) {
					if (hashtagList.contains(s)) {
						searchDessert.add((Dessert) d);
						break;
					}
				}
			}
		}

		ArrayList<Dessert> tmplist = new ArrayList<>();
		tmplist.clear();
		tmplist.addAll(searchDessert);
		// 가격
		for (Dessert d : tmplist) {
			if (!d.matchesPrice(minprice, maxprice))
				searchDessert.remove(d);
		}

		tmplist.clear();
		tmplist.addAll(searchDessert);
		// 종류
		for (Dessert d : tmplist) {
			if (!d.category.contentEquals(category))
				searchDessert.remove(d);
		}

		tmplist.clear();
		tmplist.addAll(searchDessert);
		// 알레르기유무
		if (allergy) {
			ArrayList<String> allergyList = user.allergyList;
			for (Dessert d : tmplist) {
				for (String s : d.materials) {
					for (String s2 : allergyList) {
						if (s.contains(s2)) {
							searchDessert.remove(d);
							break;
						}
					}
				}

			}
		}
	}

	public void basicSearchDrink(String name) { // 기본 검색에 사용
		for (int i = 0; i < menudrinkMgr.mList.size(); i++) {
			Drink d = (Drink) menudrinkMgr.mList.get(i);
			if (d.matches2(name))
				searchDrinkIdx.add(i);
		}
	}

	public void basicSearchDessert(String name) { // 기본 검색에 사용
		for (int i = 0; i < menudessertMgr.mList.size(); i++) {
			Dessert d = (Dessert) menudessertMgr.mList.get(i);
			if (d.matches2(name))
				searchDessertIdx.add(i);
		}
	}

	public void run() throws IOException {
		readFiles();
		calStars();
		sortDrinkSale();
		// showFunctionMenu();
	}

	public void readFiles() { // 파일 읽기
		customerMgr.readAll("customer.txt", new Factory() {
			public Manageable create() {
				return new Customer();
			}
		});

		staffMgr.readAll("staff.txt", new Factory() {
			public Manageable create() {
				return new Staff();
			}
		});

		menudessertMgr.readAll("menudessert.txt", new Factory() {
			public Manageable create() {
				return new Dessert();
			}
		});

		menudrinkMgr.readAll("menudrink.txt", new Factory() {
			public Manageable create() {
				return new Drink();
			}
		});

		orderMgr.readAll("order.txt", new Factory() {
			public Manageable create() {
				return new Order();
			}
		});

		reviewMgr.readAll("review.txt", new Factory() {
			public Manageable create() {
				return new Review();
			}
		});
	}

	public void calStars() {
		// 별점 평균 구하기
		for (Manageable rw : reviewMgr.mList) {
			Review tmp = (Review) rw;

			Manageable m = menudrinkMgr.find(tmp.menu);
			if (m != null) {
				Drink md = (Drink) m;
				md.star_cnt++;
				md.star += (float) tmp.star;
				md.star_avg = md.star / md.star_cnt;
			}

			m = menudessertMgr.find(tmp.menu);
			if (m != null) {
				Dessert md = (Dessert) m;
				md.star_cnt++;
				md.star += (float) tmp.star;
				md.star_avg = md.star / md.star_cnt;
			}
		}

		String name = "";
		float star = 0;
		for (Manageable rw : menudrinkMgr.mList) {
			Drink md = (Drink) rw;
			if (md.name.equals(name)) {
				md.star_avg = star;
			} else {
				name = md.name;
				star = md.star_avg;
			}
		}
	}

	public void showFunctionMenu() throws IOException { // 콘솔 단계에서 사용했던 함수입니다. GUI에서는 사용되지 않고 있습니다.
		Scanner scan = new Scanner(System.in);
		boolean end = false;
		while (true) {
			System.out.println("****현재 제공하는 기능들****");
			System.out.println("(1)메뉴 데이터 추가 (2)메뉴 데이터 수정(3)후기 데이터 추가 (4)후기 데이터 삭제 (5)후기 데이터 수정 (6)주문하기");
			System.out.println("(7)음료, 디저트 메뉴 출력 (8)주문 내역 출력 (9)후기 출력 (10)고객, 직원 정보 출력");
			System.out.println("(11)음료 메뉴 추천 기능 (12)정렬 (13)종료");

			System.out.print(">>");
			int num = scan.nextInt();
			switch (num) {
			case 1:
				addMenuData(); // 메뉴 데이터 추가
				break;
			case 2:
				modifyMenuData(); // 메뉴 데이터 수정
				break;
			case 3:
				// addReviewData(); // 후기 데이터 추가
				break;
			case 4:
				// deleteReviewData(); // 후기 데이터 삭제
				break;
			case 5:
				modifyReviewData(); // 후기 데이터 수정
				break;
			case 6:
				// cart(); // 주문하기
				break;
			case 7:
				menudrinkMgr.printAll(); // 음료, 디저트 메뉴 출력
				menudessertMgr.printAll();
				break;
			case 8:
				orderMgr.printAll(); // 주문 내역 출력
				break;
			case 9:
				reviewMgr.printAll(); // 후기 출력
				break;
			case 10:
				customerMgr.printAll(); // (8)고객, 직원 정보 출력
				staffMgr.printAll();
				break;
			case 11:
				// recommendation(); // 음료 메뉴 추천 기능
				break;
			case 12:
				// sorting(); // 정렬
			case 13:
				end = true;
				break;
			default:
				break;
			}
			if (end)
				break;
		}
	}

	public void addCustomerData(Customer customer) throws IOException {	//고객 데이터 추가
		customerMgr.addToList(customer);
		customerMgr.rewriteFile(new FileWriter("customer.txt"));
	}

	void addMenuData() throws IOException { // 1번
		System.out.println("****메뉴 데이터 추가****");
		System.out.print("타입 (0은 음료, 1은 디저트) : ");
		int num = scan.nextInt();
		switch (num) {
		case 0:
			System.out.println("데이터를 입력해주세요");
			System.out.println("형식 : 이름 가격 종류 레시피 # 해시태그 . 핫/아이스");
			System.out.print("(예시) 꿀아메리카노 4000 커피 에스프레소 꿀 # 달달함 . Hot\n>>");
			Drink drink = new Drink();
			drink.read(scan);
			menudrinkMgr.addToList(drink);
			menudrinkMgr.rewriteFile(new FileWriter("menudrink.txt"));
			System.out.println("음료가 추가되었습니다.");
			break;
		case 1:
			System.out.println("데이터를 입력해주세요");
			System.out.println("형식 : 이름 가격 종류 레시피 # 해시태그 .");
			System.out.print("(예시) 블루베리치즈케이크 5500 케이크 블루베리 치즈 밀가루 우유 달걀 # 달달함 .\n>>");
			Dessert dessert = new Dessert();
			dessert.read(scan);
			menudessertMgr.addToList(dessert);
			menudessertMgr.rewriteFile(new FileWriter("menudessert.txt"));
			System.out.println("디저트가 추가되었습니다.");
			break;
		}
	}

	void modifyMenuData() throws IOException { // 메뉴 데이터 수정
		ArrayList<String> materials = new ArrayList<>(); // 재료
		ArrayList<String> hashTags = new ArrayList<>(); // 해시태크
		System.out.println("****메뉴 데이터 수정****");
		System.out.print("타입 (0은 음료, 1은 디저트) : ");
		int num = scan.nextInt();
		switch (num) {
		case 0:
			System.out.println("수정하고 싶은 음료 메뉴: ");
			System.out.print("(예시)아메리카노 3500\n>>");
			String menudrinkName = scan.next();
			int price = scan.nextInt();
			String category = scan.next();

			while (true) {
				String material = scan.next();
				if ((material.substring(0, 1)).equals("#"))
					break; // #이 나오면 해시태그 입력 시작
				materials.add(material);
			}
			while (true) {
				String hashtag = scan.next();
				if (hashtag.contentEquals("."))
					break;
				hashTags.add(hashtag);
			}
			String HotIceChoice = scan.next();

			ArrayList<Manageable> menudrinkList = menudrinkMgr.mList;
			Manageable tmp = null;
			for (Manageable m : menudrinkList) {
				if (m.matches(menudrinkName) && m.matches(category) && m.matches(HotIceChoice))
					tmp = m;
			}
			menudrinkList.remove(tmp);

			System.out.println("수정할 내용: ");
			System.out.println("형식 : 이름 가격 종류 레시피 # 해시태그 . 핫/아이스");
			System.out.print("(예시) 꿀아메리카노 4000 커피 에스프레소 꿀 # 달달함 . Hot\n>>");
			Drink drink = new Drink();
			drink.read(scan);
			tmp = drink;
			menudrinkMgr.addToList(tmp);
			menudrinkMgr.rewriteFile(new FileWriter("menudrink.txt"));
			System.out.println("음료가 수정되었습니다.");
			break;

		case 1:
			System.out.println("수정하고 싶은 디저트 메뉴: ");
			System.out.print("(예시)블루베리치즈케이크\n>>");
			String menudessertName = scan.next();

			ArrayList<Manageable> menudessertList = menudessertMgr.mList;
			Manageable tmpp = null;
			for (Manageable m : menudessertList) {
				if (m.matches(menudessertName))
					tmpp = m;
			}
			menudessertList.remove(tmpp);

			System.out.println("수정할 내용: ");
			System.out.println("형식 : 이름 가격 종류 레시피 # 해시태그 .");
			System.out.print("(예시) 블루베리치즈케이크 5500 케이크 블루베리 치즈 밀가루 우유 달걀 # 달달함 .\n>>");
			Dessert dessert = new Dessert();
			dessert.read(scan);
			tmpp = dessert;
			menudessertMgr.addToList(tmpp);
			menudessertMgr.rewriteFile(new FileWriter("menudessert.txt"));
			System.out.println("디저트가 수정되었습니다.");
			break;
		}

	}

	void modifyReviewData() throws IOException { // 후기데이터 수정
		System.out.println("****후기 데이터 수정****");
		System.out.println("수정 할 후기의 아이디, 메뉴명을 입력해주세요");
		System.out.print("(예시) Customer9 아메리카노\n>>");
		String id = scan.next();
		String menuName = scan.next();

		ArrayList<Manageable> reviewList = reviewMgr.mList;
		Manageable tmp = null;
		for (Manageable m : reviewList) {
			if (m.matches(id) && m.matches(menuName))
				tmp = m;
		}
		reviewList.remove(tmp);

		System.out.println("수정 할 데이터를 입력해주세요");
		System.out.println("형식 : 아이디 메뉴명 별점 내용");
		System.out.print("(예시) Customer9 아메리카노 5 기본에 충실한 맛\n>>");
		Review review = new Review();
		review.read(scan);
		tmp = review;
		reviewMgr.addToList(tmp);
		reviewMgr.rewriteFile(new FileWriter("review.txt"));
		System.out.println("후기가 수정되었습니다.");

		for (Manageable rw : reviewMgr.mList) {
			Review tmpp = (Review) rw;

			Manageable m = menudrinkMgr.find(tmpp.menu);
			if (m != null) {
				Drink md = (Drink) m;
				md.star_cnt++;
				md.star += (float) tmpp.star;
				md.star_avg = md.star / md.star_cnt;
			}

			m = menudessertMgr.find(tmpp.menu);
			if (m != null) {
				Dessert md = (Dessert) m;
				md.star_cnt++;
				md.star += (float) tmpp.star;
				md.star_avg = md.star / md.star_cnt;
			}
		}
		String name = "";
		float star = 0;
		for (Manageable rw : menudrinkMgr.mList) {
			Drink md = (Drink) rw;
			if (md.name.equals(name)) {
				md.star_avg = star;
			} else {
				name = md.name;
				star = md.star_avg;
			}
		}
	}

	public void addReviewData(Review review) throws IOException { // 후기 데이터 추가
		reviewMgr.addToList(review);
		reviewMgr.rewriteFile(new FileWriter("review.txt"));
		System.out.println("후기가 추가되었습니다.");

		for (Manageable rw : reviewMgr.mList) {
			Review tmp = (Review) rw;

			Manageable m = menudrinkMgr.find(tmp.menu);
			if (m != null) {
				Drink md = (Drink) m;
				md.star_cnt++;
				md.star += (float) tmp.star;
				md.star_avg = md.star / md.star_cnt;
			}

			m = menudessertMgr.find(tmp.menu);
			if (m != null) {
				Dessert md = (Dessert) m;
				md.star_cnt++;
				md.star += (float) tmp.star;
				md.star_avg = md.star / md.star_cnt;
			}
		}
		String name = "";
		float star = 0;
		for (Manageable rw : menudrinkMgr.mList) {
			Drink md = (Drink) rw;
			if (md.name.equals(name)) {
				md.star_avg = star;
			} else {
				name = md.name;
				star = md.star_avg;
			}
		}
	}

	public static void deleteReviewData(int index) throws IOException { // 후기 데이터 삭제
		ArrayList<Manageable> reviewList = reviewMgr.mList;
		Manageable tmp = reviewMgr.mList.get(index);
		reviewList.remove(tmp);
		reviewMgr.rewriteFile(new FileWriter("review.txt"));

		for (Manageable rw : reviewMgr.mList) { // 후기 추가 수정 삭제시 평점 재계산을 위한 코드
			Review tmpp = (Review) rw;

			Manageable m = menudrinkMgr.find(tmpp.menu);
			if (m != null) {
				Drink md = (Drink) m;
				md.star_cnt++;
				md.star += (float) tmpp.star;
				md.star_avg = md.star / md.star_cnt;
			}

			m = menudessertMgr.find(tmpp.menu);
			if (m != null) {
				Dessert md = (Dessert) m;
				md.star_cnt++;
				md.star += (float) tmpp.star;
				md.star_avg = md.star / md.star_cnt;
			}
		}
		String name = "";
		float star = 0;
		for (Manageable rw : menudrinkMgr.mList) {
			Drink md = (Drink) rw;
			if (md.name.equals(name)) {
				md.star_avg = star;
			} else {
				name = md.name;
				star = md.star_avg;
			}
		}

	}

	public void recommendation(String id) { // 추천 기능
		Customer foundCustomer = (Customer) customerMgr.find(id);
		preferCategory = foundCustomer.analyzePreferCategory();
		preferHashTag = foundCustomer.analyzePreferHashTag();

		for (Manageable ml : menudrinkMgr.mList) {
			Drink drink = (Drink) ml;
			int rank = drink.getRecommendType(preferCategory, preferHashTag);
			switch (rank) {
			case 1:
				bothSatisfiedDrink.add((Drink) ml);
				break;
			case 2:
				categorySatisfiedDrink.add((Drink) ml);
				break;
			case 3:
				hashTagSatisfiedDrink.add((Drink) ml);
				break;
			default:
				break;
			}
		}
	}

	public void sortReviewStar() { // 후기 별점 평균 정렬
		Collections.sort(reviewMgr.mList, new Comparator<Manageable>() {
			@Override
			public int compare(Manageable o1, Manageable o2) {
				// TODO Auto-generated method stub
				Review r1 = (Review) o1;
				Review r2 = (Review) o2;
				return r2.star - r1.star;
			}
		});
	}

	public void sortDrinkStar() { // 음료 별점 평균 정렬
		Collections.sort(menudrinkMgr.mList, new Comparator<Manageable>() {
			@Override
			public int compare(Manageable o1, Manageable o2) {
				Drink r1 = (Drink) o1;
				Drink r2 = (Drink) o2;
				return (int) ((r2.star_avg - r1.star_avg) * 10000);
			}
		});
		System.out.println("별점 순 정렬************");
		menudrinkMgr.printAll();
	}

	public void sortDrinkNameAsc() { // 음료 이름 정렬
		Collections.sort(menudrinkMgr.mList, new Comparator<Manageable>() {
			@Override
			public int compare(Manageable o1, Manageable o2) {
				Drink drink1 = (Drink) o1;
				Drink drink2 = (Drink) o2;
				return drink1.name.compareTo(drink2.name);
			}
		});
	}

	public void sortDrinkNameDes() { // 음료 이름 정렬
		Collections.sort(menudrinkMgr.mList, new Comparator<Manageable>() {
			@Override
			public int compare(Manageable o1, Manageable o2) {
				Drink drink1 = (Drink) o1;
				Drink drink2 = (Drink) o2;
				return drink2.name.compareTo(drink1.name);
			}
		});
	}

	public void sortDrinkSale() { // 음료 가격 정렬
		Collections.sort(menudrinkMgr.mList, new Comparator<Manageable>() {
			@Override
			public int compare(Manageable o1, Manageable o2) {
				// TODO Auto-generated method stub
				Drink r1 = (Drink) o1;
				Drink r2 = (Drink) o2;
				return r2.acc_cnt - r1.acc_cnt;
			}
		});
	}

	public void sortDessertStar() { // 디저트 별점 평균 정렬
		Collections.sort(menudessertMgr.mList, new Comparator<Manageable>() {
			@Override
			public int compare(Manageable o1, Manageable o2) {
				// TODO Auto-generated method stub
				Dessert r1 = (Dessert) o1;
				Dessert r2 = (Dessert) o2;
				return (int) ((r2.star_avg - r1.star_avg) * 10000);
			}
		});
	}

	public void sortDessertNameAsc() { // 디저트 별점 평균 정렬
		Collections.sort(menudessertMgr.mList, new Comparator<Manageable>() {
			@Override
			public int compare(Manageable o1, Manageable o2) {
				Dessert dessert1 = (Dessert) o1;
				Dessert dessert2 = (Dessert) o2;
				return dessert1.name.compareTo(dessert2.name);
			}
		});
	}

	public void sortDessertNameDes() { // 디저트 이름 정렬
		Collections.sort(menudessertMgr.mList, new Comparator<Manageable>() {
			@Override
			public int compare(Manageable o1, Manageable o2) {
				Dessert dessert1 = (Dessert) o1;
				Dessert dessert2 = (Dessert) o2;
				return dessert2.name.compareTo(dessert1.name);
			}
		});
	}

	public void sortDessertSale() { // 디저트 가격 정렬
		Collections.sort(menudessertMgr.mList, new Comparator<Manageable>() {
			@Override
			public int compare(Manageable o1, Manageable o2) {
				// TODO Auto-generated method stub
				Dessert r1 = (Dessert) o1;
				Dessert r2 = (Dessert) o2;
				return r2.acc_cnt - r1.acc_cnt;
			}
		});
	}

	public static void main(String[] args) throws IOException {
		Cafe c = new Cafe();
		c.run();
	}

}