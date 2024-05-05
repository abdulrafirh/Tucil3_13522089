//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception{
        Astar solver = new Astar();
        solver.solve("piano", "chord");
        System.out.println(solver.getSolution());
        System.out.printf("Ladder Length : %d\n", solver.getSolution().size());
        System.out.printf("Visited Node : %d\n", solver.getVisitedNode());
        System.out.printf("Elapsed Time : %.3f ms\n", solver.getElapsedTime() / (double)1000000);
//        WordSet ws = new WordSet();
//        TextDistanceCalculator tdc = new TextDistanceCalculator();
//        tdc.generateCSVTable(ws);
    }
}