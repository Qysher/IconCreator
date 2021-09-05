package utils;

import javax.swing.*;

public class MessageUtils {

    public static void showExceptionMessage(Exception e) {
        JOptionPane.showMessageDialog(null, e.getStackTrace(), e.getClass() + ": " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
    }

    public static void showInfoMessage(String title, Object message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

}
