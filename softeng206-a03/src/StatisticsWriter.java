import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class StatisticsWriter {
	private String path;
	private String resetpath;
	private File sourceFile;
	private ArrayList<Integer> levellines;

	public StatisticsWriter() {
		path = "src/.statisticslist";
		resetpath = "src/.newlist";
		sourceFile = new File(path);
		setup();
	}

	private void setup() {
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

	public void writeWord(int level, String word, WordType type) {
		if (!contains(word)) {
			try {
				BufferedReader in = new BufferedReader(new FileReader(path));
				BufferedWriter out = new BufferedWriter(new FileWriter("src/out.txt"));
				String string = null;
				int line = 0;
				while ((string = in.readLine()) != null) {
					line++;
					if (line != levellines.get(level - 1)) {
						out.write(string);
						out.newLine();
					} else {
						out.write(string);
						out.newLine();
						if (type.equals(WordType.Mastered)) {
							out.write(word + " Mastered 1 Faulted 0 Failed 0");
						} else if (type.equals(WordType.Faulted)) {
							out.write(word + " Mastered 0 Faulted 1 Failed 0");
						} else if (type.equals(WordType.Failed)) {
							out.write(word + " Mastered 0 Faulted 0 Failed 1");
						}
						out.newLine();
					}
				}
				out.flush();
				in.close();
				out.close();
				sourceFile.delete();
				sourceFile = new File("src/out.txt");
				sourceFile.renameTo(new File(path));
				sourceFile = new File(path);
				for (int i = level; i < levellines.size(); i++) {
					levellines.set(i, levellines.get(i) + 1);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				BufferedReader in = new BufferedReader(new FileReader(path));
				BufferedWriter out = new BufferedWriter(new FileWriter("src/out.txt"));
				String string = null;
				while ((string = in.readLine()) != null) {
					if (!string.split(" ")[0].equals(word)) {
						out.write(string);
						out.newLine();
					} else {
						if (type.equals(WordType.Mastered)) {
							out.write(word + " Mastered " + (Integer.parseInt(string.split(" ")[2]) + 1) + " Faulted "
									+ string.split(" ")[4] + " Failed " + string.split(" ")[6]);
						} else if (type.equals(WordType.Faulted)) {
							out.write(word + " Mastered " + string.split(" ")[2] + " Faulted "
									+ (Integer.parseInt(string.split(" ")[4]) + 1) + " Failed " + string.split(" ")[6]);
						} else if (type.equals(WordType.Failed)) {
							out.write(word + " Mastered " + string.split(" ")[2] + " Faulted " + string.split(" ")[4]
									+ " Failed " + (Integer.parseInt(string.split(" ")[6]) + 1));
						}
						out.newLine();
					}
				}
				out.flush();
				in.close();
				out.close();
				sourceFile.delete();
				sourceFile = new File("src/out.txt");
				sourceFile.renameTo(new File(path));
				sourceFile = new File(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void resetStatisticslist() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(resetpath));
			BufferedWriter out = new BufferedWriter(new FileWriter(path));
			String string = null;
			while ((string = in.readLine()) != null) {
				out.write(string);
				out.newLine();
			}
			out.flush();
			in.close();
			out.close();
		} catch (IOException e) {

		}
		setup();
	}

	private boolean contains(String word) {
		try {
			Scanner scanner = new Scanner(sourceFile);
			while (scanner.hasNextLine()) {
				if (scanner.nextLine().split(" ")[0].equals(word)) {
					scanner.close();
					return true;
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

}
