package cafe;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Drink extends Menu {

	@Override
	public void read(Scanner scan) {
		super.read(scan);
		String tmpFileName = scan.next();
	    filename = "image/drink/"+tmpFileName;
	}

	@Override
	public void printpart1() {
		super.printpart1();
	}

	public void printpart2() {
		super.printpart2();
	}
	
	   @Override
	   public void askToRewriteFile() throws IOException {
	      Cafe.menudrinkMgr.rewriteFile(new FileWriter("menudrink.txt"));
	   }

	@Override
	public boolean matches(String kwd) {
		if (name.equals(kwd))
			return true;
		for (String ht : hashTags) {
			if (ht.equals(kwd))
				return true;
		}
		return false;
	}
	
	public boolean matches2(String kwd) {
		if (name.contains(kwd))
			return true;
		return false;
	}

	@Override
	public void writeToFile(BufferedWriter bw) throws IOException {
		super.writeToFile(bw);
		bw.append("\n");
	}

	public int getRecommendType(ArrayList<String> preferCategory, ArrayList<String> preferHashTag) {
		boolean categorySatisfied = false;
		boolean hashTagSatisfied = false;

		for (String pc : preferCategory) {
			if (pc.equals(category)) {
				categorySatisfied = true;
				break;
			}
		}
		for (String ph : preferHashTag) {
			if (matches(ph)) {
				hashTagSatisfied = true;
				break;
			}
		}

		if (categorySatisfied) {
			if (hashTagSatisfied)
				return 1; // 종류. 해시태그 모두 만족
			else
				return 2; // 종류만 만족
		} else if (hashTagSatisfied)
			return 3; // 해시태그만 만족
		return 4; // 둘 다 만족 x
	}
}
