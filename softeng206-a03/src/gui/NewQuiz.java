package gui;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import method.Method;
import method.VoiceType;
import method.WordType;

@SuppressWarnings("serial")
public class NewQuiz extends JFrame{
	private ArrayList<String> wordlist;
	private JPanel pane;
	private JPanel p1;
	private JPanel p2;
	private JPanel p3;
	private JPanel p4;
	private JLabel rate;
	private JTextField text;
	private JRadioButton v1;
	private JRadioButton v2;
	private JButton submit;
	private JButton relisten;
	private boolean firsttry;
	private boolean confirmwork;
	private int wordnum;
	private VoiceType type;
	private int correct;
	public NewQuiz(int level,ArrayList<String> list){
		super("NewQuiz");
		wordlist=list;
		wordnum=0;
		correct=0;
		confirmwork=true;
		firsttry=true;
		type=VoiceType.Voice1;
		this.pane = (JPanel) this.getContentPane();
		this.pane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		this.pane.setLayout(new GridLayout(0, 1, 4, 4));

		p1=new JPanel();
		p1.setLayout(new FlowLayout());
		p1.add(new JLabel("Accuracy Rate: "));
		rate=new JLabel("0 / 10");
		p1.add(rate);
		pane.add(p1);

		p2=new JPanel();
		p2.setLayout(new FlowLayout());
		p2.add(new JLabel("Enter answer:"));
		text=new JTextField(8);
		p2.add(text);
		pane.add(p2);

		p3=new JPanel();
		p3.setLayout(new FlowLayout());
		v1 = new JRadioButton("Voice One");
		v1.setSelected(true);
		v1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				v2.setSelected(false);
				v1.setSelected(true);
				type=VoiceType.Voice1;
			}});
		v2 = new JRadioButton("Voice Two");
		v2.setSelected(false);
		v2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				v1.setSelected(false);
				v2.setSelected(true);
				type=VoiceType.Voice2;
			}});
		p3.add(v1);
		p3.add(v2);
		pane.add(p3);

		p4=new JPanel();
		p4.setLayout(new FlowLayout());
		submit=new JButton("Start");
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(confirmwork){
					if(submit.getText().equals("Start")){
						Method.speakWord(wordlist.get(wordnum), type);
						submit.setText("Submit");
					}
					else{
						if(firsttry){
							String ans = text.getText().toLowerCase();
							text.setText("");
							if(ans.equals(wordlist.get(wordnum).toLowerCase())){
								Method.speakWord("Correct", type);
								Method.writeWord(1, wordlist.get(wordnum), WordType.Mastered);
								firsttry=true;
								wordnum++;
								correct++;
							}else{
								Method.speakWord("Incorrect, try once more, "+wordlist.get(wordnum), type);
								firsttry=false;
							}
						}else{
							String ans = text.getText().toLowerCase();
							text.setText("");
							if(ans.equals(wordlist.get(wordnum).toLowerCase())){
								Method.speakWord("Correct", type);
								Method.writeWord(1, wordlist.get(wordnum), WordType.Faulted);
							}else{
								Method.speakWord("Incorrect", type);
								Method.writeWord(1, wordlist.get(wordnum), WordType.Failed);
							}
							firsttry=true;
							wordnum++;
						}
						if(wordnum<wordlist.size()){
							Method.speakWord(wordlist.get(wordnum), type);
						}else{
							confirmwork=false;
							new Feedback(level,correct,10);
						}
					}
					rate.setText(" "+correct+" / 10");
				}
			}
		});
		relisten=new JButton("Relisten");
		relisten.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Method.speakWord(wordlist.get(wordnum), type);
			}});
		p4.add(submit);
		p4.add(relisten);
		pane.add(p4);

		this.setVisible(true);
		pack();
	}


}
