import java.util.*;

public interface WordLadderSolver {

    /*
     * @param
     * start     : starting Word
     * end       : ending Word
     *
     * @output
     * output word ladder chain from start to end in an instance of ArrayList<String>
     */
    List<String> solve(String start, String end);

    /*
     * Pop from the queue and evaluate the popped value
     */
    void evaluateNextNode();

    /*
     * Resolve a possible path, by calculating its weight and enqueueing it
     * Also checks if the search is finished
     */
    void resolveBranch(AbstractMap.SimpleEntry<List<String>, Double> prev, String current);

    /*
     * @param
     * prev     : The previous queue item
     *
     * Evaluate the start cost for the next item to be enqueued
     */
    double evaluateStartCost(AbstractMap.SimpleEntry<List<String>, Double> prev);

    /*
     * @param
     * current      : node to be calculated the heuristic value from
     * target       : target value for the heuristic calculation
     *
     * Calculate the heuristic value of current string with respect to the target
     */
    double evaluateHeuristic(String current);

    /*
     * Weight is calculated as the sum of the cost and heuristic function
     */
    double evaluateWeight(AbstractMap.SimpleEntry<List<String>, Double> prev, String current);
}
