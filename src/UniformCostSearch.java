import java.util.*;

public class UniformCostSearch implements WordLadderSolver {

    // Calculation Tools
    private final WordGenerator wg;
    private final WordSet   dict;

    // State Objects
    private String end;
    private boolean done;
    private PriorityQueue<Pair<LinkedList<String>, Double>> theQueue;
    private HashSet<String> visited;

    // Solution Object
    private LinkedList<String> solution;
    private Integer visitedNode;
    private Long elapsedTime;

    public UniformCostSearch(){
        wg = new WordGenerator();
        dict = new WordSet();
        theQueue = new PriorityQueue<>();
        visited = new HashSet<>();
    }

    public void solve(String start, String end) throws Exception{
        dict.inputHandler(start, end);
        this.end = end;
        this.visitedNode = 1;
        this.done = false;
        this.solution = null;
        theQueue.clear();
        visited.clear();
        this.elapsedTime = System.nanoTime();

        LinkedList<String> startPath = new LinkedList<>();
        startPath.add(start);
        visited.add(start);
        theQueue.add(new Pair<>(startPath, 0.0));
        if (start.equals(end)) {trivialCase(); return;}

        while(!done && !theQueue.isEmpty()){
            evaluateNextNode();
        }

        if (!done){
            solution = new LinkedList<>();
            throw new Exception("UCS : No solution exists");
        }

        this.elapsedTime = System.nanoTime() - this.elapsedTime;
    }


    public void trivialCase(){
        visitedNode = 1;
        solution = theQueue.peek().first();
        elapsedTime = System.nanoTime() - elapsedTime;
    }

    public void evaluateNextNode(){
         Pair<LinkedList<String>, Double> currentItem = theQueue.remove();

        LinkedList<String> possibilities = wg.getAdjacentWords(currentItem.first().getLast(), dict);

        for(int i = 0; i < possibilities.size() && !done; i++){
            if (!visited.contains(possibilities.get(i))){
                resolveBranch(currentItem, possibilities.get(i));
                visited.add(possibilities.get(i));
                this.visitedNode += 1;
            }
        }
    }

    public void resolveBranch(Pair<LinkedList<String>, Double> prev, String current){
        if (current.equals(end)){
            done = true;
            solution = new LinkedList<>(prev.first());
            solution.add(current);
        }
        else{
            LinkedList<String> newPath = new LinkedList<>(prev.first());
            newPath.add(current);
            theQueue.add(new Pair<>(newPath, prev.second() + 1.0));
        }
    }

    public LinkedList<String> getSolution(){
        return solution;
    }

    public Long getElapsedTime(){
        return elapsedTime;
    }

    public Integer getVisitedNode(){
        return visitedNode;
    }
}
