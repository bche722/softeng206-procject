
public class Word {

	private String word;
	private int masteredCount;
	private int failedCount;
	private int faultedCount;

	public Word(String word, int masteredCount, int failedCount, int faultedCount) {
		this.word = word;
		this.masteredCount = masteredCount;
		this.failedCount = failedCount;
		this.faultedCount = faultedCount;
	}

	public Word(String word) {
		this(word, 0, 0, 0);
	}

	public String getWord() {
		return word;
	}

	public int getMasteredCount() {
		return masteredCount;
	}

	public int getFailedCount() {
		return failedCount;
	}

	public int getFaultedCount() {
		return faultedCount;
	}

	public void incrementMasteredCount() {
		masteredCount++;
	}

	public void incrementFailedCount() {
		failedCount++;
	}

	public void incrementFaultedCount() {
		faultedCount++;
	}
	
	public void clearResults(){
		masteredCount = 0;
		failedCount = 0;
		faultedCount = 0;
	}

	public boolean isFailed() {
		return failedCount > 0 && masteredCount == 0 && faultedCount == 0;
	}
	
	public boolean wasQuizzed(){
		return failedCount > 0 || masteredCount > 0 || faultedCount > 0;
	}

	@Override
	public String toString() {
		return word+","+masteredCount+","+failedCount+","+faultedCount;
	}
	
	

}
