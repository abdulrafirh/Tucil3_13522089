import java.util.HashSet;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public class WordSet implements Iterable<String> {

    private final HashSet<String> wordStore;
    private static final String defaultDict = "src/resources/words_alpha.txt";

    public WordSet(){
        wordStore = new HashSet<>();
        loadFile(defaultDict);
    }

    public WordSet(String txtPath){
        wordStore = new HashSet<>();
        loadFile(txtPath);
    }

    public void inputHandler(String start, String end)throws Exception{
        if (start.length() != end.length()){
            throw new Exception("Invalid Input : Words have different length");
        }
        else if(!wordExist(start)){
            throw new Exception("Invalid starting word (doesn't exist in dictionary)");
        }
        else if(!wordExist(end)){
            throw new Exception("Invalid ending word (doesn't exist in dictionary)");
        }
    }

    public void loadFile(String path){
        try {
            File txtFile = new File(path);
            Scanner txtReader = new Scanner(txtFile);
            while (txtReader.hasNextLine()) {
                String data = txtReader.nextLine();
                wordStore.add(data);
            }
            txtReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public boolean wordExist(String word){
        return wordStore.contains(word);
    }

    @Override
    public Iterator<String> iterator(){
        return wordStore.iterator();
    }

    public long length(){
        return wordStore.size();
    }
}
