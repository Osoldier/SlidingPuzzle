package ch.hepia.it.opt.tp1.gui;

import ch.hepia.it.opt.tp1.P8.PuzzleN;
import ch.hepia.it.opt.tp1.core.State;
import ch.hepia.it.opt.tp1.core.heuristics.ManhattanDistFunction;
import ch.hepia.it.opt.tp1.core.heuristics.MisplacedTilesFunction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Stack;

/**
 * Created by Thomas on 18.10.16.
 */
public class MainView extends JFrame implements ActionListener {

    private final Timer timer;
    private final PuzzlePanel puzzlePanel;
    private final JButton btnSolve1, btnSolve2, btnSolve3;
    private final JTextField[] initials = new JTextField[16];
    private final JTextField[] finals = new JTextField[16];
    private final JButton btnRandom;
    private PuzzleN p;
    private int pSize = 4;

    private Stack<State> path;

    public MainView() {
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setSize(1200, 700);
        for (int i = 0; i < initials.length; i++) {
            initials[i] = new JTextField(""+(i+1) % 16);
            int x = 420 + ((i % 4) * 35);
            int y = 80 + (int)Math.floor(i/4.0)*35;
            initials[i].setBounds(x, y, 30, 30);
            this.add(initials[i]);

            finals[i] = new JTextField(""+(i+1) % 16);
            finals[i].setBounds(x+180, y, 30, 30);
            this.add(finals[i]);
        }
        String[] types = {"3x3", "4x4"};
        JComboBox ptype = new JComboBox(types);
        ptype.setBounds(420, 300, 80, 30);
        ptype.setSelectedIndex(1);
        ptype.addActionListener(e -> {
            JComboBox cb = (JComboBox)e.getSource();
            boolean v = true;
            if(cb.getSelectedIndex() == 0) {
                pSize = 3;
                initials[0].setText("1");
                initials[1].setText("2");
                initials[2].setText("3");
                initials[4].setText("4");
                initials[5].setText("5");
                initials[6].setText("6");
                initials[8].setText("7");
                initials[9].setText("8");
                initials[10].setText("0");

                finals[0].setText("1");
                finals[1].setText("2");
                finals[2].setText("3");
                finals[4].setText("4");
                finals[5].setText("5");
                finals[6].setText("6");
                finals[8].setText("7");
                finals[9].setText("8");
                finals[10].setText("0");
                v = false;
            } else {
                for (int i = 0; i < 16; i++) {
                    initials[i].setText(""+(i+1) % 16);
                    finals[i].setText(""+(i+1) % 16);

                }
                pSize = 4;
            }
            for (int i = 0; i < 4; i++) {
                initials[3+i*4].setVisible(v);
                initials[11+i].setVisible(v);
                finals[3+i*4].setVisible(v);
                finals[11+i].setVisible(v);
            }

        });
        this.add(ptype);
        this.puzzlePanel = new PuzzlePanel(10,10,400,400);
        this.btnSolve1 = new JButton("Solve Blind");
        this.btnSolve1.setLocation(420, 10);
        this.btnSolve1.setSize(200, 30);
        this.btnSolve1.addActionListener(e -> {
            PuzzleN p1 = initFromUI();
            State solution = p1.solveBlindSearch();
            showSolution(solution);
        });
        this.add(this.btnSolve1);

        this.btnSolve2 = new JButton("Solve H1");
        this.btnSolve2.setLocation(630, 10);
        this.btnSolve2.setSize(200, 30);
        this.btnSolve2.addActionListener(e -> {
            PuzzleN p1 = initFromUI();
            State solution = p1.solveHeuristic(new MisplacedTilesFunction());
            showSolution(solution);
        });
        this.add(this.btnSolve2);

        this.btnSolve3 = new JButton("Solve H2");
        this.btnSolve3.setLocation(840, 10);
        this.btnSolve3.setSize(200, 30);
        this.btnSolve3.addActionListener(e -> {
            PuzzleN p1 = initFromUI();
            State solution = p1.solveHeuristic(new ManhattanDistFunction());
            showSolution(solution);
        });
        this.add(this.btnSolve3);

        this.btnRandom = new JButton("Random Inital Puzzle");
        this.btnRandom.setBounds(840, 100, 200, 30);
        this.btnRandom.addActionListener(e -> {
            boolean[] usedValues = new boolean[pSize*pSize];
            Random rand = new Random();
            int i = 0;
            if(pSize == 3) {
                while(i < 11) {
                    int k = rand.nextInt(pSize*pSize);
                    if(!usedValues[k]) {
                        System.out.println(i);
                        usedValues[k] = true;
                        initials[i].setText(""+k);
                        if(i == 2 || i == 6 || i == 10) i++;
                        i++;
                    }
                }
            } else {
                while(i < 16) {
                    int k = rand.nextInt(pSize*pSize);
                    if(!usedValues[k]) {
                        System.out.println(i);
                        usedValues[k] = true;
                        initials[i].setText(""+k);
                        i++;
                    }
                }
            }

        });
        this.add(btnRandom);
        this.add(this.puzzlePanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.timer = new Timer(100, this);
        this.timer.start();

        this.setVisible(true);
    }

    public PuzzleN initFromUI() {
        int[][] initialTiles = null;
        int[][] finalTiles = null;
        if(pSize == 4) {
            initialTiles = new int[4][4];
            finalTiles = new int[4][4];
            for (int i = 0; i < initials.length; i++) {
                initialTiles[(int)Math.floor(i/4.0)][i % 4] = Integer.parseInt(initials[i].getText());
                finalTiles[(int)Math.floor(i/4.0)][i % 4] = Integer.parseInt(finals[i].getText());
            }
        } else {
            initialTiles = new int[][] {
                {Integer.parseInt(initials[0].getText()), Integer.parseInt(initials[1].getText()), Integer.parseInt(initials[2].getText())},
                {Integer.parseInt(initials[4].getText()), Integer.parseInt(initials[5].getText()), Integer.parseInt(initials[6].getText())},
                {Integer.parseInt(initials[8].getText()), Integer.parseInt(initials[9].getText()), Integer.parseInt(initials[10].getText())}
            };
            finalTiles = new int[][] {
                    {Integer.parseInt(finals[0].getText()), Integer.parseInt(finals[1].getText()), Integer.parseInt(finals[2].getText())},
                    {Integer.parseInt(finals[4].getText()), Integer.parseInt(finals[5].getText()), Integer.parseInt(finals[6].getText())},
                    {Integer.parseInt(finals[8].getText()), Integer.parseInt(finals[9].getText()), Integer.parseInt(finals[10].getText())}
            };
        }
        return new PuzzleN(new State(null, initialTiles), new State(null, finalTiles));
    }

    public void showSolution(State solution) {
        //Solve and print
        Stack<State> path = new Stack<State>();
        if (solution != null) {
            State tmp = solution;
            int i = 0;
            while (tmp != null) {
                path.push(tmp);
                tmp = tmp.getParent();
                i++;
            }
            System.out.println("Solution found in " + (i - 1) + " steps");
        } else {
            System.out.println("not found");
        }
        this.path = path;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(path != null && !path.isEmpty()) {
            State s = path.pop();
            this.puzzlePanel.setRenderState(s);
        }
        this.repaint();
    }

    public static void main(String[] args) {
        MainView mv = new MainView();
    }
}
