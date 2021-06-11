package cafe;

import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import mgr.Manageable;

public class Menu implements Manageable {
   public String name;
   public int price;
   public String category; // 종류(커피, 라떼, 스무디 등)
   public String filename;
   float star; // 별점 더하기용
   float star_cnt; // 이름 나온 개수 세서 평균 구할 때 나누는 용
   public float star_avg; // 평균 별점
   int acc_cnt; //누적 판매량

   public ArrayList<String> materials = new ArrayList<>(); // 재료
   public ArrayList<String> hashTags = new ArrayList<>(); // 해시태크

   // 0 에스프레소 3000 커피 에스프레소 #씀 아이스 Tall
   public void read(Scanner scan) {
      name = scan.next();
      price = scan.nextInt();
      category = scan.next();
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
      } // . 만나면 해시태그 입력 종료
   } // drink, dessert 공통된 부분 : 이름, 가격, 카테고리, 재료, 해시태그

   public void askToRewriteFile() throws IOException {
	      //음료, 디저트에 따라 다른 파일에 저장할 것
	   }
   
   public void printpart1() { // 이름 가격
      System.out.printf("[%s]  %d원\n", name, price);
   }

   public void printpart2() { // 재료 해시태그 -> drink 때문에 나눔
      System.out.printf("\t재료 : ");
      for (String m : materials) {
         System.out.printf("%s ", m);
      }
      for (String h : hashTags) {
         System.out.printf("    #%s ", h);
      }
      System.out.printf("\n");
   }

   public boolean matches(String kwd) {
      if (name.contains(kwd))
         return true;
      for(String ht: hashTags) {
    	  if(ht.equals(kwd))
    		  return true;
      }
      return false;
   }
   
   public boolean matchesPrice(int min,int max) {
	   if(min==0 && max==0)
		   return true;
	   else if(min==0) {
		   if(max>price)
			   return true;
	   }else if(max==0) {
		   if(min<=price)
			   return true;
	   }else 
		   if(min<=price && max>price)
			   return true;
	   return false;
   }

   @Override
   public void print() {
      printpart1();
      printpart2();
   }
   
   public boolean matchesallergy(ArrayList<String> allergyList) {
	   for(String s : materials) {
		   for(String s2 : allergyList) {
			   if(s.contains(s2)) {
				   return true;
			   }
		   }
	   }
		return false;
	}

   @Override
   public void writeToFile(BufferedWriter bw) throws IOException {
      bw.append(name + " ");
      bw.append(price + " ");
      bw.append(category + " ");
      for (String m : materials) {
         bw.append(m + " ");
      }
      bw.append("# ");
      for (String h : hashTags) {
         bw.append(h + " ");
      }
      bw.append(". ");
   }

   public void modify(int price, String category, ArrayList<String> materials, ArrayList<String> hashTags) {
	      this.price = price;
	      this.category = category;
	      this.materials = materials;
	      this.hashTags = hashTags;
	   }
}
