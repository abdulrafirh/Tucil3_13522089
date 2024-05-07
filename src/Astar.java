import java.util.*;

public class Astar implements WordLadderSolver {

    // Calculation Tools
    private final WordGenerator wg;
    private final WordSet   dict;
    private final TextDistanceCalculator tdc;

    // State Objects
    private String end;
    private boolean done;
    private final PriorityQueue<Pair<List<String>, Double>> theQueue;
    private final HashSet<String> visited;

    // Solution Object
    private List<String> solution;
    private Integer visitedNode;
    private Long elapsedTime;

    public Astar(){
        wg = new WordGenerator();
        dict = new WordSet();
        tdc = new TextDistanceCalculator();
        theQueue = new PriorityQueue<>();
        visited = new HashSet<>();
    }

    public void solve(String start, String end) throws Exception{
        dict.inputHandler(start, end);
        this.end = end;
        this.visitedNode = 1;
        this.solution = null;
        done = false;
        theQueue.clear();
        visited.clear();
        this.elapsedTime = System.nanoTime();
        ArrayList<String> startPath = new ArrayList<>();
        startPath.add(start);
        visited.add(start);
        theQueue.add(new Pair<>(startPath, evaluateWeight(startPath)));
        if (start.equals(end)) {trivialCase(); return;}

        while(!done && !theQueue.isEmpty()){
            evaluateNextNode();
        }

        this.elapsedTime = System.nanoTime() - this.elapsedTime;
        if (!done){
            solution = null;
            throw new Exception("A* : No solution exists");
        }
    }

    public void inputHandler(String start, String end)throws Exception{
        if (start.length() != end.length()){
            throw new Exception("Invalid Input : Words have different length");
        }
        else if(!dict.wordExist(start)){
            throw new Exception("Invalid starting word (doesn't exist in dictionary)");
        }
        else if(!dict.wordExist(end)){
            throw new Exception("Invalid ending word (doesn't exist in dictionary)");
        }
    }

    public void trivialCase(){
        visitedNode = 1;
        solution = theQueue.peek().first();
        elapsedTime = System.nanoTime() - elapsedTime;
    }

    public void evaluateNextNode(){
        Pair<List<String>, Double> currentItem = theQueue.remove();

        List<String> possibilities = wg.getAdjacentWords(currentItem.first().getLast(), dict);

        for(int i = 0; i < possibilities.size() && !done; i++){
            if (!visited.contains(possibilities.get(i))){
                resolveBranch(currentItem, possibilities.get(i));
                visited.add(possibilities.get(i));
                this.visitedNode += 1;
            }
        }
    }

    public void resolveBranch(Pair<List<String>, Double> prev, String current){
        if (current.equals(end)){
            done = true;
            solution = new ArrayList<>(prev.first());
            solution.add(current);
        }
        else{
            List<String> newPath = new ArrayList<>(prev.first());
            newPath.add(current);
            theQueue.add(new Pair<>(newPath, evaluateWeight(newPath)));
        }
    }

    public double evaluateWeight(List<String> current){
        return current.size() - 1 + tdc.CalculateDistance(current.getLast(), end);
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
