import java.util.ArrayList;
import java.util.List;

public class WordGenerator {

    public List<String> getAdjacentWords(String word, WordSet dict){
        List<String> result = new ArrayList<>();
        for(char letter = 'a'; letter <= 'z'; letter++){
            for(int i = 0;i < word.length(); i++){
                if (word.charAt(i) != letter){
                    StringBuilder newWord = new StringBuilder(word);
                    newWord.setCharAt(i, letter);
                    if (dict.wordExist(newWord.toString())){
                        result.add(newWord.toString());
                    }
                }
            }
        }
        return result;
    }

}
