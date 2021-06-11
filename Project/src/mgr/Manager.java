package mgr;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Manager {
	public ArrayList<Manageable> mList = new ArrayList<>();

	public Manageable find(String kwd) {
	    for (Manageable m: mList)
	    	if (m.matches(kwd))
	    		return m;
	    return null;
	}
	
	public void readAll(String filename, Factory fac) {
		Scanner filein = openFile(filename);
		Manageable m = null;
		while (filein.hasNext()) {
			m = fac.create();
			m.read(filein);
			addToList(m);
		}
		filein.close();
	}

	public void printAll() {
		for (Manageable m : mList)
			m.print();
	}

	public Scanner openFile(String filename) {
		Scanner filein = null;
		try {
			filein = new Scanner(new File(filename));
		} catch (Exception e) {
			System.out.println(filename + ": 파일 없음");
			System.exit(0);
		}
		return filein;
	}
	
	public void addToList(Manageable m) {	//mList에 Manageable 추가
		mList.add(m);
	}
	
	public void deleteFromList(Manageable m) {
		mList.remove(m);
	}
	
	public void rewriteFile(FileWriter fw) throws IOException {
		BufferedWriter bw = new BufferedWriter(fw);
		for(Manageable m: mList) {
			m.writeToFile(bw);
		}
		bw.close();
	}
}