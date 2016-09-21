package method;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.SwingWorker;

public class Method {
	private static FailedlistWriter w1 = new FailedlistWriter();
	private static StatisticsWriter w2 = new StatisticsWriter();
	private static Reader readers =new Reader("src/.statisticslist");
	private static Reader readern =new Reader("src/NZCER-spelling-lists.txt");
	private static Reader readerr =new Reader("src/.failedlist");
	private static SpeakWorker worker=null;
	
	public static void clear() {
		w1.resetFaultedlist();
		w2.resetStatisticslist();
	}

	public static void writeWord(int level, String word, WordType type) {
		if(type==WordType.Failed){
			w1.writeWord(level, word);
		}else{
			w1.deleteWord(word);
		}
		w2.writeWord(level, word, type);
	}

	public static void speakWord(String line,VoiceType type,int delay){
		worker=new Method.SpeakWorker(type,line,worker,delay);
		worker.execute();
	}

	public static ArrayList<String> getStatisticsList(int level){
		readers.setup();
		return readers.AllWordList(level);
	}

	public static ArrayList<String> getNewGameList(int level){
		return readern.RandomWordList(level);
	}

	public static ArrayList<String> getReviewList(int level){
		readerr.setup();
		return readerr.RandomWordList(level);
	}

	static class SpeakWorker extends SwingWorker<Void,Integer> {
		private VoiceType type;
		private String line;
		private SpeakWorker lastworker;
		private int delay;
		public SpeakWorker(VoiceType type,String line,SpeakWorker lastworker,int delay){
			this.type=type;
			this.line=line;
			this.lastworker=lastworker;
			this.delay=delay;
		}
		
		@Override
		protected Void doInBackground() throws Exception {
			try {
				if(lastworker!=null){
					while(!lastworker.isDone()){
						Thread.sleep(10);
					}
				}
				BufferedWriter out = new BufferedWriter(new FileWriter("src/.speakout.scm"));
				if(type==VoiceType.Voice1){
					out.write("(voice_kal_diphone)");
				}else{
					out.write("(voice_akl_nz_jdt_diphone)");
				}
				out.newLine();
				out.write("(SayText \""+line+"\")");
				out.newLine();
				out.flush();
				out.close();
				ProcessBuilder pb = new ProcessBuilder("bash", "-c", "festival -b src/.speakout.scm");
				pb.start();
				Thread.sleep(delay);
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}    
	}
}
