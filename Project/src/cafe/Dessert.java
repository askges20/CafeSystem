package cafe;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Dessert extends Menu {
	
	@Override
	public void read(Scanner scan) {
		super.read(scan);
		String tmpFileName = scan.next();
	    filename = "image/dessert/"+tmpFileName;
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
	      Cafe.menudessertMgr.rewriteFile(new FileWriter("menudrink.txt"));
	   }
	
	@Override
	public void writeToFile(BufferedWriter bw) throws IOException {
		super.writeToFile(bw);
		bw.append("\n");
	}
	
	@Override
	   public boolean matches(String kwd) {
	      if(name.equals(kwd))
	         return true;
	      else return false;
	   }
	
	public boolean matches2(String kwd) {
		if (name.contains(kwd))
			return true;
		return false;
	}

}
