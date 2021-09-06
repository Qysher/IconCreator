package batchmode;

import config.Keys;
import core.Start;
import icon.IconPainter;
import utils.FileUtils;
import utils.MessageUtils;

import javax.imageio.ImageIO;
import java.io.File;

public class Batchmode {

    public static void startBatchMode() {
        File[] selectedFiles = FileUtils.openFilesDialog(Start.gui, "Select your foreground images", null);
        if(selectedFiles.length == 0) {
            MessageUtils.showInfoMessage("", "You have to select at least one file.");
            return;
        }

        File saveDirectory = FileUtils.selectDirectoryDialog(Start.gui, "Choose a directory where you want to save your images.", null);
        Keys.KEY_SAVE_DIRECTORY_LAST_DIRECTORY.setValue(saveDirectory.getAbsolutePath());

        for(File f : selectedFiles) {
            int count = FileUtils.firstFreeFile(saveDirectory);
            try {
                ImageIO.write(IconPainter.paintIcon(null, f.getAbsolutePath(), null),"PNG", new File(saveDirectory, count + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
                MessageUtils.showExceptionMessage(e);
            }
        }

        MessageUtils.showInfoMessage("Done.", "Done creating " + selectedFiles.length + " icon(s).");
    }

}
