import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class WordLadderSolverGUI extends JFrame implements ActionListener{

    private JTextField startWordField;
    private JTextField endWordField;
    private JTextArea solutionArea;
    private JButton solveButton;
    private ButtonGroup algorithmGroup;

    private UniformCostSearch ucs = new UniformCostSearch();
    private GreedyBFS gbfs = new GreedyBFS();
    private Astar astar = new Astar();
    private WordLadderSolver solver;

    public WordLadderSolverGUI() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        super("Word Ladder Solver");
        JFrame frame = new JFrame("Word Ladder Solver");
        frame.setMinimumSize(new Dimension(1200, 800));
        UIManager.setLookAndFeel("com.formdev.flatlaf.themes.FlatMacDarkLaf");

        // Create UI components
        JLabel startWordLabel = new JLabel("Start Word:");
        startWordField = new JTextField(15);
        JLabel endWordLabel = new JLabel("End Word:");
        endWordField = new JTextField(15);

        solutionArea = new JTextArea(5, 20);
        solutionArea.setEditable(false);

        solveButton = new JButton("Solve");
        solveButton.addActionListener(this);

        algorithmGroup = new ButtonGroup();
        JRadioButton uniformCostButton = new JRadioButton("Uniform Cost Search");
        JRadioButton greedyButton = new JRadioButton("Greedy Best-First Search");
        JRadioButton astarButton = new JRadioButton("A* Search");

        // Layout components
        JPanel inputPanel = new JPanel();
        inputPanel.add(startWordLabel);
        inputPanel.add(startWordField);
        inputPanel.add(endWordLabel);
        inputPanel.add(endWordField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(solveButton);

        algorithmGroup.add(uniformCostButton);
        algorithmGroup.add(greedyButton);
        algorithmGroup.add(astarButton);

        JPanel algorithmPanel = new JPanel();
        algorithmPanel.setBorder(BorderFactory.createTitledBorder("Algorithm"));
        algorithmPanel.add(uniformCostButton);
        algorithmPanel.add(greedyButton);
        algorithmPanel.add(astarButton);

        JScrollPane scrollPane = new JScrollPane(solutionArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setSize( 100, 100 );

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(inputPanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(algorithmPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(scrollPane);

        inputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        algorithmPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalGlue());

        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (uniformCostButton.isSelected()) {
                    solver = ucs;
                } else if (greedyButton.isSelected()) {
                    solver = gbfs;
                } else if (astarButton.isSelected()) {
                    solver = astar;
                } else{
                    return;
                }

                String startWord = startWordField.getText();
                String endWord = endWordField.getText();
                String err = null;
                try {
                    solver.solve(startWord, endWord);
                }
                catch (Exception ex2){
                    err = ex2.getMessage();
                }

                if (err == null){
                    List<String> solution = solver.getSolution();
                    String additionalInfo = "\nVisited Nodes: " + solver.getVisitedNode() + "\nExecution Time: " + (double)solver.getElapsedTime()/1000000.0 + "ms";

                    solutionArea.setText(String.join("\n", solution) + additionalInfo);
                }
                else{
                    solutionArea.setText(err);
                }
            }
        });

        solutionArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateScrollPaneSize();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateScrollPaneSize();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateScrollPaneSize();
            }

            private void updateScrollPaneSize() {
                int preferredHeight = solutionArea.getPreferredSize().height;
                scrollPane.setPreferredSize(new Dimension(scrollPane.getPreferredSize().width, preferredHeight));
                mainPanel.revalidate();
            }
        });

        setContentPane(mainPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center on screen
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ;
    }
}