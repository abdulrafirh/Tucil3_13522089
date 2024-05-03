//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        UniformCostSearch ucs = new UniformCostSearch("src/resources/words_alpha.txt");
        System.out.println(ucs.solve("gray", "dull"));
    }
}