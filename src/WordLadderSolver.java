import java.util.List;

public interface WordLadderSolver {

    /*
     * @param
     * start     : starting Word
     * end       : ending Word
     *
     * @output
     * output word ladder chain from start to end in an instance of LinkedList<String>
     */
    void solve(String start, String end) throws Exception;

    public List<String> getSolution();
    public Long getElapsedTime();
    public Integer getVisitedNode();
}
