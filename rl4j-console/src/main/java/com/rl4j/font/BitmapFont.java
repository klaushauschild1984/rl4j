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

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
public class BitmapFont {

    private final CodePage437 codePage437 = new CodePage437();
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
        final int index = codePage437.getOrDefault(c, (int) c);
        final int w = index * tileSize.getWidth();
        final int charX = w % bitmap.getWidth();
        final int charY = (w / bitmap.getWidth()) * tileSize.getHeight();
        context.drawImage(bitmap, new BitmapFontFilter(getTileSize(), charX, charY, foreground, background), x, y);
    }

}
