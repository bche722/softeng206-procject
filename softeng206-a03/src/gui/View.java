package gui;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class View extends JFrame{
	public View(ArrayList<String> list) {

		JPanel panel = new JPanel(new GridLayout(0, 4, 5, 5));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.add(new JLabel("Word"));
		panel.add(new JLabel("Mastered"));
		panel.add(new JLabel("Faulted"));
		panel.add(new JLabel("Failed"));
		
		for (String line : list) {
				panel.add(new JLabel(line.split(" ")[0]));
				panel.add(new JLabel(line.split(" ")[2]));
				panel.add(new JLabel(line.split(" ")[4]));
				panel.add(new JLabel(line.split(" ")[6]));
		}
		
		JOptionPane.showMessageDialog(this, panel, "View Statistics", JOptionPane.INFORMATION_MESSAGE);

	}

}
