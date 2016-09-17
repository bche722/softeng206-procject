import java.util.ArrayList;

public class Test {

	public static void main(String[] args) {
		Reader reader=new Reader("src/NZCER-spelling-lists.txt");
		ArrayList<String> list = reader.RandomWordList(1);
 
		for(String word:list){
			System.out.println(word);
		}
		
		Method.writeWord(3, "hhhh", Type.Failed);
		Method.writeWord(5, "heheheh", Type.Failed);
		Method.clear();
	}
}
