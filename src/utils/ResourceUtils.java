package utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

public class ResourceUtils {

    public static Image loadImageFromString(String string) {
        //System.out.println("loadImageFromString(\"" + string + "\");");
        if(string == null || string.isEmpty()) return getEmptyImage();
        if(string.startsWith("http")) {
            return loadImageFromURL(string);
        } else {
            Image image = loadImageFromFile(new File(string));
            if(image == null)
                image = loadImageFromFileIcon(new File(string));
            return image;
        }
    }

    public static Image loadImageFromURL(String url) {
        Image image = getEmptyImage();
        if(url != null && !url.isEmpty()) {
            try {
                image = ImageIO.read(new URL(url).openStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return image;
    }

    public static Image loadImageFromFile(File file) {
        Image image = getEmptyImage();
        if(file != null) {
            try {

                image = ImageIO.read(new FileInputStream(file));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return image;
    }

    public static Image loadImageFromFileIcon(File file) {
        Image image = getEmptyImage();
        if(file != null) {
            try {
                ImageIcon icon = (ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(file);
                image = icon.getImage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return image;
    }

    public static Image getEmptyImage() {
        return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    }

}
