import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameGUI extends JFrame {

	private static final long serialVersionUID = 1552746400473185110L;
	private static final String WORD_FILE = "wordlist";
	private static final String RESULT_FILE = ".data_save";
	private JPanel pane;
	
	private Map<String, Word> words;

	public GameGUI() {
		super("Spelling Aid");
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);

		try {
			words = readWordsFromFile();

			if (words.size() < 3) {
				JOptionPane.showMessageDialog(null, WORD_FILE + " contains less than 3 words", "Warning",
						JOptionPane.WARNING_MESSAGE);
			}

		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, WORD_FILE + " file does not exist. Exiting..", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}

		this.pane = (JPanel) this.getContentPane();
		this.pane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		this.pane.setLayout(new GridLayout(0, 1, 5, 5));

		this.add(new JLabel("Welcome to the Spelling Aid"));

		JButton newQuizButton = new JButton("New Spelling Quiz");
		newQuizButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doNewQuiz();
			}
		});
		this.pane.add(newQuizButton);

		JButton reviewMistakesButton = new JButton("Review Mistakes");
		reviewMistakesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doReviewMistakes();
			}
		});
		this.pane.add(reviewMistakesButton);

		JButton viewStatisticsButton = new JButton("View Statistics");
		viewStatisticsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doViewStatistics();
			}
		});
		this.pane.add(viewStatisticsButton);

		JButton clearStatisticsButton = new JButton("Clear Statistics");
		clearStatisticsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doClearStatistics();
			}
		});
		this.pane.add(clearStatisticsButton);

		pack();
	}

	private Map<String, Word> readWordsFromFile() throws FileNotFoundException {
		Scanner wordFileScanner = new Scanner(new File(WORD_FILE));

		Map<String, Word> words = new HashMap<>();

		try {
			Scanner resultFileScanner = new Scanner(new File(RESULT_FILE));

			while (resultFileScanner.hasNextLine()) {
				String[] tokens = resultFileScanner.nextLine().split(",");

				words.put(tokens[0], new Word(tokens[0], Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]),
						Integer.parseInt(tokens[3])));
			}

			resultFileScanner.close();
		} catch (FileNotFoundException e) {

		}

		while (wordFileScanner.hasNextLine()) {
			String word = wordFileScanner.nextLine();

			if (!words.containsKey(word)) {
				words.put(word, new Word(word));
			}
		}

		wordFileScanner.close();

		return words;
	}

	private void doClearStatistics() {
		for (String word : words.keySet()) {
			words.get(word).clearResults();
		}
		
		writeResults();

		JOptionPane.showMessageDialog(this, "Success.", "Clear Statistics", JOptionPane.INFORMATION_MESSAGE);
	}

	private void doViewStatistics() {
		List<String> wordList = new ArrayList<>(words.keySet());
		Collections.sort(wordList, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareToIgnoreCase(o2);
			}
		});

		JPanel panel = new JPanel(new GridLayout(0, 4, 5, 5));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.add(new JLabel("Word"));
		panel.add(new JLabel("Mastered"));
		panel.add(new JLabel("Faulted"));
		panel.add(new JLabel("Failed"));
		
		for (String word : wordList) {
			Word wordObj = words.get(word);
			
			if(wordObj.wasQuizzed()){
				panel.add(new JLabel(wordObj.getWord()));
				panel.add(new JLabel(Integer.toString(wordObj.getMasteredCount())));
				panel.add(new JLabel(Integer.toString(wordObj.getFaultedCount())));
				panel.add(new JLabel(Integer.toString(wordObj.getFailedCount())));
			}
		}
		

		JOptionPane.showMessageDialog(this, panel, "View Statistics", JOptionPane.INFORMATION_MESSAGE);

	}

	private void doReviewMistakes() {
		List<String> wordList = new ArrayList<>(words.keySet());
		
		for (String word : new ArrayList<>(wordList)) {
			if(!words.get(word).isFailed()){
				wordList.remove(word);
			}
		}
		
		if(wordList.isEmpty()){
			JOptionPane.showMessageDialog(this, "No failed words found.", "Review Mistakes", JOptionPane.INFORMATION_MESSAGE);
		}else{
			for (int i=0;i<wordList.size() && i<3;i++) {
				Word wordObj = words.get(wordList.get(i));
				
				int result = askToSpellWord(wordObj);
				
				if(result == 0){
					int answer = JOptionPane.showConfirmDialog(this, "Would you like to hear the word again?", "Review Mistakes", JOptionPane.YES_NO_OPTION);
					
					if(answer == JOptionPane.YES_OPTION){
						reviewWord(wordObj);
					}
				}else if(result == -1){
					break;
				}
				
			}
		}
		
		writeResults();
	}
	
	private void reviewWord(final Word wordObj) {
		
		final String word = wordObj.getWord();
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
				}
				for(int i=0;i<word.length();i++){
					GameGUI.this.say(Character.toString(word.charAt(i)));
					try {
						TimeUnit.MILLISECONDS.sleep(500);
					} catch (InterruptedException e) {
					}
				}
			}
		});
		thread.start();
		
		askToSpellWord(wordObj);
	}

	private boolean isAlphabetic(String word){
		for(int i=0;i<word.length();i++){
			if(!Character.isAlphabetic(word.charAt(i))){
				return false;
			}
		}
		
		return true;
	}
	
	private String askForWord(){
		String[] options = { "Submit" };
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Enter answer: ");
		JTextField text = new JTextField(10);
		panel.add(label);
		panel.add(text);
		
		text.setText("");
		
		while(true){
			int result = JOptionPane.showOptionDialog(this, panel, "Spelling", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, options[0]);
			
			if(result == -1){
				return null;
			}
			
			if(isAlphabetic(text.getText())){
				return text.getText();
			}else{
				JOptionPane.showMessageDialog(this, "Please only enter letters.", "Spelling", JOptionPane.WARNING_MESSAGE);
			}
			
		}
	}
	
	private int askToSpellWord(Word word){
		say(word.getWord());
		
		String userSpelling = askForWord();
		
		if(userSpelling == null){
			return -1;
		}

		if (word.getWord().equalsIgnoreCase(userSpelling)) {
			say("correct");
			word.incrementMasteredCount();
		} else {
			say("incorrect. try once more " + word.getWord() + ". " + word.getWord());

			userSpelling = askForWord();
			
			if(userSpelling == null){
				return -1;
			}

			if (word.getWord().equalsIgnoreCase(userSpelling)) {
				say("correct");
				word.incrementFaultedCount();
			} else {
				say("incorrect");
				word.incrementFailedCount();
				return 0;
			}
		}
		
		return 1;
	}

	private void doNewQuiz() {
		List<String> wordList = new ArrayList<>(words.keySet());
		Collections.shuffle(wordList);
		
		boolean isExit = false;

		for (int i = 0; i < 3; i++) {
			Word word = words.get(wordList.get(i));
			
			if(askToSpellWord(word) == -1){
				isExit = true;
				break;
			}
		}
		
		if(!isExit){
			writeResults();
		}
	}

	private void writeResults() {

		try {
			PrintWriter pw = new PrintWriter(new File(RESULT_FILE));

			for (String word : words.keySet()) {
				pw.println(words.get(word));
			}

			pw.close();
		} catch (FileNotFoundException e) {
		}

	}

	private void say(String word) {
		try {
			String[] cmd = { "/bin/sh", "-c", "echo " + word + " | festival --tts" };

			Runtime.getRuntime().exec(cmd);

			TimeUnit.SECONDS.sleep(1);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
