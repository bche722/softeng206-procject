import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Reader {
	private File sourceFile;
	private ArrayList<Integer> levellines;

	public Reader(String path) {
		sourceFile = new File(path);
		setup();
	}

	public void setup() {
		levellines = new ArrayList<Integer>();
		try {
			Scanner scanner = new Scanner(sourceFile);
			int n = 0;
			String line;
			while (scanner.hasNextLine()) {
				n++;
				line = scanner.nextLine();
				if (line.contains("%")) {
					levellines.add(n);
				}
			}
			levellines.add(n + 1);
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private String ReadLine(int line) {
		int n = 0;
		String s = null;
		try {
			Scanner scanner = new Scanner(sourceFile);
			while (scanner.hasNextLine()) {
				n++;
				s = scanner.nextLine();
				if (n == line) {
					break;
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return s;
	}

	public ArrayList<String> RandomWordList(int level) {
		ArrayList<String> list = new ArrayList<String>();
		int line1 = levellines.get(level - 1) + 1;
		int line2 = levellines.get(level) - 1;
		if ((line2 - line1 - 1) >= 10) {
			for (int i = 0; i < 10; i++) {
				int line = (int) (line1 + Math.random() * (line2 - line1 + 1));
				list.add(ReadLine(line));
			}
		} else {
			for (int i = line1; i <= line2; i++) {
				list.add(ReadLine(i));
			}
			Collections.shuffle(list);
		}
		return list;
	}
	
	public ArrayList<String> AllWordList(int level) {
		ArrayList<String> list = new ArrayList<String>();
		int n = 0;
		String s = null;
		try {
			Scanner scanner = new Scanner(sourceFile);
			while (scanner.hasNextLine()) {
				n++;
				s = scanner.nextLine();
				if (n >= levellines.get(level-1)+1 && n < levellines.get(level)) {
					list.add(s);
				} 
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
		return list;
	}
}
