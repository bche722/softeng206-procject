package gui;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import method.Method;
import method.WordType;

@SuppressWarnings("serial")
public class Review extends NewQuiz{
	JFrame frame=this;
	public Review(int level, ArrayList<String> list) {
		super(level, list);
		this.setTitle("Review");
		if(wordlist.size()==0){
			confirmwork=false;
		}
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
								if(wordnum<wordlist.size()){
									Method.speakWord(wordlist.get(wordnum), type,1000);
								}else{
									confirmwork=false;
									frame.dispose();
									new Feedback(level,correct,wordlist.size());
								}
							}else{
								Method.speakWord("Incorrect", type,2000);
								firsttry=false;
								JFrame a=new JFrame();
								a.add(new JLabel("Would you like to hear the spelling of the word?"));
								JButton yes=new JButton("Yes");

								yes.addActionListener(new ActionListener(){
									@Override
									public void actionPerformed(ActionEvent e) {
										a.dispose();
										for(int i=0;i<wordlist.get(wordnum).length();i++){
											Method.speakWord(wordlist.get(wordnum).charAt(i)+"", type, 800);
										}

									}
								});
								JButton no=new JButton("No");
								no.addActionListener(new ActionListener(){

									@Override
									public void actionPerformed(ActionEvent e) {
										a.dispose();

									}});
								a.setLayout(new FlowLayout());
								a.add(yes);
								a.add(no);
								a.setVisible(true);
								a.pack();

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
							if(wordnum<wordlist.size()){
								Method.speakWord(wordlist.get(wordnum), type,1000);
							}else{
								confirmwork=false;
								frame.dispose();
								new Feedback(level,correct,wordlist.size());
							}
						}
					}
					rate.setText(" "+correct+" / "+wordlist.size());
				}
				else{
					frame.dispose();
					JFrame a=new JFrame();
					a.add(new JLabel("There is no word in failed list"));
					JButton yes=new JButton("OK");
					yes.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent e) {
							a.dispose();
						}
					});
					a.add(yes);
					a.setLayout(new FlowLayout());
					a.setVisible(true);
					a.pack();
				}
			}
		});
	}
}
