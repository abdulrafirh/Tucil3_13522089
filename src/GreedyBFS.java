import java.util.*;

public class GreedyBFS implements WordLadderSolver{

    // Calculation Tools
    private final WordGenerator wg;
    private final WordSet   dict;
    private final TextDistanceCalculator tdc;

    // State Object
    private String end;
    private boolean done;
    private List<String> currentItem;
    private final HashSet<String> visited;

    // Solution
    private List<String> solution;
    private Integer visitedNode;
    private Long elapsedTime;

    public GreedyBFS(){
        wg = new WordGenerator();
        tdc = new TextDistanceCalculator();
        dict = new WordSet();
        visited = new HashSet<>();
    }

    public void solve(String start, String end) throws Exception{
        this.end = end;
        this.elapsedTime = System.nanoTime();
        this.visitedNode = 0;

        ArrayList<String> startPath = new ArrayList<>();
        startPath.add(start);
        currentItem = startPath;

        while(!done && !visited.contains(currentItem.getLast())){
            visited.add(currentItem.getLast());
            evaluateNextNode();
        }

        this.elapsedTime = System.nanoTime() - this.elapsedTime;
        if (!done){
            currentItem.removeLast();
            throw new Exception("Greedy BFS failed to get solution");
        }
    }

    public void evaluateNextNode(){
        List<String> possibilities = wg.getAdjacentWords(currentItem.getLast(), dict);
        int nextIdx = 0;
        double minVal = -1;
        for(int i = 0; i < possibilities.size() && !done; i++){
            if (possibilities.get(i).equals(end)){
                done = true;
                solution = currentItem;
                solution.add(end);
                return;
            }
            if (!visited.contains(possibilities.get(i))){
                if (minVal == -1 || evaluateWeight(possibilities.get(i)) < minVal){
                    minVal = evaluateWeight(possibilities.get(i));
                    nextIdx = i;
                }
                visitedNode += 1;
            }
        }
        currentItem.add(possibilities.get(nextIdx));
    }

    public double evaluateWeight(String current){
        return tdc.CalculateDistance(current, end);
    }

    public List<String> getSolution(){
        return solution;
    }

    public Long getElapsedTime(){
        return elapsedTime;
    }

    public Integer getVisitedNode(){
        return visitedNode;
    }
}
