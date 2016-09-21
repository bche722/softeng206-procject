import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

@SuppressWarnings("serial")
public class VideoPlayer extends JFrame {
	private EmbeddedMediaPlayerComponent mediaPlayerComponent;
	private EmbeddedMediaPlayer player;
	private File video;
	private JButton pandp;
	private JButton stop;
	private JPanel buttons;

	public VideoPlayer() {
		super("VideoPlayer");
		buildPath();
		buildPlayerComponent();
		buildPlayer();
		buildButtons();
		buildFrame();
	}

	private void buildPath() {
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "/usr/lib");
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
		video = new File("src/big_buck_bunny_1_minute.avi");
	}

	private void buildPlayerComponent() {
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		mediaPlayerComponent.setSize(1050, 650);
		add(mediaPlayerComponent,BorderLayout.CENTER);
	}

	private void buildPlayer() {
		player = mediaPlayerComponent.getMediaPlayer();
	}

	private void buildFrame(){
		setSize(1050, 680);
		setVisible(true);
		try {
			player.playMedia(video.getCanonicalPath());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void buildButtons(){
		buildPandp();
		buildStop();
		buttons=new JPanel();
		buttons.add(pandp);
		buttons.add(stop);
		buttons.setLayout(new FlowLayout());
		buttons.setSize(1050,30);
		add(buttons,BorderLayout.SOUTH);
	}
	
	private void buildPandp(){
		pandp=new JButton("pause");
		pandp.setPreferredSize(new Dimension(80,25));
		pandp.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(pandp.getText().equals("pause")){
					player.pause();
					pandp.setText("play");
				}else{
					player.play();
					pandp.setText("pause");
				}
				
			}});
	}
	
	private void buildStop(){
		stop=new JButton("stop");
		stop.setPreferredSize(new Dimension(80,25));
		stop.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				player.stop();
				pandp.setText("play");
			}});
	}
}