package icon;

import config.Keys;
import core.Start;
import utils.ResourceUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class IconPainter {

    public static BufferedImage paintIcon() {
        BufferedImage bufferedImage = new BufferedImage(512, 512, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();

        drawBackground: {
            Image backgroundImage = ResourceUtils.loadImageFromString(Keys.KEY_BACKGROUND_PATH.getValue());
            int x = bufferedImage.getWidth() / 2 - backgroundImage.getWidth(null) / 2;
            int y = bufferedImage.getHeight() / 2 - backgroundImage.getHeight(null) / 2;
            g2D.drawImage(backgroundImage,x, y, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
        }

        drawForeground: {
            Image foregroundImage = ResourceUtils.loadImageFromString(Keys.KEY_FOREGROUND_PATH.getValue());
            if(Keys.KEY_OVERRIDE_COLOR_ENABLED.getValue()) {
                long overrideColor = Long.parseLong(Keys.KEY_OVERRIDE_COLOR_HEXCODE.getValue(), 16);
                foregroundImage = overrideColor(foregroundImage, new Color((int) overrideColor));
            }
            int margin = Keys.KEY_MARGIN_VALUE.getValue();
            int x = (bufferedImage.getWidth() / 2 - foregroundImage.getWidth(null) / 2) + margin;
            int y = (bufferedImage.getHeight() / 2 - foregroundImage.getHeight(null) / 2) + margin;
            int w = bufferedImage.getWidth() - margin * 2;
            int h = bufferedImage.getHeight() - margin * 2;
            g2D.drawImage(foregroundImage, x, y, w, h, null);
        }

        g2D.dispose();
        return bufferedImage;
    }

    public static BufferedImage overrideColor(Image image, Color color) {
        BufferedImage bufferedImage = (BufferedImage) image;
        WritableRaster raster = ((BufferedImage) image).getRaster();

        for(int x = 0 ; x < bufferedImage.getWidth() ; x++) {
            for(int y = 0 ; y < bufferedImage.getHeight() ; y++) {
                int[] pixelData = raster.getPixel(x, y, (int[]) null);
                pixelData[0] = color.getRed();
                pixelData[1] = color.getGreen();
                pixelData[2] = color.getBlue();
                raster.setPixel(x, y, pixelData);
            }
        }

        return bufferedImage;
    }

}
