package cafe;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

import mgr.Manageable;

public class Review implements Manageable {	//후기 클래스
   public String id;
   public String menu;
   public Menu menuObject;
   public int star; // 별점
   public String content;

   @Override
   public void read(Scanner scan) {
      id = scan.next();
      menu = scan.next();
      menuObject = (Drink) Cafe.menudrinkMgr.find(menu);   //음료에서 찾기
      if(menuObject==null)   //없으면
         menuObject = (Dessert) Cafe.menudessertMgr.find(menu);   //디저트에서 찾기
      star = scan.nextInt();
      content = scan.nextLine();
   }
   
   public void read(String id, String menu, String star, String content) {
      this.id = id;
      this.menu = menu;
      menuObject = (Drink) Cafe.menudrinkMgr.find(menu);   //음료에서 찾기
      if(menuObject==null)   //없으면
         menuObject = (Dessert) Cafe.menudessertMgr.find(menu);   //디저트에서 찾기
      this.star = Integer.parseInt(star);
      this.content = content;
   }

   @Override
   public void print() {
      System.out.printf("[아이디 : %s] 주문한 메뉴 : %s 평점 : %d\n \t상세후기 : %s\n", id, menu, star, content);
   }

   @Override
   public boolean matches(String kwd) {
      if (id.equals(kwd))
         return true;
      if (menu.equals(kwd))
         return true;
      return false;
   }

   @Override
   public void writeToFile(BufferedWriter bw) throws IOException {
      bw.append(id + " ");
      bw.append(menu + " ");
      bw.append(star + " ");
      bw.append(content + "\n");
   }

}