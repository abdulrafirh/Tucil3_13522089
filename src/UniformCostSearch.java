import java.util.*;

public class UniformCostSearch implements WordLadderSolver {

    private final WordGenerator wg;
    private final WordSet   dict;
    private String end;
    private final Queue<AbstractMap.SimpleEntry<List<String>, Double>> theQueue;
    private boolean done;
    private List<String> solution;

    public UniformCostSearch(String dictPath){
        wg = new WordGenerator();
        dict = new WordSet();
        dict.loadFile(dictPath);
        theQueue = new LinkedList<>();
    }

    public List<String> solve(String start, String end){
        this.end = end;

        ArrayList<String> startPath = new ArrayList<>();
        startPath.add(start);
        theQueue.add(new AbstractMap.SimpleEntry<>(startPath, 0.0));

        while(!done && !theQueue.isEmpty()){
            evaluateNextNode();
        }

        if (done){
            return solution;
        }
        else{
            return new ArrayList<>();
        }
    }

    public void evaluateNextNode(){
        AbstractMap.SimpleEntry<List<String>, Double> currentItem = theQueue.remove();

        List<String> possibilities = wg.getAdjacentWords(currentItem.getKey().getLast(), dict);
        for(int i = 0; i < possibilities.size() && !done; i++){
            resolveBranch(currentItem, possibilities.get(i));
        }
    }

    public void resolveBranch(AbstractMap.SimpleEntry<List<String>, Double> prev, String current){
        if (current.equals(end)){
            done = true;
            solution = new ArrayList<>(prev.getKey());
            solution.add(current);
        }
        else{
            List<String> newPath = new ArrayList<>(prev.getKey());
            newPath.add(current);
            AbstractMap.SimpleEntry<List<String>, Double> newItem = new AbstractMap.SimpleEntry<>(newPath, evaluateWeight(prev, current));
            theQueue.add(newItem);
        }
    }

    public double evaluateStartCost(AbstractMap.SimpleEntry<List<String>, Double> prev){
        return prev.getValue() + 1;
    }

    public double evaluateHeuristic(String current){
        // UCS doesn't use Heuristics
        return 0;
    }

    public double evaluateWeight(AbstractMap.SimpleEntry<List<String>, Double> prev, String current){
        return evaluateStartCost(prev) + evaluateHeuristic(current);
    }
}
