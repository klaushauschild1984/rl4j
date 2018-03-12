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
package com.rl4j;

import com.rl4j.font.BitmapFont;
import lombok.extern.slf4j.Slf4j;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

@Slf4j
public class Console extends BackBuffer {

    private final BitmapFont bitmapFont;
    private final Character[] frontBuffer;
    private final BufferedImage renderBuffer;

    public Console(final BitmapFont bitmapFont, final Dimension size) {
        super(size);
        this.bitmapFont = bitmapFont;
        frontBuffer = new Character[size.getWidth() * size.getHeight()];
        renderBuffer = new BufferedImage( //
                size.getWidth() * bitmapFont.getTileSize().getWidth(), //
                size.getHeight() * bitmapFont.getTileSize().getHeight(),//
                BufferedImage.TYPE_INT_RGB
        );
        clear();
    }

    void flush(final Graphics2D context) {
        final long time = System.currentTimeMillis();
        boolean changed = false;
        final Graphics2D graphics = renderBuffer.createGraphics();
        for (int i = 0; i < getBackBuffer().length; i++) {
            final Character character = getBackBuffer()[i];
            if (Objects.equals(frontBuffer[i], character)) {
                continue;
            }
            frontBuffer[i] = character;
            final int x = i % getSize().getWidth();
            final int y = i / getSize().getWidth();
            bitmapFont.draw( //
                    graphics, //
                    character, //
                    x * bitmapFont.getTileSize().getWidth(), //
                    y * bitmapFont.getTileSize().getHeight() //
            );
            changed = true;
        }
        context.drawImage(renderBuffer, null, 0, 0);
        if (changed) {
            log.debug("Render time: {}ms", System.currentTimeMillis() - time);
        }
    }

}
