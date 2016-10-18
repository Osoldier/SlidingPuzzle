package ch.hepia.it.opt.tp1.gui;

import ch.hepia.it.opt.tp1.core.State;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

/**
 * Created by Thomas on 18.10.16.
 */
public class MainView extends JFrame implements ActionListener {

    private final Timer timer;
    private final PuzzlePanel puzzlePanel;
    private final TreePanel treePanel;
    private Stack<State> path;

    public MainView(Stack<State> path) {
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setSize(1200, 700);
        this.puzzlePanel = new PuzzlePanel(0,0,400,400);
        this.treePanel = new TreePanel();
        this.add(this.puzzlePanel);
        this.add(this.treePanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.timer = new Timer(400, this);
        this.timer.start();
        this.path = path;

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!path.isEmpty()) {
            State s = path.pop();
            this.puzzlePanel.setRenderState(s);
        }
        this.repaint();
    }
}
