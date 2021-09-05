package gui;

import gui.interfaces.DrawInterface;

import javax.swing.*;
import java.awt.*;

public class DrawPanel extends JPanel {

    private DrawInterface drawInterface = null;

    public DrawPanel(DrawInterface drawInterface) {
        this.drawInterface = drawInterface;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(drawInterface == null) return;
        Graphics2D g2D = (Graphics2D) g;
        drawInterface.paint(g2D, getWidth(), getHeight());
    }

}
