package mgr;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public interface Manageable {
	void read(Scanner scan);

	void print();

	boolean matches(String kwd);
	
	void writeToFile(BufferedWriter bw) throws IOException;
}
