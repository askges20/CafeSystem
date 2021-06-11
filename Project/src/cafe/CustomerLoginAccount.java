package cafe;

import javax.swing.JOptionPane;

public class CustomerLoginAccount {	//현재 로그인한 계정을 싱글톤 패턴으로 처리
	public Customer customer;
	public Order cart;
	
	private static CustomerLoginAccount account = new CustomerLoginAccount();
	
	public CustomerLoginAccount() {
		//this.customer = null;
		this.cart = new Order();
	}
	
	public static CustomerLoginAccount getAccount() {
		return account;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
		setOrderCustomer();
	}

	public void setOrderCustomer() {
		this.cart = new Order();
		this.cart.setOrderCustomer(this.customer);
	}
	
	public void clearCart() {	//장바구니 비우기. 주문완료, 로그아웃 하고나서 사용됨
		this.cart = new Order();
	}
	
	public void addToCart(Menu menu) {	//장바구니에 담기
		if(this.cart.addMenuToOrderedMenuList(menu))
			JOptionPane.showMessageDialog(null, "장바구니에 담긴 "+menu.name+"의 개수가 증가했습니다!");
		else
			JOptionPane.showMessageDialog(null, "장바구니에 "+menu.name+"가 추가되었습니다!");
	}
}
