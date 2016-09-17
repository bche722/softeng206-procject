import java.util.ArrayList;

public class Test {

	public static void main(String[] args) {
		ArrayList<String> list = Method.getNewGameList(1);
 
		for(String word:list){
			System.out.println(word);
		}
		
		Method.clear();
		Method.writeWord(5, "hhhh", WordType.Failed);
		Method.writeWord(5, "heheheh", WordType.Failed);
		
		list=Method.getReviewList(5);
		for(String word:list){
			System.out.println(word);
		}
	}
}
