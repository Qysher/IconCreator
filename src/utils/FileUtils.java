package utils;

import config.Keys;

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

    public static File[] openFilesDialog(JFrame jFrame, String prompt, File folder) {
        try {
            FileDialog fileDialog = new FileDialog(new Dialog(jFrame), prompt, FileDialog.LOAD);
            fileDialog.setMultipleMode(true);
            if(folder != null) fileDialog.setDirectory(folder.getAbsolutePath());
            fileDialog.setVisible(true);
            return fileDialog.getFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new File[]{};
    }

    public static File fileSaveDialog(JFrame jFrame, String prompt, File folder) {
        File file = null;
        try {
            FileDialog fileDialog = new FileDialog(new Dialog(jFrame), prompt, FileDialog.SAVE);
            fileDialog.setMultipleMode(false);
            if(folder != null)
                fileDialog.setDirectory(folder.getAbsolutePath());
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

    public static File selectDirectoryDialog(JFrame jFrame, String prompt, File folder) {
        File file = null;
        try {
            if(folder == null) {
                File tempFolder = new File(Keys.KEY_SAVE_DIRECTORY_LAST_DIRECTORY.getValue());
                if(tempFolder.exists()) folder = tempFolder;
            }

            JFileChooser jFileChooser = new JFileChooser();
            if(folder != null)
                jFileChooser.setCurrentDirectory(folder);
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jFileChooser.showSaveDialog(jFrame);
            jFileChooser.setDialogTitle(prompt);
            file = jFileChooser.getSelectedFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public static int firstFreeFile(File directory) {
        int counter = 0;
        while((new File(directory, counter + ".png").exists())) counter++;
        return counter;
    }

}
