import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Spelling_aid extends JFrame {
	private ArrayList<String> output;
	private ArrayList<String> wordlist;
	private int wordnum;
	private boolean firsttry;
	private boolean confirmwork;
	
	private JPanel top;
	private JPanel middle;
	private JPanel bottom;
	private JLabel label;
	private JTextField text;
	private JButton confirm;
	private JButton newquiz;
	private JButton review;
	private JButton view;
	private JButton clear;

	public Spelling_aid() {
		confirmwork=false;
		buildTop();
		buildMiddle();
		buildBottom();
		this.add(top, BorderLayout.NORTH);
		this.add(middle, BorderLayout.CENTER);
		this.add(bottom, BorderLayout.SOUTH);
		this.setTitle("Spelling Aid");
		this.setSize(700, 500);
		this.setVisible(true);
	}

	public static void main(String args[]) {
		new Spelling_aid();
	}

	private void buildTop() {
		buildLabel();
		top = new JPanel();
		top.setSize(500, 500);
		top.add(label);
	}

	private void buildMiddle() {
		buildText();
		buildConfirm();
		middle = new JPanel();
		middle.add(text);
		middle.setSize(200, 200);
		middle.add(confirm);
	}

	private void buildBottom() {
		buildNew();
		buildReview();
		buildView();
		buildClear();
		bottom = new JPanel();
		bottom.add(newquiz);
		bottom.add(review);
		bottom.add(view);
		bottom.add(clear);
	}

	private void buildLabel() {
		label = new JLabel();
		output = new ArrayList<String>();
		output.add("====================");
		output.add("Welcome to the Spelling Aid");
		output.add("====================");
		label.setText(trans(output));
	}

	private void buildText() {
		text = new JTextField(30);
	}

	private void buildConfirm() {
		confirm = new JButton();
		confirm.setText("Confirm");
		confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(confirmwork){
					if(firsttry){
						String ans = text.getText().toLowerCase();
						text.setText("");
						if(ans.equals(wordlist.get(wordnum))){
							Method.speakWord("Correct", VoiceType.Voice1);
							Method.writeWord(1, wordlist.get(wordnum), WordType.Mastered);
							firsttry=true;
							wordnum++;
						}else{
							Method.speakWord("Incorrect, try once more", VoiceType.Voice1);
							firsttry=false;
						}
					}else{
						String ans = text.getText().toLowerCase();
						text.setText("");
						if(ans.equals(wordlist.get(wordnum))){
							Method.speakWord("Correct", VoiceType.Voice1);
							Method.writeWord(1, wordlist.get(wordnum), WordType.Faulted);

						}else{
							Method.speakWord("Incorrect", VoiceType.Voice1);
							Method.writeWord(1, wordlist.get(wordnum), WordType.Failed);
						}
						firsttry=true;
						wordnum++;
					}
					if(wordnum<wordlist.size()){
						Method.speakWord(wordlist.get(wordnum), VoiceType.Voice1);
					}else{
						confirmwork=false;
					}
			}
		}
	});
}

private void buildNew() {
	newquiz = new JButton();
	newquiz.setText("New Spelling Quiz");
	newquiz.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {

			output = new ArrayList<String>();
			output.add("=============");
			output.add("New Spelling Quiz");
			output.add("=============");
			label.setText(trans(output));
			wordlist=Method.getNewGameList(1);
			wordnum=0;
			firsttry=true;
			confirmwork=true;
			Method.speakWord(wordlist.get(wordnum), VoiceType.Voice1);

		}
	});
}

private void buildReview() {
	review = new JButton();
	review.setText("Review Mistakes");
	review.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			output = new ArrayList<String>();
			output.add("===========");
			output.add("Reviewing Quiz");
			output.add("===========");
			label.setText(trans(output));	
			wordlist=Method.getReviewList(1);
			wordnum=0;
			firsttry=true;
			confirmwork=true;
			Method.speakWord(wordlist.get(wordnum), VoiceType.Voice1);
		}
	});
}

private void buildView() {
	view = new JButton();
	view.setText("View Statistics");
	view.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			output = new ArrayList<String>();
			output.add("===================");
			output.add("Here is your statistic data");
			output.add("===================");
			output.addAll(Method.getStatisticsList(1));
			label.setText(trans(output));
		}
	});
}

private void buildClear() {
	clear = new JButton();
	clear.setText("Clear Statistics");
	clear.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			output = new ArrayList<String>();
			output.add("===========================");
			output.add("Your statistic data have been cleared");
			output.add("===========================");
			Method.clear();
			label.setText(trans(output));
		}
	});
}

private static String trans(ArrayList<String> list) {
	if (list.size() > 0) {
		String string = "<html>";
		for (int i = 0; i < list.size() - 1; i++) {
			string += list.get(i) + "<br>";
		}
		string += list.get(list.size() - 1) + "</html>";
		return string;
	} else {
		return "";
	}
}
}
