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
        dict.inputHandler(start, end);
        this.end = end;
        this.solution = null;
        done = false;
        this.visited.clear();
        this.elapsedTime = System.nanoTime();
        this.visitedNode = 0;

        ArrayList<String> startPath = new ArrayList<>();
        startPath.add(start);
        currentItem = startPath;
        if (start.equals(end)) {trivialCase(); return;}

        while(!done && !visited.contains(currentItem.getLast())){
            visited.add(currentItem.getLast());
            evaluateNextNode();
        }

        this.elapsedTime = System.nanoTime() - this.elapsedTime;
        if (!done){
            currentItem.removeLast();
            solution = currentItem;
            throw new Exception("Greedy BFS : failed to get solution or no solution exists");
        }
    }

    public void trivialCase(){
        visitedNode = 1;
        solution = currentItem;
        elapsedTime = System.nanoTime() - elapsedTime;
    }

    public void evaluateNextNode(){
        List<String> possibilities = wg.getAdjacentWords(currentItem.getLast(), dict);

        if(possibilities.isEmpty()){
            currentItem.add(currentItem.getLast());
            return;
        }

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
