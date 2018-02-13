package com.rl4j;

import lombok.RequiredArgsConstructor;

import javax.imageio.ImageIO;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class BitmapFont {

    private static final Map<Character, Integer> CODEPAGE_437 = new HashMap<Character, Integer>() {
        {
            put('╔', 201);
            put('═', 205);
            put('╗', 187);
            put('║', 186);
            put('╚', 200);
            put('╝', 188);
        }
    };

    private final BufferedImage bitmap;
    private final Dimension tileSize;

    public static BitmapFont create(final InputStream inputStream, final Dimension tileSize) {
        try {
            final BufferedImage bitmap = ImageIO.read(inputStream);
            if (bitmap.getWidth() % tileSize.getWidth() != 0) {
                throw new IllegalArgumentException();
            }
            if (bitmap.getHeight() % tileSize.getHeight() != 0) {
                throw new IllegalArgumentException();
            }
            return new BitmapFont(bitmap, tileSize);
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public Dimension getTileSize() {
        return tileSize;
    }

    public void draw(final Graphics2D context, final char c, final int x, final int y, final Color foreground, final Color background) {
        final int index = CODEPAGE_437.getOrDefault(c, (int) c);
        final int w = index * tileSize.getWidth();
        final int charX = w % bitmap.getWidth();
        final int charY = (w / bitmap.getWidth()) * tileSize.getHeight();
        context.drawImage(bitmap, new BitmapFontFilter(getTileSize(), charX, charY, foreground, background), x, y);
    }

    @RequiredArgsConstructor
    private static class BitmapFontFilter implements BufferedImageOp {

        private final Dimension tileSize;
        private final int charX;
        private final int charY;
        private final Color foreground;
        private final Color background;

        @Override
        public BufferedImage filter(
                BufferedImage src, BufferedImage dest) {
            if (dest == null) {
                dest = createCompatibleDestImage(src, null);
            }

            final Graphics2D graphics = dest.createGraphics();
            graphics.setColor(background);
            graphics.fillRect(0, 0, tileSize.getWidth(), tileSize.getHeight());
            graphics.setColor(foreground);
            for (int i = 0; i < tileSize.getWidth(); i++) {
                for (int j = 0; j < tileSize.getHeight(); j++) {
                    final int value = new Color(src.getRGB(charX + i, charY + j)).getRed();
                    if (value != 0) {
                        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, value / 255f));
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }

            return dest;
        }

        @Override
        public Rectangle2D getBounds2D(BufferedImage src) {
            return new Rectangle(0, 0, tileSize.getWidth(), tileSize.getHeight());
        }

        @Override
        public BufferedImage createCompatibleDestImage(
                BufferedImage src, ColorModel destCM) {
            if (destCM == null) {
                destCM = src.getColorModel();
            }

            return new BufferedImage(destCM,
                    destCM.createCompatibleWritableRaster(
                            tileSize.getWidth(), tileSize.getHeight()),
                    destCM.isAlphaPremultiplied(), null);
        }

        @Override
        public Point2D getPoint2D(Point2D srcPt,
                                  Point2D dstPt) {
            return (Point2D) srcPt.clone();
        }

        @Override
        public RenderingHints getRenderingHints() {
            return null;
        }

    }

}
