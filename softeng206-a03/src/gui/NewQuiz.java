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
	protected ArrayList<String> wordlist;
	private JFrame frame=this;
	private JPanel pane;
	private JPanel p1;
	private JPanel p2;
	private JPanel p3;
	private JPanel p4;
	protected JLabel rate;
	protected JTextField text;
	private JRadioButton v1;
	private JRadioButton v2;
	protected JButton submit;
	private JButton relisten;
	protected boolean firsttry;
	protected boolean confirmwork;
	protected int wordnum;
	protected VoiceType type;
	protected int correct;
	
	public NewQuiz(int level,ArrayList<String> list){
		super("NewQuiz");
		this.pane = (JPanel) this.getContentPane();
		this.pane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		this.pane.setLayout(new GridLayout(0, 1, 4, 4));
		ini(list);
		buildPanel1();
		buildPanel2();
		buildPanel3();
		buildPanel4(level);
		this.setVisible(true);
		this.pack();
	}
	
	private void ini(ArrayList<String> list) {
		wordlist=list;
		wordnum=0;
		correct=0;
		confirmwork=true;
		firsttry=true;
		type=VoiceType.Voice1;
	}

	private void buildPanel4(int level){
		p4=new JPanel();
		p4.setLayout(new FlowLayout());
		buildSubmit(level);
		buildRelisten();
	}
	
	private void buildPanel3() {
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
	}

	private void buildPanel2() {
		p2=new JPanel();
		p2.setLayout(new FlowLayout());
		p2.add(new JLabel("Enter answer:"));
		text=new JTextField(8);
		p2.add(text);
		pane.add(p2);
	}

	private void buildPanel1() {
		p1=new JPanel();
		p1.setLayout(new FlowLayout());
		p1.add(new JLabel("Accuracy Rate: "));
		rate=new JLabel("0 / "+wordlist.size());
		p1.add(rate);
		pane.add(p1);
	}

	protected void buildSubmit(int level){
		submit=new JButton("Start");
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(confirmwork){
					if(submit.getText().equals("Start")){
						Method.speakWord(wordlist.get(wordnum), type,1000);
						submit.setText("Submit");
					}
					else{
						if(firsttry){
							String ans = text.getText().toLowerCase();
							text.setText("");
							if(ans.equals(wordlist.get(wordnum).toLowerCase())){
								Method.speakWord("Correct", type,1000);
								Method.writeWord(1, wordlist.get(wordnum), WordType.Mastered);
								firsttry=true;
								wordnum++;
								correct++;
							}else{
								Method.speakWord("Incorrect, try once more, "+wordlist.get(wordnum), type,2000);
								firsttry=false;
							}
						}else{
							String ans = text.getText().toLowerCase();
							text.setText("");
							if(ans.equals(wordlist.get(wordnum).toLowerCase())){
								Method.speakWord("Correct", type,1000);
								Method.writeWord(1, wordlist.get(wordnum), WordType.Faulted);
							}else{
								Method.speakWord("Incorrect", type,1000);
								Method.writeWord(1, wordlist.get(wordnum), WordType.Failed);
							}
							firsttry=true;
							wordnum++;
						}
						if(wordnum<wordlist.size()){
							Method.speakWord(wordlist.get(wordnum), type,1000);
						}else{
							confirmwork=false;
							frame.dispose();
							new Feedback(level,correct,10);
						}
					}
					rate.setText(" "+correct+" / 10");
				}
			}
		});
	}
	
	private void buildRelisten(){
		relisten=new JButton("Relisten");
		relisten.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Method.speakWord(wordlist.get(wordnum), type,1000);
			}});
		p4.add(submit);
		p4.add(relisten);
		pane.add(p4);
	}
}
