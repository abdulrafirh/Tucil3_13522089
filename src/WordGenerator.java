import java.util.LinkedList;

public class WordGenerator {

    public LinkedList<String> getAdjacentWords(String word, WordSet dict){
        LinkedList<String> result = new LinkedList<>();
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
