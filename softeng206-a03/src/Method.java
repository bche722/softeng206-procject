
public class Method {
	private static FailedlistWriter w1 = new FailedlistWriter();
	private static StatisticsWriter w2 = new StatisticsWriter();

	public static void clear() {
		w1.resetFaultedlist();
		w2.resetStatisticslist();
	}

	public static void writeWord(int level, String word, Type type) {
		if(type.equals(Type.Failed)){
			w1.writeWord(level, word);
		}else if(type.equals(Type.Mastered)){
			w1.deleteWord(word);
		}
		w2.writeWord(level, word, type);
	}
}
