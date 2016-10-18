package ch.hepia.it.opt.tp1.gui;

import ch.hepia.it.opt.tp1.core.State;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Thomas on 18.10.16.
 */
public class PuzzlePanel extends JPanel {

    private State renderState;
    private final Font FONT = new Font("TimesRoman", Font.PLAIN, 24);

    public PuzzlePanel(int x, int y, int width, int height) {
        this.setLocation(x,y);
        this.setSize(width, height);
        this.setBackground(Color.white);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(FONT);

        if(renderState != null) {
            int tSize = this.getSize().width / renderState.getTiles().length;
            for (int i = 0; i < renderState.getTiles().length; i++) {
                for (int j = 0; j < renderState.getTiles()[0].length; j++) {
                    if(renderState.getTiles()[i][j] != 0) {
                        g2.setColor(Color.red);
                        g2.fillRect(j*tSize, i*tSize, tSize, tSize);
                        g2.setColor(Color.black);
                        g2.drawString("" + renderState.getTiles()[i][j], j * tSize + tSize / 2, i * tSize + tSize / 2);
                    }
                    g2.drawRect(j*tSize, i*tSize, tSize, tSize);
                }
            }
        }
    }

    public State getRenderState() {
        return renderState;
    }

    public void setRenderState(State renderState) {
        this.renderState = renderState;
    }
}
