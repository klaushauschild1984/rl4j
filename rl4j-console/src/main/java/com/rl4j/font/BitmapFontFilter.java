/*
 * Roguelikes 4 Java Copyright (C) 2018 Klaus Hauschild
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If
 * not, see <http://www.gnu.org/licenses/>.
 */

package com.rl4j.font;

import com.rl4j.Dimension;
import lombok.RequiredArgsConstructor;

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

@RequiredArgsConstructor
class BitmapFontFilter implements BufferedImageOp {

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
