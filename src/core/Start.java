package core;

import config.Config;
import gui.GUI;

import javax.swing.*;
import java.io.File;

public class Start {

    public static GUI gui;
    public static Config config = new Config(new File("IconCreator.cfg")).load().registerShutdownHook();

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        gui = new GUI();
        gui.pack();
        gui.setVisible(true);
    }

}
