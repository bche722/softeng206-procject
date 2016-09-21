package gui;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import method.Method;

@SuppressWarnings("serial")
public class GameGUI extends JFrame {

	private JPanel pane;
	private JComboBox<String> level; 
	
	public GameGUI() {
		super("Spelling Aid");

		this.pane = (JPanel) this.getContentPane();
		this.pane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		this.pane.setLayout(new GridLayout(0, 1, 6, 6));

		this.add(new JLabel("Welcome to the Spelling Aid"));

		level= new JComboBox<String>(new String[] {"Level 1","Level 2","Level 3","Level 4","Level 5","Level 6","Level 7","Level 8","Level 9","Level 10","Level 11"});
		this.pane.add(level);
		
		JButton newQuizButton = new JButton("New Spelling Quiz");
		newQuizButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new NewQuiz(level.getSelectedIndex()+1,Method.getNewGameList(level.getSelectedIndex()+1));
			}
		});
		this.pane.add(newQuizButton);

		JButton reviewMistakesButton = new JButton("Review Mistakes");
		reviewMistakesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Review(level.getSelectedIndex()+1,Method.getReviewList(level.getSelectedIndex()+1));
			}
		});
		this.pane.add(reviewMistakesButton);

		JButton viewStatisticsButton = new JButton("View Statistics");
		viewStatisticsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new View(Method.getStatisticsList(level.getSelectedIndex()+1));
			}
		});
		this.pane.add(viewStatisticsButton);

		JButton clearStatisticsButton = new JButton("Clear Statistics");
		clearStatisticsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Method.clear();
			}
		});
		this.pane.add(clearStatisticsButton);
		
		this.setVisible(true);
		pack();
	}
}
