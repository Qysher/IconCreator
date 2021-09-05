package utils;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

public class FileUtils {

    public static File openFileDialog(JFrame jFrame, String prompt, File folder) {
        File file = null;
        try {
            FileDialog fileDialog = new FileDialog(new Dialog(jFrame), prompt, FileDialog.LOAD);
            fileDialog.setMultipleMode(false);
            if(folder != null) fileDialog.setDirectory(folder.getAbsolutePath());
            fileDialog.setVisible(true);
            if(fileDialog.getFiles().length > 0) {
                File selectedFile = fileDialog.getFiles()[0];
                if(selectedFile != null)
                    file = selectedFile;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public static File fileSaveDialog(JFrame jFrame, String prompt, File folder) {
        File file = null;
        try {
            FileDialog fileDialog = new FileDialog(new Dialog(jFrame), prompt, FileDialog.SAVE);
            fileDialog.setMultipleMode(false);
            if(folder != null) fileDialog.setDirectory(folder.getAbsolutePath());
            fileDialog.setFile(firstFreeFile(new File(fileDialog.getDirectory())) + ".png");
            fileDialog.setVisible(true);
            if(fileDialog.getFiles().length > 0) {
                File selectedFile = fileDialog.getFiles()[0];
                if(selectedFile != null)
                    file = selectedFile;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public static int firstFreeFile(File directory) {
        int counter = 0;
        while((new File(counter + ".png").exists())) counter++;
        return counter;
    }

}
