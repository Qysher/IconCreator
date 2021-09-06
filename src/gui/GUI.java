package gui;

import batchmode.Batchmode;
import config.Keys;
import icon.IconPainter;
import utils.DrawUtils;
import utils.FileUtils;
import utils.MessageUtils;
import utils.ResourceLoader;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigInteger;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

public class  GUI extends JFrame {
    private JPanel panel_contentPane;
    private JPanel panel_controls;
    private JPanel panel_preview;
    private JLabel label_background;
    private JTextField textField_background;
    private JButton button_backgroundFileSelector;
    private JLabel label_foreground;
    private JTextField textField_foreground;
    private JButton button_foregroundFileSelector;
    private JLabel label_margin;
    private JSlider slider_margin;
    private JLabel label_marginValue;
    private JLabel label_backgroundBrightness;
    private JSlider slider_backgroundBrightness;
    private JLabel label_backgroundBrightnessValue;
    private JButton saveButton;
    private JCheckBox checkBox_overrideColor;
    private JTextField textField_overrideColorHexCode;
    private JEditorPane editorPane_helpHTML;
    private JLabel label_overlay;
    private JTextField textField_overlay;
    private JButton button_overlayFileSelector;
    private JButton button_batchMode;

    public GUI() {
        setTitle("IconCreator");
        setSize(848, 571);
        setResizable(false);
        setContentPane(panel_contentPane);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        textField_background.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>)
                            evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file : droppedFiles) {
                        textField_background.setText(file.getAbsolutePath());
                        Keys.KEY_BACKGROUND_PATH.setValue(file.getAbsolutePath());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                repaint();
            }
        });

        button_backgroundFileSelector.addActionListener(e -> {
            File file = FileUtils.openFileDialog(this, "Choose a Background", null);
            String path = (file != null ? file.getAbsolutePath() : "");
            textField_background.setText(path);
            Keys.KEY_BACKGROUND_PATH.setValue(path);
            repaint();
        });

        textField_foreground.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>)
                            evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file : droppedFiles) {
                        textField_foreground.setText(file.getAbsolutePath());
                        Keys.KEY_FOREGROUND_PATH.setValue(file.getAbsolutePath());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                repaint();
            }
        });

        button_foregroundFileSelector.addActionListener(e -> {
            File file = FileUtils.openFileDialog(this, "Choose a Foreground", null);
            String path = (file != null ? file.getAbsolutePath() : "");
            textField_foreground.setText(path);
            Keys.KEY_FOREGROUND_PATH.setValue(path);
            repaint();
        });

        textField_overlay.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>)
                            evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file : droppedFiles) {
                        textField_overlay.setText(file.getAbsolutePath());
                        Keys.KEY_OVERLAY_PATH.setValue(file.getAbsolutePath());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                repaint();
            }
        });

        button_overlayFileSelector.addActionListener(e -> {
            File file = FileUtils.openFileDialog(this, "Choose an Overlay", null);
            String path = (file != null ? file.getAbsolutePath() : "");
            textField_overlay.setText(path);
            Keys.KEY_OVERLAY_PATH.setValue(path);
            repaint();
        });

        panel_preview.setLayout(new GridLayout(0, 1));
        panel_preview.add(new DrawPanel((g2D, w, h) -> {
            DrawUtils.enableAntialiasing(g2D);
            BufferedImage bufferedImage = IconPainter.paintIcon();

            drawBase: {
                int brightness = Keys.KEY_BACKGROUND_BRIGHTNESS_VALUE.getValue();
                g2D.setColor(new Color(brightness, brightness, brightness));
                g2D.fillRect(1, 1, bufferedImage.getWidth() - 1, bufferedImage.getHeight() - 1);
            }

            g2D.drawImage(bufferedImage, 0, 0, w, h, null);

            drawFrame: {
                g2D.setColor(Color.BLACK);
                g2D.drawRect(0, 0, bufferedImage.getWidth() - 1, bufferedImage.getHeight() - 1);
            }
        }));

        checkBox_overrideColor.addActionListener(e -> {
            boolean newValue = checkBox_overrideColor.isSelected();
            Keys.KEY_OVERRIDE_COLOR_ENABLED.setValue(newValue);


            repaint();
        });

        textField_overrideColorHexCode.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                Arrays.stream(textField_overrideColorHexCode.getActionListeners()).forEach((a) -> a.actionPerformed(null));
            }
        });

        textField_overrideColorHexCode.addActionListener(e -> {
            String newValue = textField_overrideColorHexCode.getText();
            newValue = newValue.replace("#", "");

            try {
                if(newValue.length() == 6) {
                    long newLongValue = new BigInteger(newValue, 16).longValue();
                    Keys.KEY_OVERRIDE_COLOR_HEXCODE.setValue(newValue);
                    textField_overrideColorHexCode.setForeground(Color.BLACK);
                } else {
                    textField_overrideColorHexCode.setForeground(Color.RED);
                }
            } catch (Exception exception) {
                textField_overrideColorHexCode.setForeground(Color.RED);
                exception.printStackTrace();
            }
            repaint();
        });

        slider_margin.addChangeListener(e -> {
            int newValue = slider_margin.getValue();
            label_marginValue.setText(String.valueOf(newValue));
            Keys.KEY_MARGIN_VALUE.setValue(newValue);
            repaint();
        });

        slider_backgroundBrightness.addChangeListener(e -> {
            int newValue = slider_backgroundBrightness.getValue();
            label_backgroundBrightnessValue.setText(String.valueOf(newValue));
            Keys.KEY_BACKGROUND_BRIGHTNESS_VALUE.setValue(newValue);
            repaint();
        });

        saveButton.addActionListener(e -> {
            File saveFile = FileUtils.fileSaveDialog(this, "Save File", null);
            if(saveFile != null) {
                try {
                    ImageIO.write(IconPainter.paintIcon(), "PNG", saveFile);
                } catch (Exception exception) {
                    exception.printStackTrace();
                    MessageUtils.showExceptionMessage(exception);
                }
            }
        });

        editorPane_helpHTML.addHyperlinkListener(e -> {
            if(e.getEventType() != HyperlinkEvent.EventType.ACTIVATED) return;
            try {
                Desktop.getDesktop().browse(new URI(e.getURL().toString()));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        button_batchMode.addActionListener(e -> Batchmode.startBatchMode());

        textField_background.setText(Keys.KEY_BACKGROUND_PATH.getValue());
        textField_foreground.setText(Keys.KEY_FOREGROUND_PATH.getValue());
        checkBox_overrideColor.setSelected(Keys.KEY_OVERRIDE_COLOR_ENABLED.getValue());
        textField_overrideColorHexCode.setText(Keys.KEY_OVERRIDE_COLOR_HEXCODE.getValue());
        textField_overlay.setText(Keys.KEY_OVERLAY_PATH.getValue());
        slider_margin.setValue(Keys.KEY_MARGIN_VALUE.getValue());
        label_marginValue.setText(String.valueOf(Keys.KEY_MARGIN_VALUE.getValue()));
        slider_backgroundBrightness.setValue(Keys.KEY_BACKGROUND_BRIGHTNESS_VALUE.getValue());
        label_backgroundBrightnessValue.setText(String.valueOf(Keys.KEY_BACKGROUND_BRIGHTNESS_VALUE.getValue()));
        editorPane_helpHTML.setText(ResourceLoader.readResourceAsText("/helpHTML.html"));
    }

    public void repaint() {
        panel_preview.repaint();
    }

}
