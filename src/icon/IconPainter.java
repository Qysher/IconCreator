package icon;

import config.Keys;
import core.Start;
import utils.DrawUtils;
import utils.ResourceUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class IconPainter {

    public static BufferedImage paintIcon() {
        return IconPainter.paintIcon(
                Keys.KEY_BACKGROUND_PATH.getValue(),
                Keys.KEY_FOREGROUND_PATH.getValue(),
                Keys.KEY_OVERLAY_PATH.getValue()
        );
    }

    public static BufferedImage paintIcon(String backgroundPath, String foregroundPath, String overlayPath) {
        backgroundPath = (backgroundPath == null ? Keys.KEY_BACKGROUND_PATH.getValue() : backgroundPath);
        foregroundPath = (foregroundPath == null ? Keys.KEY_FOREGROUND_PATH.getValue() : foregroundPath);
        overlayPath = (overlayPath == null ? Keys.KEY_OVERLAY_PATH.getValue() : overlayPath);

        BufferedImage bufferedImage = new BufferedImage(512, 512, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
        DrawUtils.enableAntialiasing(g2D);

        drawBackground: {
            Image backgroundImage = ResourceUtils.loadImageFromString(backgroundPath);
            int x = bufferedImage.getWidth() / 2 - backgroundImage.getWidth(null) / 2;
            int y = bufferedImage.getHeight() / 2 - backgroundImage.getHeight(null) / 2;
            g2D.drawImage(backgroundImage,x, y, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
        }

        drawForeground: {
            Image foregroundImage = ResourceUtils.loadImageFromString(foregroundPath);
            foregroundImage = rescaleIcon(foregroundImage, bufferedImage.getWidth(), bufferedImage.getHeight());
            if(Keys.KEY_OVERRIDE_COLOR_ENABLED.getValue()) {
                long overrideColor = Long.parseLong(Keys.KEY_OVERRIDE_COLOR_HEXCODE.getValue(), 16);
                foregroundImage = overrideColor(foregroundImage, new Color((int) overrideColor));
            }
            int margin = Keys.KEY_MARGIN_VALUE.getValue();
            int x = (bufferedImage.getWidth() / 2 - foregroundImage.getWidth(null) / 2) + margin;
            int y = (bufferedImage.getHeight() / 2 - foregroundImage.getHeight(null) / 2) + margin;
            int w = foregroundImage.getWidth(null) - margin * 2;
            int h = foregroundImage.getHeight(null) - margin * 2;
            g2D.drawImage(foregroundImage, x, y, w, h, null);
        }

        drawOverlay: {
            Image backgroundImage = ResourceUtils.loadImageFromString(overlayPath);
            backgroundImage = rescaleIcon(backgroundImage, bufferedImage.getWidth(), bufferedImage.getHeight());
            int x = bufferedImage.getWidth() / 2 - backgroundImage.getWidth(null) / 2;
            int y = bufferedImage.getHeight() / 2 - backgroundImage.getHeight(null) / 2;
            g2D.drawImage(backgroundImage,x, y, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
        }

        g2D.dispose();
        return bufferedImage;
    }

    public static BufferedImage rescaleIcon(Image image, int width, int height) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        bufferedImage.getGraphics().drawImage(image, 1, 1, width, height, null);
        bufferedImage.getGraphics().dispose();
        return bufferedImage;
    }

    public static BufferedImage convertImageToARGBImage(Image image) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        bufferedImage.getGraphics().drawImage(image, 1, 1, null);
        bufferedImage.getGraphics().dispose();
        return bufferedImage;
    }

    public static BufferedImage overrideColor(Image image, Color color) {
        BufferedImage bufferedImage = convertImageToARGBImage(image);
        WritableRaster raster = bufferedImage.getRaster();

        try {
            for(int x = 0 ; x < bufferedImage.getWidth() ; x++) {
                for(int y = 0 ; y < bufferedImage.getHeight() ; y++) {
                    int[] pixelData = raster.getPixel(x, y, (int[]) null);
                    pixelData[0] = color.getRed();
                    pixelData[1] = color.getGreen();
                    pixelData[2] = color.getBlue();
                    raster.setPixel(x, y, pixelData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bufferedImage;
    }

}
