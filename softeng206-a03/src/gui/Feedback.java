package gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import method.Method;

@SuppressWarnings("serial")
public class Feedback extends JFrame{
	private JPanel pane;
	private JPanel p1;
	private JPanel p2;
	private JPanel p3;
	private JButton redo;
	private JButton next;
	private JButton menu;
	private JButton video;
	private JFrame frame=this;
	
	public Feedback(int level,int correct,int total){
		super("Feedback");

		this.pane = (JPanel) this.getContentPane();
		this.pane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		this.pane.setLayout(new GridLayout(0, 1, 6, 6));
		
		p1=new JPanel();
		p1.setLayout(new FlowLayout());
		p1.add(new JLabel("Accuracy Rate: "+correct+"/"+total));
		pane.add(p1);
		
		p2=new JPanel();
		p2.setLayout(new FlowLayout());
		redo=new JButton("Redo this level");
		redo.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new NewQuiz(level,Method.getNewGameList(level));
				frame.dispose();
			}});
		next=new JButton("Go to next level");
		next.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new NewQuiz(level+1,Method.getNewGameList(level+1));
				frame.dispose();
			}});
		menu=new JButton("Return to main menu");
		menu.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}});
		p2.add(redo);
		p2.add(next);
		p2.add(menu);
		pane.add(p2);

		p3=new JPanel();
		p3.setLayout(new FlowLayout());
		video=new JButton("Play the reward video");
		video.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new VideoPlayer();
				frame.dispose();
			}});
		p3.add(video);
		pane.add(p3);

		if(correct<9){
			next.setEnabled(false);
			video.setEnabled(false);
		}
		if(level==11){
			next.setEnabled(false);
		}
		this.setVisible(true);
		pack();
	}
}
