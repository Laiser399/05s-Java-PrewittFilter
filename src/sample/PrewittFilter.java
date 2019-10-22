package sample;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.*;

public class PrewittFilter {

    public static BufferedImage make(BufferedImage source) {
        source = makeGrayImage(source);
        int width = source.getWidth(), height = source.getHeight();


        double[][] pixels = new double[width][height];
        fillCentralPixels(pixels, source);
        // TODO fill remains
        narrowValues(pixels);

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 1; i < width - 1; ++i) {
            for (int j = 1; j < height; ++j) {
                result.setRGB(i, j,
                        new Color((int) pixels[i][j], (int) pixels[i][j], (int) pixels[i][j]).getRGB());
            }
        }

        return result;
    }

    private static void fillCentralPixels(double[][] pixels, BufferedImage image) {
        for (int i = 1; i < image.getWidth() - 1; ++i) {
            for (int j = 1; j < image.getHeight() - 1; ++j) {
                double p1 = new Color(image.getRGB(i - 1, j - 1)).getRed();
                double p2 = new Color(image.getRGB(i, j - 1)).getRed();
                double p3 = new Color(image.getRGB(i + 1, j - 1)).getRed();
                double p4 = new Color(image.getRGB(i - 1, j)).getRed();

                double p6 = new Color(image.getRGB(i + 1, j)).getRed();
                double p7 = new Color(image.getRGB(i - 1, j + 1)).getRed();
                double p8 = new Color(image.getRGB(i, j + 1)).getRed();
                double p9 = new Color(image.getRGB(i + 1, j + 1)).getRed();

                double Gx = p7 + p8 + p9 - (p1 + p2 + p3);
                double Gy = p3 + p6 + p9 - (p1 + p4 + p7);
                double G = sqrt(pow(Gx, 2) + pow(Gy, 2));
                pixels[i][j] = G;
            }
        }
    }

    private static void narrowValues(double[][] values) {
        for (int i = 0; i < values.length; ++i)
            for (int j = 0; j < values[i].length; ++j)
                values[i][j] = min(255, values[i][j]);
    }

    private static BufferedImage makeGrayImage(BufferedImage sourceImage) {
        BufferedImage grayImage = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(),
                BufferedImage.TYPE_BYTE_GRAY);

        Graphics2D graphics = grayImage.createGraphics();
        graphics.drawImage(sourceImage, 0, 0, sourceImage.getWidth(), sourceImage.getHeight(),
                null);
        graphics.dispose();

        return grayImage;
    }


}
