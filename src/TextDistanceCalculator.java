import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextDistanceCalculator {

    private final Map<Character, Map<Character, Double>> table;

    public TextDistanceCalculator(){
        table = new HashMap<>();
        try {
            File txtFile = new File("src/resources/distance.txt");
            Scanner txtReader = new Scanner(txtFile);
            for (char chara = 'a'; chara <= 'z'; chara++) {
                String line = txtReader.nextLine();
                String[] numbersStr = line.strip().split(" ");
                table.put(chara, new HashMap<>());
                for(int i = 0; i < 26; i++){
                    table.get(chara).put((char)('a' + i), Double.parseDouble(numbersStr[i]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Build distance table first!");
            e.printStackTrace();
        }
    }

    public Double CalculateDistance(String text1, String text2){
        double distance = 0.0;
        for(int i = 0; i < text1.length(); i++){
            distance += table.get(text1.charAt(i)).get(text2.charAt(i));
        }
        return distance;
    }

    public void generateCSVTable(WordSet ws){
        HashMap<Character, HashMap<Character, Integer>> hitTable = new HashMap<>();
        HashMap<Character, HashMap<Character, Integer>> nTable = new HashMap<>();
        long progress = 0;

        for (char chara = 'a'; chara <= 'z'; chara++){
            hitTable.put(chara, new HashMap<>());
            nTable.put(chara, new HashMap<>());
            for (char charb = 'a'; charb <= 'z'; charb++){
                hitTable.get(chara).put(charb, 0);
                nTable.get(chara).put(charb, 0);
            }
        }

        for (String word : ws) {

            if (progress % (ws.length()/10) == 0){
                System.out.printf("Currently examining %d/%d words\n", progress, ws.length());
            }

            progress++;
            for (int i = 0; i < word.length(); i++){
                char currentChar = word.charAt(i);
                for(char chara = 'a'; chara <= 'z'; chara++){
                    if (currentChar != chara) {
                        StringBuilder maybeWord = new StringBuilder(word);
                        maybeWord.setCharAt(i, chara);

                        nTable.get(currentChar).put(chara, nTable.get(currentChar).get(chara) + 1);
                        if (ws.wordExist(maybeWord.toString())) {
                            hitTable.get(currentChar).put(chara, hitTable.get(currentChar).get(chara) + 1);
                        }
                    }
                }
            }
        }

        try {
            FileWriter writer = new FileWriter("src/resources/distance.txt");
            for (char chara = 'a'; chara <= 'z'; chara++){
                for (char charb = 'a'; charb <= 'z'; charb++){
                    if (chara == charb){
                        writer.write("0 ");
                    }
                    else{
                        long hitFrequency = hitTable.get(chara).get(charb)*100;
                        long tryFrequency = nTable.get(chara).get(charb);
                        writer.write(((Double)((double)tryFrequency/(double)hitFrequency)).toString());
                        writer.write(" ");
                    }
                }
                writer.write("\n");
            }
            writer.close();
        }
        catch (IOException e){
            System.out.println("Error during file writing.");
            e.printStackTrace();
        }

    }

    public static void main(String[] args){
//        WordSet ws = new WordSet();
//        ws.loadFile("src/resources/words_alpha.txt");

        TextDistanceCalculator tdc = new TextDistanceCalculator();
//        tdc.generateCSVTable(ws);
        Double dist = tdc.CalculateDistance("peek", "pook");
        System.out.println(dist);
    }
}
