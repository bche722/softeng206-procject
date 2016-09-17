import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Method {
	private static FailedlistWriter w1 = new FailedlistWriter();
	private static StatisticsWriter w2 = new StatisticsWriter();

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
	
	public static void speakWord(String line,VoiceType type){
		try {
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
