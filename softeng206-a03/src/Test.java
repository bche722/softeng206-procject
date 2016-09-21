import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import gui.Feedback;
import gui.GameGUI;
import gui.NewQuiz;
import gui.VideoPlayer;
import method.Method;

public class Test {

	public static void main(String[] args) {
		new NewQuiz(1,Method.getNewGameList(1));
	}
}
